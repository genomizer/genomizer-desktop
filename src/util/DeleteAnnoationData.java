package util;

public class DeleteAnnoationData {

    public String id;
    public String[] values;

    public DeleteAnnoationData(AnnotationDataType annotation) {
        this.id = annotation.id;
        this.values = new String[] { };
    }

}
