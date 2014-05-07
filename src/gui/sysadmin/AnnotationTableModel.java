package gui.sysadmin;

import javax.swing.table.AbstractTableModel;

import util.AnnotationDataTypes;

public class AnnotationTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1414328728572140752L;

    AnnotationDataTypes[] annotations;

    @Override
    public int getRowCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setAnnotations(AnnotationDataTypes[] annotations) {
        synchronized (annotations) {
            this.annotations = annotations;
        }
    }
    public void setAnnotations() {

    }

}

