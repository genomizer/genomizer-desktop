package requests;


public class RetrieveExperimentRequest extends Request {
    public String id;

    public RetrieveExperimentRequest(String id) {
	super("retrieveexperiment", "experiment/" + id, "GET");
    }
}
