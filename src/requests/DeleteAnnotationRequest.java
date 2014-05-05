package requests;

public class DeleteAnnotationRequest extends Request {

	private String[] delete;
	
	public DeleteAnnotationRequest(String[] names) {
		super("deleteAnnotation", "/annotation", "DELETE");
		this.delete = names;
	}
}
