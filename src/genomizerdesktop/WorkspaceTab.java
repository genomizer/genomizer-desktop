package genomizerdesktop;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class WorkspaceTab extends JPanel {

	private static final long serialVersionUID = -7278768268151806081L;
	private JPanel buttonPanel, filePanel;
	private JButton deleteButton, removeButton, downloadButton;
	private JButton analyzeButton, browseButton;
	GridBagConstraints gbc;

	public WorkspaceTab() {
		setLayout(new BorderLayout());
		buttonPanel = new JPanel();
		filePanel = new JPanel();

		add(buttonPanel, BorderLayout.NORTH);
		add(filePanel, BorderLayout.CENTER);

		GridBagLayout layout = new GridBagLayout();
		gbc = new GridBagConstraints();

		buttonPanel.setLayout(layout);
		gbc.ipadx = 60;
		gbc.ipady = 10;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.NORTHWEST;

		buttonPanel.setBackground(Color.red);
		filePanel.setBackground(Color.green);

		createButtons();
		addToButtonPanel();
		buttonPanel.setVisible(true);

		DefaultTableModel dataModel = new DefaultTableModel() {
			private static final long serialVersionUID = -4408712463905415472L;
			public int getColumnCount() { return 6; }
	        public int getRowCount() { return 10;}
	    };

	    JTable table = new JTable(dataModel);
	    table.setBackground(Color.CYAN);
	    table.getColumnModel().getColumn(2).setPreferredWidth(100);
	    
	    table.setValueAt("Name", 0, 0);
	    table.setValueAt("Date", 0, 1);
	    table.setValueAt("Uploaded by", 0, 2);
	    table.setValueAt("Data type", 0, 3);
	    table.setValueAt("Local", 0, 4);

	    filePanel.add(table);
		filePanel.setVisible(true);
		setVisible(true);
	}

	private void createButtons() {
		deleteButton = new JButton("Delete from database");
		removeButton = new JButton("Remove selected");
		downloadButton = new JButton("Download selected");
		analyzeButton = new JButton("Analyze selected");
		browseButton = new JButton("Browse local files");
	}

	private void addToButtonPanel() {
		gbc.gridx = 0;
		gbc.gridy = 0;
		buttonPanel.add(deleteButton, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		buttonPanel.add(removeButton, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		buttonPanel.add(downloadButton, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		buttonPanel.add(analyzeButton, gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;
		buttonPanel.add(browseButton, gbc);
	}
}