package gui.sysadmin.annotationview;

import util.AnnotationDataType;

import javax.swing.table.AbstractTableModel;

public class AnnotationTableModel extends AbstractTableModel {
    
    private static final long serialVersionUID = 1414328728572140752L;
    
    AnnotationDataType[]      annotations      = new AnnotationDataType[] {};
    
    @Override
    public int getRowCount() {
        return annotations.length;
    }
    
    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 3;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        
        switch (columnIndex) {
            case 0:
                return annotations[rowIndex].getName();
            case 1:
                StringBuilder string = new StringBuilder("");
                String[] values = annotations[rowIndex].getValues();
                if (values != null) {
                    string = new StringBuilder(values[0]);
                    for (int i = 1; i < values.length; i++) {
                        string.append(",").append(values[i]);
                    }
                } else {
                    System.err.println("Annotation[" + rowIndex
                            + "].getValues() was null!");
                }
                return string;
            case 2:
                return annotations[rowIndex].getForced();
            case 3:
                return annotations[rowIndex];
            default:
                System.err
                        .println("ERROR, should not be able to press here!!!");
                return null;
        }
    }
    
    public void setAnnotations(AnnotationDataType[] annotations) {
        synchronized (this.annotations) {
            this.annotations = annotations;
        }
        fireTableDataChanged();
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Name";
            case 1:
                return "Types";
            case 2:
                return "Forced";
            default:
                break;
        }
        return null;
        
    }
    
    public AnnotationDataType getAnnotationData(int i) {
        return annotations[i];
    }
}
