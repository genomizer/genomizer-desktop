package gui;

import util.ExperimentData;
import util.FileData;

public class CheckListItem {
    
    private FileData file;
    private ExperimentData exData;
    private String label;
    private String fileId;
    private String specie;
    private boolean isSelected = false;
    
    public CheckListItem(FileData file, String label, String fileId,
            String specie) {
        this.file = file;
        this.label = label;
        this.fileId = fileId;
        this.specie = specie;
        
        this.label = label + " - " + specie;
    }
    
    public boolean isSelected() {
        return isSelected;
    }
    
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    
    @Override
    public String toString() {
        return label;
    }
    
    public String fileId() {
        return fileId;
    }
    
    public String getSpecie() {
        return specie;
    }
    
    public FileData getfile() {
        return file;
    }
}
