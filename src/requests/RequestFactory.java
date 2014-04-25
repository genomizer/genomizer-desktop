package requests;

import java.util.HashMap;

public class RequestFactory {

    public RequestFactory() {
    }

    public static LoginRequest makeLoginRequest(String username, String password) {
	return new LoginRequest(username, password);
    }

    public static DownloadFileRequest makeDownloadFileRequest(String fileName,
	    String fileFormat, String uID) {
	return new DownloadFileRequest(fileName, fileFormat, uID);
    }

    public static ChangeAnnotationRequest makeChangeAnnotationRequest(
	    String id, HashMap annotations, String uID) {
	return new ChangeAnnotationRequest(id, annotations, uID);
    }

    public static AddExperimentRequest makeAddExperimentRequest(
	    String experimentID, String userID, String name, String createdBy,
	    HashMap<String, String> annotations) {
	return new AddExperimentRequest(experimentID, userID, name, createdBy,
		annotations);
    }

    public static RetrieveExperimentRequest makeRetrieveExperimentRequest(
	    String experimentID, String userID) {
	return new RetrieveExperimentRequest(experimentID, userID);
    }

    public static RemoveExperimentRequest makeRemoveExperimentRequest(
	    String experimentID, String userID) {
	return new RemoveExperimentRequest(experimentID, userID);
    }

    public static UpdateExperimentRequest makeUpdateExperimentRequest(
	    String experimentID, String userID, String name, String createdBy,
	    HashMap<String, String> annotations) {
	return new UpdateExperimentRequest(experimentID, name, createdBy,
		annotations, userID);
    }
}
