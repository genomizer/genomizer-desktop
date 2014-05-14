package requests;

public class RemoveExperimentRequest extends Request {
    public RemoveExperimentRequest(String experimentID) {
        super("removeexperiment", "/experiment/" + experimentID, "DELETE");
    }
}
