package gui;

import util.AnnotationDataType;
import util.FileDrop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadToExistingExpPanel extends JPanel
        implements ExperimentPanel {

    private JButton selectFilesToUploadButton, uploadFilesToExperimentButton;
    private ArrayList<JComboBox> annotationBoxes;
    private ArrayList<JTextField> annotationFields;
    private AnnotationDataType[] annotations;
    JPanel northPanel, centerPanel, uploadFilesPanel, buttonsPanel, mainPanel;
    private HashMap<File, UploadFileRow> uploadFileRows;

    /**
     * Initiates an uploadToExistingExpPanel with its standard buttons
     * and panels. Calls the method build() to build it further.
     */

    public UploadToExistingExpPanel() {
        selectFilesToUploadButton = new JButton("Select files");
        uploadFilesToExperimentButton = new JButton("Upload to experiment");
        uploadFileRows = new HashMap<File, UploadFileRow>();

        mainPanel = new JPanel(new BorderLayout());
        northPanel = new JPanel();
        centerPanel = new JPanel(new BorderLayout());
        uploadFilesPanel = new JPanel(new GridLayout(0, 1));
        buttonsPanel = new JPanel(new FlowLayout());

        build();
    }

    /**
     * Builds/rebuilds the panel. This is not part of the constructor so it can
     * be called from elsewhere aswell.
     */
    public void build() {
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        centerPanel.add(uploadFilesPanel, BorderLayout.NORTH);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        northPanel.setLayout(gbl_panel);

        //Makes dragging & dropping of files into the panel possible
        new FileDrop(this, new FileDrop.Listener() {
            public void filesDropped(java.io.File[] files) {
                createUploadFileRow(files);
                enableUploadButton(true);
            }
        });

        buttonsPanel.add(selectFilesToUploadButton);
        buttonsPanel.add(uploadFilesToExperimentButton);

        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        uploadFilesToExperimentButton.setEnabled(false);
        add(mainPanel);
        repaint();
        revalidate();
        uploadFilesPanel.repaint();
        uploadFilesPanel.revalidate();
    }

    /**
     * Creates an uploadFileRow from the provided files. Checks if the files are
     * already in an uploadFileRow so there won't be duplicates. Displays an
     * error message if it was selected and added previously.
     *
     * @param files The files to make an uploadFileRow out of.
     */
    public void createUploadFileRow(File[] files) {
        for (File f : files) {
            if (!uploadFileRows.containsKey(f)) {
                UploadFileRow fileRow = new UploadFileRow(f, this);
                uploadFileRows.put(f, fileRow);
            } else {
                JOptionPane.showMessageDialog(this, "File already selected: "
                                + f.getName() + "", "File error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
        repaintSelectedFiles();
    }

    /**
     * Deletes an uploadFileRow and calls repaintSelectedFiles() to repaint.
     * If it fails to find the file, an error message is shown to the user.
     *
     * @param f This is used to identify which uploadFileRow to be deleted.
     */
    public void deleteFileRow(File f) {
        if (uploadFileRows.containsKey(f)) {
            uploadFileRows.remove(f);
            uploadFilesPanel.removeAll();
            repaintSelectedFiles();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Can't delete file: " + f.getName() + "", "File error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Adds a listener for the "Select files" button.
     *
     * @param listener The listener to be added.
     */
    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        selectFilesToUploadButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the upload button.
     *
     * @param listener The listener to be added.
     */
    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadFilesToExperimentButton.addActionListener(listener);
    }

    /**
     * Sets the annotations.
     *
     * @param annotations The annotations to set the panel's annotations to.
     */
    public void setAnnotations(AnnotationDataType[] annotations) {
        this.annotations = annotations;
    }

    public void addAnnotationsForExistingExp() throws NullPointerException {
        annotationBoxes = new ArrayList<JComboBox>();
        annotationFields = new ArrayList<JTextField>();
        int x = 0;
        int y = 0;
        String[] annotationNames = new String[annotations.length];
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].isForced()) {
                if (x > 6) {
                    System.out.println("H�R");
                    x = 0;
                    y++;
                }
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.anchor = GridBagConstraints.WEST;
                gbc.insets = new Insets(5, 0, 5, 30);
                gbc.gridx = x;
                gbc.gridy = y;
                JPanel p = new JPanel(new BorderLayout());
                JLabel annotationLabel = new JLabel(annotations[i].getName());
                p.add(annotationLabel, BorderLayout.NORTH);
                if (annotations[i].getValues()[0].equals("freetext")) {
                    JTextField textField = new JTextField();
                    textField.setColumns(10);
                    annotationFields.add(textField);
                    p.add(textField, BorderLayout.CENTER);
                    northPanel.add(p, gbc);
                    textField.setEnabled(false);
                } else {
                    JComboBox comboBox = new JComboBox(
                            annotations[i].getValues());
                    comboBox.setPreferredSize(new Dimension(120, 31));
                    annotationBoxes.add(comboBox);
                    p.add(comboBox, BorderLayout.CENTER);
                    northPanel.add(p, gbc);
                    comboBox.setEnabled(false);
                }
                x++;
            }
        }
    }

    /**
     * Checks if there are any uploadfilerows.
     * Disables the uploadbutton if there aren't, and adds them to the panel if there are.
     * After these updates, it repaints the panel.
     */
    private void repaintSelectedFiles() {
        if (!uploadFileRows.isEmpty()) {
            for (File f : uploadFileRows.keySet()) {
                uploadFilesPanel.add(uploadFileRows.get(f));
            }
        } else {
            enableUploadButton(false);
        }
        repaint();
        revalidate();
    }

    /**
     * Sets the experiment button to either be enabled or disabled.
     *
     * @param b Whether it should be enabled (true) or disabled (false)
     */
    public void enableUploadButton(boolean b) {
        uploadFilesToExperimentButton.setEnabled(b);
    }

    /**
     * Removes everything in the panel and underlying panels.
     */
    @Override
    public void removeAll() {
        uploadFilesPanel.removeAll();
        northPanel.removeAll();
        super.removeAll();
    }
}
