package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ProcessTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;

	private JPanel filesPanel = new JPanel();
	private JPanel scheduleProcPanel = new JPanel();
	private JPanel genProfileDataPanel = new JPanel();
	private JPanel genRegionDataPanel = new JPanel();
	private JPanel convertFilesPanel = new JPanel();
	private JPanel procQueuePanel= new JPanel();
	private JPanel timePanel = new JPanel();
	private JPanel middelPanel = new JPanel(new GridLayout(3,1));
	private JPanel leftPanel = new JPanel(new GridLayout(2,1));
	private JCheckBox button = new JCheckBox("Button");

	//SKA VARA JLIST
	private ArrayList processQueue = new ArrayList();

	private DefaultListModel fileListModel = new DefaultListModel();
	private JCheckBoxList fileList = new JCheckBoxList();

	private JScrollPane scrollFiles = new JScrollPane();

	public ProcessTab(){
		this.setLayout(new BorderLayout());
		initPanels();
	}

	private void initPanels(){

		initWestPanels();
		initMiddlePanel();
		initEastPanels();
		initTimePanel();
		initFileList();

		test();

		printTimePanel();

	}

	private void initTimePanel() {
		this.add(timePanel,BorderLayout.SOUTH);
		timePanel.setPreferredSize(new Dimension(300,30));

	}

	private void printTimePanel(){

		JTextArea timeArea = new JTextArea();
		timeArea.setText("");
		timeArea.append("Number of jobs currently in queue: " + getNumberOfJobsInQueue() + " (est. time until empty : " + getTimeApprox() + " min )");
		timeArea.setEditable(false);


		timePanel.add(timeArea);

	}

	private void initWestPanels(){

		this.add(leftPanel,BorderLayout.WEST);

		leftPanel.add(filesPanel);
		//filesPanel.setBorder( BorderFactory.createTitledBorder("FILES"));
		filesPanel.setPreferredSize(new Dimension(300,100));

		leftPanel.add(scheduleProcPanel);
		scheduleProcPanel.setBorder( BorderFactory.createTitledBorder("SCHEDULE PROCESSING"));
		scheduleProcPanel.setPreferredSize(new Dimension(300,100));

	}

	private void initFileList(){

        scrollFiles = new JScrollPane( fileList );

        fileList.setBorder(BorderFactory.createTitledBorder("FILES"));
    	fileList.setModel(fileListModel);
    	fileList.setFixedCellWidth(265);
    	fileList.setFixedCellHeight(40);

		Object[] cbArray = new Object[16];
    	for(int i = 0; i < 16; i++) {
    		cbArray[i] = new JCheckBox("Protein223_A5_2014.WIG");
    	}
    	fileList.setListData(cbArray);
        filesPanel.add( scrollFiles );
	}

	private void initMiddlePanel(){


		this.add(middelPanel,BorderLayout.CENTER);

		middelPanel.add(genProfileDataPanel);
		genProfileDataPanel.setBorder( BorderFactory.createTitledBorder("GENERATE PROFILE DATA"));

		middelPanel.add(genRegionDataPanel);
		genRegionDataPanel.setBorder( BorderFactory.createTitledBorder("GENERATE REGION DATA"));

		middelPanel.add(convertFilesPanel);
		convertFilesPanel.setBorder( BorderFactory.createTitledBorder("CONVERT FILES"));

	}

	private void initEastPanels(){
		this.add(procQueuePanel,BorderLayout.EAST);
		procQueuePanel.setPreferredSize(new Dimension(300,100));

		JLabel queueLabel = new JLabel("PROCESSING IN QUEUE");
		queueLabel.setBackground(Color.CYAN);
		queueLabel.setOpaque(true);
		procQueuePanel.add(queueLabel);

	}

	private int getNumberOfJobsInQueue(){
		return this.processQueue.size();
	}

	private int getTimeApprox(){
		return 450;
	}

	private void test() {
		//Test
		for(int i = 0; i < 12; i++){
			processQueue.add("Elem" + i);
		}
		//
	}

}
