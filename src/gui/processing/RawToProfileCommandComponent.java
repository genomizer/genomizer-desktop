package gui.processing;

import gui.CustomButtonFactory;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import util.IconFactory;

@SuppressWarnings("serial")
public class RawToProfileCommandComponent extends  CommandComponent {

    private String[] fileNames;
    private String[] genomeReleases;

    private JButton newFileRowButton;
    private JPanel buttonPanel;
    private int fileRowCount = 0;

    private HashMap<JButton, JPanel> removeButtonToPanelMap = new HashMap<JButton, JPanel>();
    private CopyOnWriteArrayList<RawToProfileFileRow> fileRowList;
    private Stack<JPanel> buttonPanelStack = new Stack<JPanel>();

    public RawToProfileCommandComponent(String commandName, String[] fileNames,
            String[] genomeReleases) {

        super(commandName);

        this.fileNames = fileNames;
        this.genomeReleases = genomeReleases;

        this.setBorder(new TitledBorder(commandName));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        fileRowList = new CopyOnWriteArrayList<RawToProfileFileRow>();

        addFileRow();
    }

    private void addFileRow() {
        fileRowCount++;
        JPanel fileRowPanel = new JPanel();
        fileRowPanel.setLayout(new FlowLayout());

        RawToProfileFileRow fileRow = new RawToProfileFileRow(fileNames, genomeReleases);
        fileRowList.add(fileRow);

        JButton removeButton = buildRemoveButton();
        removeButton.addActionListener(new RemoveButtonListener());
        removeButtonToPanelMap.put(removeButton, fileRowPanel);

       if (newFileRowButton != null) {
            buttonPanel.remove(newFileRowButton);
            buttonPanel.revalidate();
            buttonPanel.repaint();
       }
        newFileRowButton = buildNewRowButton();
        newFileRowButton.addActionListener(new NewFileRowButtonListener());

        JPanel removeButtonPadding = new JPanel();
        JPanel newFileRowButtonPadding = new JPanel();

        removeButtonPadding.setPreferredSize(new Dimension(17, 17));

        buttonPanel = new JPanel(new GridLayout(2, 2));
        buttonPanel.add(removeButtonPadding);
        buttonPanel.add(newFileRowButtonPadding);
        buttonPanel.add(removeButton);
        buttonPanel.add(newFileRowButton);
        buttonPanelStack.push(buttonPanel);

        fileRowPanel.add(fileRow);
        fileRowPanel.add(buttonPanel);

        if (fileRowCount < 2) {
            removeButton.setEnabled(false);
            removeButton.setIcon(null);
        }

        this.add(fileRowPanel);
        this.repaint();
        this.revalidate();

    }


    private JButton buildRemoveButton() {
        ImageIcon icon = IconFactory.getMinusIcon(15, 15);
        ImageIcon hoverIcon = IconFactory.getMinusIcon(17, 17);
        int width = 17;
        int height = 25;
        String tooltip = "Remove this file row";
        return CustomButtonFactory.makeCustomButton(icon, hoverIcon, width,
                height, tooltip);
    }


    private void removeFileRow(Object source) {
        JPanel fileRowPanelToRemove = removeButtonToPanelMap.get(source);
        this.remove(fileRowPanelToRemove);
        removeFileRowWithParent(fileRowPanelToRemove);

        if (buttonPanelStack.peek().isAncestorOf((Component) source) ||  buttonPanelStack.size()==1) {
            removeButtonToPanelMap.remove(source);

            buttonPanelStack.pop();

            buttonPanel = buttonPanelStack.peek();
            buttonPanel.add(newFileRowButton);
        }else{
            buttonPanelStack.remove(((Component) source).getParent());
        }



        this.repaint();
        this.revalidate();
        fileRowCount--;

    }

    private void removeFileRowWithParent(JPanel parent) {
        Iterator<RawToProfileFileRow> fileRowIterator = fileRowList.iterator();
        while(fileRowIterator.hasNext()) {
            RawToProfileFileRow currentRow = fileRowIterator.next();
            if(parent.isAncestorOf(currentRow)) {
                fileRowList.remove(currentRow);
            }
        }
    }

    private static JButton buildNewRowButton() {
        ImageIcon icon = IconFactory.getPlusIcon(15, 15);
        ImageIcon hoverIcon = IconFactory.getPlusIcon(17, 17);
        int width = 17;
        int height = 25;
        String tooltip = "Add a new file row";
        return CustomButtonFactory.makeCustomButton(icon, hoverIcon, width,
                height, tooltip);
    }
    @Override
    public ProcessParameters[] getProcessParameters() {

        RawToProfileParameters[] parameters = new RawToProfileParameters[fileRowList.size()];
        Iterator<RawToProfileFileRow> fileRowIterator = fileRowList.iterator();
        for(int i = 0; fileRowIterator.hasNext(); i++) {
            RawToProfileFileRow currentFileRow = fileRowIterator.next();
            parameters[i] = buildProcessParameters(currentFileRow);
        }

        return parameters;
    }

    private RawToProfileParameters buildProcessParameters(RawToProfileFileRow fileRow) {
        String infile = fileRow.getInFile();
        String outfile = fileRow.getOutFile();
        String flags = fileRow.getFlags();
        String genomeRelease = fileRow.getGenomeRelease();
        boolean saveSam = false;
        return new RawToProfileParameters(infile, outfile, flags, genomeRelease, saveSam);
    }


    private class RemoveButtonListener implements ActionListener {

        @Override
        public void actionPerformed( ActionEvent e) {
            removeFileRow(e.getSource());
        }

    }

    private class NewFileRowButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            addFileRow();
        }

    }




}
