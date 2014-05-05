package util;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;

public class TreeTable {

	private String[] headings;
	private Node root;
	private DefaultTreeTableModel model;
	private JXTreeTable table;
	private List<String[]> content;
	
	public TreeTable(String[] headings, List<String[]> content) {
		this.headings = headings;
		this.content = content;
	}
	
	public TreeTable() {
		headings = new String[0];
		content = new ArrayList<String[]>();
	}
	
	public void setContent(String[] headings, List<String[]> content) {
		this.headings = headings;
		this.content = content;
	}
	
	public JXTreeTable getTreeTable() {
		root = new RootNode("Root");
		ChildNode myChild = null;
		for(String[] data : content) {
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
		table.setColumnControlVisible(true);
		table.packAll();
		return table;
	}

}
