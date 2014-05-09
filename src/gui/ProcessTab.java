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

	private ArrayList<String> genomeReleaseFiles;
	private String[] bowtieParameters = new String[4];
	private JTextArea timeArea = new JTextArea();
	private JTextArea convertArea = new JTextArea();
	private JTextField bowtiePar = new JTextField();
	private JComboBox genomeFile = new JComboBox();
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
	private JButton convertButton = new JButton("Convert to WIG");
	private JButton profileButton = new JButton("Create profile data");
	private JButton regionButton = new JButton("Create region data");
	private JButton scheduleButton = new JButton("Schedule files");
	private JScrollPane scrollFiles = new JScrollPane();
	private DefaultListModel fileListModel = new DefaultListModel();
	private JList fileList = new JList();
	// TODO SKA VARA JLIST
	private ArrayList processQueue = new ArrayList();
	private ArrayList<FileData> fileData;
	private final JPanel convPanel = new JPanel();
	private final JPanel buttonPanel = new JPanel();
	private final JList scheduleList = new JList();
	private final JTextArea textArea = new JTextArea();
	private final JTextArea genProfArea = new JTextArea();
	private final JTextArea genRegArea = new JTextArea();
	private final JList processList = new JList();
	private final JScrollPane scrollSchedule = new JScrollPane();
	private final JScrollPane scrollConvert = new JScrollPane();
	private final JScrollPane scrollRegion = new JScrollPane();
	private final JScrollPane scrollProfile = new JScrollPane();
	private final JScrollPane scrollProcessList = new JScrollPane();

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

		/*TEST*/ArrayList<String> gFiles = new ArrayList<String>();
		/*TEST*/gFiles.add(0,"d_melanogaster_fb5_22");
		/*TEST*/gFiles.add(1,"E_melanogaster_fb6_23");			
		/*TEST*/setGenomeReleaseFiles(gFiles);
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
		procQueuePanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Processing In Queue", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.add(procQueuePanel, BorderLayout.EAST);
		procQueuePanel.setPreferredSize(new Dimension(300, 100));
		initProcessInQueue();

	}

	private void initConvertFilesPanel() {
		middelPanel.add(convertFilesPanel);
		convertFilesPanel.setBorder(new TitledBorder(null, "Convert Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	}

	private void initGenRegionDataPanel() {
		middelPanel.add(genRegionDataPanel);
		genRegionDataPanel.setBorder(new TitledBorder(null, "Generate Region Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollRegion.setPreferredSize(new Dimension(610, 145));

		genRegionDataPanel.add(scrollRegion);
		scrollRegion.setViewportView(genRegArea);
		genRegArea.setEditable(false);
		genRegArea.setPreferredSize(new Dimension(590, 155));

		genRegionDataPanel.add(textArea);
	}

	private void initGenProfileDataPanel() {
		middelPanel.add(genProfileDataPanel);
		genProfileDataPanel.setBorder(new TitledBorder(null, "Generate Profile Data", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollProfile.setPreferredSize(new Dimension(610, 145));

		genProfileDataPanel.add(scrollProfile);
		scrollProfile.setViewportView(genProfArea);
		genProfArea.setEditable(false);
		genProfArea.setPreferredSize(new Dimension(590, 155));
	}

	private void initProcessInQueue() {
		scrollProcessList.setPreferredSize(new Dimension(290, 510));

		procQueuePanel.add(scrollProcessList);
		scrollProcessList.setViewportView(processList);
		processList.setPreferredSize(new Dimension(270, 510));
	}

	private void initTimePanel() {
		this.add(timePanel, BorderLayout.SOUTH);
		timePanel.setPreferredSize(new Dimension(300, 30));
	}

	private void initScheduleProcPanel() {
		scheduleProcPanel.setBorder(new TitledBorder(null, "Scheduled Processing", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scheduleProcPanel.setPreferredSize(new Dimension(300, 100));
		scrollSchedule.setPreferredSize(new Dimension(290, 240));

		scheduleProcPanel.add(scrollSchedule);
		scrollSchedule.setViewportView(scheduleList);
		scheduleList.setPreferredSize(new Dimension(260, 260));
	}

	private void initConvertTextArea(){
		scrollConvert.setPreferredSize(new Dimension(610, 145));

		convertFilesPanel.add(scrollConvert);
		scrollConvert.setViewportView(convertArea);
		convertArea.setEditable(false);
		convertArea.setPreferredSize(new Dimension(590, 155));
	}

	private void initLeftPanel() {
		leftPanel.add(filesPanel);
		leftPanel.add(scheduleProcPanel);
	}

	private void addButtonsToMenu() {
		menuPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		convPanel.setBorder(new TitledBorder(null, "Conversion parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		menuPanel.add(convPanel);
		buttonPanel.setBorder(null);
		menuPanel.add(buttonPanel);
		convPanel.add(bowtiePar);
		bowtiePar.setBorder(new TitledBorder(null, "Flags", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bowtiePar.setText("-a -m 1 --best -p 10 -v 2");
		bowtiePar.setPreferredSize(new Dimension(250, 40));
		convPanel.add(genomeFile);
		genomeFile.setPreferredSize(new Dimension(200, 40));
		
				genomeFile.setBorder(new TitledBorder(null, "Genome release files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		convPanel.add(SmoothWindowSize);
		SmoothWindowSize.setPreferredSize(new Dimension(100, 40));
		SmoothWindowSize.setBorder(BorderFactory.createTitledBorder("Window size"));
		convPanel.add(SmoothType);
		SmoothType.setPreferredSize(new Dimension(100, 40));
		SmoothType.setBorder(BorderFactory.createTitledBorder("Smooth type"));
		convPanel.add(StepPosition);
		StepPosition.setPreferredSize(new Dimension(100, 40));
		StepPosition.setBorder(BorderFactory.createTitledBorder("Step position"));
		convPanel.add(stepSize);
		stepSize.setPreferredSize(new Dimension(80, 40));
		stepSize.setBorder(BorderFactory.createTitledBorder("Step size"));
		convPanel.add(printMean);
		printMean.setVerticalAlignment(SwingConstants.BOTTOM);
		
				printMean.setBorder(BorderFactory.createTitledBorder("Print mean"));
				convPanel.add(printZeros);
				printZeros.setVerticalAlignment(SwingConstants.TOP);
				
						printZeros.setBorder(BorderFactory.createTitledBorder("Print zeros"));
		buttonPanel.add(profileButton);
	}

	private void enableButtons() {
		buttonPanel.add(regionButton);
		regionButton.setEnabled(false);
		buttonPanel.add(convertButton);
		convertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
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
		SmoothWindowSize.setText("10");
		SmoothType.setText("1");
		StepPosition.setText("5");
		genomeFile.removeAllItems();
		for(int i = 0; i < genomeReleaseFiles.size(); i++){
			genomeFile.addItem(genomeReleaseFiles.get(i));
		}
	}

	//TODO listan på GenomeRelease-filerna som databasen har måste hämtas och sättas här
	public void setGenomeReleaseFiles(ArrayList<String> genomeReleaseFiles){
		this.genomeReleaseFiles = genomeReleaseFiles;
	}


	private String getSmoothingParameters() {
		String smoothPar;
		String printmean = "0";
		String printzeros = "0";
		smoothPar = SmoothWindowSize.getText().trim() + " " + SmoothType.getText().trim() + " " + StepPosition.getText().trim();

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
		return bowtiePar.getText().trim();
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
		fileList.setPreferredSize(new Dimension(260, 245));
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
		scrollFiles.setPreferredSize(new Dimension(300, 260));
		scrollFiles.setBorder(new TitledBorder(null, "Files", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		filesPanel.add(scrollFiles);
	}

	private void fileListAddMouseListener() {
		fileList.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if(fileList.getModel().getSize() > 0){
					JList list = (JList) event.getSource();
	
					int index = list.locationToIndex(event.getPoint());
					CheckListItem item = (CheckListItem) list.getModel()
							.getElementAt(index);
	
					item.setSelected(!item.isSelected());
	
					list.repaint(list.getCellBounds(index, index));
				}
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
