package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import util.ExperimentData;
import util.FileData;
import util.TreeTable;

public class WorkspaceTab extends JPanel {

    private static final long serialVersionUID = -7278768268151806081L;
    private TreeTable table;
    private JPanel buttonPanel, filePanel;
    private JButton deleteButton, removeButton, downloadButton;
    private JButton analyzeButton, browseButton, processButton;
    private ImageIcon analyseIcon = new ImageIcon(getClass().getResource(
	    "/icons/AnalyzeSelectedButton.png"));
    private ImageIcon browseIcon = new ImageIcon(getClass().getResource(
	    "/icons/BrowseLocalFilesButton.png"));
    private ImageIcon deleteIcon = new ImageIcon(getClass().getResource(
	    "/icons/DeleteSelectedButton.png"));
    private ImageIcon downloadSelectedIcon = new ImageIcon(getClass()
	    .getResource("/icons/DownloadSelectedButton.png"));
    private ImageIcon removeFromDBIcon = new ImageIcon(getClass().getResource(
	    "/icons/RemoveFromDatabaseButton.png"));
    private GridBagConstraints gbc;

    public WorkspaceTab() {
	setLayout(new BorderLayout());
	buttonPanel = new JPanel();
	filePanel = new JPanel(new BorderLayout());
	add(buttonPanel, BorderLayout.NORTH);
	add(filePanel, BorderLayout.CENTER);

	GridBagLayout layout = new GridBagLayout();
	gbc = new GridBagConstraints();

	buttonPanel.setLayout(layout);
	gbc.ipadx = 10;
	gbc.ipady = 10;
	gbc.insets = new Insets(1, 1, 1, 1);
	gbc.anchor = GridBagConstraints.NORTHWEST;

	buttonPanel.setBackground(new Color(210, 210, 210));
	filePanel.setBackground(Color.white);

	createButtons();
	addToButtonPanel();
	buttonPanel.setVisible(true);
	table = new TreeTable();
	// table.setContent(ExperimentData.getExample());
	filePanel.add(table, BorderLayout.CENTER);
	setVisible(true);
    }

    private void createButtons() {
	removeButton = new JButton();
	removeButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		table.removeSelectedExperiments();
		table.removeSelectedFiles();
	    }
	});
	downloadButton = new JButton();
	analyzeButton = new JButton();
	browseButton = new JButton();
	deleteButton = new JButton();
	processButton = new JButton();
    }

    private void addToButtonPanel() {

	gbc.gridx = 0;
	gbc.gridy = 0;
	deleteIcon = new ImageIcon(deleteIcon.getImage().getScaledInstance(175,
		30, Image.SCALE_SMOOTH));
	deleteButton.setBorderPainted(true);
	deleteButton.setContentAreaFilled(false);
	deleteButton.setIcon(deleteIcon);
	buttonPanel.add(deleteButton, gbc);

	gbc.gridx = 1;
	gbc.gridy = 0;
	removeFromDBIcon = new ImageIcon(removeFromDBIcon.getImage()
		.getScaledInstance(175, 30, Image.SCALE_SMOOTH));
	removeButton.setBorderPainted(true);
	removeButton.setContentAreaFilled(false);
	removeButton.setIcon(removeFromDBIcon);
	buttonPanel.add(removeButton, gbc);

	gbc.gridx = 2;
	gbc.gridy = 0;
	downloadSelectedIcon = new ImageIcon(downloadSelectedIcon.getImage()
		.getScaledInstance(175, 30, Image.SCALE_SMOOTH));
	downloadButton.setBorderPainted(true);
	downloadButton.setContentAreaFilled(false);
	downloadButton.setIcon(downloadSelectedIcon);
	buttonPanel.add(downloadButton, gbc);

	gbc.gridx = 3;
	gbc.gridy = 0;
	analyseIcon = new ImageIcon(analyseIcon.getImage().getScaledInstance(
		175, 30, Image.SCALE_SMOOTH));
	analyzeButton.setBorderPainted(true);
	analyzeButton.setContentAreaFilled(false);
	analyzeButton.setIcon(analyseIcon);
	buttonPanel.add(analyzeButton, gbc);

	gbc.gridx = 4;
	gbc.gridy = 0;
	browseIcon = new ImageIcon(browseIcon.getImage().getScaledInstance(175,
		30, Image.SCALE_SMOOTH));
	browseButton.setBorderPainted(true);
	browseButton.setContentAreaFilled(false);
	browseButton.setIcon(browseIcon);
	buttonPanel.add(browseButton, gbc);

	gbc.gridx = 5;
	gbc.gridy = 0;

	processButton.setText("Process selected");
	buttonPanel.add(processButton, gbc);
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     */
    protected ImageIcon createImageIcon(String path, String description) {
	java.net.URL imgURL = getClass().getResource(path);
	if (imgURL != null) {
	    return new ImageIcon(imgURL, description);
	} else {
	    System.err.println("Couldn't find file: " + path);
	    return null;
	}
    }

    public void addDownloadFileListener(ActionListener listener) {
	downloadButton.addActionListener(listener);
    }

    public void addProcessFileListener(ActionListener listener) {
	processButton.addActionListener(listener);
    }

    private String[] concatArrays(String[] first, String[] second) {
	ArrayList<String> both = new ArrayList<String>(first.length
		+ second.length);
	Collections.addAll(both, first);
	Collections.addAll(both, second);
	return both.toArray(new String[both.size()]);
    }

    public void addExperimentsToTable(ExperimentData[] experiments) {
	ArrayList<ExperimentData> expList = new ArrayList<ExperimentData>();
	if (table.getContent() != null) {
	    for (int i = 0; i < table.getContent().length; i++) {
		expList.add(table.getContent()[i]);
	    }
	}
	for (int i = 0; i < experiments.length; i++) {
	    boolean alreadyInTable = false;
	    for (int j = 0; j < expList.size(); j++) {
		if (experiments[i].name.equals(expList.get(j).name)) {
		    alreadyInTable = true;
		    expList.get(j).addFiles(experiments[i].files);
		    break;
		}
	    }
	    if (!alreadyInTable) {
		expList.add(experiments[i]);
	    }
	}
	table.setContent(expList.toArray(new ExperimentData[expList.size()]));
	table.repaint();
	table.revalidate();
    }

    ArrayList<ExperimentData> getSelectedExperiments() {
        return table.getSelectedExperiments();
    }

    public ArrayList<FileData> getSelectedFiles() {
	return table.getSelectedFiles();
    }
}
