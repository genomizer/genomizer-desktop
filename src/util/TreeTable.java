package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class TreeTable {

    private String[] headings;
    private String[][] data;
    private Node root;
    private DefaultTreeTableModel model;
    private JXTreeTable table;
    private ExperimentData[] experiments;
    private int sortByColumn;
    private boolean desc;

    public TreeTable() {
	this.headings = new String[0];
	experiments = new ExperimentData[0];
    }

    public void setContent(String[] headings, ExperimentData[] content) {
	this.headings = headings;
	this.experiments = content;
	data = new String[experiments.length][headings.length];
	for (int i = 0; i < experiments.length; i++) {
	    data[i] = experiments[i].getAnnotationValueList();
	}
    }

    private String[][] sortMatrix(String[][] matrix, final int sortByColumn,
	    final boolean desc) {
	Arrays.sort(matrix, new Comparator<String[]>() {
	    @Override
	    public int compare(final String[] entry1, final String[] entry2) {
		final String time1 = entry1[sortByColumn];
		final String time2 = entry2[sortByColumn];
		if (desc) {
		    return time2.compareTo(time1);
		} else {
		    return time1.compareTo(time2);
		}
	    }
	});
	return matrix;
    }

    public void setSorting(int sortByColumn, boolean desc) {
	this.sortByColumn = sortByColumn;
	this.desc = desc;
    }

    private String[][] getContentFromTreeTable() {
	table.collapseAll();
	String[][] experimentContent = new String[table.getRowCount()][table
		.getColumnCount()];
	for (int i = 0; i < table.getRowCount(); i++) {
	    for (int j = 0; j < table.getColumnCount(); j++) {
		experimentContent[i][j] = (String) table.getValueAt(i, j);
	    }
	}
	return experimentContent;
    }

    private String[] getHeadingsFromTreeTable() {
	String[] headings = new String[table.getColumnCount()];
	for (int i = 0; i < table.getColumnCount(); i++) {
	    headings[i] = table.getColumnName(i);
	}
	return headings;
    }

    private ExperimentData getExperimentFromData(String[] data) {
	int mainIndex = 0;
	for (int i = 0; i < headings.length; i++) {
	    if (headings[i].equals("Experiment Name")) {
		mainIndex = i;
	    }
	}
	for (int i = 0; i < experiments.length; i++) {
	    if (experiments[i].name.equals(data[mainIndex])) {
		return experiments[i];
	    }
	}
	return null;
    }

    public ArrayList<FileData> getSelectedFiles() {
	ArrayList<FileData> files = new ArrayList<FileData>();
	int[] rows = table.getSelectedRows();
	for (int i = 0; i < rows.length; i++) {
	    for (int j = 0; j < experiments.length; j++) {
		for (int k = 0; k < experiments[j].files.length; k++) {
		    if (experiments[j].files[k].name.equals(table.getValueAt(
			    rows[i], 0))) {
			files.add(experiments[j].files[k]);
		    }
		}
	    }
	}
	return files;
    }

    public JXTreeTable getTreeTable() {
	root = new Node(new Object[] { "Root" });
	// If the table has been initated
	if (table != null) {
	    data = getContentFromTreeTable();
	    headings = getHeadingsFromTreeTable();
	}
	// sort the data
	data = sortMatrix(data, sortByColumn, desc);

	for (int i = 0; i < experiments.length; i++) {
	    Node child = new Node(data[i]);
	    Node rawFiles = new Node(new String[] { "Raw Files" });
	    Node profileFiles = new Node(new String[] { "Profile Files" });
	    Node regionFiles = new Node(new String[] { "Region Files" });
	    root.add(child);
	    ExperimentData currentExperiment = getExperimentFromData(data[i]);
	    FileData[] fileData = currentExperiment.files;
	    for (int j = 0; j < fileData.length; j++) {
		FileData currentFile = fileData[j];
		Object[] rowContent = { currentFile.name };
		if (currentFile.type.equals("raw")) {
		    rawFiles.add(new Node(rowContent));
		} else if (currentFile.type.equals("region")) {
		    regionFiles.add(new Node(rowContent));
		} else if (currentFile.type.equals("profile")) {
		    profileFiles.add(new Node(rowContent));
		}
	    }
	    if (rawFiles.getChildCount() != 0) {
		child.add(rawFiles);
	    }
	    if (regionFiles.getChildCount() != 0) {
		child.add(regionFiles);
	    }
	    if (profileFiles.getChildCount() != 0) {
		child.add(profileFiles);
	    }
	}
	model = new DefaultTreeTableModel(root, Arrays.asList(headings));
	table = new JXTreeTable(model);
	table.setShowGrid(true, true);
	table.packAll();
	return table;
    }

}