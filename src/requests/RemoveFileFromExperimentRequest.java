package requests;

/**
 * This class represents a "Add a file to an experiment" request in an
 * application for genome researchers. This request adds a file to an experiment
 * in the database of the application.
 *
 * @author worfox
 * @date 2014-04-25.
 */

public class RemoveFileFromExperimentRequest extends Request {

    public RemoveFileFromExperimentRequest(String fileID) {
        super("removefile", "/file/" + fileID, "DELETE");

    }

}
