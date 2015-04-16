package util;

/**
 * TODO: Does not do anything at all ? (OO)
 */
public class DeleteAnnotationData {

    public String[] name;

    public DeleteAnnotationData(AnnotationDataType annotation) {
        this.name = new String[] { annotation.name };
    }

}
