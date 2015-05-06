package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
    private Dimension panelSize = new Dimension(200,110);
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


    public final JRadioButton cFromFASTQ = new JRadioButton("FASTQ");
    public final JRadioButton cFromWIG = new JRadioButton("WIG");
    public final JRadioButton cFromSGR = new JRadioButton("SGR");
    public final JRadioButton cFromCHP = new JRadioButton("CHP");

    public final JRadioButton cToWIG = new JRadioButton("WIG");
    public final JRadioButton cToSGR = new JRadioButton("SGR");
    public final JRadioButton cToGFF = new JRadioButton("GFF");
    public final ButtonGroup radioGroupFrom = new ButtonGroup();
    public final ButtonGroup radioGroupTo = new ButtonGroup();
    private String currentFileType = "";

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

        setButtonListeners();
        check();
        
        
    }

    /**
     *
     */
    private void setButtonListeners() {

        radioGroupFrom.add(cFromFASTQ);
        radioGroupFrom.add(cFromSGR);
        radioGroupFrom.add(cFromCHP);
        radioGroupFrom.add(cFromWIG);
        radioGroupFrom.setSelected(cFromFASTQ.getModel(), true);
        radioGroupTo.add(cToWIG);
        radioGroupTo.add(cToSGR);
        radioGroupTo.add(cToGFF);
        setRadioButtonListener(cFromFASTQ);
        setRadioButtonListener(cFromWIG);
        setRadioButtonListener(cFromCHP);
        setRadioButtonListener(cFromSGR);
        setRadioButtonListener(cToWIG);
        setRadioButtonListener(cToSGR);
        setRadioButtonListener(cToGFF);

       // setCheckBoxListener();



        disableCToRadiobuttons();

    }



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
        convertFromPanel.setLayout(new GridLayout(0, 1, 0, 0));
        convertFromPanel.setPreferredSize(panelSize);
        convertFromPanel.setBorder(BorderFactory.createTitledBorder("Convert from"));

        convertFromPanel.add(cFromFASTQ);
        convertFromPanel.add(cFromSGR);
        convertFromPanel.add(cFromCHP);
        convertFromPanel.add(cFromWIG);

    }

    private void setupConvertToPanel(){
        convertToPanel = new JPanel();
        convertToPanel.setLayout(new GridLayout(0, 1, 0, 0));
        convertToPanel.setPreferredSize(panelSize);
        convertToPanel.setBorder(BorderFactory.createTitledBorder("Convert to"));

        convertToPanel.add(cToWIG);
        convertToPanel.add(cToSGR);
        convertToPanel.add(cToGFF);
    }



    private void setupSelectedFilesPanel(){

        selectedFilesPanel = new JPanel();

        selectedFilesPanel.setMinimumSize(new Dimension(1225/2,0));
        selectedFilesPanel.setBorder(BorderFactory.createTitledBorder("selectedFilesPanel"));
        add(selectedFilesPanel, BorderLayout.CENTER);

        scrollFiles.setPreferredSize(new Dimension(560,480));
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

    public void deleteSelectedButtonListener(ActionListener listener) {
        deleteSelectedFiles.addActionListener(listener);
    }

    public void convertSelectedButtonListener(ActionListener listener) {
        convertSelectedFiles.addActionListener(listener);
    }


    /**
     * Sets a button listener to a selected JRadioButton.
     *
     * @param radioButton
     */
    public void setRadioButtonListener(JRadioButton radioButton) {
        radioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
    }

    /**
     * Sets a button listener to a selected JCheckBox.
     *
     * @param checkbox
     */
    public void setCheckBoxListener(JCheckBox checkbox) {
        checkbox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
    }




    /**
     * Checks which parameters should be enabled and set. Every button and
     * textfield uses this method to be able to listen to eachothers events.
     */
    public void check() {
        /* Check if there are valid genome releases */
        disableCToRadiobuttons();

        if(currentFileType.equals("FASTQ")){
            cFromFASTQ.setSelected(true);
        } else if(currentFileType.equals("WIG")){
            cFromWIG.setSelected(true);
        } else if(currentFileType.equals("SGR")){
            cFromSGR.setSelected(true);
        } else if(currentFileType.equals("CHP")){
            cFromCHP.setSelected(true);
        }

            if (cFromFASTQ.isSelected() && cFromFASTQ.isEnabled()) {
                cToWIG.setEnabled(true);
                cToSGR.setEnabled(true);

                if(cToGFF.isSelected()){
                    cToWIG.setSelected(true);
                }

            }

            if (cFromWIG.isSelected() && cFromWIG.isEnabled()) {
                cToGFF.setEnabled(true);

                if(cToSGR.isSelected() || cToWIG.isSelected()){
                    cToGFF.setSelected(true);
                }

            }

            if (cFromSGR.isSelected() && cFromSGR.isEnabled()) {
                cToGFF.setEnabled(true);
                cToWIG.setEnabled(true);

                if(cToSGR.isSelected()){
                    cToWIG.setSelected(true);
                }
            }

            if (cFromCHP.isSelected() && cFromCHP.isEnabled()) {
                cToGFF.setEnabled(true);
                cToWIG.setEnabled(true);

                if(cToSGR.isSelected()){
                    cToWIG.setSelected(true);
                }

            }
    }



    public ArrayList<String> getPossibleConvertFromFileTypes(){
        ArrayList<String> fileTypeList = new ArrayList<String>();
        fileTypeList.add("FASTQ");
        fileTypeList.add("WIG");
        fileTypeList.add("SGR");
        fileTypeList.add("CHP");

        return fileTypeList;

    }

    public void setCurrentSelectedFileType(String type){
        currentFileType = type;
        check();
    }

    public void resetCurrentSelectedFileType(){
        currentFileType = "";
        check();
    }


    /**
     * Disables all the buttons and textfields in the process tab
     */
    private void disableCToRadiobuttons() {
        cToSGR.setEnabled(false);
        cToGFF.setEnabled(false);
        cToWIG.setEnabled(false);

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
        String fileName;
        String fileType;

        for (ExperimentData exData : experimentData) {
            for (FileData fileData : exData.files) {
                for (AnnotationDataValue annoDataValue : exData.annotations) {
                    if (annoDataValue.getName().equals("Species")) {
                        specie = annoDataValue.value;
                        break;
                    }
                }
                
                fileName = fileData.filename.toUpperCase();
                fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
                
                if(getPossibleConvertFromFileTypes().contains(fileType)){
                    itemList.add(new CheckListItem(fileData, fileData.filename,
                            fileData.id, specie));
                }

            }
        }
        fileList.setListData(itemList.toArray(new CheckListItem[itemList.size()]));
        if(fileList.getModel().getSize() == 0){
            JOptionPane.showMessageDialog(null,"No matching filetypes. \nPossible types to convert: \n" + 
                    getPossibleConvertFromFileTypes().toString());
        }

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
