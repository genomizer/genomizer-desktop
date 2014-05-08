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

public class ProcessTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;

	private String[] bowtieParameters = new String[4];
	private JTextArea timeArea = new JTextArea();
	private JTextArea convertArea = new JTextArea();
	private JTextField bowtiePar = new JTextField();

	//TODO genomeFile bör vara en dropdown-meny som ser vilka genomerelease-filer databasen har.
	private JTextField genomeFile = new JTextField();
	private JTextField SmoothWindowSize = new JTextField();
	private JTextField SmoothType = new JTextField();
	private JTextField StepPosition = new JTextField();
	private JCheckBox printMean = new JCheckBox("Print mean");
	private JCheckBox printZeros = new JCheckBox("Print zeros");
	private JTextField stepSize = new JTextField();

	private JPanel filesPanel = new JPanel();
	private JPanel scheduleProcPanel = new JPanel();
	private JPanel genProfileDataPanel = new JPanel();
	private JPanel genRegionDataPanel = new JPanel();
	private JPanel convertFilesPanel = new JPanel();
	private JPanel procQueuePanel = new JPanel();
	private JPanel menuPanel = new JPanel();
	private JPanel timePanel = new JPanel();
	private JPanel middelPanel = new JPanel(new GridLayout(3, 1));
	private JPanel leftPanel = new JPanel(new GridLayout(2, 1));
	private JButton convertButton = new JButton("CONVERT TO WIG");
	private JButton profileButton = new JButton("CREATE PROFILE DATA");
	private JButton regionButton = new JButton("CREATE REGION DATA");
	private JButton scheduleButton = new JButton("SCHEDULE");
	private JScrollPane scrollFiles = new JScrollPane();
	private DefaultListModel fileListModel = new DefaultListModel();
	private JList fileList = new JList();
	// TODO SKA VARA JLIST
	private ArrayList processQueue = new ArrayList();
	private ArrayList<FileData> fileData;
	private final JPanel buttonPanel = new JPanel();
	private final JPanel panel = new JPanel();
	private final JPanel panel_1 = new JPanel();
	private final JTextField textField = new JTextField();

	public ProcessTab() {
		setPreferredSize(new Dimension(1225, 725));
		setMinimumSize(new Dimension(20000, 20000));
		this.setLayout(new BorderLayout());
		initPanels();
	}

	private void initPanels() {

		initNorthPanel();
		initWestPanels();
		initMiddlePanel();
		initEastPanels();
		initTimePanel();
		writeToTimePanel();
		initConvertTextArea();
		initFileList();
		writeToTimePanel();
		initBowtieParameters();

	}

	private void initNorthPanel() {

		this.add(menuPanel, BorderLayout.NORTH);
		menuPanel.setPreferredSize(new Dimension(300, 150));
		addButtonsToMenu();
		enableButtons();

	}

	private void initWestPanels() {

		this.add(leftPanel, BorderLayout.WEST);
		filesPanel.setPreferredSize(new Dimension(300, 100));
		initLeftPanel();
		initScheduleProcPanel();

	}

	private void initMiddlePanel() {

		this.add(middelPanel, BorderLayout.CENTER);
		initGenProfileDataPanel();
		initGenRegionDataPanel();
		initConvertFilesPanel();

	}

	private void initEastPanels() {
		this.add(procQueuePanel, BorderLayout.EAST);
		procQueuePanel.setPreferredSize(new Dimension(300, 100));
		initProcessInQueue();

	}

	private void initConvertFilesPanel() {
		middelPanel.add(convertFilesPanel);
		convertFilesPanel.setBorder(BorderFactory
				.createTitledBorder("CONVERT FILES"));
	}

	private void initGenRegionDataPanel() {
		middelPanel.add(genRegionDataPanel);
		genRegionDataPanel.setBorder(BorderFactory
				.createTitledBorder("GENERATE REGION DATA"));
	}

	private void initGenProfileDataPanel() {
		middelPanel.add(genProfileDataPanel);
		genProfileDataPanel.setBorder(BorderFactory
				.createTitledBorder("GENERATE PROFILE DATA"));
	}

	private void initProcessInQueue() {
		JLabel queueLabel = new JLabel("PROCESSING IN QUEUE");
		queueLabel.setOpaque(true);
		procQueuePanel.add(queueLabel);
	}

	private void initTimePanel() {
		this.add(timePanel, BorderLayout.SOUTH);
		timePanel.setPreferredSize(new Dimension(300, 30));
	}

	private void initScheduleProcPanel() {
		scheduleProcPanel.setBorder(BorderFactory
				.createTitledBorder("SCHEDULE PROCESSING"));
		scheduleProcPanel.setPreferredSize(new Dimension(300, 100));
	}

	private void initConvertTextArea(){
		convertArea.setEditable(false);
		convertArea.setPreferredSize(new Dimension(600, 150));
		convertFilesPanel.add(convertArea);
	}

	private void initLeftPanel() {
		leftPanel.add(filesPanel);
		leftPanel.add(scheduleProcPanel);
	}

	private void addButtonsToMenu() {
		menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		menuPanel.add(buttonPanel);

		menuPanel.add(panel_1);
		panel_1.add(bowtiePar);
		bowtiePar.setBorder(new TitledBorder(null, "Flags", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bowtiePar.setText("-a -m 1 --best -p 10 -v 2");
		bowtiePar.setPreferredSize(new Dimension(250, 40));
		genomeFile.setPreferredSize(new Dimension(150, 40));
		panel_1.add(genomeFile);

		genomeFile.setBorder(new TitledBorder(null, "Genome release files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Conversion parameters", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		menuPanel.add(panel);
		panel.add(SmoothWindowSize);
		SmoothWindowSize.setPreferredSize(new Dimension(100, 40));
		SmoothWindowSize.setBorder(BorderFactory.createTitledBorder("Window size"));
		SmoothType.setPreferredSize(new Dimension(100, 40));
		panel.add(SmoothType);
		SmoothType.setBorder(BorderFactory.createTitledBorder("Smooth type"));
		StepPosition.setPreferredSize(new Dimension(100, 40));
		panel.add(StepPosition);
		StepPosition.setBorder(BorderFactory.createTitledBorder("Step position"));
		stepSize.setPreferredSize(new Dimension(80, 40));
		panel.add(stepSize);
		stepSize.setBorder(BorderFactory.createTitledBorder("Step size"));
		panel.add(printMean);

		//CHECKBOX
		printMean.setBorder(BorderFactory.createTitledBorder("Print mean"));
		panel.add(printZeros);

		//Checkbox
		printZeros.setBorder(BorderFactory.createTitledBorder("Print zeros"));
		textField.setText("d_melanogaster_fb5_22");
		textField.setPreferredSize(new Dimension(150, 40));
		textField.setBorder(BorderFactory.createTitledBorder("Genome file"));

		menuPanel.add(textField);
	}

	private void enableButtons() {
		buttonPanel.add(convertButton);
		convertButton.setEnabled(false);
		buttonPanel.add(profileButton);
		buttonPanel.add(regionButton);
		regionButton.setEnabled(false);
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
		genomeFile.setText("d_melanogaster_fb5_22");
		SmoothWindowSize.setText("10");
		SmoothType.setText("1");
		StepPosition.setText("5");

	}

	private String getSmoothingParameters() {
		String smoothPar;
		String printmean = "0";
		String printzeros = "0";
		smoothPar = SmoothWindowSize.getText() + " " + SmoothType.getText() + " " + StepPosition.getText();

		if(printMean.isSelected()){
			printmean = "1"; 
		}

		if(printZeros.isSelected()){
			printzeros = "1";
		}

		return smoothPar + " " + printmean + " " + printzeros;
	}

	private String getTextGenomeFileName() {
		return genomeFile.getText();
	}

	private String getTextFromBowtieParOne() {
		return bowtiePar.getText();
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

	private void resetFilesPanel() {

		filesPanel.removeAll();
		leftPanel.remove(filesPanel);
		filesPanel = new JPanel();
		leftPanel.remove(filesPanel);
		leftPanel.add(filesPanel);

	}

	private void setList(JList fileList) {
		this.fileList = fileList;
	}

	private void initList(JList fileList) {
		this.fileList = fileList;
	}

	private void fileListSetCellRenderer() {
		fileList.setCellRenderer(new CheckListRenderer());
		fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}

	private void initFileList() {

		fileListSetCellRenderer();
		fileListAddMouseListener();
		initScrollPane();
	}

	private void initScrollPane() {
		scrollFiles = new JScrollPane(fileList);
		scrollFiles.setBorder(BorderFactory.createTitledBorder("FILES"));
		fileList.setFixedCellWidth(280);
		fileList.setFixedCellHeight(33);
		filesPanel.add(scrollFiles);
	}

	private void fileListAddMouseListener() {
		fileList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				JList list = (JList) event.getSource();

				int index = list.locationToIndex(event.getPoint());
				CheckListItem item = (CheckListItem) list.getModel()
						.getElementAt(index);

				item.setSelected(!item.isSelected());

				list.repaint(list.getCellBounds(index, index));
			}
		});
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

				for(int j = 0; j < arr.size(); j++){

					if(arr.get(j) == fileData.get(i).filename ){
						allMarked.add( fileData.get(i) );
					}
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
		// fileList.addMouseListener(mouseAdapter);
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
		return this.processQueue.size();
	}

	// TODO Returnera den riktiga tidsapproxen
	private int getTimeApprox() {
		return 450;
	}

	public void printToConvertText(String message) {

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
