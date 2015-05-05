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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import model.ErrorLogger;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileDrop;
import util.GenomeReleaseData;

public class UploadToNewExpPanel extends JPanel implements ExperimentPanel {

    private static final long serialVersionUID = 7664913630434090250L;
    private HashMap<File, UploadFileRow> uploadFileRows;
    private HashMap<String, JComboBox<String>> annotationBoxes;
    private HashMap<String, JTextField> annotationFields;
    private HashMap<String, JPanel> currentAnnotations;
    private ArrayList<String> annotationHeaders;
    private JPanel uploadFilesPanel, newExpPanel, buttonsPanel,
            uploadBackground;
    private JButton uploadButton, uploadSelectedBtn, selectButton;
    private AnnotationDataType[] annotations;
    private JLabel expNameLabel, boldTextLabel;
    private JComboBox<String> species;
    private JTextField expID;
    private ArrayList<String> genome;
    private GridBagConstraints gbc;
    private boolean isNewExp;

    /**
     * Constructor initiating the new experiment panel.
     */
    public UploadToNewExpPanel() {
        setLayout(new BorderLayout());
        uploadFileRows = new HashMap<File, UploadFileRow>();
        annotationBoxes = new HashMap<String, JComboBox<String>>();
        annotationFields = new HashMap<String, JTextField>();
        annotationHeaders = new ArrayList<String>();
        uploadBackground = new JPanel(new BorderLayout());
        buttonsPanel = new JPanel(new FlowLayout());
        uploadFilesPanel = new JPanel(new GridLayout(0, 1));
        selectButton = new JButton();
        uploadSelectedBtn = new JButton();
        uploadButton = new JButton();
        newExpPanel = new JPanel();
        expNameLabel = new JLabel();
        boldTextLabel = new JLabel(
                "<html><b>Bold text indicates a forced annotation.</b></html>");
        boldTextLabel.setOpaque(true);
        expNameLabel = new JLabel();
        species = new JComboBox<String>();
        species.setPreferredSize(new Dimension(120, 31));
        expID = new JTextField();
        expID.setColumns(10);
        expID.getDocument().addDocumentListener(new FreetextListener());
        genome = new ArrayList<String>();
        currentAnnotations = new HashMap<String, JPanel>();
        enableUploadButton(false);
    }

    /**
     * Method creating the new experiment panel, with the given annotations to
     * fill in.
     *
     * @param annotations
     *            An array with the current available annotations on the server.
     * @param isNewExp
     */
    public void createNewExpPanel(AnnotationDataType[] annotations,
            boolean isNewExp) {
        this.annotations = annotations;
        this.isNewExp = isNewExp;
        createNewExp();
    }

    /**
     * Method redefining the removeAll() method of JPanel. Used to clear this
     * panel.
     */
    @Override
    public void removeAll() {
        newExpPanel.removeAll();
        uploadBackground.removeAll();
        buttonsPanel.removeAll();
        uploadFilesPanel.removeAll();
        super.removeAll();
    }

    /**
     * A method returning the current upload file rows.
     *
     * @return a Hashmap containing the current UploadFileRows.
     */
    public HashMap<File, UploadFileRow> getFileRows() {
        return uploadFileRows;
    }

    /**
     * Method returning the current selected species.
     *
     * @return a String representing the species.
     */
    public String getSelectedSpecies() {

        if (species != null) {
            if (species.getSelectedItem() == null) {
                return "";
            } else {
                return species.getSelectedItem().toString();
            }
        } else {
            return "";
        }
    }

    /**
     * Return the Entered value of the annotation with this name.
     *
     * @param annotationName
     *            To look for
     * @return The entered value, or null if it doesn't exist
     */
    private String getEnteredAnnotationValue(String annotationName) {

        if (annotationBoxes.containsKey(annotationName)) {
            return annotationBoxes.get(annotationName).getSelectedItem()
                    .toString();
        } else if (annotationFields.containsKey(annotationName)) {
            return annotationFields.get(annotationName).getText();
        }

        return null;
    }

    /**
     * Method used to set the current available genome versions, for the chosen
     * species.
     *
     * @param grd
     *            An array containing the current genome releases.
     */
    public void setGenomeReleases(GenomeReleaseData[] grd) {
        genome.clear();
        if (grd.length > 0) {
            for (GenomeReleaseData g : grd) {
                try {
                    genome.add(g.getVersion());
                } catch (NullPointerException e) {
                    ErrorLogger.log(e);
                    ErrorLogger.log("Couldn't find genome version.");
                }
            }
        }
        for (File f : uploadFileRows.keySet()) {
            uploadFileRows.get(f).resetType();
        }
    }

    /**
     * Method returning the current available genome releases for the species
     * selected.
     *
     * @return an array of Strings representing the releases.
     */
    public ArrayList<String> getGenomeReleases() {
        return genome;
    }

    /**
     * Method adding a listener to the "selectButton" button.
     *
     * @see controller.UploadTabController#SelectFilesToNewExpListener()
     * @param listener
     *            The listener to select files.
     */
    public void addSelectButtonListener(ActionListener listener) {
        selectButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "uploadButton" button.
     *
     * @see controller.UploadTabController#UploadNewExpListener
     * @param listener
     *            The listener to start uploading all files.
     */
    public void addUploadButtonListener(ActionListener listener) {
        uploadButton.addActionListener(listener);
    }

    /**
     * Method adding a listener to the "uploadSelectedBtn" button.
     *
     * @param listener
     *            The listener to start uploading selected files.
     */
    public void addUploadSelectedFilesListener(ActionListener listener) {
        uploadSelectedBtn.addActionListener(listener);

    }

    /**
     * Method adding a listener to the "species" combobox.
     *
     * @see controller.UploadTabController#SpeciesSelectedListener()
     * @param listener
     *            The listener for the species combobox.
     */
    public void addSpeciesSelectedListener(ActionListener listener) {
        species.addActionListener(listener);
    }

    /**
     * A method creating a panel for creating a new experiment to upload files
     * to it.
     */
    public void createNewExp() {

        // TODO: Fix this try NPTR
        try {
            GridBagLayout gbl_panel = new GridBagLayout();
            gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
            gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
            gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, Double.MIN_VALUE };
            gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0,
                    Double.MIN_VALUE };
            newExpPanel.setLayout(gbl_panel);
            add(newExpPanel, BorderLayout.NORTH);
            updateAnnotations(annotations);
            uploadBackground.add(uploadFilesPanel, BorderLayout.NORTH);
            add(uploadBackground, BorderLayout.CENTER);

            // Makes dragging & dropping of files into the panel possible
            new FileDrop(this, new FileDrop.Listener() {
                public void filesDropped(java.io.File[] files) {
                    createUploadFileRow(files);
                    enableUploadButton(true);
                }
            });

        } catch (NullPointerException e) {
            ErrorLogger.log(e);
            System.err
                    .println("NullPointerException while retrieving annotations from server.");
        }
    }

    /**
     * Method updating current annotations available at the server.
     */
    public void updateAnnotations(AnnotationDataType[] annotations) {

        if (!annotationHeaders.contains("UniqueExpID")) {
            JPanel exp = new JPanel(new BorderLayout());
            expNameLabel.setText("<html><b>Experiment ID</b></html>");
            expNameLabel.setToolTipText("Bold indicates a forced annotation");
            exp.add(expNameLabel, BorderLayout.NORTH);
            exp.add(expID, BorderLayout.CENTER);
            annotationHeaders.add("UniqueExpID");
            currentAnnotations.put("UniqueExpID", exp);
        }
        ArrayList<String> exists = new ArrayList<String>();
        exists.add("UniqueExpID");
        for (AnnotationDataType a : annotations) {
            if (annotationHeaders.contains(a.getName())) {
                if (a.getValues()[0].equalsIgnoreCase("freetext")) {
                    annotationFields.get(a.getName()).setEnabled(true);
                    exists.add(a.getName());

                } else if (annotationBoxes.containsKey(a.getName())) {

                    JComboBox<String> currentBox = annotationBoxes.get(a
                            .getName());
                    currentBox.setEnabled(true);
                    // +1 for emty item.
                    if (a.getValues().length + 1 == currentBox.getItemCount()) {
                        exists.add(a.getName());
                    } else {

                        currentBox.removeAllItems();
                        String[] aCopy = new String[a.getValues().length + 1];
                        aCopy[0] = "";
                        for (int i = 1; i <= a.getValues().length; i++) {
                            aCopy[i] = a.getValues()[i - 1];
                        }
                        for (String s : aCopy) {
                            currentBox.addItem(s);
                        }
                    }

                }
            } else {
                JPanel p = new JPanel(new BorderLayout());
                JLabel annotationLabel = null;
                if (a.isForced()) {
                    annotationLabel = new JLabel("<html><b>" + a.getName()
                            + "</b></html>");
                    annotationLabel
                            .setToolTipText("Bold indicates a forced annotation");
                } else {
                    annotationLabel = new JLabel(a.getName());
                }
                annotationHeaders.add(a.getName());
                p.add(annotationLabel, BorderLayout.NORTH);
                if (a.getValues()[0].equalsIgnoreCase("freetext")) {
                    final JTextField textField = new JTextField();
                    textField.setColumns(10);

                    // Add listener for when the text in the textfield changes.
                    textField.getDocument().addDocumentListener(
                            new FreetextListener());

                    annotationFields.put(a.getName(), textField);
                    p.add(textField, BorderLayout.CENTER);
                } else {

                    final JComboBox<String> comboBox;
                    String[] aCopy = new String[a.getValues().length + 1];
                    aCopy[0] = "";
                    for (int i = 1; i <= a.getValues().length; i++) {
                        aCopy[i] = a.getValues()[i - 1];
                    }

                    if (a.getName().equalsIgnoreCase("species")) {
                        comboBox = species;
                        species.removeAllItems();
                        for (String s : aCopy) {
                            species.addItem(s);
                        }
                        species.setSelectedIndex(0);
                    } else {
                        comboBox = new JComboBox<String>(aCopy);

                    }

                    comboBox.setPreferredSize(new Dimension(120, 31));
                    /*
                     *
                     * Listener for when the user chooses something in the
                     * combobox.
                     */
                    comboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            enableUploadButton(forcedAnnotationCheck());
                        }
                    });

                    annotationBoxes.put(a.getName(), comboBox);
                    p.add(comboBox, BorderLayout.CENTER);

                }
                currentAnnotations.put(a.getName(), p);
                exists.add(a.getName());
            }
        }

        String[] checkIt = new String[annotationHeaders.size()];
        for (int i = 0; i < annotationHeaders.size(); i++) {
            checkIt[i] = annotationHeaders.get(i);
        }
        for (String s : checkIt) {
            if (!exists.contains(s)) {
                annotationHeaders.remove(s);
                annotationFields.remove(s);
                annotationBoxes.remove(s);
                currentAnnotations.remove(s);
            }
            exists.remove(s);
        }

        buildAnnotationsMenu();
        this.annotations = annotations;
    }

    /**
     * Method building the annotation menu.
     */
    public void buildAnnotationsMenu() {
        newExpPanel.removeAll();
        int x = 0;
        int y = 0;
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 30);
        gbc.gridx = x;
        gbc.gridy = y;
        newExpPanel.add(currentAnnotations.get("UniqueExpID"), gbc);
        x++;
        for (String s : currentAnnotations.keySet()) {
            if (!s.equals("UniqueExpID")) {
                if (x > 6) {
                    x = 0;
                    y++;
                }
                gbc.gridx = x;
                gbc.gridy = y;
                newExpPanel.add(currentAnnotations.get(s), gbc);
                x++;
            }
        }
        newExpPanel.repaint();
        newExpPanel.revalidate();
        setNewOrExistingView();
        repaintSelectedFiles();
    }

    // Combined two javadocs
    /**
     * Creates an uploadFileRow from the provided files. Checks if the files are
     * already in an uploadFileRow so there won't be duplicates. Displays an
     * error message if it was selected and added previously. <br>
     * OR <br>
     * Add the selected files as UploadFileRow to the NewExp Panel.
     *
     * @param files
     *            The files to make an uploadFileRow out of. <br>
     *            OR <br>
     *            [] for each to add
     */
    public void createUploadFileRow(File[] files) {
        for (File f : files) {
            if (!uploadFileRows.containsKey(f)) {
                UploadFileRow fileRow = new UploadFileRow(f, this, isNewExp);
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
     * Deletes an uploadFileRow and calls repaintSelectedFiles() to repaint. If
     * it fails to find the file, an error message is shown to the user.<br>
     * OR<br>
     * Deletes a file row.
     *
     * @param f
     *            This is used to identify which uploadFileRow to be deleted.<br>
     *            OR<br>
     *            Used to identify which fileRow to be deleted.
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
     * Checks if there are any uploadfilerows. Disables the uploadbutton if
     * there aren't, and adds them to the panel if there are. After these
     * updates, it repaints the panel.
     */
    private void repaintSelectedFiles() {
        uploadFilesPanel.add(boldTextLabel);
        if (!uploadFileRows.isEmpty()) {
            for (File f : uploadFileRows.keySet()) {
                uploadFilesPanel.add(uploadFileRows.get(f));
            }
        } else {
            enableUploadButton(false);
        }
        buttonsPanel.add(selectButton);
        buttonsPanel.add(uploadSelectedBtn);
        buttonsPanel.add(uploadButton);
        uploadFilesPanel.add(buttonsPanel);
        repaint();
        revalidate();
    }

    /**
     *
     */
    private void setNewOrExistingView() {
        if (isNewExp) {
            expID.setText("");
            expID.setEnabled(true);
            selectButton.setText("Browse files");
            uploadSelectedBtn.setVisible(true);
            uploadSelectedBtn.setText("Create with selected files");
            uploadButton.setText("Create with all files");
        } else {
            uploadSelectedBtn.setVisible(false);
            selectButton.setText("Browse files");
            uploadButton.setText("Upload files");
        }
    }

    /**
     * Method returning the ExpID for a new experiment.
     *
     * @return the ID of a experiment.
     */
    public String getNewExpID() {
        return expID.getText();
    }

    /**
     * Method returning the genome chosen for the file given.
     *
     * @param f
     *            The file suppose to be uploaded.
     * @return a String representing the chosen genome version.
     */
    public String getGenomeVersion(File f) {
        if (uploadFileRows.containsKey(f)) {
            return uploadFileRows.get(f).getGenomeRelease();
        }
        return null;
    }

    /**
     * Method returning the chosen annotations for the new experiment.
     *
     * @return an AnnotationDataValue array with all the annotations.
     */
    public AnnotationDataValue[] getUploadAnnotations() {
        AnnotationDataValue[] a = new AnnotationDataValue[annotationHeaders
                .size() - 1];
        int nrOfAdded = 0;
        for (int i = 0; i < annotationHeaders.size(); i++) {
            String annotationName = annotationHeaders.get(i);
            if (!annotationName.equals("UniqueExpID")) {

                String value = getEnteredAnnotationValue(annotationName);

                a[nrOfAdded] = new AnnotationDataValue(Integer.toString(i),
                        annotationName, value);

                nrOfAdded++;
            }
        }
        return a;
    }

    /**
     * Method getting the files to be uploaded.
     *
     * @return an array with the files.
     */
    public ArrayList<File> getUploadFiles() {
        ArrayList<File> files = new ArrayList<>();
        for (File f : uploadFileRows.keySet()) {
            files.add(f);
        }
        return files;
    }

    /**
     * Method returning the files that are selected.
     *
     * @return an ArrayList with the selected files.
     */
    public ArrayList<File> getSelectedFilesToUpload() {
        ArrayList<File> files = new ArrayList<>();
        for (File f : uploadFileRows.keySet()) {
            if (uploadFileRows.get(f).isSelected()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * Method returning the type of the files to be uploaded.
     *
     * @return a HashMap with the filenames and there types.
     */
    public HashMap<String, String> getTypes() {
        HashMap<String, String> types = new HashMap<>();
        for (File f : uploadFileRows.keySet()) {
            types.put(f.getName(), uploadFileRows.get(f).getType());
        }
        return types;
    }

    /**
     * Method checking if the forced annotations are filled.
     *
     * @return true if all forced annotation fields (including expID) are
     *         filled. Otherwise returns false.
     */
    public boolean forcedAnnotationCheck() {

        String expIDName = expID.getText();
        if (expIDName == null || expIDName.equals("")) {
            return false;
        }

        boolean allForcedAnnotationsAreFilled = true;
        String annotationName;
        String text;
        JTextField annotationField;
        JComboBox<String> annotationBox;

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

    // one javadoc from interface, one from here.
    /**
     * Sets the experiment button to either be enabled or disabled. Only enables
     * it if there are selected files and all forced annotations fields are
     * filled.<br>
     * OR<br>
     * Calls the uploadPanel's enableUploadButton method to try to either make
     * the upload button enabled or disabled. If all of the required annotation
     * fields are NOT filled, this method won't set it to true.
     *
     * @param b
     *            Whether it should try to: enable the button (true) or disable
     *            it (false)<br>
     *            OR<br>
     *            Whether it should try to make the button enabled (true) or
     *            disabled (false).
     */

    public void enableUploadButton(boolean b) {
        if (b) {
            if (!uploadFileRows.isEmpty() && forcedAnnotationCheck()) {
                uploadSelectedBtn.setEnabled(true);
                uploadButton.setEnabled(true);
            }
        } else {
            uploadSelectedBtn.setEnabled(false);
            if (isNewExp) {
                uploadButton.setEnabled(false);
            } else {
                // TODO S�tt till true n�r edit annotation �r implementerat CF
                uploadButton.setEnabled(false);
            }
        }
    }

    /**
     * Listener for when the text in a textfield changes.
     */
    private class FreetextListener implements DocumentListener {
        @Override
        public void insertUpdate(DocumentEvent documentEvent) {
            react(documentEvent);
        }

        @Override
        public void removeUpdate(DocumentEvent documentEvent) {
            react(documentEvent);
        }

        @Override
        public void changedUpdate(DocumentEvent documentEvent) {
            react(documentEvent);
        }

        public void react(DocumentEvent documentEvent) {
            if (forcedAnnotationCheck()) {

                enableUploadButton(true);
            } else {
                enableUploadButton(false);
            }

        }
    }

    /**
     * c12jhn 16/4-15 Getter to get all of the annotationfields of the panel.
     * Used only in testing
     *
     * @return
     */
    public HashMap<String, JTextField> getAnnotationFields() {
        return this.annotationFields;
    }

    /**
     * Method that filles out the annotations fields with an existing
     * experiments values. Used to edit an existing experiment.
     *
     * @param ed
     *            - Experiment to get annotations from
     */
    public void setExistingExp(ExperimentData ed) {
        expID.setText(ed.getName());
        for (AnnotationDataValue data : ed.getAnnotations()) {
            if (annotationBoxes.containsKey(data.getName())) {
                annotationBoxes.get(data.getName()).setSelectedItem(
                        data.getValue());
                // TODO Ta bort n�r edit annotation �r implementerat
                annotationBoxes.get(data.getName()).setEnabled(false);
            } else if (annotationFields.containsKey(data.getName())) {
                annotationFields.get(data.getName()).setText(data.getValue());
                // TODO Ta bort n�r edit annotation �r implementerat
                annotationFields.get(data.getName()).setEnabled(false);
            }

        }
        expID.setEnabled(false);
    }

    /**
     * Returns the status if active experiment is a new experiment or existing
     *
     * @return
     */
    public boolean getIsNewExp() {
        return isNewExp;
    }
}
