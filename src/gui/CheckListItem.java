package gui;

public class CheckListItem {

    private String label;
    private String fileId;
    private boolean isSelected = false;

    public CheckListItem(String label,String fileId) {
        this.label = label;
        this.fileId = fileId;
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
}
