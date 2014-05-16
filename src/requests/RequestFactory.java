package requests;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.swing.JOptionPane;

import util.AnnotationDataValue;

public class RequestFactory {

    public RequestFactory() {
    }

    public static LoginRequest makeLoginRequest(String username, String password) {
        return new LoginRequest(username, password);
    }

    public static AddFileToExperiment makeAddFile(String experimentID,
            String fileName, String type, String metaData, String author,
            String uploader, boolean isPrivate, String grVersion) {
        return new AddFileToExperiment(experimentID, fileName, type, metaData,
                author, uploader, isPrivate, grVersion);
    }

    public static LogoutRequest makeLogoutRequest() {
        return new LogoutRequest();
    }

    public static DownloadFileRequest makeDownloadFileRequest(String fileName,
            String fileFormat) {
        return new DownloadFileRequest(fileName, fileFormat);
    }

    public static EditAnnotationRequest makeChangeAnnotationRequest(
            String name, String annotation) {
        return new EditAnnotationRequest(name, annotation);
    }

    public static ChangeAnnotationRequest makeChangeAnnotationRequest(
            String id, HashMap annotations) {
        return new ChangeAnnotationRequest(id, annotations);
    }

    public static AddExperimentRequest makeAddExperimentRequest(
            String expirementName, String createdBy,
            AnnotationDataValue[] annotations) {
        return new AddExperimentRequest(expirementName, createdBy, annotations);
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
        String urlEncodedAnnotationString = annotationString;
        try {
            urlEncodedAnnotationString = URLEncoder.encode(annotationString,
                    "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new SearchRequest(urlEncodedAnnotationString);
    }

    public static rawToProfileRequest makeRawToProfileRequest(String fileName,
            String fileID, String expid, String processtype,
            String[] parameters, String metadata, String genomeRelease,
            String author) {
        return new rawToProfileRequest(fileName, fileID, expid, processtype,
                parameters, metadata, genomeRelease, author);
    }

    public static AddAnnotationRequest makeAddAnnotationRequest(String name,
            String[] categories, Boolean forced) {
        return new AddAnnotationRequest(name, categories, forced);
    }

    public static DeleteAnnotationRequest makeDeleteAnnotationRequest(
            String annotationName) {
        try {
            annotationName = URLEncoder.encode(annotationName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            try {
                annotationName = URLEncoder.encode(annotationName, "ASCII");
            } catch (UnsupportedEncodingException e2) {
                e.printStackTrace();
            }
        }
        return new DeleteAnnotationRequest(annotationName);
    }

    public static GetAnnotationRequest makeGetAnnotationRequest() {
        return new GetAnnotationRequest();
    }
    
    public static GetGenomeReleasesRequest makeGetGenomeReleaseRequest() {
        return new GetGenomeReleasesRequest();
    }

    public static RenameAnnotationRequest makeRenameAnnotationFieldRequest(
            String oldname, String newname) {
        return new RenameAnnotationRequest(oldname, newname);
    }

    public static RenameAnnotationValueRequest makeRenameAnnotationValueRequest(
            String name, String oldValue, String newValue) {
        return new RenameAnnotationValueRequest(name, oldValue, newValue);
    }

    public static RemoveAnnotationValueRequest makeRemoveAnnotationValueRequest(
            String annotationName, String valueName) {
        return new RemoveAnnotationValueRequest(annotationName, valueName);
    }

    public static AddNewAnnotationValueRequest makeAddNewAnnotationValueRequest(
            String annotationName, String valueName) {
        return new AddNewAnnotationValueRequest(annotationName, valueName);
    }

    public static ProcessFeedbackRequest makeProcessFeedbackRequest() {
        return new ProcessFeedbackRequest();
    }
}
