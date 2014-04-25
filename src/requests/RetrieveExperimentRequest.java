package requests;

public class RetrieveExperimentRequest extends Request {
    public RetrieveExperimentRequest(String experimentID, String userID) {
	super("retrieveexperiment", "/" + userID + "/experiment/"
		+ experimentID, "GET");
    }
}
