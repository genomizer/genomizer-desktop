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

    public CheckListItem(FileData file,ExperimentData exData,String label,String fileId) {
        this.exData = exData;
        this.file = file;
        this.label = label;
        this.fileId = fileId;

        for(util.AnnotationDataValue specie:exData.annotations){
            if(specie.equals("specie")){
                this.specie = specie.value;
                System.out.println("Checklistitem specie: " + specie);
            }
        }

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

    public String fileId(){
        return fileId;
    }

    public String getSpecie(){
        return specie;
    }

    public FileData getfile(){
        return file;
    }
}
