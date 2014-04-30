package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class DownloadWindow extends JFrame {

	private static final long serialVersionUID = -7647204230941649167L;
	JPanel panel;
	JTable table;

	//Tar in ArrayList med de filer som valdes
	public DownloadWindow(ArrayList<String> files) {

		setUp(files);
	}

	public DownloadWindow() {
		ArrayList<String> data = new ArrayList<String>();

		data.add("Protein123_A5_2014.WIG");
		data.add("Protein123_A5_2014.RAW");
		data.add("Protein123_A5_2014.WIG");

		setUp(data);
	}

	private void setUp(ArrayList<String> data) {

		panel = new JPanel(new BorderLayout(3,3));
		add(panel);
		panel.add(new JLabel("test"), BorderLayout.NORTH);

		DefaultTableModel tableModel = new DefaultTableModel();
		table = new JTable(tableModel);

		TableColumn fileNameColumn, formatConversionColumn;
		fileNameColumn = new TableColumn();
		formatConversionColumn = new TableColumn();
		fileNameColumn.setHeaderValue("File name");
		formatConversionColumn.setHeaderValue("Format conversion");
		
		tableModel.addColumn(fileNameColumn);
		tableModel.addColumn(formatConversionColumn);
		
		table.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(
				fileNameColumn.getHeaderValue());
		table.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(
				formatConversionColumn.getHeaderValue());

		for(int i=0; i<data.size(); i++) {
			tableModel.addRow(new Object[]{data.get(i),null});
		}
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Format1");
		comboBox.addItem("Format2");
		comboBox.addItem("Format3");
		formatConversionColumn.setCellEditor(new DefaultCellEditor(comboBox));

    	table.setBackground(Color.blue);
    	table.setRowHeight(30);

    	JScrollPane scrollPane = new JScrollPane(table);
    	panel.add(scrollPane, BorderLayout.CENTER);
    	panel.add(table.getTableHeader(), BorderLayout.NORTH);
    	
/*    	System.out.println(
    			table.getTableHeader().getColumnModel().getColumn(0).getHeaderValue()); */

    	setTitle("DOWNLOAD FILES");
    	setSize(500,500);
		setVisible(true);
	}

    public static void main(String args[]) {

    	new DownloadWindow();

    }
}
