package requests;

/**
 * This class represents a "Retrieve an experiment" request in an application
 * for genome researchers. This request adds a experiment to the database of the
 * application.
 *
 * @author
 *
 */
public class RetrieveExperimentRequest extends Request {

    /**
     * Constructor creating the request.
     *
     * @param experimentID
     *            String representing the experiment id.
     */
    public RetrieveExperimentRequest(String experimentID) {
        super("retrieveexperiment", "/experiment/" + experimentID, "GET");
    }
}
