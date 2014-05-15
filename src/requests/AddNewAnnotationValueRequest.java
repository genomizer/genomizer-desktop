package requests;

public class AddNewAnnotationValueRequest extends Request {
    
    public String name;
    public String value;

    public AddNewAnnotationValueRequest(String annotationName, String valueName) {
        super("addAnnotationValue", "/annotation/value", "POST");
        this.name = annotationName;
        this.value = valueName;
    }
    
}
