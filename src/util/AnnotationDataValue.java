package util;

/**
 * Class representing an annotation. Contains the name of the annotation, the
 * corresponding value and the annotation id.
 */
public class AnnotationDataValue extends AnnotationData {

    public String value;

    public AnnotationDataValue(String id, String name, String value) {
        super(id, name);
        this.value = value;
    }

    // Test purpose
    public String getValue() {
        return value;
    }
}
