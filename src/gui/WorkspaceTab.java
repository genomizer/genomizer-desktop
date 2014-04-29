package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;

public class WorkspaceTab extends JPanel {

	private static final long serialVersionUID = -7278768268151806081L;
	private JPanel buttonPanel, filePanel;
	private JButton deleteButton, removeButton, downloadButton;
	private JButton analyzeButton, browseButton;
	GridBagConstraints gbc;

	public WorkspaceTab() {
        	setLayout(new BorderLayout());
        	buttonPanel = new JPanel();
        	filePanel = new JPanel(new BorderLayout());

        	add(buttonPanel, BorderLayout.NORTH);
        	add(filePanel, BorderLayout.CENTER);

        	GridBagLayout layout = new GridBagLayout();
        	gbc = new GridBagConstraints();

        	buttonPanel.setLayout(layout);
        	gbc.ipadx = 60;
        	gbc.ipady = 10;
        	gbc.insets = new Insets(10, 10, 10, 10);
        	gbc.anchor = GridBagConstraints.NORTHWEST;

        	buttonPanel.setBackground(Color.white);
        	filePanel.setBackground(Color.white);

        	createButtons();
        	addToButtonPanel();
        	buttonPanel.setVisible(true);

        	// DefaultTableModel dataModel = new DefaultTableModel() {
        	// private static final long serialVersionUID = -4408712463905415472L;
        	// public int getColumnCount() { return 6; }
        	// public int getRowCount() { return 10;}
        	// };

        	String[] columnNames = { "Name", "Date", "Uploaded by", "Data type" };
        	Object[][] data = {
        		{ "Protein123_A5_2014.WIG", "2014-04-29", "Per", "Profile" },
        		{ "Protein123_A5_2014.RAW", "2014-04-29", "Per", "RAW" },
        		{ "Protein123_A5_2014.WIG", "2014-04-29", "Per", "Region" } };

        	JTable table = new JTable(data, columnNames);
        	table.setBackground(Color.CYAN);

        	filePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        	filePanel.add(table, BorderLayout.CENTER);
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
		deleteButton.setOpaque(true);
		deleteButton.setBackground(Color.BLUE);
		deleteButton.setForeground(Color.WHITE);
		buttonPanel.add(deleteButton, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		removeButton.setOpaque(true);
		removeButton.setBackground(Color.BLUE);
		removeButton.setForeground(Color.white);
		buttonPanel.add(removeButton, gbc);

		gbc.gridx = 2;
		gbc.gridy = 0;
		downloadButton.setOpaque(true);
		downloadButton.setBackground(Color.BLUE);
		downloadButton.setForeground(Color.white);
		buttonPanel.add(downloadButton, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		analyzeButton.setOpaque(true);
		analyzeButton.setBackground(Color.BLUE);
		analyzeButton.setForeground(Color.white);
		buttonPanel.add(analyzeButton, gbc);

		gbc.gridx = 4;
		gbc.gridy = 0;
		browseButton.setOpaque(true);
		browseButton.setBackground(Color.BLUE);
		browseButton.setForeground(Color.white);
		buttonPanel.add(browseButton, gbc);
	}

	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path, String description) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL, description);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
}