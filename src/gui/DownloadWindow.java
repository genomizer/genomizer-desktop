package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DownloadWindow extends JFrame {

	private static final long serialVersionUID = -7647204230941649167L;
	JPanel panel;
	JTable table;

	//Tar in ArrayList med de filer som valdes
	public DownloadWindow(Object[][] files) {

    	Object[][] data = files;

		setUp(data);
	}

	public DownloadWindow() {
		Object[][] data = {
	    	{ "Protein123_A5_2014.WIG", new JComboBox() },
	    	{ "Protein123_A5_2014.RAW", new JComboBox() },
	    	{ "Protein123_A5_2014.WIG", new JComboBox() } };

		setUp(data);
	}

	private void setUp(Object[][] data) {

		for(int i=0; i<data[0].length; i++) {
			data[1][i] = new JComboBox();
		}

		panel = new JPanel(new BorderLayout(3,3));
		add(panel);
		panel.add(new JLabel("test"), BorderLayout.NORTH);

		String[] columnNames = { "File name", "Format conversion" };

    	table = new JTable(data, columnNames);
    	table.setBackground(Color.blue);
    	table.setRowHeight(30);

    	JScrollPane scrollPane = new JScrollPane(table);
    	panel.add(scrollPane, BorderLayout.CENTER);
    	panel.add(table.getTableHeader(), BorderLayout.NORTH);

    	setTitle("DOWNLOAD FILES");
    	setSize(500,500);
		setVisible(true);
	}

    public static void main(String args[]) {

    	new DownloadWindow();

    }
}
