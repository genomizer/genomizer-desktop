package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class TreeTable {

    private String[] headings;
    private Node root;
    private DefaultTreeTableModel model;
    private JXTreeTable table;
    private ExperimentData[] content;
    private int sortByColumn;
    private boolean desc;

    public TreeTable() {
	this.headings = new String[0];
	content = new ExperimentData[0];
    }

    public void setContent(String[] headings, ExperimentData[] content) {
	this.headings = headings;
	this.content = content;
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

    private ArrayList<String[]> mergeFilesAndContent(String[][] expContent) {
	ArrayList<String[]> totalContent = new ArrayList<String[]>();
	int mainIndex = 0;
	for (int i = 0; i < headings.length; i++) {
	    if (headings[i].equals("Experiment Name")) {
		mainIndex = i;
	    }
	}
	String[] fileNames = new String[0];
	for (int i = 0; i < expContent.length; i++) {
	    totalContent.add(expContent[i]);
	    for (int j = 0; j < expContent.length; j++) {
		if (content[j].name.equals(expContent[i][mainIndex])) {
		    fileNames = content[j].getFileListString();
		    for (int k = 0; k < fileNames.length; k++) {
			totalContent.add(new String[] { fileNames[k] });
		    }
		}
	    }
	}
	return totalContent;
    }

    public JXTreeTable getTreeTable() {
	String[][] experimentContent = null;
	String[] headers = null;
	if (table != null) {
	    experimentContent = getContentFromTreeTable();
	    headers = getHeadingsFromTreeTable();
	} else {
	    experimentContent = new String[content.length][headings.length];
	    for (int i = 0; i < content.length; i++) {
		experimentContent[i] = content[i].getAnnotationValueList();
	    }
	    headers = headings;
	}

	experimentContent = sortMatrix(experimentContent, sortByColumn, desc);
	ArrayList<String[]> totalContent = mergeFilesAndContent(experimentContent);

	root = new Node(new Object[] { "Root" });
	Node myChild = null;
	for (String[] data : totalContent) {
	    Node child = new Node(data);
	    if (data.length > 1) {
		root.add(child);
		myChild = child;
	    } else {
		myChild.add(child);
	    }
	}
	model = new DefaultTreeTableModel(root, Arrays.asList(headers));
	table = new JXTreeTable(model);
	table.setShowGrid(true, true);
	table.packAll();
	return table;
    }

}
