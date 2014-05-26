package util;

import com.sun.istack.internal.NotNull;
import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import java.util.Arrays;

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
        if (Arrays.equals(node.getData(),getData())) {
            return 0;
        }
        return -1;
    }

}
