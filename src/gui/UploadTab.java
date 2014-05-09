package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import util.AnnotationDataType;

public class UploadTab extends JPanel {

    private static final long serialVersionUID = -2830290705724588252L;
    private JButton addToExistingExpButton, newExpButton, selectButton, uploadButton;
    private JPanel northPanel, uploadPanel, newExpPanel, uploadFilesPanel, uploadBackground;
    private JTextArea experimentNameField;
    private UploadToExistingExpPanel uploadToExistingExpPanel;
    private AnnotationDataType[] annotations;
    private ArrayList<String> annotationHeaders;
    private ArrayList<JComboBox> annotationBoxes;
    private ArrayList<JTextField> annotationFields;
    private ArrayList<UploadFileRow> uploadFileRows;
    private enum ActivePanel {EXISTING, NEW, NONE}
    private ActivePanel activePanel;

    public UploadTab() {
	annotationHeaders = new ArrayList<String>();
	uploadFileRows = new ArrayList<UploadFileRow>();
	activePanel = ActivePanel.NONE;
	setLayout(new BorderLayout());
	uploadToExistingExpPanel = new UploadToExistingExpPanel();
	northPanel = new JPanel();
	add(northPanel, BorderLayout.NORTH);
	northPanel.add(new JLabel("Experiment name: "));
	experimentNameField = new JTextArea();
	experimentNameField.setColumns(30);
	northPanel.add(experimentNameField);
	addToExistingExpButton = new JButton("Add to existing experiment");
	northPanel.add(addToExistingExpButton, BorderLayout.EAST);
	uploadBackground = new JPanel(new BorderLayout());
	uploadFilesPanel = new JPanel(new GridLayout(0,1));
	newExpButton = new JButton("Create new experiment");
	selectButton = new JButton("Select files");
	uploadButton = new JButton("Upload Selected Files");
	newExpPanel = new JPanel();
	newExpPanel.setBorder(BorderFactory.createTitledBorder("Experiment"));
	createUploadPanel();
	northPanel.add(newExpButton, BorderLayout.EAST);
    }

    private void createUploadPanel() {
        uploadPanel = new JPanel(new BorderLayout());
        add(uploadPanel, BorderLayout.CENTER);
    }

    public void addExistingExpPanel(AnnotationDataType[] annotations) {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.EXISTING;
        uploadToExistingExpPanel = new UploadToExistingExpPanel();
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
                annotationHeaders.add(annotations[i].getName());
                p.add(annotationLabel, BorderLayout.NORTH);
                if (annotations[i].getValues()[0].equals("freetext")) {
                    JTextField textField = new JTextField();
                    textField.setColumns(10);
                    annotationFields.add(textField);
                    p.add(textField, BorderLayout.CENTER);
                    newExpPanel.add(p, gbc);
                } else {
                    JComboBox comboBox = new JComboBox(
                        annotations[i].getValues());
                    comboBox.setPreferredSize(new Dimension(120, 31));
                    annotationBoxes.add(comboBox);
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
    
    public void createUploadFileRow(String[] fileNames) {
	for(String fileName : fileNames) {
	    UploadFileRow fileRow = new UploadFileRow(fileName, this);
	    uploadFileRows.add(fileRow);
	}
	repaintSelectedFiles();
    }

    private void repaintSelectedFiles() {
	for (UploadFileRow fileRow : uploadFileRows) {
	    uploadFilesPanel.add(fileRow);
	}
	uploadFilesPanel.add(selectButton);
	uploadFilesPanel.add(uploadButton);
	repaint();
	revalidate();
    }

    public void killContentsOfUploadPanel() {
        switch (activePanel) {
            case NONE:
                break;
            case EXISTING:
                uploadPanel.remove(uploadToExistingExpPanel);
                uploadToExistingExpPanel.removeAll();
                uploadToExistingExpPanel.removeAllInCenter();
                activePanel = ActivePanel.NONE;
                break;
            case NEW:
                uploadPanel.remove(newExpPanel);
                newExpPanel.removeAll();
                uploadFilesPanel.removeAll();
                activePanel = ActivePanel.NONE;
                break;
        }
    }

    public void deleteFileRow(String fileName) {
	for(int i = 0; i < uploadFileRows.size(); i++) {
	    if(uploadFileRows.get(i).getFileName().equals(fileName)) {
		uploadFileRows.remove(i);
	    }
	}
	uploadFilesPanel.removeAll();
	repaintSelectedFiles();
    }
    
    public AnnotationDataType[] getUploadAnnotations() {
	AnnotationDataType[] annotations = new AnnotationDataType[annotationHeaders.size()];
	for(String s : annotationHeaders) {
	    
	}
	return null;
    }
}
