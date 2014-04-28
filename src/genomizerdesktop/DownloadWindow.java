package genomizerdesktop;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTable;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class DownloadWindow extends JFrame {

	JTable table;

	//Tar in ArrayList med de filer som valdes
	public DownloadWindow(ArrayList<Object> files) {

		TableModel tableModel = new DefaultTableModel() {
			private static final long serialVersionUID = -8123605827198778312L;


		};

		table = new JTable();

		setVisible(true);
	}
}
