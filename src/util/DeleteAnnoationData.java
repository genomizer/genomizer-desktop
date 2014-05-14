package util;

public class DeleteAnnoationData {
    
    public String[] name;
    
    public DeleteAnnoationData(AnnotationDataType annotation) {
        this.name = new String[] { annotation.name };
    }
    
}
