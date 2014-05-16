package requests;

public class RemoveAnnotationFieldRequest extends Request {
    
    public RemoveAnnotationFieldRequest(String annotationName) {
        super("deleteAnnotation", "/annotation/field/" + annotationName, "DELETE");
    }
}
