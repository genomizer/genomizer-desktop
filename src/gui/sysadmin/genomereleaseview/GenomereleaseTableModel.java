package gui.sysadmin.genomereleaseview;

import gui.sysadmin.strings.SysStrings;

import javax.swing.table.AbstractTableModel;

import util.GenomeReleaseData;

/***
 * 
 * @author oi11ahn
 * 
 *         The table model for the genome release table.
 * 
 */

public class GenomereleaseTableModel extends AbstractTableModel {

    private final int nrOfColumns = 2;
    GenomeReleaseData[] grData = new GenomeReleaseData[] {};

    public int getRowCount() {
        if(grData == null)
            return 0;

        return grData.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return grData[rowIndex].getVersion();

            case 1:
                return grData[rowIndex].getSpecies();

            case 2:
                return grData[rowIndex].getFilenames();

        }

        return null;
    }

    public void setGenomeReleases(GenomeReleaseData[] grData) {

        if (grData == null) {

        }

        this.grData = grData;
        fireTableDataChanged();

    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return SysStrings.GENOME_TABLE_VERSION;
            case 1:
                return SysStrings.GENOME_TABLE_SPECIES;
            case 2:
                return SysStrings.GENOME_TABLE_FILENAME;
            default:
                break;
        }
        return null;

    }


    public int getColumnCount() {

        return nrOfColumns;
    }

    public String[] getFilenames(int rowIndex) {
        return grData[rowIndex].getFilenames();
    }

}
