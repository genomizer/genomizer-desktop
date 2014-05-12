package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import util.FileData;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.JTabbedPane;

public class ProcessTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;
	private final JList<CheckListItem> fileList = new JList<CheckListItem>();
	private final JList<CheckListItem> scheduleList = new JList<CheckListItem>();
	private final JList<CheckListItem> processList = new JList<CheckListItem>();

	private final JPanel convPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();
	private final JPanel filesPanel = new JPanel();
	private final JPanel scheduleProcPanel = new JPanel();
	private final JPanel genProfileDataPanel = new JPanel();
	private final JPanel genRegionDataPanel = new JPanel();
	private final JPanel convertFilesPanel = new JPanel();
	private final JPanel procQueuePanel = new JPanel();
	private final JPanel menuPanel = new JPanel();
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
	private final JPanel convTabpanel = new JPanel();

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
	private final JCheckBox scheduleButton = new JCheckBox("Schedule files");

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

	private void initPanels() {

		addNorthPanel();
		addWestPanels();
		addMiddlePanel();
		addEastPanels();

		addTimePanel();
		addConvertTextArea();
		initFileList();

		/*TEST*/ArrayList<String> gFiles = new ArrayList<String>();
		/*TEST*/gFiles.add(0,"d_melanogaster_fb5_22");
		/*TEST*/gFiles.add(1,"E_melanogaster_fb6_23");
		/*TEST*/setGenomeReleaseFiles(gFiles);
		initBowtieParameters();
		writeToTimePanel();

		/*TEST*/CheckListItem[] itemList = new CheckListItem[20];

		/*TEST*/for(int i = 0; i < 20; i++){
		/*TEST*/	itemList[i] = new CheckListItem( "[" + i + "] TEST.RAW" );
		/*TEST*/}

		/*TEST*/fileList.setListData(itemList);

	}

	private void addNorthPanel() {
		menuPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
		menuPanel.setPreferredSize(new Dimension(1200, 175));

		this.add(menuPanel, BorderLayout.NORTH);
		addButtonsToMenu();
		enableButtons();

	}

	private void addWestPanels() {

		this.add(westPanel, BorderLayout.WEST);
		filesPanel.setBorder(new TitledBorder(null, "Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		filesPanel.setPreferredSize(new Dimension(300, 100));
		addFilesScheduleToWestPanel();
		addScheduleProcPanel();

	}

	private void addMiddlePanel() {

		this.add(middlePanel, BorderLayout.CENTER);
		addGenProfileDataPanel();
		addGenRegionDataPanel();
		addConvertFilesPanel();

	}

	private void addEastPanels() {
		procQueuePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processing In Queue", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(procQueuePanel, BorderLayout.EAST);
		procQueuePanel.setPreferredSize(new Dimension(300, 100));
		addProcessInQueue();

	}

	private void addConvertFilesPanel() {
		middlePanel.add(convertFilesPanel);
		convertFilesPanel.setBorder(new TitledBorder(null, "Convert Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

	private void addGenRegionDataPanel() {
		middlePanel.add(genRegionDataPanel);
		genRegionDataPanel.setBorder(new TitledBorder(null, "Generate Region Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollRegion.setPreferredSize(new Dimension(610, 145));
		addScrollGenRegionData();
	}

	private void addScrollGenRegionData() {
		genRegionDataPanel.add(scrollRegion);
		scrollRegion.setViewportView(genRegArea);
		genRegArea.setEditable(false);
		genRegArea.setPreferredSize(new Dimension(590, 135));

		genRegionDataPanel.add(textArea);
	}

	private void addGenProfileDataPanel() {
		middlePanel.add(genProfileDataPanel);
		genProfileDataPanel.setBorder(new TitledBorder(null, "Generate Profile Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollProfile.setPreferredSize(new Dimension(610, 145));

		genProfileDataPanel.add(scrollProfile);
		scrollProfile.setViewportView(genProfArea);
		genProfArea.setEditable(false);
		genProfArea.setPreferredSize(new Dimension(590, 140));
	}

	private void addProcessInQueue() {
		scrollProcessList.setPreferredSize(new Dimension(290, 491));

		procQueuePanel.add(scrollProcessList);
		scrollProcessList.setViewportView(processList);
		processList.setPreferredSize(new Dimension(270, 450));
	}

	private void addTimePanel() {
		this.add(timePanel, BorderLayout.SOUTH);
		timePanel.setPreferredSize(new Dimension(300, 30));
	}

	private void addScheduleProcPanel() {
		scheduleProcPanel.setBorder(new TitledBorder(null, "Scheduled Processing", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scheduleProcPanel.setPreferredSize(new Dimension(300, 100));
		scrollSchedule.setPreferredSize(new Dimension(290, 203));

		scheduleProcPanel.add(scrollSchedule);
		scrollSchedule.setViewportView(scheduleList);
		scheduleList.setPreferredSize(new Dimension(260, 200));
	}

	private void addConvertTextArea(){
		scrollConvert.setPreferredSize(new Dimension(610, 145));

		convertFilesPanel.add(scrollConvert);
		scrollConvert.setViewportView(convertArea);
		convertArea.setEditable(false);
		convertArea.setPreferredSize(new Dimension(590, 140));
	}

	private void addFilesScheduleToWestPanel() {
		westPanel.add(filesPanel);
		scrollFiles.setPreferredSize(new Dimension(290, 230));

		filesPanel.add(scrollFiles);

		scrollFiles.setViewportView(fileList);
		westPanel.add(scheduleProcPanel);
	}

	private void addButtonsToMenu() {
		addPanelsToMenu();
		addFlagsToConv();
		addGenomeFileToConv();
		addSmoothWindowSizeToConv();
		addSmoothTypeToConv();
		addStepPositionToConv();
		addStepSizeToConv();
		addPrintMeanToConv();
		addPrintZeroToConv();
	}

	private void addPanelsToMenu() {
		menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		menuPanel.add(tabbedPane);
		convTabpanel.setPreferredSize(new Dimension(1222, 135));

		tabbedPane.addTab("Create profile data", null, convTabpanel, null);
		convTabpanel.add(convPanel);
		convPanel.add(flagsPanel);
		flagsPanel.setBorder(new TitledBorder(null, "Flags", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		convPanel.add(genomeReleasePanel);
		genomeReleasePanel.setBorder(new TitledBorder(null, "Genome release files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		convPanel.add(windowSizePanel);
		windowSizePanel.setBorder(new TitledBorder(null, "Window size", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		convPanel.add(smoothTypePanel);
		smoothTypePanel.setBorder(new TitledBorder(null, "Smooth type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		convPanel.add(stepPositionPanel);
		stepPositionPanel.setBorder(new TitledBorder(null, "Step position", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		convPanel.setBorder(null);
		checkBoxPanel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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
		gbl_checkBoxPanel.columnWidths = new int[]{110, 110, 110, 0};
		gbl_checkBoxPanel.rowHeights = new int[]{50, 0};
		gbl_checkBoxPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_checkBoxPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};

		checkBoxPanel.setLayout(gbl_checkBoxPanel);
		convPanel.add(stepSizePanel);
		stepSizePanel.setBorder(new TitledBorder(null, "Step size", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		stepSizeBox.setVerticalAlignment(JCheckBox.BOTTOM);
		printMean.setVerticalAlignment(JCheckBox.TOP);
		printZeros.setVerticalAlignment(JCheckBox.TOP);

		tabbedPane.addTab("Convert to WIG", null, convWigTabPanel, null);

		tabbedPane.addTab("Create region data", null, createRegTabPanel, null);
	}

	private void addFlagsToConv() {
		flagsPanel.add(flags);
		flags.setBorder(null);
		flags.setText("-a -m 1 --best -p 10 -v 2");
		flags.setPreferredSize(new Dimension(175, 45));
	}

	private void addGenomeFileToConv() {
		genomeReleasePanel.add(genomeFile);
		genomeFile.setPreferredSize(new Dimension(175, 45));
		genomeFile.setBorder(null);
	}

	private void addSmoothWindowSizeToConv() {
		windowSizePanel.add(smoothWindowSize);
		smoothWindowSize.setPreferredSize(new Dimension(90, 45));
		smoothWindowSize.setBorder(null);
		smoothWindowSize.setHorizontalAlignment(JTextField.CENTER);
	}

	private void addSmoothTypeToConv() {
		smoothTypePanel.add(smoothType);
		smoothType.setPreferredSize(new Dimension(90, 45));
		smoothType.setBorder(null);
		smoothType.setHorizontalAlignment(JTextField.CENTER);
	}

	private void addStepPositionToConv() {
		stepPositionPanel.add(stepPosition);
		stepPosition.setPreferredSize(new Dimension(90, 45));
		stepPosition.setBorder(null);
		stepPosition.setHorizontalAlignment(JTextField.CENTER);
	}

	private void addStepSizeToConv() {
		stepSizePanel.add(stepSize);
		stepSize.setPreferredSize(new Dimension(80, 45));
		stepSize.setBorder(null);
		stepSize.setHorizontalAlignment(JTextField.CENTER);
	}

	private void addPrintMeanToConv() {
		printMean.setPreferredSize(new Dimension(110, 65));
		printMean.setBorder(BorderFactory.createTitledBorder("Print mean"));
		stepSizeBox.setSelected(true);

		//TODO Flytta lyssnare
		stepSizeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(stepSizeBox.isSelected()){
					stepSize.setEnabled(true);
					stepSize.setText("10");
				}else{
					stepSize.setEnabled(false);
					stepSize.setText("");
				}
			}
		});
	//	stepSizeBox.setMinimumSize(new Dimension(77, 23));
	//	stepSizeBox.setMaximumSize(new Dimension(77, 23));
		stepSizeBox.setPreferredSize(new Dimension(85, 65));
	}

	private void addPrintZeroToConv() {
		printZeros.setPreferredSize(new Dimension(110, 65));
		printZeros.setBorder(BorderFactory.createTitledBorder("Print zeros"));
	}

	private void enableButtons() {
		createRegTabPanel.add(regionButton);
		regionButton.setEnabled(false);
		convWigTabPanel.add(convertButton);
		convertButton.setEnabled(false);
		convTabpanel.add(buttonPanel);
		buttonPanel.add(profileButton);
		scheduleProcPanel.add(scheduleButton);
		scheduleButton.setPreferredSize(new Dimension(297, 23));
	}

	public String[] getBowtieParameters(){
		return this.bowtieParameters;
	}

	public void setBowtieParameters(){

		bowtieParameters[0] = getTextFromBowtieParOne(); //"-a -m 1 --best -p 10 -v 2";
		bowtieParameters[1] = getTextGenomeFileName(); //"d_melanogaster_fb5_22";
		bowtieParameters[2] = getSmoothingParameters(); //"10 1 5 0 1";
		bowtieParameters[3] = getStepSize(); //"y 10";

	}

	private String getStepSize() {
		if(stepSizeBox.isSelected()){
			return "y " + stepSize.getText().trim();
		}else {
			return "";
		}
	}

	private void initBowtieParameters(){

		stepSize.setText("10");
		smoothWindowSize.setText("10");
		smoothType.setText("1");
		stepPosition.setText("5");
		genomeFile.removeAllItems();

		for(int i = 0; i < genomeReleaseFiles.size(); i++){
			genomeFile.addItem(genomeReleaseFiles.get(i));
		}
	}

	//TODO
	public void setGenomeReleaseFiles(ArrayList<String> genomeReleaseFiles){
		this.genomeReleaseFiles = genomeReleaseFiles;
	}


	private String getSmoothingParameters() {
		String smoothPar;
		String printmean = "0";
		String printzeros = "0";

		smoothPar = smoothWindowSize.getText().trim() + " " + smoothType.getText().trim() + " " + stepPosition.getText().trim();

		if(printMean.isSelected()){
			printmean = "1";
		}

		if(printZeros.isSelected()){
			printzeros = "1";
		}

		return smoothPar + " " + printmean.trim() + " " + printzeros.trim();
	}

	private String getTextGenomeFileName() {
		return genomeFile.getSelectedItem().toString().trim();
	}

	private String getTextFromBowtieParOne() {
		return flags.getText().trim();
	}

	public void setFileInfo(ArrayList<FileData> allFileData){
		this.fileData = allFileData;
		parseFileData();
	}

	private void parseFileData(){

		CheckListItem[] itemList = new CheckListItem[fileData.size()];

		for(int i = 0; i < fileData.size(); i++){
			itemList[i] = new CheckListItem( fileData.get(i).filename );
		}

		fileList.setListData(itemList);
		this.revalidate();
		this.repaint();
	}

	private void fileListSetCellRenderer() {
		fileList.setCellRenderer(new CheckListRenderer());
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public JList getFileList(){
		return fileList;
	}

	private void initFileList() {
		fileListSetCellRenderer();
	}

	public ArrayList<String> getAllMarkedFiles() {

		ArrayList<String> arr = new ArrayList<String>();

		for (int i = 0; i < fileList.getModel().getSize(); i++) {
			CheckListItem checkItem = (CheckListItem) fileList.getModel()
					.getElementAt(i);
			checkItemIsSelected(arr, checkItem);
		}
		return arr;
	}

	public ArrayList<FileData> getAllMarkedFileData(){

		ArrayList<FileData> allMarked = new ArrayList<FileData>();
		ArrayList<String> arr = getAllMarkedFiles();

		if(!(fileData == null)){
			for(int i = 0; i < fileData.size(); i++){
				if(arr.contains(fileData.get(i).filename)){
					allMarked.add( fileData.get(i) );
				}
			}
		}
		return allMarked;
	}

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
		scheduleButton.addActionListener(listener);
	}

	private int getNumberOfJobsInQueue() {
		return this.processList.countComponents();
	}

	private int getTimeApprox() {
		return 450;
	}

	public void printToProfileText(String message,String color) {

		genProfArea.append(message);

		if(color.equals("red")){
			genProfArea.setForeground(Color.RED);
		}
	}

	private void writeToTimePanel() {

		timeArea.setText("");
		timeArea.setEditable(false);
		timeArea.append("Number of jobs currently in queue: "
				+ getNumberOfJobsInQueue() + " (est. time until empty : "
				+ getTimeApprox() + " min )");
		timePanel.add(timeArea);

	}


}
