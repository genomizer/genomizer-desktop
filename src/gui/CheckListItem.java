package gui;

public class CheckListItem {

    private String label;
    private String fileId;
    private String specie;
    private boolean isSelected = false;

    public CheckListItem(String label,String fileId,String specie) {
        this.label = label;
        this.fileId = fileId;
        this.specie = specie;
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
}
