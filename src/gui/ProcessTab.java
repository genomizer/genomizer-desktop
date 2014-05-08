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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import util.FileData;

public class ProcessTab extends JPanel {

    private static final long serialVersionUID = -2830290705724588252L;

    private JTextArea timeArea = new JTextArea();
    private JTextArea convertArea = new JTextArea();
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
	private FileData[] fileData;

    public ProcessTab() {
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

        // BARA TILLFï¿½LLIG
        fileList = new JList(new CheckListItem[]{
                new CheckListItem("[0] Protein223_A5_2014.RAW"),
                new CheckListItem("[1] Protein223_A5_2014.RAW"),
                new CheckListItem("[2] Protein223_A5_2014.RAW"),
                new CheckListItem("[3] Protein223_A5_2014.RAW"),
                new CheckListItem("[4] Protein223_A5_2014.RAW")});

        initFileList();
        writeToTimePanel();

    }

    private void initNorthPanel() {

        this.add(menuPanel, BorderLayout.NORTH);
        menuPanel.setPreferredSize(new Dimension(300, 100));
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
      //  convertFilesPanel.setPreferredSize(new Dimension(300, 100));
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
        menuPanel.add(convertButton);
        menuPanel.add(profileButton);
        menuPanel.add(regionButton);
        menuPanel.add(scheduleButton);
    }

    private void enableButtons() {
        convertButton.setEnabled(false);
        regionButton.setEnabled(false);
        scheduleButton.setEnabled(false);
    }

    private void writeToTimePanel() {

        timeArea.setText("");
        timeArea.setEditable(false);
        timeArea.append("Number of jobs currently in queue: "
                + getNumberOfJobsInQueue() + " (est. time until empty : "
                + getTimeApprox() + " min )");
        timePanel.add(timeArea);

    }

	public void setFileInfo(FileData[] fileData){
		this.fileData = fileData;
		parseFileData();
		//initFileList();
	}


	//TODO Går inte att lägga in checklistitem-listan i JList fileList.
	//Det gör att listan med filnamn inte visas i GUI.
	private void parseFileData(){

		JList list = new JList(new CheckListItem[] {
				new CheckListItem("[0] Protein223_A5_2014.RAW"),
				new CheckListItem("[1] Protein223_A5_2014.RAW"),
				new CheckListItem("[2] Protein223_A5_2014.RAW"),
				new CheckListItem("[3] Protein223_A5_2014.RAW"),
				new CheckListItem("[4] Protein223_A5_2014.RAW") });


		Object[] itemList = new CheckListItem[fileData.length];

		for(int i = 0; i < fileData.length; i++){
		//	itemList[i] = new CheckListItem( fileData[i].name );
		}

		//this.fileList = new JList(itemList);
		//this.fileList = list;

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
        fileList.setFixedCellWidth(265);
        fileList.setFixedCellHeight(40);
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

		if(fileData == null){

			return null;
		} else {

			for(int i = 0; i < fileData.length; i++){

				for(int j = 0; j < arr.size(); j++){

					if(arr.get(j) == fileData[i].name ){
						allMarked.add( fileData[i] );
					}
				}
			}

			return allMarked;
		}
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

}
