package gui.processing;

import gui.CustomButtonFactory;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import util.IconFactory;

@SuppressWarnings("serial")
public class RawToProfileCommandComponent extends  CommandComponent {

    private String[] fileNames;
    private String[] genomeReleases;

    private JButton newFileRowButton;
    private int fileRowCount = 0;

    private HashMap<JButton, JPanel> removeButtonToPanelMap = new HashMap<JButton, JPanel>();
    private ArrayList<RawToProfileFileRow> fileRowList;

    public RawToProfileCommandComponent(String commandName, String[] fileNames,
            String[] genomeReleases) {

        super(commandName);

        this.fileNames = fileNames;
        this.genomeReleases = genomeReleases;

        this.setBorder(new TitledBorder(commandName));
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        fileRowList = new ArrayList<RawToProfileFileRow>();

        addFileRow();
    }

    private void addFileRow() {
        fileRowCount++;
        JPanel fileRowPanel = new JPanel();
        fileRowPanel.setLayout(new FlowLayout());

        RawToProfileFileRow fileRow = new RawToProfileFileRow(fileNames, genomeReleases);
        fileRowList.add(fileRow);

        JPanel removeButtonPanel = new JPanel(new GridLayout(2, 1));
        JButton removeButton = buildRemoveButton();
        removeButton.addActionListener(new RemoveButtonListener());
        removeButtonToPanelMap.put(removeButton, fileRowPanel);

        JPanel paddingPanel = new JPanel();
        paddingPanel.setPreferredSize(new Dimension(17, 17));
        removeButtonPanel.add(paddingPanel);
        removeButtonPanel.add(removeButton);

        fileRowPanel.add(fileRow);
        fileRowPanel.add(removeButtonPanel);

        if (fileRowCount < 2) {
            removeButton.setEnabled(false);
            removeButton.setIcon(null);
        }

        this.add(fileRowPanel);
        addNewFileRowButton();
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

    private JButton buildNewRowButton() {
        ImageIcon icon = IconFactory.getPlusIcon(15, 15);
        ImageIcon hoverIcon = IconFactory.getPlusIcon(17, 17);
        int width = 17;
        int height = 25;
        String tooltip = "Add a new file row";
        return CustomButtonFactory.makeCustomButton(icon, hoverIcon, width,
                height, tooltip);
    }

    private void removeFileRow(Object source) {
        JPanel fileRowToRemove = removeButtonToPanelMap.get(source);
        this.remove(fileRowToRemove);
        fileRowList.remove(fileRowToRemove);
        removeButtonToPanelMap.remove(source);
        this.repaint();
        this.revalidate();
        fileRowCount--;

    }

    private void addNewFileRowButton() {
        if (newFileRowButton != null) {
            this.remove(newFileRowButton);
        }
        newFileRowButton = buildNewRowButton();
        newFileRowButton.addActionListener(new NewFileRowButtonListener());
        this.add(newFileRowButton);
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
        public void actionPerformed(ActionEvent e) {
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
