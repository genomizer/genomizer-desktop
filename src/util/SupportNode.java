package util;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

/**
 * A custom node implementation (For TreeTable).
 */
public class SupportNode extends AbstractMutableTreeTableNode implements
        Comparable {

    public SupportNode(Object[] data) {
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

    @Override
    public int compareTo(Object arg0) {
        SupportNode node = (SupportNode) arg0;
        if (node.getData().equals(getData())) {
            return 0;
        }
        return -1;
    }

}
