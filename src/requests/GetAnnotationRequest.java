package requests;


public class GetAnnotationRequest extends Request {

	public GetAnnotationRequest() {
		super("getAnnotations", "/annotation", "GET");
	}

}
