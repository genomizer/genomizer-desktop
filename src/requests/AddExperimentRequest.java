package requests;

import java.util.HashMap;

public class AddExperimentRequest extends Request {
    public String experimentID;
    public String name;
    public String createdBy;
    public HashMap<String, String> annotations = new HashMap<String, String>();

    public AddExperimentRequest(String experimentID, String userID,
	    String name, String createdBy, HashMap<String, String> annotations) {
	super("addexperiment", "/" + userID + "/experiment/" + experimentID,
		"POST");
	this.experimentID = experimentID;
	this.name = name;
	this.createdBy = createdBy;
	this.annotations = annotations;
    }
}
