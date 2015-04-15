package gui.sysadmin.annotationview;

import javax.swing.table.AbstractTableModel;

import util.AnnotationDataType;




/***
 * This class is the Model for the search table
 *
 *
 */
public class AnnotationTableModel extends AbstractTableModel {

    private final int numberOfColumns = 3;
    private static final long serialVersionUID = 1414328728572140752L;

    AnnotationDataType[] annotations = new AnnotationDataType[] {};

    /***
     * Get the number of rows.
     */
    public int getRowCount() {
        return annotations.length;
    }

    /***
     * Get the number of columns.
     */
    public int getColumnCount() {
        return numberOfColumns;
    }


    /***
     * Sets the different columns of the table.
     */
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {

        /** The annotation name in the first column */
            case 0:
                return annotations[rowIndex].getName();

                /** The correspondning annotation values in the second column */
            case 1:
                StringBuilder string = new StringBuilder("");
                String[] values = annotations[rowIndex].getValues();
                if (values != null) {
                    string = new StringBuilder(values[0]);
                    for (int i = 1; i < values.length; i++) {
                        string.append(',').append(values[i]);
                    }
                } else {
                    System.err.println("Annotation[" + rowIndex
                            + "].getValues() was null!");
                }
                return string;
                /** If the annotation is forced or not. */
            case 2:
                return annotations[rowIndex].isForced();

                /** Nothing */
            case 3:
                return annotations[rowIndex];
            default:
                System.err
                        .println("ERROR, should not be able to press here!!!");
                return null;
        }
    }

    /***
     * Takes in a new array of the AnnotationDataType and sets this to be the
     * current annotations. Then notifies the table to update.
     *
     * @param annotations
     *            the new array of annotations.
     */

    public void setAnnotations(AnnotationDataType[] annotations) {
        synchronized (this.annotations) {
            this.annotations = annotations;
        }
        fireTableDataChanged();
    }

    /***
     * Takes the column index as parameter, returns the name of the
     * corresponding column.
     *
     *
     * @param column
     *            a column index
     * @return the name of the column corresponding to the index
     */
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

    /***
     * Gets a specific index of the annotations.
     *
     * @param i
     *            the index
     * @return the annotation corresponding to the index.
     */

    public AnnotationDataType getAnnotationData(int i) {
        return annotations[i];
    }
}
