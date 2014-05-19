package gui;

import communication.HTTPURLUpload;
import util.ActivePanel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileDrop;
import util.IconFactory;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import util.ActivePanel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.FileDrop;
import communication.UploadHandler;

/**
 * A class representing a upload view in a application for genome reasearches.
 * This calss allows the user to upload files to the database of the
 * application.
 *
 * @author
 *
 */
public class UploadTab extends JPanel implements ExperimentPanel {

    private static final long serialVersionUID = -2830290705724588252L;
    private JButton addToExistingExpButton, newExpButton, selectButton,
            uploadButton;
    private JPanel northPanel, expNamePanel, uploadPanel, newExpPanel,
            uploadFilesPanel, uploadBackground;
    private JTextArea experimentNameField;
    private UploadToExistingExpPanel uploadToExistingExpPanel;
    private AnnotationDataType[] annotations;
    private ArrayList<String> annotationHeaders;
    private CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads;
    private HashMap<String, JComboBox> annotationBoxes;
    private HashMap<String, JTextField> annotationFields;
    private HashMap<File, UploadFileRow> uploadFileRows;
    private ActivePanel activePanel;
    private JLabel expNameLabel;
    private JTextField expID;
    private JScrollPane uploadScroll;
    private JPanel buttonsPanel;

    /**
     * Constructor creating a upload tab.
     */
    public UploadTab() {
        uploadFileRows = new HashMap<File, UploadFileRow>();
        annotationHeaders = new ArrayList<String>();
        activePanel = ActivePanel.NONE;
        setLayout(new BorderLayout());
        uploadToExistingExpPanel = new UploadToExistingExpPanel();
        northPanel = new JPanel();
        expNamePanel = new JPanel();
        add(northPanel, BorderLayout.NORTH);
        northPanel.add(new JLabel("Experiment name: "));
        experimentNameField = new JTextArea();
        experimentNameField.setColumns(30);
        expNamePanel.add(experimentNameField);
        northPanel.add(expNamePanel);
        northPanel.setBorder(BorderFactory.createTitledBorder("Upload"));
        addToExistingExpButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getSearchIcon(35, 35),
                IconFactory.getSearchHoverIcon(37, 37), 37, 37, "Search for existing experiment");
        northPanel.add(addToExistingExpButton, BorderLayout.EAST);
        uploadPanel = new JPanel(new BorderLayout());
        uploadScroll = new JScrollPane(uploadPanel);
        add(uploadScroll, BorderLayout.CENTER);
        uploadBackground = new JPanel(new BorderLayout());
        buttonsPanel = new JPanel(new FlowLayout());
        uploadFilesPanel = new JPanel(new GridLayout(0, 1));
        newExpButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getNewExperimentIcon(35, 35),
                IconFactory.getNewExperimentHoverIcon(37, 37), 37, 37, "Create new experiment");
        selectButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getBrowseIcon(40, 40),
                IconFactory.getBrowseHoverIcon(42, 42), 42, 42, "Browse for files");
        uploadButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getUploadIcon(40, 40),
                IconFactory.getUploadHoverIcon(42,42), 42, 42, "Upload data");;
        newExpPanel = new JPanel();
        expNameLabel = new JLabel();
        expID = new JTextField();
        expID.setColumns(10);
        expID.getDocument().addDocumentListener(new FreetextListener());
        northPanel.add(newExpButton, BorderLayout.EAST);
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
        addToExistingExpButton.addActionListener(listener);
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
        selectButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "uploadButton".
     *
     * @param listener
     *            The listener to start uploading selected files.
     */
    public void addUploadButtonListener(ActionListener listener) {
        uploadButton.addActionListener(listener);
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

     * @param ed
     *
     */
    public void addExistingExpPanel(ExperimentData ed) {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.EXISTING;
        uploadToExistingExpPanel.build();
        ArrayList<AnnotationDataValue> annots = ed.getAnnotations();
        uploadToExistingExpPanel.addExistingExp(ed);
//        uploadToExistingExpPanel.addAnnotationsForExistingExp();
        uploadPanel.add(uploadToExistingExpPanel, BorderLayout.CENTER);
        /*
         * setBorder(BorderFactory
         * .createTitledBorder("Upload to existing experiment"));
         */
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
        killContentsOfUploadPanel();

        JLabel redTextLabel = new JLabel("<html><b>Bolded text = forced annotation.</b></html>");
        redTextLabel.setOpaque(true);
        uploadFilesPanel.add(redTextLabel, BorderLayout.NORTH);

        /*
         * setBorder(BorderFactory
         * .createTitledBorder("Create new experiment"));
         */
        try {
            this.annotations = annotations;
            activePanel = ActivePanel.NEW;
            GridBagLayout gbl_panel = new GridBagLayout();
            gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
            gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
            gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, Double.MIN_VALUE };
            gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0,
                    Double.MIN_VALUE };
            newExpPanel.setLayout(gbl_panel);
            uploadPanel.add(newExpPanel, BorderLayout.NORTH);
            addAnnotationsForExp();
            repaintSelectedFiles();
            uploadBackground.add(uploadFilesPanel, BorderLayout.NORTH);
            uploadPanel.add(uploadBackground, BorderLayout.CENTER);
            new FileDrop(this, new FileDrop.Listener() {
                public void filesDropped(java.io.File[] files) {
                    createUploadFileRow(files);
                    enableUploadButton(true);
                }
            });
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this,
                    "Eggs are supposed to be green.", "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * A method dynamically adding annotations from the server. In order to
     * customize the experiment, which the files should be uploaded to.
     *
     *
     * @throws NullPointerException
     *             if a annotation points at null value.
     */
    private void addAnnotationsForExp() throws NullPointerException {
        annotationBoxes = new HashMap<String, JComboBox>();
        annotationFields = new HashMap<String, JTextField>();
        int x = 0;
        int y = 0;
        String[] annotationNames = new String[annotations.length];
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 30);
        gbc.gridx = x;
        gbc.gridy = y;
        
        JPanel exp = new JPanel(new BorderLayout());
        expNameLabel.setText("<html><b>Experiment ID</b></html>");
        //expNameLabel.setForeground(Color.RED);
        expNameLabel.setToolTipText("Bold indicates a forced annotation");
        exp.add(expNameLabel, BorderLayout.NORTH);
        expID.setText(experimentNameField.getText());
        exp.add(expID, BorderLayout.CENTER);
        newExpPanel.add(exp, gbc);
        x++;
        for (int i = 0; i < annotations.length; i++) {

            if (annotations[i].getValues().length > 0
                    && annotations[i].isForced()) {
                if (x > 6) {
                    x = 0;
                    y++;
                }
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(5, 0, 5, 30);
                gbc.gridx = x;
                gbc.gridy = y;
                JPanel p = new JPanel(new BorderLayout());
                String label = null;
                JLabel annotationLabel = new JLabel("<html><b>" + annotations[i].getName() + "</b></html>");
                if (annotations[i].isForced()) {
                    //annotationLabel.setForeground(Color.RED);
                    annotationLabel
                            .setToolTipText("Bold indicates a forced annotation");
                }
                annotationHeaders.add(annotations[i].getName());
                p.add(annotationLabel, BorderLayout.NORTH);
                if (annotations[i].getValues()[0].equals("freetext")) {
                    final JTextField textField = new JTextField();
                    textField.setColumns(10);

                    // Add listener for when the text in the textfield changes.
                    textField.getDocument().addDocumentListener(
                            new FreetextListener());

                    annotationFields.put(annotations[i].getName(), textField);
                    p.add(textField, BorderLayout.CENTER);
                    newExpPanel.add(p, gbc);

                } else {
                    final JComboBox comboBox = new JComboBox(
                            annotations[i].getValues());
                    comboBox.setPreferredSize(new Dimension(120, 31));

                    /*
                     * Listener for when the user chooses something in the
                     * combobox.
                     */
                    comboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            String text = (String) comboBox.getSelectedItem();
                            if (!text.equals("") && text != null) {
                                enableUploadButton(true);
                            }
                        }
                    });

                    annotationBoxes.put(annotations[i].getName(), comboBox);
                    p.add(comboBox, BorderLayout.CENTER);
                    newExpPanel.add(p, gbc);
                }
                x++;
            }
        }
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
        for (File f : files) {
            if (!uploadFileRows.containsKey(f)) {
                UploadFileRow fileRow = new UploadFileRow(f, this);
                uploadFileRows.put(f, fileRow);
            } else {
                JOptionPane.showMessageDialog(this, "File already selected: "
                        + f.getName() + "", "File error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        repaintSelectedFiles();
    }

    /**
     * Checks if there are any uploadfilerows. Disables the uploadbutton if
     * there aren't, and adds them to the panel if there are. After these
     * updates, it repaints the panel.
     */
    private void repaintSelectedFiles() {
        if (!uploadFileRows.isEmpty()) {
            for (File f : uploadFileRows.keySet()) {
                uploadFilesPanel.add(uploadFileRows.get(f));
            }
        } else {
            enableUploadButton(false);
        }
        buttonsPanel.add(selectButton);
        buttonsPanel.add(Box.createHorizontalStrut(20));
        buttonsPanel.add(uploadButton);
        uploadFilesPanel.add(buttonsPanel);
        repaint();
        revalidate();
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
                uploadPanel.removeAll();
                newExpPanel.removeAll();
                uploadFilesPanel.removeAll();
                uploadBackground.removeAll();
                repaint();
                revalidate();
                activePanel = ActivePanel.NONE;
                break;
            case GEO:
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
        if (uploadFileRows.containsKey(f)) {
            uploadFileRows.remove(f);
            uploadFilesPanel.removeAll();
            buttonsPanel.removeAll();
            repaintSelectedFiles();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Can't delete file: " + f.getName() + "", "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method returning the ExpID for a new experiment.
     *
     * @return a String with the ID of a experiment.
     */
    public String getNewExpID() {
        return expID.getText();
    }

    public AnnotationDataValue[] getUploadAnnotations() {
        AnnotationDataValue[] annotations = new AnnotationDataValue[annotationHeaders
                .size()];
        for (int i = 0; i < annotationHeaders.size(); i++) {
            if (annotationBoxes.containsKey(annotationHeaders.get(i))) {
                annotations[i] = new AnnotationDataValue(Integer.toString(i),
                        annotationHeaders.get(i), annotationBoxes
                                .get(annotationHeaders.get(i))
                                .getSelectedItem().toString());
            } else if (annotationFields.containsKey(annotationHeaders.get(i))) {
                annotations[i] = new AnnotationDataValue(Integer.toString(i),
                        annotationHeaders.get(i), annotationFields.get(
                                annotationHeaders.get(i)).getText());
            }
        }
        return annotations;
    }

    /**
     * Method returning the files to be uploaded.
     *
     * @return a array with the files.
     *
     */
    public ArrayList<File> getUploadFiles() {
        ArrayList<File> files = new ArrayList<File>();
        for (File f : uploadFileRows.keySet()) {
            files.add(f);
        }
        return files;
    }

    /**
     * Method returning the type of the files to be uploaded.
     *
     * @return a HashMap with the filenames and there types.
     *
     */
    public HashMap<String, String> getTypes() {
        HashMap<String, String> types = new HashMap<String, String>();
        for (File f : uploadFileRows.keySet()) {
            types.put(f.getName(), uploadFileRows.get(f).getType());
        }
        return types;
    }

    /**
     * @return true if all forced annotation fields (including expID) are
     *         filled. Otherwise returns false.
     */
    public boolean forcedAnnotationCheck() {

        String expIDName = expID.getText();
        if (expIDName == null || expIDName.equals("")) {
            return false;
        }

        boolean allForcedAnnotationsAreFilled = true;
        String annotationName = null;
        String text = null;
        JTextField annotationField = null;
        JComboBox<Object> annotationBox = null;

        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].isForced()) {
                annotationName = annotations[i].getName();
                if (annotationFields.containsKey(annotationName)) {
                    annotationField = annotationFields.get(annotationName);
                    text = annotationField.getText();
                    if (text == null || text.equals("")) {
                        allForcedAnnotationsAreFilled = false;
                    }
                    text = null;
                } else if (annotationBoxes.containsKey(annotationName)) {
                    annotationBox = annotationBoxes.get(annotationName);
                    text = (String) annotationBox.getSelectedItem();
                    if (text == null || text.equals("")) {
                        allForcedAnnotationsAreFilled = false;
                    }

                    text = null;
                }
            }
        }
        return allForcedAnnotationsAreFilled;
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
        if (b) {
            if (!uploadFileRows.isEmpty() && forcedAnnotationCheck()) {
                uploadButton.setEnabled(b);
            }
        } else {
            uploadButton.setEnabled(b);
        }
    }

    public String getSearchText() {
        return experimentNameField.getText();
    };

    public void setOngoingUploads(
            CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads) {
        this.ongoingUploads = ongoingUploads;
    }

    private void updateProgress() {
        new Thread(new Runnable() {
            private boolean running;

            @Override
            public void run() {
                running = true;
                while (running) {
                    for (File key : uploadFileRows.keySet()) {
                        UploadFileRow row = uploadFileRows.get(key);
                        for(HTTPURLUpload upload : ongoingUploads) {
                            if(upload.getFileName().equals(row.getFileName())) {
                                row.updateProgressBar(upload.getCurrentProgress());
                            }
                        }
                    }
                    for (File key : uploadToExistingExpPanel.getFileRows().keySet()) {
                        UploadFileRow row = uploadToExistingExpPanel.getFileRows().get(key);
                        for(HTTPURLUpload upload : ongoingUploads) {
                            if(upload.getFileName().equals(row.getFileName())) {
                                row.updateProgressBar(upload.getCurrentProgress());
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
     * Listener for when the text in a textfield changes.
     */
    private class FreetextListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            react();
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            react();
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            react();
        }

        public void react() {
            enableUploadButton(true);
        }
    };
}
