package util;

public class DeleteAnnoationData {
    
    public String[] values;
    public String   name;
    
    public DeleteAnnoationData(AnnotationDataType annotation) {
        this.name = annotation.name;
        this.values = new String[] {};
    }
    
}
