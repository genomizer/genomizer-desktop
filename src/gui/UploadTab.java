package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class UploadTab extends JPanel {

	private static final long serialVersionUID = -2830290705724588252L;
	private JButton addToExistingExpButton;
	private JPanel northPanel, uploadPanel;
    private JTextArea experimentNameField;
    private UploadToExistingExpPanel uploadToExistingExpPanel;

	public UploadTab() {
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
	}

	public void createUploadPanel() {
		uploadPanel = new JPanel(new BorderLayout());
        add(uploadPanel, BorderLayout.CENTER);
	}

    public void addExistingExpPanel() {
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
}
