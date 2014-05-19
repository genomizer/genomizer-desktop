package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileDrop;
import util.IconFactory;

public class UploadToExistingExpPanel extends JPanel implements ExperimentPanel {
    
    private JButton selectFilesToUploadButton, uploadFilesToExperimentButton;
    private ArrayList<JComboBox> annotationBoxes;
    private ArrayList<JTextField> annotationFields;
    private AnnotationDataType[] annotations;
    private JPanel northPanel, centerPanel, uploadFilesPanel, buttonsPanel,
            mainPanel;
    private HashMap<File, UploadFileRow> uploadFileRows;
    private JPanel backgroundPanel;
    private ExperimentData ed;
    
    /**
     * Initiates an uploadToExistingExpPanel with its standard buttons and
     * panels. Calls the method build() to build it further.
     */
    
    public UploadToExistingExpPanel() {
        selectFilesToUploadButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getBrowseIcon(40, 40),
                IconFactory.getBrowseHoverIcon(42, 42), 42, 42,
                "Browse for files");
        uploadFilesToExperimentButton = CustomButtonFactory.makeCustomButton(
                IconFactory.getUploadIcon(40, 40),
                IconFactory.getUploadHoverIcon(42, 42), 42, 42, "Upload data");
        uploadFileRows = new HashMap<File, UploadFileRow>();
        
        mainPanel = new JPanel(new BorderLayout());
        northPanel = new JPanel(new BorderLayout());
        centerPanel = new JPanel(new BorderLayout());
        backgroundPanel = new JPanel(new BorderLayout());
        uploadFilesPanel = new JPanel(new GridLayout(0, 1));
        buttonsPanel = new JPanel(new FlowLayout());
        
        setLayout(new BorderLayout());
        build();
    }
    
    public HashMap<File, UploadFileRow> getFileRows() {
        return uploadFileRows;
    }
    
    /**
     * Builds/rebuilds the panel. This is not part of the constructor so it can
     * be called from elsewhere aswell.
     */
    public void build() {
        add(northPanel, BorderLayout.NORTH);
        centerPanel.add(uploadFilesPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        northPanel.setLayout(gbl_panel);
        
        addFileDrop();
        
        // buttonsPanel.add(selectFilesToUploadButton);
        // buttonsPanel.add(uploadFilesToExperimentButton);
        
        // mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        // uploadFilesToExperimentButton.setEnabled(false);
        // add(mainPanel, BorderLayout.CENTER);
        // repaint();
        // revalidate();
        // uploadFilesPanel.repaint();
        // uploadFilesPanel.revalidate();
        repaintSelectedFiles();
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
                System.out.println("added");
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
     * Deletes an uploadFileRow and calls repaintSelectedFiles() to repaint. If
     * it fails to find the file, an error message is shown to the user.
     * 
     * @param f
     *            This is used to identify which uploadFileRow to be deleted.
     */
    public void deleteFileRow(File f) {
        if (uploadFileRows.containsKey(f)) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!<<<<<<<<<<<<<<<<");
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
     * @param listener
     *            The listener to be added.
     */
    public void addSelectFilesToUploadButtonListener(ActionListener listener) {
        selectFilesToUploadButton.addActionListener(listener);
    }
    
    /**
     * Adds a listener to the upload button.
     * 
     * @param listener
     *            The listener to be added.
     */
    public void addUploadToExperimentButtonListener(ActionListener listener) {
        uploadFilesToExperimentButton.addActionListener(listener);
    }
    
    /**
     * Sets the annotations.
     * 
     * @param annotations
     *            The annotations to set the panel's annotations to.
     */
    public void setAnnotations(AnnotationDataType[] annotations) {
        this.annotations = annotations;
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
        buttonsPanel.add(selectFilesToUploadButton);
        buttonsPanel.add(Box.createHorizontalStrut(20));
        buttonsPanel.add(uploadFilesToExperimentButton);
        uploadFilesPanel.add(buttonsPanel);
        repaint();
        revalidate();
    }
    
    /**
     * Tries to set the experiment button to either be enabled or disabled. If
     * there are no fileRows, it won't be set to true.
     * 
     * @param b
     *            Whether it should be enabled (true) or disabled (false)
     */
    public void enableUploadButton(boolean b) {
        if (b && !uploadFileRows.isEmpty()) {
            uploadFilesToExperimentButton.setEnabled(b);
        } else {
            uploadFilesToExperimentButton.setEnabled(false);
        }
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
    
    /**
     * 
     * 
     * @param ed
     *            The experiment to be added, in the form of ExperimentData
     */
    public void addExistingExp(ExperimentData ed) {
        this.ed = ed;
        build();
        ArrayList<AnnotationDataValue> annot = ed.getAnnotations();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 30);
        int x = 0;
        int y = 0;
        gbc.gridx = x;
        gbc.gridy = y;
        JPanel exp = new JPanel(new BorderLayout());
        JLabel expHeader = new JLabel("Experiment ID");
        JTextField expID = new JTextField(ed.getName());
        expID.setEnabled(false);
        exp.add(expHeader, BorderLayout.NORTH);
        exp.add(expID, BorderLayout.CENTER);
        northPanel.add(exp, gbc);
        x++;
        for (AnnotationDataValue adv : annot) {
            if (x > 6) {
                x = 0;
                y++;
            }
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 0, 5, 30);
            gbc.gridx = x;
            gbc.gridy = y;
            JPanel p = new JPanel(new BorderLayout());
            JLabel annotationHeader = new JLabel(adv.getName());
            JTextField annotationValue = new JTextField(adv.getValue());
            annotationValue.setEnabled(false);
            p.add(annotationHeader, BorderLayout.NORTH);
            p.add(annotationValue, BorderLayout.CENTER);
            northPanel.add(p, gbc);
            x++;
        }
    }
    
    public ExperimentData getExperiment() {
        return ed;
    }
    
    public ArrayList<File> getFilesToUpload() {
        ArrayList<File> files = new ArrayList<File>();
        for (File f : uploadFileRows.keySet()) {
            files.add(f);
        }
        return files;
    }
    
    public HashMap<String, String> getTypes() {
        HashMap<String, String> types = new HashMap<String, String>();
        for (File f : uploadFileRows.keySet()) {
            types.put(f.getName(), uploadFileRows.get(f).getType());
        }
        return types;
    }
    
    public void addFileDrop() {
        // Makes dragging & dropping of files into the panel possible
        new FileDrop(this, new FileDrop.Listener() {
            public void filesDropped(java.io.File[] files) {
                createUploadFileRow(files);
                enableUploadButton(true);
            }
        });
    }
}
