package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.ErrorLogger;

import util.ActivePanel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;

import communication.HTTPURLUpload;
import controller.ConvertTabController;
import controller.ProcessTabController;
import controller.UploadTabController;

/**
 * A class representing a upload view in an application for genome research.
 * This class allows the user to upload files to the database of the
 * application.
 */
public class ConvertTab extends JPanel {

    private static final long serialVersionUID = -2830290705724588252L;
    private JButton convertSelectedFiles, deleteSelectedFiles;
    private JPanel upperPanel, lowerPanel;
    private CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads;
    private ActivePanel activePanel;
    private JLabel boldTextLabel;
    private JTextField experimentNameField;
  //  private JScrollPane uploadScroll;
    private Dimension panelSize = new Dimension(200,80);
    private ArrayList<ExperimentData> experimentData;



    // Test purpose
    private ArrayList<UploadToNewExpPanel> newExps = new ArrayList<UploadToNewExpPanel>();
    private JPanel convertPanel;
    private JPanel deletePanel;
    private JPanel convertFromPanel;
    private JPanel convertToPanel;
    private JPanel selectedFilesPanel;
    private JPanel selectedFilesTopPanel;
    private JPanel selectedFilesBotPanel;
    private JPanel queuedFilesPanel;
    private JSplitPane splitPane;
    private Dimension minimumSize;
    private JPanel emptySouthPanel;
    private JList<CheckListItem> fileList = new JList<CheckListItem>();
    private final JScrollPane scrollFiles = new JScrollPane();
    private ConvertTabController convertTabController;

    /**
     * Constructor creating a convert tab.
     */
    public ConvertTab() {


        activePanel = ActivePanel.NONE;
        setPreferredSize(new Dimension(1225, 725));
        setLayout(new BorderLayout());


        setupUpperPanel();
        setupSelectedFilesPanel();
        setupQueuedFilesPanel();
        setupEmptySouthPanel();
        fileListSetCellRenderer();

  //      setupEmptyDividerPanel();

     //   uploadPanel = new JPanel(new BorderLayout());
     //   uploadScroll = new JScrollPane(uploadPanel);
     //   add(uploadScroll, BorderLayout.CENTER);

        updateProgress();




    }




//    private void addNewCheckListItemTest(){
//
//        ArrayList<CheckListItem> itemList = new ArrayList<CheckListItem>();
//        FileData fildata = new FileData("a","a","a","a","a","a",true,"a","a","a","a","a");
//        CheckListItem checksItem = new CheckListItem(fildata,"a","b","c");
//        System.out.println("1");
//
//        itemList.add(checksItem);
//        System.out.println("2");
//        scrollFiles.setViewportView(fileList);
//        System.out.println("3");
//        CheckListItem[] checkList = itemList.toArray(new CheckListItem[itemList.size()]);
//        System.out.println("4");
//        fileList = new JList<CheckListItem>();
//        fileList.setListData(checkList);
//        System.out.println("5");
//
//        this.revalidate();
//        this.repaint();
//
//
//    }







    public JList<CheckListItem> getFileList() {
        return fileList;
    }


    /**
     * Adds listener to the fileList.
     *
     * @param mouseAdapter
     */
    public void addFileListMouseListener(MouseAdapter mouseAdapter) {
        fileList.addMouseListener(mouseAdapter);
    }




    private void setupUpperPanel(){


        upperPanel = new JPanel();
        add(upperPanel, BorderLayout.NORTH);


        setupConvertPanel();
        setupDeletePanel();
        setupConvertFromPanel();
        setupConvertToPanel();

        upperPanel.add(convertPanel);
        upperPanel.add(deletePanel);
        upperPanel.add(convertFromPanel);
        upperPanel.add(convertToPanel);
        upperPanel.setBorder(BorderFactory.createTitledBorder("Upload"));


    }

    private void setupConvertPanel(){
        convertPanel = new JPanel();
        convertSelectedFiles = new JButton("Convert selected files");
        convertPanel.add(convertSelectedFiles);
        convertPanel.setBorder(BorderFactory.createTitledBorder("Convert"));
    }

    private void setupDeletePanel(){
        deletePanel = new JPanel();
        deleteSelectedFiles = new JButton("Delete selected files");
        deletePanel.add(deleteSelectedFiles);
        deletePanel.setBorder(BorderFactory.createTitledBorder("Delete"));
    }

    private void setupConvertFromPanel(){
        convertFromPanel = new JPanel();
        convertFromPanel.setPreferredSize(panelSize);
        convertFromPanel.setBorder(BorderFactory.createTitledBorder("Convert from"));

    }

    private void setupConvertToPanel(){
        convertToPanel = new JPanel();
        convertToPanel.setPreferredSize(panelSize);
        convertToPanel.setBorder(BorderFactory.createTitledBorder("Convert to"));
    }



    private void setupSelectedFilesPanel(){

        selectedFilesPanel = new JPanel();

        selectedFilesPanel.setPreferredSize(new Dimension(1225/2,0));
        selectedFilesPanel.setBorder(BorderFactory.createTitledBorder("selectedFilesPanel"));
        add(selectedFilesPanel, BorderLayout.CENTER);


        selectedFilesPanel.add(scrollFiles, BorderLayout.CENTER);

        scrollFiles.setViewportView(fileList);


    }


    private void setupQueuedFilesPanel(){
        queuedFilesPanel = new JPanel();
        queuedFilesPanel.setPreferredSize(new Dimension(1225/2,30));
        queuedFilesPanel.setBorder(BorderFactory.createTitledBorder("queuedFilesPanel"));
        add(queuedFilesPanel, BorderLayout.EAST);

    }


    private void setupEmptySouthPanel() {
        emptySouthPanel = new JPanel();
        emptySouthPanel.setPreferredSize(new Dimension(1225,30));
        add(emptySouthPanel,BorderLayout.SOUTH);
    }





//  private void setupLowerPanel(){
//      lowerPanel = new JPanel();
//      lowerPanel.setBorder(BorderFactory.createTitledBorder("lowerPanel"));
//      add(lowerPanel);
//
//
//      minimumSize = new Dimension(500, 500);
//
//      setupSelectedFilesPanel();
//      setupQueuedFilesPanel();
//
//      splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,selectedFilesPanel,queuedFilesPanel);
//      splitPane.setPreferredSize(new Dimension(1000, 1000));
//      //splitPane.setOneTouchExpandable(true);
//     // splitPane.setDividerLocation(500);
//
//
//
//      lowerPanel.add(splitPane);
//
//  }

    /**
     * Method adding a listener to the "newExpButton".
     *
     * @param listener
     *            The listener to create a experiment.
     */
    public void addNewExpButtonListener(ActionListener listener) {
        deleteSelectedFiles.addActionListener(listener);
    }

    /**
     * Displays a panel for adding to an existing experiment.
     *
     * @param ed
     *            The experiment data for the existing experiment.
     */
    public void addExistingExpPanel(ExperimentData ed) {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.EXISTING;
 //       uploadToExistingExpPanel.build();
  //      uploadToExistingExpPanel.addExistingExp(ed);
 //       uploadPanel.add(uploadToExistingExpPanel, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    /**
     * Displays a panel for creating a new experiment.
     *
     * @param annotations
     *            The available annotations at the server.
     *
     */
    public void addNewExpPanel(AnnotationDataType[] annotations) {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.NEW;
  //      uploadToNewExpPanel.createNewExpPanel(annotations);
  //      uploadPanel.add(uploadToNewExpPanel, BorderLayout.CENTER);
  //      newExps.add(uploadToNewExpPanel);
        repaint();
        revalidate();
    }

    /**
     * Method returning a uploadToExistingExpPanel.
     *
     * @return a panel used when uploading file to a existing experiment.
     */
  //  public UploadToExistingExpPanel getExistExpPanel() {
  //      return uploadToExistingExpPanel;
  //  }


    public boolean newExpStarted() {
        return activePanel == ActivePanel.NEW;
    }


    /**
     * A method removing the components in the panels when one of them gets
     * chosen by the user, to make sure the new components won't overlap and end
     * up invisible. The method checks the Enum ActivePanel to check which panel
     * was the active one.
     */
    public void killContentsOfUploadPanel() {
        switch (activePanel) {
            case NONE:
                break;
            case EXISTING:
                repaint();
                revalidate();
                activePanel = ActivePanel.NONE;
                break;
            case NEW:
                repaint();
                revalidate();
                activePanel = ActivePanel.NONE;
                break;
        }
    }

    /**
     * Method setting the ongoing uploads.
     *
     * @param ongoingUploads
     *            The uploads currently ongoing.
     */
    public void setOngoingUploads(
            CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads) {
        this.ongoingUploads = ongoingUploads;
    }

    /**
     * Method updating the progress of ongoing uploads.
     */
    private void updateProgress() {

        new Thread(new Runnable() {
            private boolean running;

            @Override
            public void run() {
                running = true;
                while (running) {
                    try {

                       // addNewCheckListItemTest();

                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        ErrorLogger.log(e);
                        running = false;
                    }
                    // TODO: THIS IS BROKEN, more is created on each logout-in !!! System.err.println(this.toString());
                }
            }
        }).start();
    }







    /**
     * Gets all marked files in the fileList.
     *
     * @return ArrayList<FileData> - List of all the files.
     */

    public ArrayList<FileData> getAllMarkedFiles() {

        ArrayList<FileData> arr = new ArrayList<FileData>();

        for (int i = 0; i < fileList.getModel().getSize(); i++) {
            CheckListItem checkItem = fileList.getModel().getElementAt(i);
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
    private void checkItemIsSelected(ArrayList<FileData> arr, CheckListItem checkItem) {
        if (checkItem.isSelected()) {
            arr.add(checkItem.getfile());
        }
    }





    public void setController(ConvertTabController convertTabController) {
        this.convertTabController = convertTabController;
    }




    public void addConvertFileListener(ActionListener listener) {
        // TODO Auto-generated method stub

    }

    /**
     * Sets a cell renderer to fileList.
     */
    private void fileListSetCellRenderer() {
        fileList.setCellRenderer(new CheckListRenderer());
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }




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
     * Sets the experimentData list with all selected files to process from
     * workspace.
     *
     * @param experimentData
     */
    public void setFileInfo(ArrayList<ExperimentData> experimentData) {
        this.experimentData = experimentData;
        // Parse out experiment files.
        parseFileData();
    }

    /**
     * Get all the experiments from the convert tab.
     *
     * @return
     */
    public ArrayList<ExperimentData> getFileInfo() {
        return this.experimentData;
    }



//    public void setController(UploadTabController uploadTabController) {
 //       this.uploadTabController = uploadTabController;
 //   }
}
