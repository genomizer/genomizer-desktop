package requests;

public class GetAnnotationRequest extends Request {
    public GetAnnotationRequest() {
        super("getAnnotation", "/annotation", "GET");
    }
}
