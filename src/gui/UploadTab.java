package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import util.ActivePanel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileDrop;

import communication.HTTPURLUpload;

/**
 * A class representing a upload view in an application for genome research.
 * This class allows the user to upload files to the database of the
 * application.
 */
public class UploadTab extends JPanel /*implements ExperimentPanel*/ {

    private static final long serialVersionUID = -2830290705724588252L;
    private JButton existingExpButton, newExpButton, selectButton,
            uploadButton, uploadSelectedBtn;
    private JPanel northPanel, expNamePanel, uploadPanel, newExpPanel,
            uploadFilesPanel, uploadBackground;
    private UploadToExistingExpPanel uploadToExistingExpPanel;
    private AnnotationDataType[] annotations;
    private ArrayList<String> annotationHeaders;
    private CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads;
    private HashMap<String, JComboBox> annotationBoxes;
    private HashMap<String, JTextField> annotationFields;
    private HashMap<File, UploadFileRow> uploadFileRows;
    private ActivePanel activePanel;
    private JLabel expNameLabel, boldTextLabel;
    private JTextField experimentNameField, expID;
    private JScrollPane uploadScroll;
    private JPanel buttonsPanel;

    //Test purpose
    private ArrayList<UploadToNewExpPanel> newExps = new ArrayList<UploadToNewExpPanel>();
    private UploadToNewExpPanel newPanel;

    /**
     * Constructor creating a upload tab.
     */
    public UploadTab() {
        uploadFileRows = new HashMap<File, UploadFileRow>();
        annotationHeaders = new ArrayList<String>();
        activePanel = ActivePanel.NONE;
        setLayout(new BorderLayout());
        uploadToExistingExpPanel = new UploadToExistingExpPanel();
        newPanel = new UploadToNewExpPanel();
        northPanel = new JPanel();
        expNamePanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);
        northPanel.add(new JLabel("Experiment name: "));
        experimentNameField = new JTextField();
        experimentNameField.setColumns(30);
        expNamePanel.add(experimentNameField);
        northPanel.add(expNamePanel);
        northPanel.setBorder(BorderFactory.createTitledBorder("Upload"));
        // addToExistingExpButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getSearchIcon(35, 35),
        // IconFactory.getSearchHoverIcon(37, 37), 37, 37,
        // "Search for existing experiment");
        existingExpButton = new JButton("Search for existing experiment");
        newExpButton = new JButton("Create new experiment");
        northPanel.add(existingExpButton, BorderLayout.EAST);
        northPanel.add(newExpButton, BorderLayout.EAST);
        uploadPanel = new JPanel(new BorderLayout());
        uploadScroll = new JScrollPane(uploadPanel);
        add(uploadScroll, BorderLayout.CENTER);
        uploadBackground = new JPanel(new BorderLayout());
//        buttonsPanel = new JPanel(new FlowLayout());
//        uploadFilesPanel = new JPanel(new GridLayout(0, 1));
//        selectButton = new JButton("Browse for files");
//        uploadSelectedBtn = new JButton("Create with selected files");
//        uploadButton = new JButton("Create with all files");

        // newExpButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getNewExperimentIcon(35, 35),
        // IconFactory.getNewExperimentHoverIcon(37, 37), 37, 37,
        // "Create new experiment");
        // selectButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getBrowseIcon(40, 40),
        // IconFactory.getBrowseHoverIcon(42, 42), 42, 42, "Browse for files");
        // uploadButton = CustomButtonFactory.makeCustomButton(
        // IconFactory.getUploadIcon(40, 40),
        // IconFactory.getUploadHoverIcon(42, 42), 42, 42, "Upload data");
        boldTextLabel = new JLabel( // Bolded isn't a word..
                "<html><b>Bold text indicates a forced annotation.</b></html>");
        boldTextLabel.setOpaque(true);
        newExpPanel = new JPanel();
        expNameLabel = new JLabel();
//        expID = new JTextField();
//        expID.setColumns(10);
//        expID.getDocument().addDocumentListener(new FreetextListener());
        enableUploadButton(false);
        updateProgress();
    }

    /**
     * Method adding a listener to the "addToExistingExpButton".
     *
     * @param listener
     *            The listener to add file to existing experiment.
     */
    public void addAddToExistingExpButtonListener(ActionListener listener) {
        existingExpButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "newExpButton".
     *
     * @param listener
     *            The listener to create a experiment.
     */
    public void addNewExpButtonListener(ActionListener listener) {
        newExpButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "selectButton".
     *
     * @param listener
     *            The listener to select files.
     */
    public void addSelectButtonListener(ActionListener listener) {
        newPanel.addSelectButtonListener(listener);
    }

    /**
     * Method adding a listener to the "uploadButton".
     *
     * @param listener
     *            The listener to start uploading all files.
     */
    public void addUploadButtonListener(ActionListener listener) {
        newPanel.addUploadButtonListener(listener);
    }

    /**
     * Method adding a listener to the "uploadSelectedBtn".
     *
     * @param listener
     *            The listener to start uploading selected files.
     */
    public void addUploadSelectedFiles(ActionListener listener) {
        newPanel.addUploadSelectedFiles(listener);
    }

    /**
     * Method returning a uploadToExistingExpPanel.
     *
     * @return a panel used when uploading file to a existing experiment.
     */
    public UploadToExistingExpPanel getUploadToExistingExpPanel() {
        return uploadToExistingExpPanel;
    }

    /**
     * Displays a panel for adding to an existing experiment.
     *
     * @param ed
     */
    public void addExistingExpPanel(ExperimentData ed) {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.EXISTING;
        uploadToExistingExpPanel.build();
        ArrayList<AnnotationDataValue> annots = ed.getAnnotations();
        uploadToExistingExpPanel.addExistingExp(ed);
        // uploadToExistingExpPanel.addAnnotationsForExistingExp();
        uploadPanel.add(uploadToExistingExpPanel, BorderLayout.CENTER);
        /*
         * setBorder(BorderFactory
         * .createTitledBorder("Upload to existing experiment"));
         */
        repaint();
        revalidate();
    }

    /**
     * TRY
     */
    public void addNewExpPanel(AnnotationDataType[] annotations) {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.NEW;
        newPanel.createNewExpPanel(annotations);
        uploadPanel.add(newPanel, BorderLayout.CENTER);
        newExps.add(newPanel);
        repaint();
        revalidate();
    }


    /**
     * A method creating a panel for creating a new experiment to upload files
     * to it.
     *
     * @param annotations
     *            The annotations currently available at the server.
     */
    public void createNewExp(AnnotationDataType[] annotations) {
        this.annotations = annotations;
        for(AnnotationDataType a : annotations)
            System.out.println(a.name);
        addNewExpPanel(annotations);
    }

    /**
     * Creates an uploadFileRow from the provided files. Checks if the files are
     * already in an uploadFileRow so there won't be duplicates. Displays an
     * error message if it was selected and added previously.
     *
     * @param files
     *            The files to make an uploadFileRow out of.
     */
    public void createUploadFileRow(File[] files) {
        newPanel.createUploadFileRow(files);
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
                uploadPanel.remove(uploadToExistingExpPanel);
                uploadToExistingExpPanel.removeAll();
                repaint();
                revalidate();
                activePanel = ActivePanel.NONE;
                break;
            case NEW:
                uploadPanel.remove(newPanel);
                newPanel.removeAll();
                repaint();
                revalidate();
                activePanel = ActivePanel.NONE;
                break;
        }
    }

    /**
     * Deletes an uploadFileRow and calls repaintSelectedFiles() to repaint. If
     * it fails to find the file, an error message is shown to the user.
     *
     * @param f
     *            This is used to identify which uploadFileRow to be deleted.
     */
    public void deleteFileRow(File f) {
       newPanel.deleteFileRow(f);
    }

    /**
     * Method returning the ExpID for a new experiment.
     *
     * @return a String with the ID of a experiment.
     */
    public String getNewExpID() {
        return newPanel.getNewExpID();
    }

    public AnnotationDataValue[] getUploadAnnotations() {
        return newPanel.getUploadAnnotations();
    }

    /**
     * Method returning the files to be uploaded.
     *
     * @return a array with the files.
     */
    public ArrayList<File> getUploadFiles() {
        return newPanel.getUploadFiles();
    }

    /**
     * Method returning the type of the files to be uploaded.
     *
     * @return a HashMap with the filenames and there types.
     */
    public HashMap<String, String> getTypes() {
        return newPanel.getTypes();
    }

    /**
     * @return true if all forced annotation fields (including expID) are
     *         filled. Otherwise returns false.
     */
    public boolean forcedAnnotationCheck() {
        return newPanel.forcedAnnotationCheck();
    }

    /**
     * Sets the experiment button to either be enabled or disabled. Only enables
     * it if there are selected files and all forced annotations fields are
     * filled.
     *
     * @param b
     *            Whether it should try to: enable the button (true) or disable
     *            it (false)
     */
    public void enableUploadButton(boolean b) {
        newPanel.enableUploadButton(b);
    }

    /**
     * Method returning the text in the experiment name field.
     *
     * @return a String with the experiment name.
     */
    public String getSearchText() {
        return experimentNameField.getText();
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
                    for (File key : newPanel.getFileRows()
                            .keySet()) {
                        UploadFileRow row = newPanel
                                .getFileRows().get(key);
                        for (HTTPURLUpload upload : ongoingUploads) {
                            if (upload.getFileName().equals(row.getFileName())) {
                                row.updateProgressBar(upload
                                        .getCurrentProgress());
                            }
                        }
                    }
                    for (File key : uploadToExistingExpPanel.getFileRows()
                            .keySet()) {
                        UploadFileRow row = uploadToExistingExpPanel
                                .getFileRows().get(key);
                        for (HTTPURLUpload upload : ongoingUploads) {
                            if (upload.getFileName().equals(row.getFileName())) {
                                row.updateProgressBar(upload
                                        .getCurrentProgress());
                            }
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        running = false;
                    }
                }
            }
        }).start();
    }

    /**
     * Method returning the files that are selected.
     *
     * @return an ArrayList with the selected files.
     */
    public ArrayList<File> getSelectedFilesToUpload() {
        return newPanel.getSelectedFilesToUpload();
    }

    public JButton getExistingExpButton() {
        return existingExpButton;
    }
    public JTextField getExperimentNameField() {
        return experimentNameField;
    }
}
