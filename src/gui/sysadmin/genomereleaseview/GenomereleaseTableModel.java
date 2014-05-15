package gui.sysadmin.genomereleaseview;

import javax.swing.table.AbstractTableModel;

public class GenomereleaseTableModel extends AbstractTableModel {

    /* Should be a datatype of its own later */
    private String[] grFilename;
    private String[] species;
    private String[] grVersion;

    @Override
    public int getRowCount() {

        return grFilename.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return grVersion[rowIndex];/* Get this from new datatype! */

            case 1:
                return species[rowIndex];

            case 2:
                return grFilename[rowIndex];

        }

        return null;
    }

    public void setGenomeReleases(String[] grFilename, String[] species,
            String[] grVersion) {

        this.grFilename = grFilename;
        this.species = species;
        this.grVersion = grVersion;

    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Version";
            case 1:
                return "Species";
            case 2:
                return "Filename";
            default:
                break;
        }
        return null;

    }

    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 3;
    }
}