package Requests;

import java.util.HashMap;

public class ChangeAnnotationRequest extends Request {
    private String id;
    private HashMap<String, String> annotations = new HashMap<String, String>();

    public ChangeAnnotationRequest(String id, HashMap annotations) {
	super("changeannotation");
	this.id = id;
	this.annotations = annotations;
    }
}
