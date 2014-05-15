package gui.sysadmin.genomereleaseview;

import javax.swing.table.AbstractTableModel;

import util.GenomeReleaseData;

public class GenomereleaseTableModel extends AbstractTableModel {

    /* Should be a datatype of its own later */
    // private String[] grFilename;
    // private String[] species;
    // private String[] grVersion;

    GenomeReleaseData[] grData = new GenomeReleaseData[] {};

    @Override
    public int getRowCount() {

        return grData.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        switch (columnIndex) {
            case 0:
                return grData[rowIndex].getVersion();/*
                                                      * Get this from new
                                                      * datatype!
                                                      */

            case 1:
                return grData[rowIndex].getSpecies();

            case 2:
                return grData[rowIndex].getFilename();

        }

        return null;
    }

    public void setGenomeReleases(GenomeReleaseData[] grData) {

        this.grData = grData;
        // this.grFilename = grFilename;
        // this.species = species;
        // this.grVersion = grVersion;

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