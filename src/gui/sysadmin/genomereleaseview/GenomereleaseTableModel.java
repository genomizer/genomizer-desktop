package gui.sysadmin.genomereleaseview;

import javax.swing.table.AbstractTableModel;

import gui.sysadmin.strings.SysStrings;
import util.GenomeReleaseData;

public class GenomereleaseTableModel extends AbstractTableModel {
    
    GenomeReleaseData[] grData = new GenomeReleaseData[] {};
    
    @Override
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
            
            System.out.println("Oh noes...");
            
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
    
    @Override
    public int getColumnCount() {
        // TODO Auto-generated method stub
        return 3;
    }
    
    public String[] getFilenames(int rowIndex) {
        return grData[rowIndex].getFilenames();
    }
    
}
