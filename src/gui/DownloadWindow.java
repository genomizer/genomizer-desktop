package gui;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DownloadWindow extends JFrame {

	private static final long serialVersionUID = -7647204230941649167L;
	JTable table;

	//Tar in ArrayList med de filer som valdes
	public DownloadWindow(ArrayList<Object> files) {

		TableModel tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -8123605827198778312L;


		};

		table = new JTable(tableModel);

		setVisible(true);
	}
}
