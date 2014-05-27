package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

/**
 * Visual presentation of the process tab.
 *
 * @author c11ann
 */
public class ProcessTab extends JPanel {

    private static final long serialVersionUID = -2830290705724588252L;

    private final JList<CheckListItem> fileList = new JList<CheckListItem>();

    private final JPanel rawParameterPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JPanel filesPanel = new JPanel(new BorderLayout());
    private final JPanel consolePanel = new JPanel(new BorderLayout());
    private JPanel procInfoPanel = new JPanel(new BorderLayout());
    private final JPanel rawToProfileMenuPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final JPanel westPanel = new JPanel(new BorderLayout());

    private final JPanel flagsPanel = new JPanel();
    private final JPanel genomeReleasePanel = new JPanel();
    private final JPanel windowSizePanel = new JPanel();
    private final JPanel smoothTypePanel = new JPanel();
    private final JPanel stepPositionPanel = new JPanel();
    private final JPanel stepSizePanel = new JPanel();
    private final JPanel checkBoxPanel = new JPanel();
    private final JPanel rawTabpanel = new JPanel(new BorderLayout());
    private final JPanel removePanel = new JPanel(new FlowLayout());
    private final JPanel createProfilePanel = new JPanel();
    private final JPanel formatPanel = new JPanel();

    private final JTextArea consoleArea = new JTextArea();
    private final JTextField flags = new JTextField();
    public final JTextField smoothWindowSize = new JTextField();
    public final JTextField stepPosition = new JTextField();
    public final JTextField stepSize = new JTextField();
    private final JTextField inputReads = new JTextField();
    private final JTextField chromosome = new JTextField();
    private final JTextField ratioWindowSize = new JTextField();
    private final JTextField ratioStepPosition = new JTextField();

    private final JScrollPane scrollConsole = new JScrollPane();
    private final JScrollPane scrollProcessList = new JScrollPane();
    private final JScrollPane scrollFiles = new JScrollPane();

    private final JButton profileButton = new JButton("Start process");
    private final JButton ratioCalcButton = new JButton(
            "Ratio calculation option");
    private final JButton processFeedbackButton = new JButton(
            "Get process feedback");
    private final JButton deleteButton = new JButton("Delete selected");
    private final JButton infoButton = new JButton("?");

    private final JCheckBox printMean = new JCheckBox("Print mean");
    private final JCheckBox printZeros = new JCheckBox("Print zeros");
    public final JCheckBox stepSizeBox = new JCheckBox("Step size");
    public final JCheckBox useSmoothing = new JCheckBox("Smoothing");
    private final JCheckBox useRatio = new JCheckBox("Ratio calculation");

    private final JComboBox<String> genomeFile = new JComboBox<String>();
    private final JComboBox<String> smoothType = new JComboBox<String>();

    public final JRadioButton outputSGR = new JRadioButton("SGR");
    public final JRadioButton outputGFF = new JRadioButton("GFF");
    public final JRadioButton outputSAM = new JRadioButton("SAM");
    public final ButtonGroup radioGroup = new ButtonGroup();

    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
    public final JOptionPane ratioPopup = new JOptionPane();
    private String[] regularParameters = new String[4];
    private ProcessFeedbackData[] processFeedbackData;
    private ArrayList<ExperimentData> experimentData;

    public ProcessTab() {
        processFeedbackData = new ProcessFeedbackData[0];
        setPreferredSize(new Dimension(1225, 725));
        setMinimumSize(new Dimension(20000, 20000));
        this.setLayout(new BorderLayout());
        initPanels();
        disableAllParameters();
    }

    /**
     * Initiates all the process tabs panels.
     */
    private void initPanels() {

        addNorthPanel();
        addWestPanels();
        addConsolePanel();
        addProcessInfoPanel();
        addSouthPanel();
        addConsolePanelComponents();
        initFileList();

        ArrayList<String> smooth = new ArrayList<String>();
        smooth.add("Median");
        smooth.add("Trimmed Mean");
        initComboBoxes(smooth, smoothType);

        initRegularParameters();
        setDefaultRatioPar();
        setButtonListeners();

    }

    private void helpPopup() {

        JOptionPane
                .showMessageDialog(
                        this,
                        "Regular parameters\n\nFormat: \nBowtie flags: \nGenome release files: \nWindow size: \nSmooth type: \nStep position: \nStep size: \n"
                                + "\nRatio calculation parameters\n\nSingle/Double: \nInput reads cut-off: \nChromosomes: \nWindow size: \nSmooth type: \nStep position: \nPrint zeros: \nPrint mean: \n",
                        "Parameter information", JOptionPane.PLAIN_MESSAGE);
    }

    private void setButtonListeners() {
        radioGroup.add(outputSGR);
        radioGroup.add(outputGFF);
        radioGroup.add(outputSAM);
        radioGroup.setSelected(outputSAM.getModel(), true);
        setComboBoxActionListener(genomeFile);
        setRadioButtonListener(outputSGR);
        setRadioButtonListener(outputGFF);
        setRadioButtonListener(outputSAM);
        setCheckBoxListener(stepSizeBox);
        setCheckBoxListener(useSmoothing);
        setCheckBoxListener(useRatio);
        setJButtonListener(infoButton);
        setFlagsListener();
    }

    private void initComboBoxes(ArrayList<String> items,
            JComboBox<String> dropDownList) {

        if (!dropDownList.equals(null)) {
            for (String item : items) {
                dropDownList.addItem(item);
            }
        }
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
        westPanel.setBorder(BorderFactory.createTitledBorder("Files"));
        addFilesPanel();
    }

    /**
     * Initiates the center panel in the process tabs borderlayout.
     */
    private void addConsolePanel() {
        this.add(consolePanel, BorderLayout.CENTER);
        consolePanel.setBorder(BorderFactory.createTitledBorder("Console"));
    }

    /**
     * Initiates the east panel in the process tabs borderlayout.
     */
    private void addProcessInfoPanel() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                procInfoPanel = new JPanel();
                procInfoPanel.setBorder(BorderFactory
                        .createTitledBorder("Processing Information"));
                procInfoPanel.setLayout(new BorderLayout());
                JPanel procInfoSouthPanel = new JPanel(new FlowLayout());
                JPanel procInfoCenterPanel = new JPanel(new BorderLayout());
                add(procInfoPanel, BorderLayout.EAST);
                procInfoPanel.add(procInfoSouthPanel, BorderLayout.SOUTH);
                procInfoPanel.add(procInfoCenterPanel, BorderLayout.CENTER);
                scrollProcessList.setPreferredSize(new Dimension(300, 700));
                procInfoCenterPanel.add(scrollProcessList, BorderLayout.CENTER);
                // create the root node
                DefaultMutableTreeNode root = new DefaultMutableTreeNode(
                        "<html><b>Current processes</b></html>");
                // create the child nodes
                ArrayList<String> authors = new ArrayList<String>();
                for (int i = 0; i < processFeedbackData.length; i++) {
                    if (!authors.contains(processFeedbackData[i].author)) {
                        authors.add(processFeedbackData[i].author);
                    }
                }
                for (String author : authors) {

                    DefaultMutableTreeNode authorNode = new DefaultMutableTreeNode(
                            "<html><b>Author</b>: " + author + "</html>");
                    root.add(authorNode);
                    DefaultMutableTreeNode finishedNode = new DefaultMutableTreeNode(
                            "<html><b>Finished</b></html>");
                    authorNode.add(finishedNode);
                    DefaultMutableTreeNode crashedNode = new DefaultMutableTreeNode(
                            "<html><b>Crashed</b></html>");
                    authorNode.add(crashedNode);
                    DefaultMutableTreeNode startedNode = new DefaultMutableTreeNode(
                            "<html><b>Started</b></html>");
                    authorNode.add(startedNode);
                    DefaultMutableTreeNode waitingNode = new DefaultMutableTreeNode(
                            "<html><b>Waiting</b></html>");
                    authorNode.add(waitingNode);
                    for (int i = 0; i < processFeedbackData.length; i++) {
                        ProcessFeedbackData data = processFeedbackData[i];
                        if (author.equals(data.author)) {
                            Format format = new SimpleDateFormat(
                                    "yyyy-MM-dd, HH:mm");
                            String timeAdded = "Not added";
                            String timeStarted = "Not started";
                            String timeFinished = "Not finished";
                            if (data.timeAdded != 0) {
                                timeAdded = format.format(
                                        new Date(data.timeAdded)).toString();
                            }
                            if (data.timeStarted != 0) {
                                timeStarted = format.format(
                                        new Date(data.timeStarted)).toString();
                            }
                            if (data.timeFinished != 0) {
                                timeFinished = format.format(
                                        new Date(data.timeFinished)).toString();
                            }
                            DefaultMutableTreeNode expNode = new DefaultMutableTreeNode(
                                    "<html><b>ExpID</b>: "
                                            + data.experimentName + "</html>");
                            DefaultMutableTreeNode addedTimeNode = new DefaultMutableTreeNode(
                                    "<html><u>Time Added</u>: " + timeAdded
                                            + "</html>");
                            DefaultMutableTreeNode startedTimeNode = new DefaultMutableTreeNode(
                                    "<html><u>Time Started</u>: " + timeStarted
                                            + "</html>");
                            DefaultMutableTreeNode finishedTimeNode = new DefaultMutableTreeNode(
                                    "<html><u>Time Finished</u>: "
                                            + timeFinished + "</html>");

                            expNode.add(addedTimeNode);
                            expNode.add(startedTimeNode);
                            expNode.add(finishedTimeNode);

                            if (data.status.equals("Finished")) {
                                finishedNode.add(expNode);
                            } else if (data.status.equals("Waiting")) {
                                waitingNode.add(expNode);
                            } else if (data.status.equals("Crashed")) {
                                crashedNode.add(expNode);
                            } else if (data.status.equals("Started")) {
                                startedNode.add(expNode);
                            }

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
                scrollProcessList.setViewportView(tree);
                procInfoSouthPanel.add(Box.createHorizontalStrut(35));
                procInfoSouthPanel.add(processFeedbackButton);
                procInfoSouthPanel.add(Box.createHorizontalStrut(35));
            }
        });
    }

    /**
     * Initiates southPanel to south in the process tabs borderlayout.
     */
    private void addSouthPanel() {
        this.add(southPanel, BorderLayout.SOUTH);
        southPanel.setPreferredSize(new Dimension(300, 30));

    }

    /**
     * Writes text to convertArea. The user gets a visual message whether or not
     * the conversion succeeded.
     */
    private void addConsolePanelComponents() {
        consolePanel.add(scrollConsole, BorderLayout.CENTER);
        scrollConsole.setViewportView(consoleArea);
        consoleArea.setEditable(false);
        JPanel clearConsolePanel = new JPanel(new FlowLayout());
        consolePanel.add(clearConsolePanel, BorderLayout.SOUTH);
        JButton clearConsoleButton = new JButton("Clear console");
        clearConsoleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consoleArea.setText("");
            }
        });
        clearConsolePanel.add(clearConsoleButton);
    }

    /**
     * Initiates filesPanel in westPanel.
     */
    private void addFilesPanel() {
        westPanel.add(filesPanel);
        filesPanel.add(scrollFiles);
        removePanel.add(deleteButton);
        westPanel.add(removePanel, BorderLayout.SOUTH);
        scrollFiles.setViewportView(fileList);
    }

    /**
     * Initiates all checkboxes and textfields in raw to profile tab.
     */
    private void addOptionsToRawToProfileTab() {
        addPanelsToRawToProfileTab();
        addFlagsToRawTab();
        addGenomeFileToRawTab();
        addSmoothTypeToRawTab();
        addSmoothWindowSizeToRawTab();
        addStepPositionToRawTab();
        addStepSizeToRawTab();
        addPrintMeanToRawTab();
        addPrintZeroToRawTab();
    }

    /**
     * Initiates all panels to the raw to profile tab.
     */
    private void addPanelsToRawToProfileTab() {
        rawToProfileMenuPanel.setLayout(new BorderLayout());
        rawToProfileMenuPanel.add(tabbedPane, BorderLayout.CENTER);

        tabbedPane.addTab("Create profile data", null, rawTabpanel, null);
        rawTabpanel.add(rawParameterPanel, BorderLayout.NORTH);
        formatPanel.setPreferredSize(new Dimension(100, 82));
        formatPanel.setBorder(BorderFactory.createTitledBorder("Format"));

        rawParameterPanel.add(infoButton);

        rawParameterPanel.add(formatPanel);
        formatPanel.setLayout(new GridLayout(0, 1, 0, 0));
        formatPanel.add(outputSAM);
        formatPanel.add(outputGFF);
        formatPanel.add(outputSGR);

        rawParameterPanel.add(flagsPanel);
        flagsPanel.setBorder(BorderFactory.createTitledBorder("Bowtie flags"));
        rawParameterPanel.add(genomeReleasePanel);
        genomeReleasePanel.setBorder(BorderFactory
                .createTitledBorder("Genome release files"));
        rawParameterPanel.add(windowSizePanel);
        windowSizePanel.setBorder(BorderFactory
                .createTitledBorder("Window size"));
        rawParameterPanel.add(smoothTypePanel);
        smoothTypePanel.setBorder(BorderFactory
                .createTitledBorder("Smooth type"));
        rawParameterPanel.add(stepPositionPanel);
        stepPositionPanel.setBorder(BorderFactory
                .createTitledBorder("Step position"));
        rawParameterPanel.setBorder(null);
        checkBoxPanel.setPreferredSize(new Dimension(315, 81));

        rawParameterPanel.add(stepSizePanel);
        stepSizePanel.setBorder(BorderFactory.createTitledBorder("Step size"));

    }

    /**
     * Initiates the flag text field in raw to profile tab
     */
    private void addFlagsToRawTab() {
        flagsPanel.add(flags);
        flags.setBorder(null);
        flags.setText("-a -m 1 --best -p 10 -v 2 -q -S");
        flags.setPreferredSize(new Dimension(180, 30));
    }

    /**
     * Initiates the genome files combobox in raw to profile tab
     */
    private void addGenomeFileToRawTab() {
        genomeReleasePanel.add(genomeFile);
        genomeFile.setPreferredSize(new Dimension(180, 30));
        genomeFile.setBorder(null);
    }

    /**
     * Initiates the window size text field in raw to profile tab
     */
    private void addSmoothWindowSizeToRawTab() {
        windowSizePanel.add(smoothWindowSize);
        smoothWindowSize.setPreferredSize(new Dimension(70, 30));
        smoothWindowSize.setBorder(null);
        smoothWindowSize.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the smooth type text field in raw to profile tab
     */
    private void addSmoothTypeToRawTab() {
        smoothTypePanel.add(smoothType);
        smoothType.setPreferredSize(new Dimension(140, 30));
        smoothType.setBorder(null);
    }

    /**
     * Initiates the step position text field in raw to profile tab
     */
    private void addStepPositionToRawTab() {
        stepPositionPanel.add(stepPosition);
        stepPosition.setPreferredSize(new Dimension(75, 30));
        stepPosition.setBorder(null);
        stepPosition.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the step size text field in raw to profile tab
     */
    private void addStepSizeToRawTab() {
        stepSizePanel.add(stepSize);
        stepSize.setPreferredSize(new Dimension(70, 30));
        stepSize.setBorder(null);
        stepSize.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the print mean checkbox in raw to profile tab
     */
    private void addPrintMeanToRawTab() {
        printMean.setPreferredSize(new Dimension(90, 30));
        printMean.setBorder(null);
        stepSizeBox.setPreferredSize(new Dimension(80, 30));
    }

    /**
     * Initiates the print zeros checkbox in raw to profile tab
     */
    private void addPrintZeroToRawTab() {
        printZeros.setPreferredSize(new Dimension(90, 30));
        printZeros.setBorder(null);
    }

    /**
     * Initiates the all buttons.
     */
    private void enableButtons() {
        rawTabpanel.add(buttonPanel, BorderLayout.SOUTH);
        useSmoothing.setPreferredSize(new Dimension(95, 30));

        buttonPanel.add(useSmoothing);
        buttonPanel.add(printMean);
        buttonPanel.add(printZeros);
        buttonPanel.add(stepSizeBox);
        useRatio.setPreferredSize(new Dimension(130, 30));

        buttonPanel.add(useRatio);
        buttonPanel.add(ratioCalcButton);
    }

    /**
     * Initiates default parameters in the raw to profile tab.
     */
    private void initRegularParameters() {
        stepSize.setText("");
        smoothWindowSize.setText("");
        smoothType.setSelectedIndex(0);
        stepPosition.setText("");
        printZeros.setSelected(true);
        genomeFile.removeAllItems();
    }

    private void initFileList() {
        fileListSetCellRenderer();
    }

    public JList<CheckListItem> getFileList() {
        return fileList;
    }

    /**
     * Gets the step size from raw to profile tab.
     *
     * @return String - "y" + stepSize || "n" + stepSize
     */
    private String getStepSize() {
        if (outputSGR.isSelected() && stepSizeBox.isSelected()) {
            return "y " + stepSize.getText().trim();
        } else {
            return "";
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
     * Gets the selected genome files name from the combobox.
     *
     * @return String - Name of the selected genome file
     */
    private String getTextGenomeFileName() {
        return genomeFile.getSelectedItem().toString().trim();
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

        smoothPar = smoothWindowSize.getText().trim() + " ";

        if (!useSmoothing.isSelected()) {
            return "";
        } else if (smoothType.getSelectedItem().equals("Median")) {
            smoothPar = smoothPar + "1" + " ";
        } else {
            smoothPar = smoothPar + "0" + " ";
        }

        smoothPar = smoothPar + stepPosition.getText().trim();

        if (printMean.isSelected()) {
            printmean = "1";
        }

        if (printZeros.isSelected()) {
            printzeros = "1";
        }

        return smoothPar + " " + printmean.trim() + " " + printzeros.trim();
    }

    /**
     * Gets all the parameters that the user has written in the raw to profile
     * tab when trying to create profile data from raw data.
     *
     * @return String[] - bowtieParameters
     */
    public String[] getregularParameters() {
        return this.regularParameters;
    }

    /**
     * Sets bowtieParameters from all the parameters in raw to profile tab.
     */
    public void setRegularParameters() {

        regularParameters[0] = getTextFromFlags(); // "-a -m 1 --best -p 10 -v 2";
        regularParameters[1] = getTextGenomeFileName(); // "hg38";
        regularParameters[2] = getSmoothingParameters(); // "10 1 5 0 1";
        regularParameters[3] = getStepSize(); // "y 10";

    }

    public String[] getRegularParameters() {

        String[] parameters = new String[6];
        parameters[0] = getTextFromFlags();
        parameters[1] = getTextGenomeFileName();
        if (outputGFF.isSelected()) {
            parameters[2] = "y";
        } else {
            parameters[2] = "";
        }
        if (outputSGR.isSelected()) {
            parameters[3] = "y";
            parameters[4] = getSmoothingParameters();
        } else {
            parameters[3] = "";
            parameters[4] = "";
        }

        parameters[5] = getStepSize();
        return parameters;
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
     * Sets the fileData list with all selected files to process from workspace.
     *
     * @param experimentData
     */
    public void setFileInfo(ArrayList<ExperimentData> experimentData) {
        this.experimentData = experimentData;
        parseFileData();
    }

    public ArrayList<ExperimentData> getFileInfo() {
        return this.experimentData;
    }

    public void setDefaultRatioPar() {
        inputReads.setText("4");
        chromosome.setText("0");
        ratioWindowSize.setText("150");
        ratioStepPosition.setText("7");
    }

    public void setProfileButton(boolean bool) {
        profileButton.setEnabled(bool);
    }

    private void fileListSetCellRenderer() {
        fileList.setCellRenderer(new CheckListRenderer());
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        // convertButton.addActionListener(listener);
    }

    public void addProcessFeedbackListener(ActionListener listener) {
        processFeedbackButton.addActionListener(listener);
    }

    public void addRawToProfileDataListener(ActionListener listener) {
        profileButton.addActionListener(listener);
    }

    public void addRawToRegionDataListener(ActionListener listener) {
        // regionButton.addActionListener(listener);
    }

    public void addRatioCalcListener(ActionListener listener) {
        ratioCalcButton.addActionListener(listener);
    }

    public void addDeleteSelectedListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData) {
        this.processFeedbackData = processFeedbackData;
        remove(procInfoPanel);
        this.addProcessInfoPanel();
        repaint();
        revalidate();
    }

    public void setFlagsListener() {
        flags.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void changedUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                check();
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                check();
            }
        });
    }

    public void setComboBoxActionListener(JComboBox<String> combobox) {
        combobox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
    }

    public void setCheckBoxListener(JCheckBox checkbox) {
        checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
    }

    public void setRadioButtonListener(JRadioButton radioButton) {
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
    }

    private void setJButtonListener(JButton infoButton) {
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                helpPopup();
            }
        });
    }

    private void check() {
        /* Check if there are valid genome releases */
        if (genomeFile.getItemCount() > 0) {

            outputSGR.setEnabled(true);
            outputGFF.setEnabled(true);
            outputSAM.setEnabled(true);
            genomeFile.setEnabled(true);
            flags.setEnabled(true);
            profileButton.setEnabled(true);
        } else {
            outputSGR.setEnabled(false);
            outputGFF.setEnabled(false);
            outputSAM.setEnabled(false);
            genomeFile.setEnabled(false);
            flags.setEnabled(false);
            profileButton.setEnabled(false);
        }
        /* Check if SGR is enabled */
        if (outputSGR.isEnabled() && outputSGR.isSelected()) {
            useSmoothing.setEnabled(true);
        } else {
            useSmoothing.setEnabled(false);
        }
        /* Check if smoothing is enabled */
        if (useSmoothing.isEnabled() && useSmoothing.isSelected()) {
            smoothWindowSize.setEnabled(true);
            smoothType.setEnabled(true);
            stepPosition.setEnabled(true);
            printMean.setEnabled(true);
            printZeros.setEnabled(true);
            stepSizeBox.setEnabled(true);
            stepSize.setEnabled(true);
            smoothWindowSize.setText("10");
            stepPosition.setText("5");
        } else {
            smoothWindowSize.setEnabled(false);
            smoothType.setEnabled(false);
            stepPosition.setEnabled(false);
            printMean.setEnabled(false);
            printZeros.setEnabled(false);
            stepSizeBox.setEnabled(false);
            smoothWindowSize.setText("");
            stepPosition.setText("");
        }
        /* Check if step size is enabled */
        if (stepSizeBox.isEnabled() && stepSizeBox.isSelected()) {
            stepSize.setEnabled(true);
            stepSize.setText("10");
            useRatio.setEnabled(true);
        } else {
            stepSize.setEnabled(false);
            stepSize.setText("");
            useRatio.setEnabled(false);
        }
        /* Check if ratio calculation is enabled */
        if (useRatio.isEnabled() && useRatio.isSelected()) {
            ratioCalcButton.setEnabled(true);
        } else {
            ratioCalcButton.setEnabled(false);
        }

    }

    private void disableAllParameters() {
        useRatio.setEnabled(false);
        ratioCalcButton.setEnabled(false);
        buttonPanel.add(createProfilePanel);
        createProfilePanel.add(profileButton);
        profileButton.setEnabled(false);
        genomeFile.setEnabled(false);
        smoothWindowSize.setEnabled(false);
        smoothType.setEnabled(false);
        useSmoothing.setEnabled(false);
        stepPosition.setEnabled(false);
        printMean.setEnabled(false);
        printZeros.setEnabled(false);
        stepSizeBox.setEnabled(false);
        outputSGR.setEnabled(false);
        outputGFF.setEnabled(false);
        outputSAM.setEnabled(false);
        stepSize.setEnabled(false);
        flags.setEnabled(false);
    }

    /**
     * Prints message to genProfArea. The message is red if it is a warning
     * message, black otherwise.
     *
     * @param message
     *            - Whether or not create profile data succeeded
     */
    public void printToConsole(String message) {
        consoleArea.append(message);
    }

    public boolean useRatio() {
        return (useRatio.isEnabled() && useRatio.isSelected());
    }

}
