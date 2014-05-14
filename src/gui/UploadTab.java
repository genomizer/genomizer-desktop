package gui;

import util.ActivePanel;
import util.AnnotationDataType;
import util.AnnotationDataValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadTab extends JPanel implements ExperimentPanel {

    private static final long serialVersionUID = -2830290705724588252L;
    private JButton addToExistingExpButton,
            newExpButton, selectButton, uploadButton;
    private JPanel northPanel, expNamePanel,
            uploadPanel, newExpPanel, uploadFilesPanel, uploadBackground;
    private JTextArea                      experimentNameField;
    private UploadToExistingExpPanel       uploadToExistingExpPanel;
    private AnnotationDataType[]           annotations;
    private ArrayList<String>              annotationHeaders;
    private ArrayList<File>                currFiles;
    private HashMap<String, JComboBox>     annotationBoxes;
    private HashMap<String, JTextField>    annotationFields;
    private HashMap<String, UploadFileRow> uploadFileRows;
    private ActivePanel                    activePanel;
    private JLabel                         expNameLabel;
    private JTextField                     expName;
    private JScrollPane                    uploadScroll;
    private JPanel                         buttonsPanel;

    public UploadTab() {
        currFiles = new ArrayList<File>();
        annotationHeaders = new ArrayList<String>();
        uploadFileRows = new HashMap<String, UploadFileRow>();
        activePanel = ActivePanel.NONE;
        setLayout(new BorderLayout());
        uploadToExistingExpPanel = new UploadToExistingExpPanel();
        northPanel = new JPanel();
        expNamePanel = new JPanel();
        expNamePanel.setBorder(BorderFactory.createTitledBorder(""));
        add(northPanel, BorderLayout.NORTH);
        northPanel.add(new JLabel("Experiment name: "));
        experimentNameField = new JTextArea();
        experimentNameField.setColumns(30);
        expNamePanel.add(experimentNameField);
        northPanel.add(expNamePanel);
        addToExistingExpButton = new JButton("Add to existing experiment");
        northPanel.add(addToExistingExpButton, BorderLayout.EAST);
        uploadPanel = new JPanel(new BorderLayout());
        uploadScroll = new JScrollPane(uploadPanel);
        add(uploadScroll, BorderLayout.CENTER);
        uploadBackground = new JPanel(new BorderLayout());
        buttonsPanel = new JPanel(new FlowLayout());
        uploadFilesPanel = new JPanel(new GridLayout(0, 1));
        newExpButton = new JButton("Create new experiment");
        selectButton = new JButton("Select files");
        uploadButton = new JButton("Upload Selected Files");
        newExpPanel = new JPanel();
        newExpPanel.setBorder(BorderFactory.createTitledBorder("Experiment"));
        expNameLabel = new JLabel();
        expName = new JTextField();
        expName.setColumns(10);
        northPanel.add(newExpButton, BorderLayout.EAST);
    }

    /**
     * Displays a panel for adding to an existing experiment.
     *
     * @param annotations The annotations of the experiment.
     */
    public void addExistingExpPanel(AnnotationDataType[] annotations) {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.EXISTING;
        uploadToExistingExpPanel.build();
        uploadToExistingExpPanel.setAnnotations(annotations);
        uploadToExistingExpPanel.addAnnotationsForExistingExp();
        uploadPanel.add(uploadToExistingExpPanel, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    public UploadToExistingExpPanel getUploadToExistingExpPanel() {
        return uploadToExistingExpPanel;
    }

    public void addAddToExistingExpButtonListener(ActionListener listener) {
        addToExistingExpButton.addActionListener(listener);
    }

    public void addNewExpButtonListener(ActionListener listener) {
        newExpButton.addActionListener(listener);
    }

    public void addSelectButtonListener(ActionListener listener) {
        selectButton.addActionListener(listener);
    }

    public void addUploadButtonListener(ActionListener listener) {
        uploadButton.addActionListener(listener);
    }

    private void createNewExpPanel() {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.NEW;
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        newExpPanel.setLayout(gbl_panel);
        uploadPanel.add(newExpPanel, BorderLayout.NORTH);
        addAnnotationsForNewExp();
        repaintSelectedFiles();
        uploadBackground.add(uploadFilesPanel, BorderLayout.NORTH);
        uploadPanel.add(uploadBackground, BorderLayout.CENTER);
    }

    private void addAnnotationsForNewExp() throws NullPointerException {
        annotationBoxes = new HashMap<String, JComboBox>();
        annotationFields = new HashMap<String, JTextField>();
        int x = 0;
        int y = 0;
        String[] annotationNames = new String[annotations.length];
        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < annotations.length; i++) {
            if (i == 0) {
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(5, 0, 5, 30);
                gbc.gridx = x;
                gbc.gridy = y;
                JPanel p = new JPanel(new BorderLayout());
                expNameLabel.setText("Experiment name");
                p.add(expNameLabel, BorderLayout.NORTH);
                p.add(expName, BorderLayout.CENTER);
                newExpPanel.add(p, gbc);
                x++;
            } else if (annotations[i].isForced()) {
                if (x > 6) {
                    x = 0;
                    y++;
                }
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(5, 0, 5, 30);
                gbc.gridx = x;
                gbc.gridy = y;
                JPanel p = new JPanel(new BorderLayout());
                JLabel annotationLabel = new JLabel(annotations[i].getName());
                annotationHeaders.add(annotations[i].getName());
                p.add(annotationLabel, BorderLayout.NORTH);
                if (annotations[i].getValues()[0].equals("freetext")) {
                    JTextField textField = new JTextField();
                    textField.setColumns(10);
                    annotationFields.put(annotations[i].getName(), textField);
                    p.add(textField, BorderLayout.CENTER);
                    newExpPanel.add(p, gbc);
                } else {
                    JComboBox comboBox = new JComboBox(
                            annotations[i].getValues());
                    comboBox.setPreferredSize(new Dimension(120, 31));
                    annotationBoxes.put(annotations[i].getName(), comboBox);
                    p.add(comboBox, BorderLayout.CENTER);
                    newExpPanel.add(p, gbc);
                }
                x++;
            }
        }
    }

    public void createNewExp(AnnotationDataType[] annotations) {
        try {
            this.annotations = annotations;
            createNewExpPanel();
            repaint();
            revalidate();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(this,
                    "Eggs are supposed to be green.", "Inane error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createUploadFileRow(String[] fileNames, File[] files) {
        for (File f : files) {
            currFiles.add(f);
        }
        for (String fileName : fileNames) {
            UploadFileRow fileRow = new UploadFileRow(fileName, this);
            uploadFileRows.put(fileName, fileRow);
        }
        repaintSelectedFiles();
    }

    private void repaintSelectedFiles() {
        if (!currFiles.isEmpty()) {
            for (File f : currFiles) {
                uploadFilesPanel.add(uploadFileRows.get(f.getName()));
            }
        }
        buttonsPanel.add(selectButton);
        buttonsPanel.add(uploadButton);
        uploadFilesPanel.add(buttonsPanel);
        repaint();
        revalidate();
    }

    /**
     * Removes the components in the panels when one of them gets chosen by the
     * user, to make sure the new components won't overlap and end up invisible.
     * The method checks the Enum ActivePanel to check which panel was the
     * active one.
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
        }
    }

    public void deleteFileRow(String fileName) {
        uploadFileRows.remove(fileName);
        for (File f : currFiles) {
            if (f.getName().equals(fileName)) {
                currFiles.remove(f);
                break;
            }
        }
        uploadFilesPanel.removeAll();
        buttonsPanel.removeAll();
        repaintSelectedFiles();
    }

    public String getNewExpName() {
        return expName.getText();
    }

    public AnnotationDataValue[] getUploadAnnotations() {
        AnnotationDataValue[] annotations = new AnnotationDataValue[annotationHeaders
                .size()];
        for (int i = 0; i < annotationHeaders.size(); i++) {
            if (annotationBoxes.containsKey(annotationHeaders.get(i))) {
                annotations[i] = new AnnotationDataValue(Integer.toString(i),
                        annotationHeaders.get(i), annotationBoxes
                        .get(annotationHeaders.get(i))
                        .getSelectedItem().toString()
                );
            } else if (annotationFields.containsKey(annotationHeaders.get(i))) {
                annotations[i] = new AnnotationDataValue(Integer.toString(i),
                        annotationHeaders.get(i), annotationFields.get(
                        annotationHeaders.get(i)).getText()
                );
            }
        }
        return annotations;
    }

    public File[] getUploadFiles() {
        File[] files = new File[currFiles.size()];
        for (int i = 0; i < currFiles.size(); i++) {
            files[i] = currFiles.get(i);
        }
        return files;
    }

    public HashMap<String, String> getTypes() {
        HashMap<String, String> types = new HashMap<String, String>();
        for (File f : currFiles) {
            types.put(f.getName(), uploadFileRows.get(f.getName()).getType());
        }
        return types;
    }
}
