package requests;

import java.util.HashMap;

import util.AnnotationDataType;
import util.DeleteAnnoationData;

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

	public static rawToProfileRequest makeRawToProfileRequest(String fileName, String fileID, String expid,
			String processtype, String[] parameters, String metadata,
			String genomeRelease, String author) {
		return new rawToProfileRequest(fileName, fileID, expid,
				processtype, parameters, metadata,
				genomeRelease, author);
	}

	public static AddAnnotationRequest makeAddAnnotationRequest(String name,
			String[] categories, Boolean forced) {
		return new AddAnnotationRequest(name, categories, forced);
	}

	public static DeleteAnnotationRequest makeDeleteAnnotationRequest(
			DeleteAnnoationData deleteAnnoationData) {
		return new DeleteAnnotationRequest(deleteAnnoationData);
	}

	public static GetAnnotationRequest makeGetAnnotationRequest() {
		return new GetAnnotationRequest();
	}
}
