package requests;

public class AddAnnotationRequest extends Request {

	private String name;
	private String[] type;
	private String defaultType;
	private Boolean forced;
	
	public AddAnnotationRequest(String name, String[] categories, Boolean forced) {
		super("addAnnotation", "/annotation", "POST");
		this.name = name;
		this.type = categories;
		this.defaultType = "unknown";
		this.forced = forced;
		
	}

}
