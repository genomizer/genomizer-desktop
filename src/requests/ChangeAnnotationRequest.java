package requests;

import java.util.HashMap;

public class ChangeAnnotationRequest extends Request {
    public String id;
    public HashMap<String, String> annotations = new HashMap<String, String>();

    public ChangeAnnotationRequest(String id, HashMap<String, String> annotations) {
	super("changeannotation", "/file/changeAnnotation", "GET");
	this.id = id;
	this.annotations = annotations;
    }
}
