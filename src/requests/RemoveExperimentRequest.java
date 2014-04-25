package requests;

public class RemoveExperimentRequest extends Request {
    public RemoveExperimentRequest(String experimentID, String userID) {
	super("removeexperiment", "/" + userID + "/experiment/" + experimentID,
		"DELETE");
    }
}
