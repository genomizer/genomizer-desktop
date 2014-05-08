package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

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
    private JButton addToExistingExpButton, newExpButton;
    private JPanel northPanel, uploadPanel, newExpPanel;
    private JTextArea experimentNameField;
    private UploadToExistingExpPanel uploadToExistingExpPanel;
    private AnnotationDataType[] annotations;
    private ArrayList<JComboBox> annotationBoxes;
    private ArrayList<JTextField> annotationFields;
    private JButton selectButton;
    private enum ActivePanel {EXISTING, NEW, NONE}
    private ActivePanel activePanel;

    public UploadTab() {
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
        createUploadPanel();
        newExpButton = new JButton("Create new experiment");
        northPanel.add(newExpButton, BorderLayout.EAST);

        newExpPanel = new JPanel();
        selectButton = new JButton("Select files");
    }

    private void createUploadPanel() {
        uploadPanel = new JPanel(new BorderLayout());
        add(uploadPanel, BorderLayout.CENTER);
    }

    public void addExistingExpPanel(AnnotationDataType[] annotations) {
        activePanel = ActivePanel.EXISTING;
        uploadToExistingExpPanel.setAnnotations(annotations);
        uploadToExistingExpPanel.addAnnotationsForExistingExp();
        uploadPanel.add(uploadToExistingExpPanel, BorderLayout.CENTER);
        uploadPanel.repaint();
        uploadPanel.revalidate();
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

    private void createNewExpPanel() {
        killContentsOfUploadPanel();
        activePanel = ActivePanel.NEW;
        newExpPanel.setBorder(BorderFactory.createTitledBorder("Experiment"));
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            Double.MIN_VALUE };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
        newExpPanel.setLayout(gbl_panel);
        uploadPanel.add(newExpPanel, BorderLayout.NORTH);
        addAnnotationsForNewExp();
        JPanel p = new JPanel();
        p.add(selectButton, BorderLayout.NORTH);
        uploadPanel.add(p, BorderLayout.CENTER);
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
	    uploadPanel.repaint();
	    uploadPanel.revalidate();
	} catch (NullPointerException e) {
	    JOptionPane.showMessageDialog(this,
		    "Eggs are supposed to be green.", "Inane error",
		    JOptionPane.ERROR_MESSAGE);
	}
    }

    public void killContentsOfUploadPanel() {
        switch (activePanel) {
            case NONE:
                break;
            case EXISTING:
                uploadPanel.remove(uploadToExistingExpPanel);
                uploadToExistingExpPanel.removeAll();
                uploadToExistingExpPanel.addSelectFilesToUploadButton();
                uploadToExistingExpPanel.addUploadFilesToExperimentButton();
                activePanel = ActivePanel.NONE;
                break;
            case NEW:
                uploadPanel.remove(newExpPanel);
                newExpPanel.removeAll();
                activePanel = ActivePanel.NONE;
                break;
        }
    }
}