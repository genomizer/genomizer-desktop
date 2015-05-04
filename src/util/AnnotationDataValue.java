package util;

/**
 * Class representing an annotation. Contains the name of the annotation, the
 * corresponding value and the annotation id.
 */
public class AnnotationDataValue extends AnnotationData {

    public String value;

    // TODO: What is 'id', and what test purpose? (OO)
    public AnnotationDataValue(String id, String name, String value) {
        super(name);
        this.value = value;
    }

    // Test purpose
    public String getValue() {
        return value;
    }

    @Override
    public String toString(){
        return "Annotation Data Value: " + super.toString() + ", "+ getValue();
    }

}
