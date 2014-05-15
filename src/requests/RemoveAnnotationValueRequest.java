package requests;

public class RemoveAnnotationValueRequest extends Request {
    
    public RemoveAnnotationValueRequest(String annotationName, String valueName) {
        super("removeAnnotationValue", "/annotation/value/" + annotationName
                + "/" + valueName, "DELETE");
    }
}
