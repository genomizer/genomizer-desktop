package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class UploadTab extends JPanel {

    private static final long serialVersionUID = -2830290705724588252L;
    private JButton uploadButton, browseButton;
    private JTextArea directoryTextField;
    private JPanel northPanel;
    private JPanel uploadPanel;
    private JTable uploadTable;
    private JScrollPane uploadScroll;
    private JComboBox comboBox1;
    private DefaultTableModel tableModel;


    public UploadTab() {
	setLayout(new BorderLayout());
	directoryTextField = new JTextArea();
	directoryTextField.setColumns(30);
	northPanel = new JPanel();
	add(northPanel, BorderLayout.NORTH);
	northPanel.add(new JLabel("File directory:"));
	northPanel.add(directoryTextField, BorderLayout.NORTH);
	browseButton = new JButton("BROWSE");
	uploadButton = new JButton("UPLOAD");
	northPanel.add(browseButton, BorderLayout.CENTER);
	northPanel.add(uploadButton, BorderLayout.CENTER);
	createUploadPanel();
	add(uploadPanel, BorderLayout.CENTER);
    }

    public void createUploadPanel() {
	uploadPanel = new JPanel(new GridLayout());
	initiateTable();
	uploadPanel.add(uploadTable.getTableHeader());
	uploadScroll = new JScrollPane(uploadTable);
	uploadScroll.setBackground(Color.blue);
	uploadPanel.add(uploadScroll);
    }

    public void initiateTable() {
	tableModel = new DefaultTableModel();
	uploadTable = new JTable(tableModel);
	uploadTable.setBackground(Color.blue);
	uploadTable.setForeground(Color.white);
	uploadTable.setFont(new Font("Arial", Font.PLAIN, 24));
    }

    public void createNewAnnotation(String annotation, int nrOfCurrAnnotations) {
	TableColumn sportColumn = new TableColumn(nrOfCurrAnnotations);
//	TableColumn sportColumn = uploadTable.getColumnModel().getColumn(nrOfCurrAnnotations);
	comboBox1 = new JComboBox();
	comboBox1.addItem("Snowboarding");
	comboBox1.addItem("Rowing");
	comboBox1.addItem("Chasing toddlers");
	comboBox1.addItem("Speed reading");
	comboBox1.addItem("Teaching high school");
	comboBox1.addItem("None");
	sportColumn.setCellEditor(new DefaultCellEditor(comboBox1));
	sportColumn.setHeaderValue(annotation);
	uploadTable.addColumn(sportColumn);
    }

    private void addEntry() {
	Vector<String> entry = new Vector<String>();
	for(int i = 0; i < uploadTable.getColumnCount(); i++) {
	    entry.add(i, "Choose annotation..");
	}
	tableModel.addRow(entry);

    }

    public void addBrowseBtnListener(ActionListener listener) {
	browseButton.addActionListener(listener);
    }

    public void addUploadBtnListener(ActionListener listener) {
	uploadButton.addActionListener(listener);
    }

    public JTextArea getDirectoryTextField() {
	return directoryTextField;
    }
}
