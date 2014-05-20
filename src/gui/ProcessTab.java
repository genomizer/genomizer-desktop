package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;
import util.TreeTable;

/**
 * Visual presentation of the process tab.
 *
 * @author c11ann
 */
public class ProcessTab extends JPanel {

    private static final long serialVersionUID = -2830290705724588252L;

    private final JList<CheckListItem> fileList = new JList<CheckListItem>();
    private final JList<CheckListItem> scheduleList = new JList<CheckListItem>();

    private final JPanel convPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JPanel filesPanel = new JPanel(new BorderLayout());
    private final JPanel scheduleProcPanel = new JPanel(new BorderLayout());
    private final JPanel genProfileDataPanel = new JPanel(new BorderLayout());
    private final JPanel genRegionDataPanel = new JPanel(new BorderLayout());
    private final JPanel convertFilesPanel = new JPanel(new BorderLayout());
    private JPanel procInfoPanel = new JPanel(new BorderLayout());
    private final JPanel rawToProfileMenuPanel = new JPanel();
    private final JPanel timePanel = new JPanel();
    private final JPanel middlePanel = new JPanel(new GridLayout(3, 1));
    private final JPanel westPanel = new JPanel(new GridLayout(2, 1));

    private final JPanel flagsPanel = new JPanel();
    private final JPanel genomeReleasePanel = new JPanel();
    private final JPanel windowSizePanel = new JPanel();
    private final JPanel smoothTypePanel = new JPanel();
    private final JPanel stepPositionPanel = new JPanel();
    private final JPanel stepSizePanel = new JPanel();
    private final JPanel checkBoxPanel = new JPanel();
    private final JPanel createRegTabPanel = new JPanel();
    private final JPanel convWigTabPanel = new JPanel();
    private final JPanel convTabpanel = new JPanel(new BorderLayout());
    private final JPanel lowerCheckBoxPanel = new JPanel();
    private final JPanel upperCheckBoxPanel = new JPanel();

    private final JTextArea textArea = new JTextArea();
    private final JTextArea genProfArea = new JTextArea();
    private final JTextArea genRegArea = new JTextArea();
    private final JTextArea timeArea = new JTextArea();
    private final JTextArea convertArea = new JTextArea();

    private final JTextField flags = new JTextField();
    private final JTextField smoothWindowSize = new JTextField();
    private final JComboBox<String> smoothType = new JComboBox<String>();
    private final JTextField stepPosition = new JTextField();
    private final JTextField stepSize = new JTextField();

    // RATIO CALC
    private final JTextField inputReads = new JTextField();
    private final JTextField chromosome = new JTextField();
    private final JTextField ratioWindowSize = new JTextField();
    private final JTextField ratioStepPosition = new JTextField();
    private final JCheckBox ratioPrintMean = new JCheckBox("Print mean");
    private final JCheckBox ratioPrintZeros = new JCheckBox("Print zeros");

    private final JScrollPane scrollSchedule = new JScrollPane();
    private final JScrollPane scrollConvert = new JScrollPane();
    private final JScrollPane scrollRegion = new JScrollPane();
    private final JScrollPane scrollProfile = new JScrollPane();
    private final JScrollPane scrollProcessList = new JScrollPane();
    private final JScrollPane scrollFiles = new JScrollPane();

    private final JButton convertButton = new JButton("Convert to WIG");
    private final JButton profileButton = new JButton("Create profile data");
    private final JButton regionButton = new JButton("Create region data");
    private final JButton ratioCalcButton = new JButton("Use ratio calculation");
    private JButton processFeedbackButton;
    private JButton addToFileListButton;
    // private final JCheckBox scheduleButton = new JCheckBox(
    // "Schedule files");

    private final JCheckBox printMean = new JCheckBox("Print mean");
    private final JCheckBox printZeros = new JCheckBox("Print zeros");
    private final JCheckBox stepSizeBox = new JCheckBox("Step size");
    private final JCheckBox ouputSGR = new JCheckBox("SGR Format");
    private final JCheckBox outputGFF = new JCheckBox("GFF Format");
    private final JComboBox<String> genomeFile = new JComboBox<String>();
    private final JComboBox<String> single = new JComboBox<String>();
    private final JComboBox<String> ratioSmoothType = new JComboBox<String>();
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
    public final JOptionPane ratioPopup = new JOptionPane();
    private ArrayList<String> genomeReleaseFiles;
    private String[] bowtieParameters = new String[4];
    private ProcessFeedbackData[] processFeedbackData;
    private TreeTable table;
    private ArrayList<ExperimentData> experimentData;

    private ActionListener procFeedbackListener;

    public ProcessTab() {
        processFeedbackData = new ProcessFeedbackData[0];
        setPreferredSize(new Dimension(1225, 725));
        setMinimumSize(new Dimension(20000, 20000));
        this.setLayout(new BorderLayout());
        initPanels();
        table = new TreeTable();
    }

    /**
     * Initiates all the process tabs panels.
     */
    private void initPanels() {

        addNorthPanel();
        addWestPanels();
        addMiddlePanel();
        addProcessInfoPanel();

        addTimePanel();
        addConvertTextArea();
        initFileList();

        ArrayList<String> ratioSmooth = new ArrayList<String>();
        /* TEST */
        ratioSmooth.add("1");
        ratioSmooth.add("0");

        ratioSmoothType.addItem(ratioSmooth.get(0));
        ratioSmoothType.addItem(ratioSmooth.get(1));
        smoothType.addItem(ratioSmooth.get(0));
        smoothType.addItem(ratioSmooth.get(1));

        /* TEST */
        ArrayList<String> comboSingle = new ArrayList<String>();
        /* TEST */
        comboSingle.add("single");
        comboSingle.add("double");

        single.addItem(comboSingle.get(0));
        single.addItem(comboSingle.get(1));

        /* TEST */
        ArrayList<String> gFiles = new ArrayList<String>();
        /* TEST */
        gFiles.add("");
        /* TEST */
        setGenomeReleaseFiles(gFiles);

        initBowtieParameters();
        writeToTimePanel();
        setUnusedRatioPar();

    }

    /**
     * Initiates the north panel in the process tabs borderlayout.
     */
    private void addNorthPanel() {
        rawToProfileMenuPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        rawToProfileMenuPanel.setBorder(BorderFactory
                .createTitledBorder("Process"));
        this.add(rawToProfileMenuPanel, BorderLayout.NORTH);
        addOptionsToRawToProfileTab();
        enableButtons();

    }

    /**
     * Initiates the west panel in the process tabs borderlayout.
     */
    private void addWestPanels() {

        this.add(westPanel, BorderLayout.WEST);
        filesPanel.setBorder(BorderFactory.createTitledBorder("Files"));
        addFilesScheduleToWestPanel();
        addScheduleProcPanel();

    }

    /**
     * Initiates the center panel in the process tabs borderlayout.
     */
    private void addMiddlePanel() {

        this.add(middlePanel, BorderLayout.CENTER);
        addGenProfileDataPanel();
        addGenRegionDataPanel();
        addConvertFilesPanel();

    }

    /**
     * Initiates the east panel in the process tabs borderlayout.
     */
    private void addProcessInfoPanel() {
        procInfoPanel = new JPanel();
        procInfoPanel.setBorder(BorderFactory
                .createTitledBorder("Processing Information"));
        procInfoPanel.setLayout(new BorderLayout());
        JPanel procInfoSouthPanel = new JPanel(new FlowLayout());
        JPanel procInfoCenterPanel = new JPanel(new BorderLayout());
        this.add(procInfoPanel, BorderLayout.EAST);
        procInfoPanel.add(procInfoSouthPanel, BorderLayout.SOUTH);
        procInfoPanel.add(procInfoCenterPanel, BorderLayout.CENTER);
        scrollProcessList.setPreferredSize(new Dimension(200, 700));
        procInfoCenterPanel.add(scrollProcessList, BorderLayout.CENTER);
        // create the root node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(
                "<html><b>Current processes</b></html>");
        // create the child nodes
        for (int i = 0; i < processFeedbackData.length; i++) {
            ProcessFeedbackData data = processFeedbackData[i];
            DefaultMutableTreeNode procNode = new DefaultMutableTreeNode(
                    "<html><b>Process " + i + "</b></html>");
            root.add(procNode);
            DefaultMutableTreeNode expNode = new DefaultMutableTreeNode(
                    "<html><u>ExpID</u>: " + data.experimentName + "</html>");
            procNode.add(expNode);
            DefaultMutableTreeNode authorNode = new DefaultMutableTreeNode(
                    "<html><u>Author</u>: " + data.author + "</html>");
            procNode.add(authorNode);
            DefaultMutableTreeNode statusNode = new DefaultMutableTreeNode(
                    "<html><u>Status</u>: " + data.status + "</html>");
            procNode.add(statusNode);
            DefaultMutableTreeNode addedNode = new DefaultMutableTreeNode(
                    "<html><u>Time Added</u>: " + data.timeAdded + "</html>");
            procNode.add(addedNode);
            DefaultMutableTreeNode startedNode = new DefaultMutableTreeNode(
                    "<html><u>Time Started</u>: " + data.timeStarted
                            + "</html>");
            procNode.add(startedNode);
            DefaultMutableTreeNode finishedNode = new DefaultMutableTreeNode(
                    "<html><u>Time Finished</u>: " + data.timeFinished
                            + "</html>");
            procNode.add(finishedNode);
            DefaultMutableTreeNode outputNode = new DefaultMutableTreeNode(
                    "<html><b>Output files</b></html>");
            procNode.add(outputNode);
            for (int j = 0; j < data.outputFiles.length; j++) {
                DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(
                        "<html><u>File Name</u>: " + data.outputFiles[j]
                                + "</html>");
                outputNode.add(fileNode);
            }

        }
        // create the tree by passing in the root node
        JTree tree = new JTree(root);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree
                .getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
        scrollProcessList.setViewportView(tree);
        // processFeedbackButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getRefreshIcon(30, 30),
        // IconFactory.getRefreshHoverIcon(32, 32), 32, 32,
        // "Get process information from server");
        processFeedbackButton = new JButton("Get process feedback");
        processFeedbackButton.addActionListener(procFeedbackListener);
        // addToFileListButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getAddToListIcon(30, 30),
        // IconFactory.getAddToListHoverIcon(32, 32), 32, 32,
        // "Add selected files to list");
        addToFileListButton = new JButton("Add to file list");
        addToFileListButton.setEnabled(false);
        procInfoSouthPanel.add(addToFileListButton);
        procInfoSouthPanel.add(Box.createHorizontalStrut(35));
        procInfoSouthPanel.add(processFeedbackButton);
    }

    /**
     * Initiates the convertFilesPanel in the center panel.
     */
    private void addConvertFilesPanel() {
        middlePanel.add(convertFilesPanel, BorderLayout.CENTER);
        convertFilesPanel.setBorder(BorderFactory
                .createTitledBorder("Convert Files"));
    }

    /**
     * Initiates the genRegionDataPanel in the center panel.
     */
    private void addGenRegionDataPanel() {
        middlePanel.add(genRegionDataPanel, BorderLayout.CENTER);
        genRegionDataPanel.setBorder(BorderFactory
                .createTitledBorder("Generate Region Data"));
        addScrollGenRegionData();
    }

    /**
     * Adds scrollpane to genRegionData in the genRedionDataPanel.
     */
    private void addScrollGenRegionData() {
        genRegionDataPanel.add(scrollRegion, BorderLayout.CENTER);
        scrollRegion.setViewportView(genRegArea);
        genRegArea.setEditable(false);
        genRegionDataPanel.add(textArea);
    }

    /**
     * Initiates the genProfileDataPanel in the center panel.
     */
    private void addGenProfileDataPanel() {
        middlePanel.add(genProfileDataPanel, BorderLayout.CENTER);
        genProfileDataPanel.setBorder(BorderFactory
                .createTitledBorder("Generate Profile Data"));
        genProfileDataPanel.add(scrollProfile);
        scrollProfile.setViewportView(genProfArea);
        genProfArea.setEditable(false);
    }

    /**
     * Initiates timePanel to south in the process tabs borderlayout.
     */
    private void addTimePanel() {
        this.add(timePanel, BorderLayout.SOUTH);
        timePanel.setPreferredSize(new Dimension(300, 30));
    }

    /**
     * Initiates the scrollSchedule in scheduleProcPanel.
     */
    private void addScheduleProcPanel() {
        scheduleProcPanel.setBorder(BorderFactory
                .createTitledBorder("Scheduled Processing"));
        scheduleProcPanel.add(scrollSchedule);
        scrollSchedule.setViewportView(scheduleList);
    }

    /**
     * Writes text to convertArea. The user gets a visual message whether or not
     * the conversion succeeded.
     */
    private void addConvertTextArea() {

        convertFilesPanel.add(scrollConvert);
        scrollConvert.setViewportView(convertArea);
        convertArea.setEditable(false);
    }

    /**
     * Initiates filesPanel in westPanel.
     */
    private void addFilesScheduleToWestPanel() {
        westPanel.add(filesPanel);
        filesPanel.add(scrollFiles);

        scrollFiles.setViewportView(fileList);
        westPanel.add(scheduleProcPanel);
    }

    /**
     * Initiates all checkboxes and textfields in raw to profile tab.
     */
    private void addOptionsToRawToProfileTab() {
        addPanelsToRawToProfileTab();
        addFlagsToConv();
        addGenomeFileToConv();
        addSmoothTypeToConv();
        addSmoothWindowSizeToConv();
        addStepPositionToConv();
        addStepSizeToConv();
        addPrintMeanToConv();
        addPrintZeroToConv();
    }

    /**
     * Initiates all panels to the raw to profile tab.
     */
    private void addPanelsToRawToProfileTab() {
        rawToProfileMenuPanel.setLayout(new BorderLayout());
        rawToProfileMenuPanel.add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addTab("Create profile data", null, convTabpanel, null);
        convTabpanel.add(convPanel, BorderLayout.NORTH);
        convPanel.add(flagsPanel);
        flagsPanel.setBorder(BorderFactory.createTitledBorder("Bowtie flags"));
        convPanel.add(genomeReleasePanel);
        genomeReleasePanel.setBorder(BorderFactory
                .createTitledBorder("Genome release files"));
        convPanel.add(windowSizePanel);
        windowSizePanel.setBorder(BorderFactory
                .createTitledBorder("Window size"));
        convPanel.add(smoothTypePanel);
        smoothTypePanel.setBorder(BorderFactory
                .createTitledBorder("Smooth type"));
        convPanel.add(stepPositionPanel);
        stepPositionPanel.setBorder(BorderFactory
                .createTitledBorder("Step position"));
        convPanel.setBorder(null);
        checkBoxPanel.setAlignmentY(1.0f);
        checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Checkboxes"));
        convPanel.add(checkBoxPanel);

        checkBoxPanel.setLayout(new GridLayout(2, 0));
        lowerCheckBoxPanel.setLayout(new GridBagLayout());
        upperCheckBoxPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 0;
        checkBoxPanel.add(upperCheckBoxPanel);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.gridx = 0;
        gbc.gridy = 1;
        checkBoxPanel.add(lowerCheckBoxPanel);

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 0, 0, 0);
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        upperCheckBoxPanel.add(printMean, gbc2);

        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 0, 0, 0);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        upperCheckBoxPanel.add(printZeros, gbc2);

        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 0, 0, 0);
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        upperCheckBoxPanel.add(stepSizeBox, gbc2);

        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 0, 0, 5);
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        lowerCheckBoxPanel.add(outputGFF, gbc2);

        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 5, 0, 0);
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        lowerCheckBoxPanel.add(ouputSGR, gbc2);

        checkBoxPanel.setPreferredSize(new Dimension(295, 96));

        convPanel.add(stepSizePanel);
        stepSizePanel.setBorder(BorderFactory.createTitledBorder("Step size"));

        tabbedPane.addTab("Convert to WIG", null, convWigTabPanel, null);

        tabbedPane.addTab("Create region data", null, createRegTabPanel, null);
    }

    /**
     * Initiates the flag text field in raw to profile tab
     */
    private void addFlagsToConv() {
        flagsPanel.add(flags);
        flags.setBorder(null);
        flags.setText("-a -m 1 --best -p 10 -v 2 -q -S");
        flags.setPreferredSize(new Dimension(180, 45));
    }

    /**
     * Initiates the genome files combobox in raw to profile tab
     */
    private void addGenomeFileToConv() {
        genomeReleasePanel.add(genomeFile);
        genomeFile.setPreferredSize(new Dimension(180, 45));
        genomeFile.setBorder(null);
    }

    /**
     * Initiates the window size text field in raw to profile tab
     */
    private void addSmoothWindowSizeToConv() {
        windowSizePanel.add(smoothWindowSize);
        smoothWindowSize.setPreferredSize(new Dimension(70, 45));
        smoothWindowSize.setBorder(null);
        smoothWindowSize.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the smooth type text field in raw to profile tab
     */
    private void addSmoothTypeToConv() {
        smoothTypePanel.add(smoothType);
        smoothType.setPreferredSize(new Dimension(70, 45));
        smoothType.setBorder(null);
        // smoothType.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the step position text field in raw to profile tab
     */
    private void addStepPositionToConv() {
        stepPositionPanel.add(stepPosition);
        stepPosition.setPreferredSize(new Dimension(75, 45));
        stepPosition.setBorder(null);
        stepPosition.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the step size text field in raw to profile tab
     */
    private void addStepSizeToConv() {
        stepSizePanel.add(stepSize);
        stepSize.setPreferredSize(new Dimension(70, 45));
        stepSize.setBorder(null);
        stepSize.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the print mean checkbox in raw to profile tab
     */
    private void addPrintMeanToConv() {
        printMean.setPreferredSize(new Dimension(110, 55));
        printMean.setBorder(null);
        stepSizeBox.setSelected(true);

        // TODO Flytta lyssnare
        stepSizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                if (stepSizeBox.isSelected()) {
                    stepSize.setEnabled(true);
                    stepSize.setText("10");
                } else {
                    stepSize.setEnabled(false);
                    stepSize.setText("");
                }
            }
        });
        stepSizeBox.setPreferredSize(new Dimension(110, 55));
    }

    /**
     * Initiates the print zeros checkbox in raw to profile tab
     */
    private void addPrintZeroToConv() {
        printZeros.setPreferredSize(new Dimension(110, 55));
        printZeros.setBorder(null);
    }

    /**
     * Initiates the all buttons.
     */
    private void enableButtons() {
        createRegTabPanel.add(regionButton);
        regionButton.setEnabled(false);
        convWigTabPanel.add(convertButton);
        convertButton.setEnabled(false);
        convTabpanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(ratioCalcButton);
        buttonPanel.add(profileButton);
        // scheduleProcPanel.add(scheduleButton);

    }

    /**
     * Gets all the parameters that the user has written in the raw to profile
     * tab when trying to create profile data from raw data.
     *
     * @return String[] - bowtieParameters
     */
    public String[] getBowtieParameters() {
        return this.bowtieParameters;
    }

    /**
     * Sets bowtieParameters from all the parameters in raw to profile tab.
     */
    public void setBowtieParameters() {

        bowtieParameters[0] = getTextFromFlags(); // "-a -m 1 --best -p 10 -v 2";
        bowtieParameters[1] = getTextGenomeFileName(); // "hg38";
        bowtieParameters[2] = getSmoothingParameters(); // "10 1 5 0 1";
        bowtieParameters[3] = getStepSize(); // "y 10";

    }

    /**
     * Gets the step size from raw to profile tab.
     *
     * @return String - "y" + stepSize || "n" + stepSize
     */
    private String getStepSize() {
        if (stepSizeBox.isSelected()) {
            return "y " + stepSize.getText().trim();
        } else {
            return "n" + stepSize.getText().trim();
        }
    }

    /**
     * Initiates default parameters in the raw to profile tab.
     */
    private void initBowtieParameters() {

        stepSize.setText("10");
        smoothWindowSize.setText("10");
        smoothType.setSelectedIndex(0);
        stepPosition.setText("5");
        printZeros.setSelected(true);
        genomeFile.removeAllItems();

        for (int i = 0; i < genomeReleaseFiles.size(); i++) {
            genomeFile.addItem(genomeReleaseFiles.get(i));
        }
    }

    // TODO

    /**
     * Sets the genomeReleaseFiles list, Retrieves all genome files a specific
     * species has.
     *
     * @param genomeReleaseFiles
     */
    // TODO
    public void setGenomeReleaseFiles(ArrayList<String> genomeReleaseFiles) {
        this.genomeReleaseFiles = genomeReleaseFiles;
    }

    /**
     * Returns all smoothing parameters in one string from the raw to profile
     * tab.
     *
     * @return String - all parameters in a string
     */
    private String getSmoothingParameters() {
        String smoothPar;
        String printmean = "0";
        String printzeros = "0";

        smoothPar = smoothWindowSize.getText().trim() + " "
                + smoothType.getSelectedItem() + " "
                + stepPosition.getText().trim();

        if (printMean.isSelected()) {
            printmean = "1";
        }

        if (printZeros.isSelected()) {
            printzeros = "1";
        }

        return smoothPar + " " + printmean.trim() + " " + printzeros.trim();
    }

    /**
     * Gets the selected genome files name from the combobox.
     *
     * @return String - Name of the selected genome file
     */
    private String getTextGenomeFileName() {
        return genomeFile.getSelectedItem().toString().trim();
    }

    public void setGenomeFileList(GenomeReleaseData[] genomeReleases) {

        genomeFile.removeAllItems();
        if (genomeReleases != null && genomeReleases.length > 0) {
            for (GenomeReleaseData version : genomeReleases) {
                if (version != null) {
                    genomeFile.addItem(version.getVersion());
                }
            }
        }
    }

    /**
     * Gets the text in the flag parameter in raw to profile tab.
     *
     * @return
     */
    private String getTextFromFlags() {
        return flags.getText().trim();
    }

    /**
     * Sets the fileData list with all selected files to process from workspace.
     *
     * @param allFileData
     */
    public void setFileInfo(ArrayList<FileData> allFileData,
            ArrayList<ExperimentData> experimentData) {
        // this.fileData = allFileData;
        this.experimentData = experimentData;
        parseFileData();
    }

    /**
     * Parse out the names of all the files in fileData list, Filles the
     * fileList with CheckListItem.
     */
    private void parseFileData() {

        ArrayList<CheckListItem> itemList = new ArrayList<CheckListItem>();
        String specie = "";

        for (ExperimentData exData : experimentData) {
            for (FileData fileData : exData.files) {
                for (AnnotationDataValue annoDataValue : exData.annotations) {
                    if (annoDataValue.getName().equals("Species")) {
                        specie = annoDataValue.value;
                        break;
                    }
                }
                itemList.add(new CheckListItem(fileData, fileData.filename,
                        fileData.id, specie));
            }
        }
        fileList.setListData(itemList.toArray(new CheckListItem[itemList.size()]));
        this.revalidate();
        this.repaint();
    }

    private void fileListSetCellRenderer() {
        fileList.setCellRenderer(new CheckListRenderer());
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public JList getFileList() {
        return fileList;
    }

    private void initFileList() {
        fileListSetCellRenderer();
    }

    /**
     * Gets the names of all the files that are marked in the fileList.
     *
     * @return ArrayList<FileData> - List of all the file names.
     */

    public ArrayList<FileData> getAllMarkedFiles() {

        ArrayList<FileData> arr = new ArrayList<FileData>();

        for (int i = 0; i < fileList.getModel().getSize(); i++) {
            CheckListItem checkItem = (CheckListItem) fileList.getModel()
                    .getElementAt(i);
            checkItemIsSelected(arr, checkItem);
        }
        return arr;
    }

    /**
     * Gets the fileData of all the files that are marked in the fileList.
     *
     * @return ArrayList<FileData> - List of all the files.
     */
    public ArrayList<FileData> getAllMarkedFileData() {

        ArrayList<FileData> arr = getAllMarkedFiles();

        return arr;
    }

    /**
     * Checks if an item in a list is selected.
     *
     * @param arr
     *            - the list
     * @param checkItem
     *            - the item in the list
     */
    private void checkItemIsSelected(ArrayList<FileData> arr,
            CheckListItem checkItem) {
        if (checkItem.isSelected()) {
            arr.add(checkItem.getfile());
        }
    }

    public void addFileListMouseListener(MouseAdapter mouseAdapter) {
        fileList.addMouseListener(mouseAdapter);
    }

    public void addConvertFileListener(ActionListener listener) {
        convertButton.addActionListener(listener);
    }

    public void addProcessFeedbackListener(ActionListener listener) {
        procFeedbackListener = listener;
        processFeedbackButton.addActionListener(procFeedbackListener);
    }

    public void addRawToProfileDataListener(ActionListener listener) {
        profileButton.addActionListener(listener);
    }

    public void addRawToRegionDataListener(ActionListener listener) {
        regionButton.addActionListener(listener);
    }

    public void addScheduleFileListener(ActionListener listener) {
        // scheduleButton.addActionListener(listener);
        // scheduleButton.addActionListener(listener);
    }

    private int getNumberOfJobsInQueue() {
        return 0;
    }

    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData) {
        this.processFeedbackData = processFeedbackData;
        remove(procInfoPanel);
        this.addProcessInfoPanel();
        repaint();
        revalidate();
    }

    private int getTimeApprox() {
        return 450;
    }

    /**
     * Prints message to genProfArea. The message is red if it is a warning
     * message, black otherwise.
     *
     * @param message
     *            - Whether or not create profile data succeeded
     * @param color
     *            - What color the message should have
     */
    public void printToProfileText(String message, String color) {

        genProfArea.append(message);
        genProfArea.setForeground(Color.black);

        if (color.equals("red")) {
            genProfArea.setForeground(Color.RED);
        }
    }

    /**
     * Writes the time to timeArea in timePanel.
     */
    private void writeToTimePanel() {

        timeArea.setText("Time panel");
        timeArea.setEditable(false);
        timePanel.add(timeArea);

    }

    public String[] getOtherParameters() {
        String[] s = new String[2];
        s[0] = "y";
        s[1] = "y";
        return s;
    }

    public void addRatioCalcListener(ActionListener listener) {
        ratioCalcButton.addActionListener(listener);
    }

    public void setDefaultRatioPar() {
        /*
         * private final JTextField inputReads = new JTextField(); private final
         * JTextField chromosome = new JTextField(); private final JTextField
         * ratioWindowSize = new JTextField(); private final JTextField
         * ratioSmoothType = new JTextField(); private final JTextField
         * ratioStepPosition private final JCheckBox ratioPrintMean private
         * final JCheckBox ratioPrintZeros
         */

        inputReads.setText("4");
        chromosome.setText("0");
        ratioWindowSize.setText("150");
        ratioSmoothType.setSelectedIndex(0);
        ratioStepPosition.setText("7");

    }

    private void setUnusedRatioPar() {

        inputReads.setText("");
        chromosome.setText("");
        ratioWindowSize.setText("");
        ratioSmoothType.setSelectedIndex(0);
        ratioStepPosition.setText("");
    }

}
