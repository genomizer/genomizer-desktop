package gui;

import java.awt.BorderLayout;
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
    /* Tabort */private final JList<CheckListItem> scheduleList = new JList<CheckListItem>();

    private final JPanel convPanel = new JPanel();
    private final JPanel buttonPanel = new JPanel();
    private final JPanel filesPanel = new JPanel(new BorderLayout());
    // private final JPanel scheduleProcPanel = new JPanel(new BorderLayout());
    /* Tabort */private final JPanel genProfileDataPanel = new JPanel(
            new BorderLayout());
    /* Tabort */private final JPanel genRegionDataPanel = new JPanel(
            new BorderLayout());
    private final JPanel consolePanel = new JPanel(new BorderLayout());
    private JPanel procInfoPanel = new JPanel(new BorderLayout());
    private final JPanel rawToProfileMenuPanel = new JPanel();
    /* Bytnamn till southPanel */private final JPanel timePanel = new JPanel();
    /* Byts mot consolePanel */private final JPanel middlePanel = new JPanel(
            new GridLayout(1, 1));
    /* Byts mot filesPanel */private final JPanel westPanel = new JPanel(
            new GridLayout(1, 1));

    /* Bytnamn */private final JPanel flagsPanel = new JPanel();
    /* Bytnamn */private final JPanel genomeReleasePanel = new JPanel();
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

    /* Tabort */private final JTextArea textArea = new JTextArea();
    /* Tabort */private final JTextArea genProfArea = new JTextArea();
    /* Tabort */private final JTextArea genRegArea = new JTextArea();
    /* Tabort */private final JTextArea timeArea = new JTextArea();
    private final JTextArea consoleArea = new JTextArea();

    private final JTextField flags = new JTextField();
    public final JTextField smoothWindowSize = new JTextField();
    public final JTextField stepPosition = new JTextField();
    public final JTextField stepSize = new JTextField();

    // RATIO CALC
    private final JTextField inputReads = new JTextField();
    private final JTextField chromosome = new JTextField();
    private final JTextField ratioWindowSize = new JTextField();
    private final JTextField ratioStepPosition = new JTextField();

    // private final JScrollPane scrollSchedule = new JScrollPane();
    /* Tabort || bytnamn */private final JScrollPane scrollConvert = new JScrollPane();
    /* Tabort */private final JScrollPane scrollRegion = new JScrollPane();
    /* Tabort */private final JScrollPane scrollProfile = new JScrollPane();
    private final JScrollPane scrollProcessList = new JScrollPane();
    private final JScrollPane scrollFiles = new JScrollPane();

    private final JButton convertButton = new JButton("Convert to WIG");
    private final JButton profileButton = new JButton("Create profile data");
    private final JButton regionButton = new JButton("Create region data");
    private final JButton ratioCalcButton = new JButton(
            "Ratio calculation option");
    private final JButton processFeedbackButton = new JButton(
            "Get process feedback");
    private JButton addToFileListButton;
    // private final JCheckBox scheduleButton = new JCheckBox(
    // "Schedule files");

    private final JCheckBox printMean = new JCheckBox("Print mean");
    private final JCheckBox printZeros = new JCheckBox("Print zeros");
    public final JCheckBox stepSizeBox = new JCheckBox("Step size");
    public final JCheckBox useSmoothing = new JCheckBox("Smoothing");
    public final JRadioButton outputSGR = new JRadioButton("SGR");
    public final JRadioButton outputGFF = new JRadioButton("GFF");
    public final JRadioButton outputSAM = new JRadioButton("SAM");
    public final ButtonGroup radioGroup = new ButtonGroup();
    private final JCheckBox useRatio = new JCheckBox("Ratio calculation");

    private final JComboBox<String> genomeFile = new JComboBox<String>();
    private final JComboBox<String> smoothType = new JComboBox<String>();
    /* Bytnamn */private final JComboBox<String> single = new JComboBox<String>();
    /* Osäker om kvar */private final JComboBox<String> ratioSmoothType = new JComboBox<String>();

    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
    public final JOptionPane ratioPopup = new JOptionPane();
    /* Oanvänd */private ArrayList<String> genomeReleaseFiles;
    /* Borde vara kortare/enklare */private String[] bowtieParameters = new String[4];
    /* Osäker */private ProcessFeedbackData[] processFeedbackData;
    private ArrayList<ExperimentData> experimentData;
    /* Oanvänd */private ActionListener procFeedbackListener;

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
        addMiddlePanel();
        addProcessInfoPanel();
        addTimePanel();
        addConvertTextArea();
        initFileList();
        /* TEST */
        initComboBoxes();
        /* TEST */
        initBowtieParameters();
        setDefaultRatioPar();
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
        setFlagsListener();

    }

    /* Hårdkodning */
    private void initComboBoxes() {

        ArrayList<String> ratioSmooth = new ArrayList<String>();
        /* TEST */
        ratioSmooth.add("Median");
        ratioSmooth.add("Trimmed Mean");

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
    /* Borde bara vara filesPanel */
    private void addWestPanels() {

        this.add(westPanel, BorderLayout.WEST);
        filesPanel.setBorder(BorderFactory.createTitledBorder("Files"));
        addFilesScheduleToWestPanel();
        // addScheduleProcPanel();

    }

    /**
     * Initiates the center panel in the process tabs borderlayout.
     */
    /* Borde bara vara consolePanel */
    private void addMiddlePanel() {

        this.add(middlePanel, BorderLayout.CENTER);
        // addGenProfileDataPanel();
        // addGenRegionDataPanel();
        addConvertFilesPanel();

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
                scrollProcessList.setPreferredSize(new Dimension(200, 700));
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
                        Format format = new SimpleDateFormat(
                                "yyyy-MM-dd, HH:mm");
                        ProcessFeedbackData data = processFeedbackData[i];
                        String timeAdded = "Not added";
                        String timeStarted = "Not started";
                        String timeFinished = "Not finished";
                        if (data.timeAdded != 0) {
                            timeAdded = format.format(new Date(data.timeAdded))
                                    .toString();
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
                                "<html><b>ExpID</b>: " + data.experimentName
                                        + "</html>");
                        DefaultMutableTreeNode addedTimeNode = new DefaultMutableTreeNode(
                                "<html><u>Time Added</u>: " + timeAdded
                                        + "</html>");
                        DefaultMutableTreeNode startedTimeNode = new DefaultMutableTreeNode(
                                "<html><u>Time Started</u>: " + timeStarted
                                        + "</html>");
                        DefaultMutableTreeNode finishedTimeNode = new DefaultMutableTreeNode(
                                "<html><u>Time Finished</u>: " + timeFinished
                                        + "</html>");

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
                // create the tree by passing in the root node
                JTree tree = new JTree(root);
                tree.setRootVisible(false);
                DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree
                        .getCellRenderer();
                renderer.setLeafIcon(null);
                renderer.setClosedIcon(null);
                renderer.setOpenIcon(null);
                scrollProcessList.setViewportView(tree);
                addToFileListButton = new JButton("Add to file list");
                addToFileListButton.setEnabled(false);
                procInfoSouthPanel.add(addToFileListButton);
                procInfoSouthPanel.add(Box.createHorizontalStrut(35));
                procInfoSouthPanel.add(processFeedbackButton);
            }
        });
    }

    /**
     * Initiates the convertFilesPanel in the center panel.
     */
    /* Tabort fragetacekn pa consolePanel */
    private void addConvertFilesPanel() {
        middlePanel.add(consolePanel, BorderLayout.CENTER);
        consolePanel.setBorder(BorderFactory.createTitledBorder("Console"));
    }

    /**
     * Initiates the genRegionDataPanel in the center panel.
     */
    /* Tabort */
    private void addGenRegionDataPanel() {
        middlePanel.add(genRegionDataPanel, BorderLayout.CENTER);
        genRegionDataPanel.setBorder(BorderFactory
                .createTitledBorder("Generate Region Data"));
        addScrollGenRegionData();
    }

    /**
     * Adds scrollpane to genRegionData in the genRedionDataPanel.
     */
    /* Tabort */
    private void addScrollGenRegionData() {
        genRegionDataPanel.add(scrollRegion, BorderLayout.CENTER);
        scrollRegion.setViewportView(genRegArea);
        genRegArea.setEditable(false);
        genRegionDataPanel.add(textArea);
    }

    /**
     * Initiates the genProfileDataPanel in the center panel.
     */
    /* Tabort */
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
    /* Bytanamn */
    private void addTimePanel() {
        this.add(timePanel, BorderLayout.SOUTH);
        timePanel.setPreferredSize(new Dimension(300, 30));
    }

    /**
     * Initiates the scrollSchedule in scheduleProcPanel.
     */
    /*
     * private void addScheduleProcPanel() {
     * scheduleProcPanel.setBorder(BorderFactory
     * .createTitledBorder("Scheduled Processing"));
     * scheduleProcPanel.add(scrollSchedule);
     * scrollSchedule.setViewportView(scheduleList); }
     */

    /**
     * Writes text to convertArea. The user gets a visual message whether or not
     * the conversion succeeded.
     */
    /* Fragetecken */
    private void addConvertTextArea() {

        consolePanel.add(scrollConvert);
        scrollConvert.setViewportView(consoleArea);
        consoleArea.setEditable(false);
    }

    /**
     * Initiates filesPanel in westPanel.
     */
    private void addFilesScheduleToWestPanel() {
        westPanel.add(filesPanel);
        filesPanel.add(scrollFiles);

        scrollFiles.setViewportView(fileList);
        // westPanel.add(scheduleProcPanel);
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

        checkBoxPanel.setLayout(new GridLayout(2, 0));
        lowerCheckBoxPanel.setLayout(new GridBagLayout());
        lowerCheckBoxPanel.setBorder(BorderFactory.createTitledBorder("Format"));
        lowerCheckBoxPanel.setPreferredSize(new Dimension(100, 80));
        GridBagConstraints gbc1 = new GridBagConstraints();

        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.insets = new Insets(0, 0, 0, 5);
        gbc1.gridx = 1;
        gbc1.gridy = 1;
        lowerCheckBoxPanel.add(outputSAM, gbc1);

        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.insets = new Insets(0, 0, 0, 5);
        gbc1.gridx = 1;
        gbc1.gridy = 2;
        lowerCheckBoxPanel.add(outputGFF, gbc1);

        gbc1.fill = GridBagConstraints.BOTH;
        gbc1.insets = new Insets(0, 0, 0, 5);
        gbc1.gridx = 1;
        gbc1.gridy = 3;
        lowerCheckBoxPanel.add(outputSGR, gbc1);
        convPanel.add(lowerCheckBoxPanel);

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
   //     checkBoxPanel.setAlignmentY(1.0f);
  //      checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Checkboxes"));
 //       convPanel.add(checkBoxPanel);

      //  checkBoxPanel.setLayout(new GridLayout(2, 0));
     //   lowerCheckBoxPanel.setLayout(new GridBagLayout());
        upperCheckBoxPanel.setLayout(new GridBagLayout());
/*
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
        gbc2.insets = new Insets(0, 10, 0, 0);
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        upperCheckBoxPanel.add(printZeros, gbc2);

        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 10, 0, 0);
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        upperCheckBoxPanel.add(stepSizeBox, gbc2);
        */
/*
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 0, 0, 5);
        gbc2.gridx = 0;
        gbc2.gridy = 1;
        lowerCheckBoxPanel.add(useSmoothing, gbc2);
*/
        /*
        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 0, 0, 5);
        gbc2.gridx = 1;
        gbc2.gridy = 1;
        lowerCheckBoxPanel.add(outputSAM, gbc2);

        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 0, 0, 5);
        gbc2.gridx = 2;
        gbc2.gridy = 1;
        lowerCheckBoxPanel.add(outputGFF, gbc2);

        gbc2.fill = GridBagConstraints.BOTH;
        gbc2.insets = new Insets(0, 5, 0, 0);
        gbc2.gridx = 3;
        gbc2.gridy = 1;
        lowerCheckBoxPanel.add(outputSGR, gbc2);*/

        checkBoxPanel.setPreferredSize(new Dimension(315, 81));

        convPanel.add(stepSizePanel);
        stepSizePanel.setBorder(BorderFactory.createTitledBorder("Step size"));

      //  tabbedPane.addTab("Convert to WIG", null, convWigTabPanel, null);

      //  tabbedPane.addTab("Create region data", null, createRegTabPanel, null);
    }

    /**
     * Initiates the flag text field in raw to profile tab
     */
    private void addFlagsToConv() {
        flagsPanel.add(flags);
        flags.setBorder(null);
        flags.setText("-a -m 1 --best -p 10 -v 2 -q -S");
        flags.setPreferredSize(new Dimension(180, 30));
    }

    /**
     * Initiates the genome files combobox in raw to profile tab
     */
    private void addGenomeFileToConv() {
        genomeReleasePanel.add(genomeFile);
        genomeFile.setPreferredSize(new Dimension(180, 30));
        genomeFile.setBorder(null);
    }

    /**
     * Initiates the window size text field in raw to profile tab
     */
    private void addSmoothWindowSizeToConv() {
        windowSizePanel.add(smoothWindowSize);
        smoothWindowSize.setPreferredSize(new Dimension(70, 30));
        smoothWindowSize.setBorder(null);
        smoothWindowSize.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the smooth type text field in raw to profile tab
     */
    private void addSmoothTypeToConv() {
        smoothTypePanel.add(smoothType);
        smoothType.setPreferredSize(new Dimension(140, 30));
        smoothType.setBorder(null);
    }

    /**
     * Initiates the step position text field in raw to profile tab
     */
    private void addStepPositionToConv() {
        stepPositionPanel.add(stepPosition);
        stepPosition.setPreferredSize(new Dimension(75, 30));
        stepPosition.setBorder(null);
        stepPosition.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the step size text field in raw to profile tab
     */
    private void addStepSizeToConv() {
        stepSizePanel.add(stepSize);
        stepSize.setPreferredSize(new Dimension(70, 30));
        stepSize.setBorder(null);
        stepSize.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Initiates the print mean checkbox in raw to profile tab
     */
    private void addPrintMeanToConv() {
        printMean.setPreferredSize(new Dimension(110, 30));
        printMean.setBorder(null);
        stepSizeBox.setPreferredSize(new Dimension(110, 30));
    }

    /**
     * Initiates the print zeros checkbox in raw to profile tab
     */
    private void addPrintZeroToConv() {
        printZeros.setPreferredSize(new Dimension(110, 30));
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

        buttonPanel.add(useSmoothing);
        buttonPanel.add(printMean);
        buttonPanel.add(printZeros);
        buttonPanel.add(stepSizeBox);

        buttonPanel.add(useRatio);
        buttonPanel.add(ratioCalcButton);
        buttonPanel.add(profileButton);
        // scheduleProcPanel.add(scheduleButton);

    }

    /**
     * Initiates default parameters in the raw to profile tab.
     */
    private void initBowtieParameters() {

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

    public JList getFileList() {
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

        if(!useSmoothing.isSelected()){
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
        } else {
            parameters[3] = "";
        }
        if (outputSGR.isSelected()) {
            parameters[4] = getSmoothingParameters();
        } else {
            parameters[4] = "";
        }

        parameters[5] = getStepSize();
        return parameters;
    }

    /**
     * Sets the genomeReleaseFiles list, Retrieves all genome files a specific
     * species has.
     *
     * @param genomeReleaseFiles
     */
    public void setGenomeReleaseFiles(ArrayList<String> genomeReleaseFiles) {
        this.genomeReleaseFiles = genomeReleaseFiles;
    }

    public void setGenomeFileList(GenomeReleaseData[] genomeReleases) {
        System.out.println("genomreleases: " + genomeReleases);
        genomeFile.removeAllItems();
        if (genomeReleases != null && genomeReleases.length > 0) {
            for (GenomeReleaseData version : genomeReleases) {
                if (version != null) {
                    genomeFile.addItem(version.getVersion());
                }
            }
        }
        check();
    }

    /**
     * Sets the fileData list with all selected files to process from workspace.
     *
     * @param allFileData
     */
    public void setFileInfo(ArrayList<FileData> allFileData,
            ArrayList<ExperimentData> experimentData) {
        this.experimentData = experimentData;
        parseFileData();
    }

    public void setDefaultRatioPar() {

        inputReads.setText("4");
        chromosome.setText("0");
        ratioWindowSize.setText("150");
        ratioSmoothType.setSelectedIndex(0);
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
        convertButton.addActionListener(listener);
    }

    public void addProcessFeedbackListener(ActionListener listener) {
        processFeedbackButton.addActionListener(listener);
    }

    public void addRawToProfileDataListener(ActionListener listener) {
        profileButton.addActionListener(listener);
    }

    public void addRawToRegionDataListener(ActionListener listener) {
        regionButton.addActionListener(listener);
    }

    public void addScheduleFileListener(ActionListener listener) {
        // scheduleButton.addActionListener(listener);
    }

    public void addRatioCalcListener(ActionListener listener) {
        ratioCalcButton.addActionListener(listener);
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

    public void setComboBoxActionListener(JComboBox combobox) {
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

    private void check() {

       // genomeFile.addItem("HEJ");

        if (genomeFile.getItemCount() > 0) {
            genomeFile.setEnabled(true);
            flags.setEnabled(true);
        } else {
            disableAllParameters();
        }

        if (genomeFile.isEnabled()) {
            profileButton.setEnabled(true);
            outputSGR.setEnabled(true);
            outputGFF.setEnabled(true);
            outputSAM.setEnabled(true);
        }
        if (outputSGR.isSelected() && outputSGR.isEnabled()) {
            //setSmoothingEnabled(true, "10", "5");
            useSmoothing.setEnabled(true);
        }else {
            useSmoothing.setEnabled(false);
        }

        if(useSmoothing.isEnabled() && useSmoothing.isSelected()){
            setSmoothingEnabled(true, "10", "5");
        } else {
            setSmoothingEnabled(false, "", "");
        }

        if (stepSizeBox.isSelected() && outputSGR.isSelected()
                && outputSGR.isEnabled()) {
            stepSize.setEnabled(true);
            stepSize.setText("10");
        } else {
            stepSize.setEnabled(false);
            stepSize.setText("");
        }

        if (stepSizeBox.isSelected() && stepSizeBox.isEnabled()) {
            useRatio.setEnabled(true);
        } else {
            useRatio.setEnabled(false);
            useRatio.setSelected(false);
        }

        if (useRatio.isSelected() && useRatio.isEnabled()) {
            ratioCalcButton.setEnabled(true);
        } else {
            ratioCalcButton.setEnabled(false);
        }

        if (outputSAM.isSelected() && outputSAM.isEnabled()) {
            setSmoothingEnabled(false, "", "");
        }


    }

    private void setSmoothingEnabled(boolean bool, String windowSize,
            String step) {
        smoothWindowSize.setEnabled(bool);
        smoothType.setEnabled(bool);
        stepPosition.setEnabled(bool);
        printMean.setEnabled(bool);
        printZeros.setEnabled(bool);
        stepSizeBox.setEnabled(bool);
        stepSize.setEnabled(bool);
      //  useSmoothing.setEnabled(bool);
        smoothWindowSize.setText(windowSize);
        stepPosition.setText(step);
    }

    private void disableAllParameters() {
        useRatio.setEnabled(false);
        ratioCalcButton.setEnabled(false);
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
        return useRatio.isSelected();
    }
}
