package requests;

import java.util.HashMap;

public class ChangeAnnotationRequest extends Request {
    public String id;
    public HashMap<String, String> annotations = new HashMap<String, String>();

    public ChangeAnnotationRequest(String id, HashMap annotations) {
	super("changeannotation");
	this.id = id;
	this.annotations = annotations;
    }
}
