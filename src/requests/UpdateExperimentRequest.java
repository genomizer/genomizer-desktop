package requests;

import java.util.HashMap;

public class UpdateExperimentRequest extends Request {
    public String experimentID;
    public String name;
    public String createdBy;
    public HashMap<String, String> annotations = new HashMap<String, String>();

    public UpdateExperimentRequest(String experimentID, String name,
	    String createdBy, HashMap<String, String> annotations, String userID) {
	super("updateexperiment", "/" + userID + "/experiment/" + experimentID,
		"PUT");
	this.experimentID = experimentID;
	this.name = name;
	this.createdBy = createdBy;
	this.annotations = annotations;
    }
}
