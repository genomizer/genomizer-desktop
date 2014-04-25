package requests;

import java.util.HashMap;

public class UpdateExperimentRequest extends Request {
    public String id;
    public String name;
    public String createdBy;
    public HashMap<String, String> annotations = new HashMap<String, String>();

    public UpdateExperimentRequest(String id, String name, String createdBy,
	    HashMap<String, String> annotations) {
	super("updateexperiment", "experiment/" + id, "PUT");
	this.id = id;
	this.name = name;
	this.createdBy = createdBy;
	this.annotations = annotations;
    }
}
