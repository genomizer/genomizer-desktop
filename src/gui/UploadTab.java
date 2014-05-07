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
	private JButton uploadButton, browseButton, addToExistingExpButton;
	private JTextArea directoryTextField;
	private JPanel northPanel;
	private JPanel uploadPanel;
	private JTable uploadTable;
	private JScrollPane uploadScroll;
	private JComboBox comboBox1;
	private DefaultTableModel tableModel;
    private UploadToExistingExpPanel uploadToExistingExpPanel;

	public UploadTab() {
		setLayout(new BorderLayout());
		directoryTextField = new JTextArea();
		directoryTextField.setColumns(30);
        uploadToExistingExpPanel = new UploadToExistingExpPanel();
		northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		northPanel.add(new JLabel("File directory:"));
		northPanel.add(directoryTextField, BorderLayout.NORTH);
		browseButton = new JButton("BROWSE");
		uploadButton = new JButton("UPLOAD");
        addToExistingExpButton = new JButton("Add to existing experiment");
		northPanel.add(browseButton, BorderLayout.CENTER);
		northPanel.add(uploadButton, BorderLayout.CENTER);
        northPanel.add(addToExistingExpButton, BorderLayout.EAST);
		createUploadPanel();
	}

	public void createUploadPanel() {
		uploadPanel = new JPanel(new GridLayout());
        add(uploadPanel);
	}

    public void addExistingExpPanel() {
        uploadPanel.add(uploadToExistingExpPanel, BorderLayout.CENTER);
        uploadPanel.repaint();
        uploadPanel.revalidate();
    }

    public UploadToExistingExpPanel getUploadToExistingExpPanel() {
        return uploadToExistingExpPanel;
    }

	public void addBrowseBtnListener(ActionListener listener) {
		browseButton.addActionListener(listener);
	}

	public void addUploadBtnListener(ActionListener listener) {
		uploadButton.addActionListener(listener);
	}

    public void addAddToExistingExpButtonListener(ActionListener listener) {
        addToExistingExpButton.addActionListener(listener);
    }

	public JTextArea getDirectoryTextField() {
		return directoryTextField;
	}
}
