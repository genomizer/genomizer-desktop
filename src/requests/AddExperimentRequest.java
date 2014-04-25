package requests;

import java.util.HashMap;

public class AddExperimentRequest extends Request {
    public String id;
    public String name;
    public String createdBy;
    public HashMap<String, String> annotations = new HashMap<String, String>();

    public AddExperimentRequest(String id, String name, String createdBy,
	    HashMap<String, String> annotations) {
	super("addexperiment", "experiment/" + id, "POST");
	this.id = id;
	this.name = name;
	this.createdBy = createdBy;
	this.annotations = annotations;
    }
}
