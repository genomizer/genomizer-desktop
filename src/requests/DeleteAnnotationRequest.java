package requests;

public class DeleteAnnotationRequest extends Request {
    
    public DeleteAnnotationRequest(String annotationName) {
        super("deleteAnnotation", "/annotation/field/" + annotationName, "DELETE");
    }
}
