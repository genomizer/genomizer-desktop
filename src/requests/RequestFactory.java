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
            String uploader, boolean isPrivate, String grVersion,
            String checkSumMD5) {
        return new AddFileToExperiment(experimentID, fileName, type, metaData,
                author, uploader, isPrivate, grVersion, checkSumMD5);
    }
    
    public static LogoutRequest makeLogoutRequest() {
        return new LogoutRequest();
    }
    
    public static DownloadFileRequest makeDownloadFileRequest(String fileName,
            String fileFormat) {
        String urlFileName = decodeToURL(fileName);
        return new DownloadFileRequest(urlFileName, fileFormat);
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
            String experimentName, AnnotationDataValue[] annotations) {
        return new AddExperimentRequest(experimentName, annotations);
    }
    
    public static RetrieveExperimentRequest makeRetrieveExperimentRequest(
            String experimentID) {
        String urlExperimentID = decodeToURL(experimentID);
        return new RetrieveExperimentRequest(urlExperimentID);
    }
    
    public static RemoveExperimentRequest makeRemoveExperimentRequest(
            String experimentID) {
        String urlExperimentID = decodeToURL(experimentID);
        return new RemoveExperimentRequest(urlExperimentID);
    }
    
    public static UpdateExperimentRequest makeUpdateExperimentRequest(
            String experimentID, String name, String createdBy,
            HashMap<String, String> annotations) {
        return new UpdateExperimentRequest(experimentID, name, createdBy,
                annotations);
    }
    
    public static SearchRequest makeSearchRequest(String annotationString) {
        String urlEncodedAnnotationString = annotationString;
        urlEncodedAnnotationString = decodeToURL(annotationString);
        return new SearchRequest(urlEncodedAnnotationString);
    }
    
    public static rawToProfileRequest makeRawToProfileRequest(String expid,
            String[] parameters, String metadata, String genomeRelease,
            String author) {
        return new rawToProfileRequest(expid, parameters, metadata,
                genomeRelease, author);
    }
    
    public static AddAnnotationRequest makeAddAnnotationRequest(String name,
            String[] categories, Boolean forced) {
        return new AddAnnotationRequest(name, categories, forced);
    }
    
    public static RemoveAnnotationFieldRequest makeDeleteAnnotationRequest(
            String annotationName) {
        annotationName = decodeToURL(annotationName);
        return new RemoveAnnotationFieldRequest(annotationName);
    }
    
    public static GetAnnotationRequest makeGetAnnotationRequest() {
        return new GetAnnotationRequest();
    }
    
    public static GetGenomeReleasesRequest makeGetGenomeReleaseRequest() {
        return new GetGenomeReleasesRequest();
    }
    
    public static RemoveGenomeReleaseRequest makeRemoveGenomeReleaseRequest(
            String specie, String version) {
        
        System.out.println("/genomeRelease/" + specie + "/" + version);
        return new RemoveGenomeReleaseRequest(specie, version);
    }
    
    public static RenameAnnotationFieldRequest makeRenameAnnotationFieldRequest(
            String oldName, String newName) {
        return new RenameAnnotationFieldRequest(oldName, newName);
    }
    
    public static RenameAnnotationValueRequest makeRenameAnnotationValueRequest(
            String name, String oldValue, String newValue) {
        return new RenameAnnotationValueRequest(name, oldValue, newValue);
    }
    
    public static RemoveAnnotationValueRequest makeRemoveAnnotationValueRequest(
            String annotationName, String valueName) {
        annotationName = decodeToURL(annotationName);
        valueName = decodeToURL(valueName);
        return new RemoveAnnotationValueRequest(annotationName, valueName);
    }
    
    protected static String decodeToURL(String string) {
        try {
            string = URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            try {
                string = URLEncoder.encode(string, "ASCII");
            } catch (UnsupportedEncodingException e2) {
                e.printStackTrace();
            }
        }
        return string;
    }
    
    public static AddNewAnnotationValueRequest makeAddNewAnnotationValueRequest(
            String annotationName, String valueName) {
        return new AddNewAnnotationValueRequest(annotationName, valueName);
    }
    
    public static ProcessFeedbackRequest makeProcessFeedbackRequest() {
        return new ProcessFeedbackRequest();
    }
    
    public static GetGenomeSpecieReleasesRequest makeGetGenomeSpecieReleaseRequest(
            String specie) {
        return new GetGenomeSpecieReleasesRequest(specie);
    }
    
    public static AddGenomeReleaseRequest makeAddGenomeRelease(String[] files,
            String species, String version) {
        
        return new AddGenomeReleaseRequest(files, species, version);
    }
    
    public static RemoveFileFromExperimentRequest makeRemoveFileFromExperimentRequest(
            String fileID) {
        return new RemoveFileFromExperimentRequest(fileID);
        
    }
    
    public static AddFileFromGeoRequest makeAddFileFromGeoRequest(
            String experimentID, String filename, String type, String author,
            String uploader, boolean isPrivate, String grVersion, String url) {
        return new AddFileFromGeoRequest(experimentID, filename, type, author,
                uploader, isPrivate, grVersion, url);
        
    }
}
