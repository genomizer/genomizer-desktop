package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.table.TableModel;

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

	private String[][] sortMatrix(String[][] matrix, final int sortByColumn, final boolean desc) {
		Arrays.sort(matrix, new Comparator<String[]>() {
			@Override
			public int compare(final String[] entry1, final String[] entry2) {
				final String time1 = entry1[sortByColumn];
				final String time2 = entry2[sortByColumn];
				if(desc) {
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

	public JXTreeTable getTreeTable() {
		String[][] experimentContent = new String[content.length][headings.length];
		for(int i=0; i<content.length; i++) {
			experimentContent[i] = content[i].getAnnotationValueList();
		}
		experimentContent = sortMatrix(experimentContent, sortByColumn, desc);
		ArrayList<String[]> totalContent = new ArrayList<String[]>();
		int mainIndex = 0;
		for(int i=0; i<headings.length; i++) {
			if(headings[i].equals("Experiment Name")) {
				mainIndex = i;
			}
		}
		String[] fileNames = new String[0];
		for(int i=0; i<content.length; i++) {
			totalContent.add(experimentContent[i]);
			for(int j=0; j<content.length; j++) {
				if(content[j].name.equals(experimentContent[i][mainIndex])) {
					fileNames = content[j].getFileListString();
					for(int k=0; k<fileNames.length; k++) {
						totalContent.add(new String[] {fileNames[k]});
					}
				}
			}
		}

		root = new RootNode("Root");
		ChildNode myChild = null;
		for(String[] data : totalContent) {
			ChildNode child = new ChildNode(data);
			if(data.length > 1) {
				root.add(child);
				myChild = child;
			} else {
				myChild.add(child);
			}
		}
		model = new DefaultTreeTableModel(root, Arrays.asList(headings));
		table = new JXTreeTable(model);
		table.setShowGrid(true, true);
		table.packAll();
		return table;
	}

	public JXTreeTable updateTreeTable() {
		table.collapseAll();
		String[][] experimentContent = new String[table.getRowCount()][table.getColumnCount()];
		for(int i=0; i<table.getRowCount(); i++) {
			for(int j=0; j<table.getColumnCount(); j++) {
				experimentContent[i][j] = (String) table.getValueAt(i, j);
			}
		}
		String[] newHeadings = new String[table.getColumnCount()];
		for(int i=0; i<table.getColumnCount(); i++) {
			newHeadings[i] = table.getColumnName(i);
		}
		
		experimentContent = sortMatrix(experimentContent, sortByColumn, desc);
		ArrayList<String[]> totalContent = new ArrayList<String[]>();
		int mainIndex = 0;
		for(int i=0; i<newHeadings.length; i++) {
			if(newHeadings[i].equals("Experiment Name")) {
				mainIndex = i;
			}
		}
		String[] fileNames = new String[0];
		for(int i=0; i<content.length; i++) {
			totalContent.add(experimentContent[i]);
			for(int j=0; j<content.length; j++) {
				if(content[j].name.equals(experimentContent[i][mainIndex])) {
					fileNames = content[j].getFileListString();
					for(int k=0; k<fileNames.length; k++) {
						totalContent.add(new String[] {fileNames[k]});
					}
				}
			}
		}

		root = new RootNode("Root");
		ChildNode myChild = null;
		for(String[] data : totalContent) {
			ChildNode child = new ChildNode(data);
			if(data.length > 1) {
				root.add(child);
				myChild = child;
			} else {
				myChild.add(child);
			}
		}
		model = new DefaultTreeTableModel(root, Arrays.asList(newHeadings));
		table = new JXTreeTable(model);
		table.setShowGrid(true, true);
		table.packAll();
		return table;
	}

}
