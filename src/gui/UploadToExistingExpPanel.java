package gui;

import util.AnnotationDataType;
import util.FileDrop;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class UploadToExistingExpPanel extends JPanel implements ExperimentPanel {
    
    private JButton selectFilesToUploadButton, uploadFilesToExperimentButton;
    private ArrayList<JComboBox> annotationBoxes;
    private ArrayList<JTextField> annotationFields;
    private AnnotationDataType[] annotations;
    JPanel northPanel, centerPanel, uploadFilesPanel, buttonsPanel, mainPanel;
    private HashMap<File, UploadFileRow> uploadFileRows;
    
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
        
        new FileDrop(this, new FileDrop.Listener() {
            public void filesDropped(java.io.File[] files) {
                createUploadFileRow(files);
            }
        });
        
        buttonsPanel.add(selectFilesToUploadButton);
        buttonsPanel.add(uploadFilesToExperimentButton);
        
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        uploadFilesToExperimentButton.setEnabled(false);
        add(mainPanel);
        repaint();
        revalidate();
    }
    
    public void createUploadFileRow(File[] files) {
        for (File f : files) {
            UploadFileRow fileRow = new UploadFileRow(f, this);
            uploadFileRows.put(f, fileRow);
        }
        repaintSelectedFiles();
    }
    
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
    
    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        selectFilesToUploadButton.addActionListener(listener);
    }
    
    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadFilesToExperimentButton.addActionListener(listener);
    }
    
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
    
    public void enableUploadButton(boolean b) {
        uploadFilesToExperimentButton.setEnabled(b);
    }
    
    @Override
    public void removeAll() {
        uploadFilesPanel.removeAll();
        northPanel.removeAll();
        super.removeAll();
    }
}
