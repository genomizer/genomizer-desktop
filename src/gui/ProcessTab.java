package gui;

import util.FileData;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

/**
 * Visual presentation of the process tab.
 *
 * @author c11ann
 */
public class ProcessTab extends JPanel {

    private static final long serialVersionUID = -2830290705724588252L;

    private final JList<CheckListItem> fileList = new JList<CheckListItem>();
    private final JList<CheckListItem> scheduleList = new JList<CheckListItem>();
    private final JList<CheckListItem> processList = new JList<CheckListItem>();

    private final JPanel convPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JPanel filesPanel = new JPanel(new BorderLayout());
    private final JPanel scheduleProcPanel = new JPanel(new BorderLayout());
    private final JPanel genProfileDataPanel = new JPanel(new BorderLayout());
    private final JPanel genRegionDataPanel = new JPanel(new BorderLayout());
    private final JPanel convertFilesPanel = new JPanel(new BorderLayout());
    private final JPanel procQueuePanel = new JPanel(new BorderLayout());
    private final JPanel RawToProfileMenuPanel = new JPanel();
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

    private final JTextArea textArea = new JTextArea();
    private final JTextArea genProfArea = new JTextArea();
    private final JTextArea genRegArea = new JTextArea();
    private final JTextArea timeArea = new JTextArea();
    private final JTextArea convertArea = new JTextArea();

    private final JTextField flags = new JTextField();
    private final JTextField smoothWindowSize = new JTextField();
    private final JTextField smoothType = new JTextField();
    private final JTextField stepPosition = new JTextField();
    private final JTextField stepSize = new JTextField();

    private final JScrollPane scrollSchedule = new JScrollPane();
    private final JScrollPane scrollConvert = new JScrollPane();
    private final JScrollPane scrollRegion = new JScrollPane();
    private final JScrollPane scrollProfile = new JScrollPane();
    private final JScrollPane scrollProcessList = new JScrollPane();
    private final JScrollPane scrollFiles = new JScrollPane();

    private final JButton convertButton = new JButton("Convert to WIG");
    private final JButton profileButton = new JButton("Create profile data");
    private final JButton regionButton = new JButton("Create region data");
    // private final JCheckBox scheduleButton = new JCheckBox(
    // "Schedule files");

    private final JCheckBox printMean = new JCheckBox("Print mean");
    private final JCheckBox printZeros = new JCheckBox("Print zeros");
    private final JCheckBox stepSizeBox = new JCheckBox("Step size");
    private final JComboBox<String> genomeFile = new JComboBox<String>();

    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
    private ArrayList<String> genomeReleaseFiles;
    private ArrayList<FileData> fileData;
    private String[] bowtieParameters = new String[4];

    public ProcessTab() {
        setPreferredSize(new Dimension(1225, 725));
        setMinimumSize(new Dimension(20000, 20000));
        this.setLayout(new BorderLayout());
        initPanels();
    }

    /**
     * Initiates all the process tabs panels.
     */
    private void initPanels() {

        addNorthPanel();
        addWestPanels();
        addMiddlePanel();
        addEastPanels();

        addTimePanel();
        addConvertTextArea();
        initFileList();

        /* TEST */
        ArrayList<String> gFiles = new ArrayList<String>();
        /* TEST */
        gFiles.add(0, "d_melanogaster_fb5_22");
        /* TEST */
        setGenomeReleaseFiles(gFiles);

        initBowtieParameters();
        writeToTimePanel();

        /* TEST */// CheckListItem[] itemList = new CheckListItem[20];

        /* TEST */// for(int i = 0; i < 20; i++){
        /* TEST */// itemList[i] = new CheckListItem( "[" + i + "] TESTjg.RAW"
        // );
        /* TEST */// }

        /* TEST */// fileList.setListData(itemList);

    }

    /**
     * Initiates the north panel in the process tabs borderlayout.
     */
    private void addNorthPanel() {
        RawToProfileMenuPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
      //  RawToProfileMenuPanel.setPreferredSize(new Dimension(1200, 200));

        this.add(RawToProfileMenuPanel, BorderLayout.NORTH);
        addOptionsToRawToProfileTab();
        enableButtons();

    }

    /**
     * Initiates the west panel in the process tabs borderlayout.
     */
    private void addWestPanels() {

        this.add(westPanel, BorderLayout.WEST);
        filesPanel.setBorder(BorderFactory.createTitledBorder("Files")/*new TitledBorder(null, "Files",
                TitledBorder.LEADING, TitledBorder.TOP, null, null)*/);
        //    filesPanel.setPreferredSize(new Dimension(300, 100));
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
    private void addEastPanels() {
        procQueuePanel.setBorder(BorderFactory.createTitledBorder("Processing In Queue")/*new TitledBorder(UIManager
                .getBorder("TitledBorder.border"), "Processing In Queue",
                TitledBorder.LEADING, TitledBorder.TOP, null, null
                )*/);
        this.add(procQueuePanel, BorderLayout.EAST);
        addProcessInQueue();

    }

    /**
     * Initiates the convertFilesPanel in the center panel.
     */
    private void addConvertFilesPanel() {
        middlePanel.add(convertFilesPanel, BorderLayout.CENTER);
        convertFilesPanel.setBorder(BorderFactory.createTitledBorder("Convert Files")/*new TitledBorder(null, "Convert Files",
                TitledBorder.LEADING, TitledBorder.TOP, null, null)*/);
    }

    /**
     * Initiates the genRegionDataPanel in the center panel.
     */
    private void addGenRegionDataPanel() {
        middlePanel.add(genRegionDataPanel, BorderLayout.CENTER);
        genRegionDataPanel.setBorder(BorderFactory.createTitledBorder("Generate Region Data")/*new TitledBorder(null,
                "Generate Region Data", TitledBorder.LEADING, TitledBorder.TOP,
                null, null)*/);
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
        middlePanel.add(genProfileDataPanel,BorderLayout.CENTER);
        genProfileDataPanel.setBorder(BorderFactory.createTitledBorder("Generate Profile Data")/*new TitledBorder(null,
                "Generate Profile Data", TitledBorder.LEADING,
                TitledBorder.TOP, null, null)*/);

        genProfileDataPanel.add(scrollProfile);
        scrollProfile.setViewportView(genProfArea);
        genProfArea.setEditable(false);
    }

    /**
     * Initiates the scrollProcessList in procQueuePanel.
     */
    private void addProcessInQueue() {
        procQueuePanel.add(scrollProcessList,BorderLayout.CENTER);
        scrollProcessList.setViewportView(processList);
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
        scheduleProcPanel.setBorder(BorderFactory.createTitledBorder("Scheduled Processing")/*new TitledBorder(null,
                "Scheduled Processing", TitledBorder.LEADING, TitledBorder.TOP,
                null, null)*/);

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
        addSmoothWindowSizeToConv();
        addSmoothTypeToConv();
        addStepPositionToConv();
        addStepSizeToConv();
        addPrintMeanToConv();
        addPrintZeroToConv();
    }

    /**
     * Initiates all panels to the raw to profile tab.
     */
    private void addPanelsToRawToProfileTab() {
        RawToProfileMenuPanel
        .setLayout(new BorderLayout());
        RawToProfileMenuPanel.add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addTab("Create profile data", null, convTabpanel, null);
        convTabpanel.add(convPanel,BorderLayout.NORTH);
        convPanel.add(flagsPanel);
        flagsPanel.setBorder(BorderFactory.createTitledBorder("Flags")/*new TitledBorder(null, "Flags",
                TitledBorder.LEADING, TitledBorder.TOP, null, null)*/);
        convPanel.add(genomeReleasePanel);
        genomeReleasePanel.setBorder(/*new TitledBorder(null,
                "Genome release files", TitledBorder.LEADING, TitledBorder.TOP,
                null, null)*/BorderFactory.createTitledBorder("Genome release files"));
        convPanel.add(windowSizePanel);
        windowSizePanel.setBorder(/*new TitledBorder(null, "Window size",
                TitledBorder.LEADING, TitledBorder.TOP, null, null)*/BorderFactory.createTitledBorder("Window size"));
        convPanel.add(smoothTypePanel);
        smoothTypePanel.setBorder(/*new TitledBorder(null, "Smooth type",
                TitledBorder.LEADING, TitledBorder.TOP, null, null)*/BorderFactory.createTitledBorder("Smooth type"));
        convPanel.add(stepPositionPanel);
        stepPositionPanel.setBorder(/*new TitledBorder(null, "Step position",
                TitledBorder.LEADING, TitledBorder.TOP, null, null)*/BorderFactory.createTitledBorder("Step position"));
        convPanel.setBorder(null);
        checkBoxPanel.setAlignmentY(1.0f);
        checkBoxPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null,
                null));
        convPanel.add(checkBoxPanel);

        GridBagConstraints gbc_printMean = new GridBagConstraints();
        gbc_printMean.fill = GridBagConstraints.BOTH;
        gbc_printMean.insets = new Insets(0, 0, 0, 5);
        gbc_printMean.gridx = 0;
        gbc_printMean.gridy = 0;
        checkBoxPanel.add(printMean, gbc_printMean);

        GridBagConstraints gbc_printZeros = new GridBagConstraints();
        gbc_printZeros.fill = GridBagConstraints.BOTH;
        gbc_printZeros.insets = new Insets(0, 0, 0, 5);
        gbc_printZeros.gridx = 1;
        gbc_printZeros.gridy = 0;
        checkBoxPanel.add(printZeros, gbc_printZeros);

        GridBagConstraints gbc_stepSizeBox = new GridBagConstraints();
        gbc_stepSizeBox.fill = GridBagConstraints.BOTH;
        gbc_stepSizeBox.insets = new Insets(0, 0, 0, 5);
        gbc_stepSizeBox.gridx = 2;
        gbc_stepSizeBox.gridy = 0;
        checkBoxPanel.add(stepSizeBox, gbc_stepSizeBox);

        GridBagLayout gbl_checkBoxPanel = new GridBagLayout();
        gbl_checkBoxPanel.columnWidths = new int[] { 110, 110, 110, 0 };
        gbl_checkBoxPanel.rowHeights = new int[] { 50, 0 };
        gbl_checkBoxPanel.columnWeights = new double[] { 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gbl_checkBoxPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };

        checkBoxPanel.setLayout(gbl_checkBoxPanel);
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
        smoothType.setHorizontalAlignment(JTextField.CENTER);
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
        convTabpanel.add(buttonPanel,BorderLayout.SOUTH);
        buttonPanel.add(profileButton);
        // scheduleProcPanel.add(scheduleButton);
        // scheduleButton.setPreferredSize(new Dimension(297, 23));
        // scheduleProcPanel.add(scheduleButton);
        // scheduleButton.setPreferredSize(new Dimension(297, 23));
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
        bowtieParameters[1] = getTextGenomeFileName(); // "d_melanogaster_fb5_22";
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
        smoothType.setText("1");
        stepPosition.setText("5");
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
                + smoothType.getText().trim() + " "
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
    public void setFileInfo(ArrayList<FileData> allFileData) {
        this.fileData = allFileData;
        parseFileData();
    }

    /**
     * Parse out the names of all the files in fileData list, Filles the
     * fileList with CheckListItem.
     */
    private void parseFileData() {

        CheckListItem[] itemList = new CheckListItem[fileData.size()];

        for (int i = 0; i < fileData.size(); i++) {
            itemList[i] = new CheckListItem(fileData.get(i).filename);
        }

        fileList.setListData(itemList);
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
     * @return ArrayList<String> - List of all the file names.
     */
    public ArrayList<String> getAllMarkedFiles() {

        ArrayList<String> arr = new ArrayList<String>();

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

        ArrayList<FileData> allMarked = new ArrayList<FileData>();
        ArrayList<String> arr = getAllMarkedFiles();

        if (!(fileData == null)) {
            for (int i = 0; i < fileData.size(); i++) {
                if (arr.contains(fileData.get(i).filename)) {
                    allMarked.add(fileData.get(i));
                }
            }
        }
        return allMarked;
    }

    /**
     * Checks if an item in a list is selected.
     *
     * @param arr       - the list
     * @param checkItem - the item in the list
     */
    private void checkItemIsSelected(ArrayList<String> arr,
            CheckListItem checkItem) {
        if (checkItem.isSelected()) {
            arr.add(checkItem.toString());
        }
    }

    public void addFileListMouseListener(MouseAdapter mouseAdapter) {
        fileList.addMouseListener(mouseAdapter);
    }

    public void addConvertFileListener(ActionListener listener) {
        convertButton.addActionListener(listener);
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
        return this.processList.countComponents();
    }

    private int getTimeApprox() {
        return 450;
    }

    /**
     * Prints message to genProfArea. The message is red if it is a warning
     * message, black otherwise.
     *
     * @param message - Whether or not create profile data succeeded
     * @param color   - What color the message should have
     */
    public void printToProfileText(String message, String color) {

        genProfArea.append(message);

        if (color.equals("red")) {
            genProfArea.setForeground(Color.RED);
        }
    }

    /**
     * Writes the time to timeArea in timePanel.
     */
    private void writeToTimePanel() {

        timeArea.setText("");
        timeArea.setEditable(false);
        timeArea.append("Number of jobs currently in queue: "
                + getNumberOfJobsInQueue() + " (est. time until empty : "
                + getTimeApprox() + " min )");
        timePanel.add(timeArea);

    }

    public String[] getRatioCalcParameters() {
        String[] s = new String[2];
        s[0] = "single 4 0";
        s[1] = "150 1 7 0 0";

        return s;
    }

    public String[] getOtherParameters() {
        String[] s = new String[2];
        s[0] = "y";
        s[1] = "y";
        return s;
    }

}
