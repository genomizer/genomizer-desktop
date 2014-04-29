package requests;

import java.util.HashMap;

public class RequestFactory {

    public RequestFactory() {
    }

    public static LoginRequest makeLoginRequest(String username, String password) {
	return new LoginRequest(username, password);
    }

    public static AddFileToExperiment makeAddFile(String experimentID,
	    String fileName, String size, String type) {
	return new AddFileToExperiment(experimentID, fileName, size, type);
    }

    public static LogoutRequest makeLogoutRequest() {
	return new LogoutRequest();
    }

    public static DownloadFileRequest makeDownloadFileRequest(String fileName,
	    String fileFormat) {
	return new DownloadFileRequest(fileName, fileFormat);
    }

    public static ChangeAnnotationRequest makeChangeAnnotationRequest(
	    String id, HashMap annotations) {
	return new ChangeAnnotationRequest(id, annotations);
    }

    public static AddExperimentRequest makeAddExperimentRequest(
	    String experimentID, String name, String createdBy,
	    HashMap<String, String> annotations) {
	return new AddExperimentRequest(experimentID, name, createdBy,
		annotations);
    }

    public static RetrieveExperimentRequest makeRetrieveExperimentRequest(
	    String experimentID) {
	return new RetrieveExperimentRequest(experimentID);
    }

    public static RemoveExperimentRequest makeRemoveExperimentRequest(
	    String experimentID) {
	return new RemoveExperimentRequest(experimentID);
    }

    public static UpdateExperimentRequest makeUpdateExperimentRequest(
	    String experimentID, String name, String createdBy,
	    HashMap<String, String> annotations) {
	return new UpdateExperimentRequest(experimentID, name, createdBy,
		annotations);
    }

    public static SearchRequest makeSearchRequest(String annotationString) {
	return new SearchRequest(annotationString);
    }
}
