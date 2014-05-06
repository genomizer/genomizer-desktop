package util;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;


/**
 * A custom node implementation.
 */
public class Node extends AbstractMutableTreeTableNode {

	public Node(Object[] data) {
		super(data);
	}

	@Override
	public Object getValueAt(int columnIndex) {
		return getData()[columnIndex];
	}


	@Override
	public int getColumnCount() {
		return getData().length;
	}

	public Object[] getData() {
		return (Object[]) super.getUserObject();
	}

}
