package util;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

/**
 * A custom node implementation (For TreeTable).
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
