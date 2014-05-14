package requests;

public class RetrieveExperimentRequest extends Request {
    public RetrieveExperimentRequest(String experimentID) {
        super("retrieveexperiment", "/experiment/" + experimentID, "GET");
    }
}
