package gui.processing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import util.ProcessFeedbackData;

public class ProcessInfoPanel extends JPanel {

    private JScrollPane scrollProcessList = new JScrollPane();
    private JTree tree;
    private final JButton processFeedbackButton = new JButton(
            "Get process feedback");
    private final JButton abortProcessButton = new JButton("Abort process");

    /**
     * Initiates the east panel in the process tabs borderlayout.
     */
    public ProcessInfoPanel() {

        super();

        this.setBorder(BorderFactory
                .createTitledBorder("Processing Information"));
        this.setLayout(new BorderLayout());
        JPanel procInfoSouthPanel = new JPanel(new FlowLayout());
        JPanel procInfoCenterPanel = new JPanel(new BorderLayout());

        this.add(procInfoSouthPanel, BorderLayout.SOUTH);
        this.add(procInfoCenterPanel, BorderLayout.CENTER);
        scrollProcessList.setPreferredSize(new Dimension(300, 700));
        procInfoCenterPanel.add(scrollProcessList, BorderLayout.CENTER);

        procInfoSouthPanel.add(Box.createHorizontalStrut(35));
        procInfoSouthPanel.add(processFeedbackButton);
        procInfoSouthPanel.add(abortProcessButton);
        procInfoSouthPanel.add(Box.createHorizontalStrut(35));

    }

    private JTree createFeedbackTree(ArrayList<String> experiments,
            ProcessFeedbackData[] processFeedbackData) {

        // create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(
                "<html><b>Current processes</b></html>");

        // create the child nodes
        for (String s : experiments) {
            DefaultMutableTreeNode experimentNode = new DefaultMutableTreeNode(
                    "<html><b>ExpID</b>: " + s + "</html>");
            root.add(experimentNode);
            for (ProcessFeedbackData p : processFeedbackData) {
                if (p.experimentName.equals(s)) {
                    DefaultMutableTreeNode processNode = new ProcessNode(p);
                    experimentNode.add(processNode);
                    processNode.add(new DefaultMutableTreeNode(
                            "<html><b>Author</b>: " + p.author + "</html>"));
                    DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(
                            "<html><b>Files</b>: " + "Files" + "</html>");
                    for (String files : p.outputFiles) {
                        fileNode.add(new DefaultMutableTreeNode(
                                "<html><b>File</b>: " + files + "</html>"));
                    }
                    processNode.add(fileNode);
                    processNode.add(new DefaultMutableTreeNode(
                            "<html><b>Status</b>: " + p.status + "</html>"));
                    Format format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
                    String timeAdded = "Not added";
                    String timeStarted = "Not started";
                    String timeFinished = "Not finished";
                    if (p.timeAdded != 0) {
                        timeAdded = format.format(new Date(p.timeAdded))
                                .toString();
                    }
                    if (p.timeStarted != 0) {
                        timeStarted = format.format(new Date(p.timeStarted))
                                .toString();
                    }
                    if (p.timeFinished != 0) {
                        timeFinished = format.format(new Date(p.timeFinished))
                                .toString();
                    }
                    processNode
                            .add(new DefaultMutableTreeNode(
                                    "<html><b>TimeAdded</b>: " + timeAdded
                                            + "</html>"));
                    processNode.add(new DefaultMutableTreeNode(
                            "<html><b>TimeStarted</b>: " + timeStarted
                                    + "</html>"));
                    processNode.add(new DefaultMutableTreeNode(
                            "<html><b>TimeFinished</b>: " + timeFinished
                                    + "</html>"));
                }
            }
        }

        // create the tree by passing in the root node
        JTree tree = new JTree(root);
        tree.setRootVisible(false);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree
                .getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);

        return tree;
    }

    /**
     * Adds button listener to processFeedbackButton.
     *
     * @see controller.ProcessTabController#processFeedbackListener()
     * @param listener
     */
    public void addProcessFeedbackListener(ActionListener listener) {
        processFeedbackButton.addActionListener(listener);
    }

    /**
     * Adds button listener to abortProcessButton.
     *
     * @see controller.ProcessTabController#abortProcessListener()
     * @param listener
     */
    public void addAbortProcessListener(ActionListener listener) {
        abortProcessButton.addActionListener(listener);
    }

    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData) {

        System.out.println(processFeedbackData);
        ArrayList<String> experiments = new ArrayList<String>();

        for (ProcessFeedbackData p : processFeedbackData) {
            if (!experiments.contains(p.experimentName)) {
                experiments.add(p.experimentName);
            }
        }

        this.tree = createFeedbackTree(experiments, processFeedbackData);

        scrollProcessList.setViewportView(tree);

    }

    public ProcessFeedbackData getSelectedProcess() {

        TreePath[] tps = this.tree.getSelectionPaths();

        if (tps.length != 1) {
            return null;
        }

        TreePath tp = tps[0];

        Object nod = tp.getLastPathComponent();

        while ( nod != null ){
            if ( nod instanceof ProcessNode ) {
                return ((ProcessNode)nod).getProcessFeedbackData();
            }
            nod = ((DefaultMutableTreeNode)nod).getParent();
        }

        return null;

    }


    private class ProcessNode extends DefaultMutableTreeNode{
        /**
         *
         */
        private static final long serialVersionUID = -8248950057225139957L;

        private ProcessFeedbackData p;

        private ProcessNode(ProcessFeedbackData p){
            super("<html><b>ProcessID</b>: " + p.PID
                    + "</html>");
            this.p = p;
        }

        public ProcessFeedbackData getProcessFeedbackData(){
            return p;
        }
    }
}


