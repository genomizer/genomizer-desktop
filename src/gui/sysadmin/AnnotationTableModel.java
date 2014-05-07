package gui.sysadmin;

import javax.swing.table.AbstractTableModel;

import util.AnnotationData;
import util.AnnotationDataType;

public class AnnotationTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1414328728572140752L;

	AnnotationDataType[] annotations = new AnnotationDataType[]{};

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
			String[] values = annotations[rowIndex].getValues();
			StringBuilder string = new StringBuilder(values[0]);
			for (int i = 1; i <  values.length; i++ ) {
				string.append(",").append(values[i]);
			}
			return string;
		case 2:
			return annotations[rowIndex].getForced();
		default:
			System.err.println("ERROR, should not be able to press here!!!");
			return null;
		}
	}
    public void setAnnotations(AnnotationDataType[] annotations) {
        synchronized (annotations) {
            this.annotations = annotations;
        }
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
