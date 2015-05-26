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
import javax.swing.DefaultListModel;
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
    public JButton convertSelectedFiles, deleteSelectedFiles,removeAllConvertedFiles;
    private JPanel upperPanel;
    private Dimension panelSize = new Dimension(200,110);
    private ArrayList<ExperimentData> experimentData;
    private JPanel convertPanel;
    private JPanel deletePanel;
    private JPanel convertFromPanel;
    private JPanel convertToPanel;
    private JPanel selectedFilesPanel;
    private JPanel queuedFilesPanel;
    private JPanel emptySouthPanel;
    private int count = 0;
    private JList<CheckListItem> fileList = new JList<CheckListItem>();
    private JList<String> ConvertedfilesList = new JList<String>();
    private final JScrollPane scrollFiles = new JScrollPane();
    private final JScrollPane convertedFiles = new JScrollPane();
    private ConvertTabController convertTabController;
    private DefaultListModel<String> convertedListModel = new DefaultListModel<String>();

    public final JRadioButton cFromGFF = new JRadioButton("GFF");
    public final JRadioButton cFromWIG = new JRadioButton("WIG");
    public final JRadioButton cFromSGR = new JRadioButton("SGR");
    public final JRadioButton cFromBED = new JRadioButton("BED");
    public final JRadioButton cToWIG = new JRadioButton("WIG");
    public final JRadioButton cToSGR = new JRadioButton("SGR");
    public final ButtonGroup radioGroupFrom = new ButtonGroup();
    public final ButtonGroup radioGroupTo = new ButtonGroup();
    private String currentFileType = "";


    /**
     * Constructor creating a convert tab.
     */
    public ConvertTab() {

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
    
    public void setCount(int c){
        count = c;
    }
    
    public int getCount(){
        return count;
    }

    /**
     *
     * Sets the listeners to the radiobuttons
     */
    private void setButtonListeners() {

        radioGroupFrom.add(cFromBED);
        radioGroupFrom.add(cFromGFF);
        radioGroupFrom.add(cFromSGR);
        radioGroupFrom.add(cFromWIG);
        //radioGroupFrom.setSelected(cFromBED.getModel(), true);
        radioGroupTo.add(cToSGR);
        radioGroupTo.add(cToWIG);
        //radioGroupTo.setSelected(cToSGR.getModel(), true);
        setRadioButtonListener(cFromBED);
        setRadioButtonListener(cFromGFF);
        setRadioButtonListener(cFromSGR);
        setRadioButtonListener(cFromWIG);
        setRadioButtonListener(cToWIG);
        setRadioButtonListener(cToSGR);

        //();

    }


    /**
     * Returns a list of the files.
     * @return
     */
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



    /**
     * Sets up panel in the panel at the top in the window.
     */
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

    /**
     * sets up the panel with the convertbutton.
     */
    private void setupConvertPanel(){
        convertPanel = new JPanel();
        convertSelectedFiles = new JButton("Convert selected files");
        convertPanel.add(convertSelectedFiles);
        convertSelectedFiles.setEnabled(false);
        convertPanel.setBorder(BorderFactory.createTitledBorder("Convert"));
    }

    /**
     * sets up the panel with the delete buttons.
     */
    private void setupDeletePanel(){
        deletePanel = new JPanel();
        deleteSelectedFiles = new JButton("Delete selected files");
        removeAllConvertedFiles = new JButton("Clear converted files");
        deletePanel.add(deleteSelectedFiles);
        deletePanel.add(removeAllConvertedFiles);
        deleteSelectedFiles.setEnabled(false);
        deletePanel.setBorder(BorderFactory.createTitledBorder("Delete"));
    }
    

    /**
     * sets up the panel that contains which filetype you want to
     * convert from.
     */
    private void setupConvertFromPanel(){
        convertFromPanel = new JPanel();
        convertFromPanel.setLayout(new GridLayout(0, 1, 0, 0));
        convertFromPanel.setPreferredSize(panelSize);
        convertFromPanel.setBorder(BorderFactory.createTitledBorder("Convert from"));

        convertFromPanel.add(cFromBED);
        convertFromPanel.add(cFromGFF);
        convertFromPanel.add(cFromSGR);
        convertFromPanel.add(cFromWIG);

        cFromBED.setEnabled(false);
        cFromGFF.setEnabled(false);
        cFromSGR.setEnabled(false);
        cFromWIG.setEnabled(false);

    }

    /**
     * Sets up the panel that contains which filetype you want to
     * convert to.
     */
    private void setupConvertToPanel(){
        convertToPanel = new JPanel();
        convertToPanel.setLayout(new GridLayout(0, 1, 0, 0));
        convertToPanel.setPreferredSize(panelSize);
        convertToPanel.setBorder(BorderFactory.createTitledBorder("Convert to"));

        convertToPanel.add(cToSGR);
        convertToPanel.add(cToWIG);

        cToSGR.setEnabled(false);
        cToWIG.setEnabled(false);

    }


    /**
     * Sets up the panel which contains the files.
     */
    private void setupSelectedFilesPanel(){

        selectedFilesPanel = new JPanel();

        selectedFilesPanel.setMinimumSize(new Dimension(1225/2,0));
        selectedFilesPanel.setBorder(BorderFactory.createTitledBorder("Convert from"));
        add(selectedFilesPanel, BorderLayout.CENTER);

        scrollFiles.setPreferredSize(new Dimension(560,480));
        selectedFilesPanel.add(scrollFiles, BorderLayout.CENTER);

        scrollFiles.setViewportView(fileList);



    }


    /**
     * Sets u the panel which contains the queued files.
     */
    private void setupQueuedFilesPanel(){
        queuedFilesPanel = new JPanel();
        queuedFilesPanel.setPreferredSize(new Dimension(1225/2,30));
        queuedFilesPanel.setBorder(BorderFactory.createTitledBorder("Converted files"));
        add(queuedFilesPanel, BorderLayout.EAST);

        convertedFiles.setPreferredSize(new Dimension(560,480));
        queuedFilesPanel.add(convertedFiles,BorderLayout.CENTER);

        convertedFiles.setViewportView(ConvertedfilesList);

    }

    /**
     * Sets up an empty panel at the bottom of the window, just for show.
     */
    private void setupEmptySouthPanel() {
        emptySouthPanel = new JPanel();
        emptySouthPanel.setPreferredSize(new Dimension(1225,30));
        add(emptySouthPanel,BorderLayout.SOUTH);
    }

    /**
     * Add a listener to the deleteSelectedFiles button.
     * @param listener
     */
    public void deleteSelectedButtonListener(ActionListener listener) {
        deleteSelectedFiles.addActionListener(listener);
    }
    
    /**
     * Add a listener to the deleteAllConvertedFiles button.
     * @param listener
     */
    public void removeAllConvertedFileListener(ActionListener listener) {
        removeAllConvertedFiles.addActionListener(listener);
    }

    /**
     * Add a listener to the convertSelectedFiles button.
     * @param listener
     */
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
     * Checks which parameters should be enabled and set. It is important
     * how this method works to get the right dependency between the
     * radiobuttons and the checklist.
     */
    public void check() {
        /* Check if there are valid genome releases */
     //   disableCToRadiobuttons();


        if(currentFileType.equals("BED")){
            cFromBED.setSelected(true);
        } else if(currentFileType.equals("GFF")){
            cFromGFF.setSelected(true);
        } else if(currentFileType.equals("SGR")){
            cFromSGR.setSelected(true);
        } else if(currentFileType.equals("WIG")){
            cFromWIG.setSelected(true);
        }

        if(cFromBED.isSelected()){
            cToWIG.setEnabled(true);
            cToSGR.setEnabled(true);
        } else if (cFromGFF.isSelected()){
            cToWIG.setEnabled(true);
            cToSGR.setEnabled(true);
        } else if(cFromSGR.isSelected()){
            cToWIG.setSelected(true);
            cToWIG.setEnabled(true);
            cToSGR.setEnabled(false);
        } else if (cFromWIG.isSelected()){
            cToSGR.setSelected(true);
            cToSGR.setEnabled(true);
            cToWIG.setEnabled(false);
        }

        if(cToSGR.isSelected() || cToWIG.isSelected()){
            setConvertButtonEnabled();
        } else {
            setConvertButtonDisabled();
        }

    }

    public void setAllButtonsNotSelected(){
        radioGroupFrom.clearSelection();
        radioGroupTo.clearSelection();
        cToWIG.setEnabled(false);
        cToSGR.setEnabled(false);
        cFromBED.setEnabled(false);
        cFromGFF.setEnabled(false);
        cFromSGR.setEnabled(false);
        cFromWIG.setEnabled(false);
    }

    public void setAllFromButtonsEnabled(){
        cFromBED.setEnabled(true);
        cFromGFF.setEnabled(true);
        cFromSGR.setEnabled(true);
        cFromWIG.setEnabled(true);
    }

    public void setConvertButtonDisabled(){
        convertSelectedFiles.setEnabled(false);
    }

    public void setConvertButtonEnabled(){
        convertSelectedFiles.setEnabled(true);
    }

    public void setDeleteButtonDisabled(){
        deleteSelectedFiles.setEnabled(false);
    }

    public void setDeleteButtonEnabled(){
        deleteSelectedFiles.setEnabled(true);
    }

    /**
     * Returns the current possible types to convert from. Should
     * be updated as soon as a new filetype is added.
     * @return
     */
    public ArrayList<String> getPossibleConvertFromFileTypes(){
        ArrayList<String> fileTypeList = new ArrayList<String>();
        fileTypeList.add("BED");
        fileTypeList.add("GFF");
        fileTypeList.add("SGR");
        fileTypeList.add("WIG");

        return fileTypeList;

    }

    /**
     * Sets the name of the filetype of the current selected filetype.
     * @param type
     */
    public void setCurrentSelectedFileType(String type){
        currentFileType = type;
        check();
    }

    /**
     * resets the name of the filetype.
     */
    public void resetCurrentSelectedFileType(){
        currentFileType = "";
        check();
    }


    /**
     * Disables the ToRadioButtons in the convert tab.
     */
    private void disableCToRadiobuttons() {
        cToSGR.setEnabled(false);
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
    
    public void resetFileList(){
        fileList.removeAll();
    }

    /**
     * Gets all marked files in the fileList.
     *
     * @return ArrayList<CheckListItem> - List of all the files.
     */
    public ArrayList<CheckListItem> getFilesToConvert() {
        ArrayList<CheckListItem> arr = new ArrayList<CheckListItem>();

        for (int i = 0; i < fileList.getModel().getSize(); i++) {
            CheckListItem checkItem = fileList.getModel().getElementAt(i);
            checkItemIsSelected2(arr, checkItem);
        }

        return arr;
    }
    /**
     * Checks if an item in a list is selected and add the file to the selected
     * files list;
     *
     * @param arr - the list
     * @param checkItem - the item in the list
     */
    private void checkItemIsSelected2(ArrayList<CheckListItem> arr, CheckListItem checkItem) {
        if (checkItem.isSelected()) {
            arr.add(checkItem);
        }
    }

    /**
     * Checks if an item in a list is selected and add the file to the selected
     * files list;
     *
     * @param arr - the list
     * @param checkItem - the item in the list
     */
    private void checkItemIsSelected(ArrayList<FileData> arr, CheckListItem checkItem) {
        if (checkItem.isSelected()) {
            arr.add(checkItem.getfile());
        }
    }


    /**
     * Sets the controller for this tab.
     * @param convertTabController
     */
    public void setController(ConvertTabController convertTabController) {
        this.convertTabController = convertTabController;
    }


    /**
     * Sets a cell renderer to fileList. OBS, it is useful.. Will not
     * update the list if removed.
     */
    private void fileListSetCellRenderer() {
        fileList.setCellRenderer(new CheckListRenderer());
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }



    /**
     * Parses the data from the experiments.
     */
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
    /**
     * Add a file to the converted files panel
     * @param s
     */
    public void addConvertedFile(String s) {
        convertedListModel.addElement(s);
        queuedFilesPanel.remove(convertedFiles);
        ConvertedfilesList = new JList<String>(convertedListModel);
        convertedFiles.setViewportView(ConvertedfilesList);
        queuedFilesPanel.add(convertedFiles);
        this.revalidate();
        this.repaint();
    }
    
    public void emptyConvertedFilesList(){
        ConvertedfilesList = new JList<String>();
        convertedFiles.setViewportView(ConvertedfilesList);
        this.revalidate();
        this.repaint();
    }

}
