package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;

import util.FileData;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.ScrollPaneConstants;
import javax.swing.JProgressBar;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class ProcessTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;
	private final JList fileList = new JList();
	private final JList scheduleList = new JList();
	private final JList processList = new JList();

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
	/*TEST*/private final JScrollPane scrollFiles = new JScrollPane(fileList);

	private final JButton convertButton = new JButton("Convert to WIG");
	private final JButton profileButton = new JButton("Create profile data");
	private final JButton regionButton = new JButton("Create region data");
	private final JButton scheduleButton = new JButton("Schedule files");

	private final JCheckBox printMean = new JCheckBox("Print mean");
	private final JCheckBox printZeros = new JCheckBox("Print zeros");
	private final JComboBox genomeFile = new JComboBox();

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
			/*TEST*/itemList[i] = new CheckListItem( "[" + i + "] TEST.RAW" );
			/*TEST*/}

		/*TEST*/fileList.setListData(itemList);

	}

	private void addNorthPanel() {

		this.add(menuPanel, BorderLayout.NORTH);
		menuPanel.setPreferredSize(new Dimension(300, 150));
		addButtonsToMenu();
		enableButtons();

	}

	private void addWestPanels() {

		this.add(westPanel, BorderLayout.WEST);
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
		genRegArea.setPreferredSize(new Dimension(590, 155));

		genRegionDataPanel.add(textArea);
	}

	private void addGenProfileDataPanel() {
		middlePanel.add(genProfileDataPanel);
		genProfileDataPanel.setBorder(new TitledBorder(null, "Generate Profile Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollProfile.setPreferredSize(new Dimension(610, 145));

		genProfileDataPanel.add(scrollProfile);
		scrollProfile.setViewportView(genProfArea);
		genProfArea.setEditable(false);
		genProfArea.setPreferredSize(new Dimension(590, 155));
	}

	private void addProcessInQueue() {
		scrollProcessList.setPreferredSize(new Dimension(290, 510));

		procQueuePanel.add(scrollProcessList);
		scrollProcessList.setViewportView(processList);
		processList.setPreferredSize(new Dimension(270, 510));
	}

	private void addTimePanel() {
		this.add(timePanel, BorderLayout.SOUTH);
		timePanel.setPreferredSize(new Dimension(300, 30));
	}

	private void addScheduleProcPanel() {
		scheduleProcPanel.setBorder(new TitledBorder(null, "Scheduled Processing", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scheduleProcPanel.setPreferredSize(new Dimension(300, 100));
		scrollSchedule.setPreferredSize(new Dimension(290, 240));

		scheduleProcPanel.add(scrollSchedule);
		scrollSchedule.setViewportView(scheduleList);
		scheduleList.setPreferredSize(new Dimension(260, 260));
	}

	private void addConvertTextArea(){
		scrollConvert.setPreferredSize(new Dimension(610, 145));

		convertFilesPanel.add(scrollConvert);
		scrollConvert.setViewportView(convertArea);
		convertArea.setEditable(false);
		convertArea.setPreferredSize(new Dimension(590, 155));
	}

	private void addFilesScheduleToWestPanel() {
		westPanel.add(filesPanel);
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
		convPanel.setBorder(new TitledBorder(null, "Conversion parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		menuPanel.add(convPanel);
		menuPanel.add(buttonPanel);
	}

	private void addFlagsToConv() {
		convPanel.add(flags);
		flags.setBorder(new TitledBorder(null, "Flags", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		flags.setText("-a -m 1 --best -p 10 -v 2");
		flags.setPreferredSize(new Dimension(250, 50));
	}

	private void addGenomeFileToConv() {
		convPanel.add(genomeFile);
		genomeFile.setPreferredSize(new Dimension(230, 60));
		genomeFile.setBorder(new TitledBorder(null, "Genome release files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

	private void addSmoothWindowSizeToConv() {
		convPanel.add(smoothWindowSize);
		smoothWindowSize.setPreferredSize(new Dimension(110, 50));
		smoothWindowSize.setBorder(BorderFactory.createTitledBorder("Window size"));
	}

	private void addSmoothTypeToConv() {
		convPanel.add(smoothType);
		smoothType.setPreferredSize(new Dimension(110, 50));
		smoothType.setBorder(BorderFactory.createTitledBorder("Smooth type"));
	}

	private void addStepPositionToConv() {
		convPanel.add(stepPosition);
		stepPosition.setPreferredSize(new Dimension(110, 50));
		stepPosition.setBorder(BorderFactory.createTitledBorder("Step position"));
	}

	private void addStepSizeToConv() {
		convPanel.add(stepSize);
		stepSize.setPreferredSize(new Dimension(80, 50));
		stepSize.setBorder(BorderFactory.createTitledBorder("Step size"));
	}

	private void addPrintMeanToConv() {
		convPanel.add(printMean);
		printMean.setVerticalAlignment(SwingConstants.BOTTOM);
		printMean.setPreferredSize(new Dimension(110,50));
		printMean.setBorder(BorderFactory.createTitledBorder("Print mean"));
	}

	private void addPrintZeroToConv() {
		convPanel.add(printZeros);
		printZeros.setVerticalAlignment(SwingConstants.TOP);
		printZeros.setPreferredSize(new Dimension(110,50));
		printZeros.setBorder(BorderFactory.createTitledBorder("Print zeros"));
	}

	private void enableButtons() {

		buttonPanel.add(profileButton);
		buttonPanel.add(regionButton);
		regionButton.setEnabled(false);
		buttonPanel.add(convertButton);
		convertButton.setEnabled(false);
		buttonPanel.add(scheduleButton);
		scheduleButton.setEnabled(false);
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
		return stepSize.getText();
	}

	private void initBowtieParameters(){

		stepSize.setText("y 10");
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
		fileList.setPreferredSize(new Dimension(260, 245));
		fileList.setCellRenderer(new CheckListRenderer());
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	public JList getFileList(){
		return fileList;
	}

	private void initFileList() {
		fileListSetCellRenderer();
		initFileListScrollPane();
	}

	private void initFileListScrollPane() {
	//	scrollFiles = new JScrollPane(fileList);
		scrollFiles.setPreferredSize(new Dimension(300, 260));
		scrollFiles.setBorder(new TitledBorder(null, "Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		filesPanel.add(scrollFiles);
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

	public void printToConvertText(String message,String color) {

		convertArea.append(message);

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
