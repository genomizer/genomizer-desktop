package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

public class ProcessTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;

	private JPanel filesPanel = new JPanel();
	private JPanel scheduleProcPanel = new JPanel();
	private JPanel genProfileDataPanel = new JPanel();
	private JPanel genRegionDataPanel = new JPanel();
	private JPanel convertFilesPanel = new JPanel();
	private JPanel procQueuePanel= new JPanel();
	private JPanel menuPanel = new JPanel();
	private JPanel timePanel = new JPanel();
	private JPanel middelPanel = new JPanel(new GridLayout(3,1));
	private JPanel leftPanel = new JPanel(new GridLayout(2,1));
	private JButton convertButton = new JButton("CONVERT TO WIG");
	private JButton profileButton = new JButton("CREATE PROFILE DATA");
	private JButton regionButton = new JButton("CREATE REGION DATA");
	private JButton scheduleButton = new JButton("SCHEDULE");

	//SKA VARA JLIST
	private ArrayList processQueue = new ArrayList();

	private DefaultListModel fileListModel = new DefaultListModel();
	//private JCheckBoxList fileList = new JCheckBoxList();
	private JList fileList = new JList();

	private JScrollPane scrollFiles = new JScrollPane();

	private ArrayList<JCheckBox> arrayCheck = new ArrayList<JCheckBox>();

	public ProcessTab(){
		this.setLayout(new BorderLayout());
		initPanels();
	}

	private void initPanels(){

		initNorthPanel();
		initWestPanels();
		initMiddlePanel();
		initEastPanels();
		initTimePanel();
		initFileList();

		test();

		printTimePanel();

	}

	private void initNorthPanel() {

		this.add(menuPanel,BorderLayout.NORTH);
		menuPanel.setPreferredSize(new Dimension(300,100));

		menuPanel.add(convertButton);
		menuPanel.add(profileButton);
		menuPanel.add(regionButton);
		menuPanel.add(scheduleButton);
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

        fileList = new JList(new CheckListItem[] {
        		new CheckListItem("[0] Protein223_A5_2014.RAW"),
                new CheckListItem("[1] Protein223_A5_2014.RAW"),
                new CheckListItem("[2] Protein223_A5_2014.RAW"),
                new CheckListItem("[3] Protein223_A5_2014.RAW"),
                new CheckListItem("[4] Protein223_A5_2014.RAW")});

          fileList.setCellRenderer(new CheckListRenderer());
          fileList.setSelectionMode(
             ListSelectionModel.SINGLE_SELECTION);


          fileList.addMouseListener(new MouseAdapter()
          {
             public void mouseClicked(MouseEvent event)
             {
                JList list = (JList) event.getSource();

                int index = list.locationToIndex(event.getPoint());
                CheckListItem item = (CheckListItem)
                   list.getModel().getElementAt(index);

                item.setSelected(! item.isSelected());


                list.repaint(list.getCellBounds(index, index));
             }
          });

        scrollFiles = new JScrollPane( fileList );

        scrollFiles.setBorder(BorderFactory.createTitledBorder("FILES"));
        fileList.setFixedCellWidth(265);
        fileList.setFixedCellHeight(40);

        filesPanel.add(scrollFiles);
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

	public ArrayList<String> getAllMarkedFiles(){

		ArrayList<String> arr = new ArrayList<String>();

		for(int i = 0; i < fileList.getModel().getSize();i++){
			CheckListItem checkItem = (CheckListItem) fileList.getModel().getElementAt(i);

			if(checkItem.isSelected()){
				arr.add(checkItem.toString());
			}
		}
		return arr;
	}

	public void addFileListMouseListener(MouseAdapter mouseAdapter){
		//fileList.addMouseListener(mouseAdapter);
	}

    public void addConvertFileListener(ActionListener listener) {
    	convertButton.addActionListener(listener);
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
