package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

import gui.LoginWindow;
import requests.AddAnnotationRequest;
import requests.AddExperimentRequest;
import requests.AddFileToExperiment;
import requests.AddGenomeReleaseRequest;
import requests.AddNewAnnotationValueRequest;
import requests.DownloadFileRequest;
import requests.GetAnnotationRequest;
import requests.GetGenomeReleasesRequest;
import requests.GetGenomeSpecieReleasesRequest;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.ProcessFeedbackRequest;
import requests.RemoveAnnotationFieldRequest;
import requests.RemoveAnnotationValueRequest;
import requests.RemoveExperimentRequest;
import requests.RemoveFileFromExperimentRequest;
import requests.RemoveGenomeReleaseRequest;
import requests.RenameAnnotationFieldRequest;
import requests.RenameAnnotationValueRequest;
import requests.RequestFactory;
import requests.RetrieveExperimentRequest;
import requests.SearchRequest;
import requests.rawToProfileRequest;
import responses.AddFileToExperimentResponse;
import responses.DownloadFileResponse;
import responses.LoginResponse;
import responses.ResponseParser;
import responses.sysadmin.AddGenomeReleaseResponse;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

import com.google.gson.Gson;
import communication.Connection;
import communication.ConnectionFactory;
import communication.DownloadHandler;
import communication.HTTPURLUpload;

// import org.apache.http.protocol.HTTP;

public class Model implements GenomizerModel {

    private String default_username = "pvt";
    private String default_password = "pvt";
    private static final String TEXT_PLAIN = "text/plain";
    private static final String JSON = "application/json";
    private String userID = "";
    private ArrayList<String> searchHistory;
    private CopyOnWriteArrayList<DownloadHandler> ongoingDownloads;
    private CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads;
    private ConnectionFactory connFactory;

    public Model() {
        connFactory = new ConnectionFactory();
        searchHistory = new ArrayList<String>();
        ongoingDownloads = new CopyOnWriteArrayList<DownloadHandler>();
        ongoingUploads = new CopyOnWriteArrayList<HTTPURLUpload>();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    /**
     * Sends a rawToProfile request to the server, with which file the user
     * wants to create profile data from.
     * <p/>
     * Returns whether or not the server could create profile data or not.
     */
    public boolean rawToProfile(String expid, String[] parameters,
            String metadata, String genomeRelease, String author) {

        // /hej anna
        System.out.println("RAW TO PROFILE\n");
        // System.out.println("Filename: " + fileName);
        // System.out.println("File ID: " + fileID);
        System.out.println("Expid: " + expid);
        // System.out.println("Processtype: " + processtype);
        System.out.println("Parameter 1: " + parameters[0]);
        System.out.println("Parameter 2: " + parameters[1]);
        System.out.println("Parameter 3: " + parameters[2]);
        System.out.println("Parameter 4: " + parameters[3]);
        System.out.println("Parameter 5: " + parameters[4]);
        System.out.println("Parameter 6: " + parameters[5]);
        System.out.println("Parameter 7: " + parameters[6]);
        System.out.println("Parameter 8: " + parameters[7]);
        System.out.println("Metadata: " + metadata);
        System.out.println("Genome Release: " + genomeRelease);
        System.out.println("Author: " + author);
        System.out.println("\n");

        rawToProfileRequest rawToProfilerequest = RequestFactory
                .makeRawToProfileRequest(expid, parameters, metadata,
                        genomeRelease, author);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(rawToProfilerequest, userID, JSON);
        if (conn.getResponseCode() == 200) {
            return true;
        } else {
            System.out.println("Response Code: " + conn.getResponseCode());
            return false;
        }
    }

    @Override
    public boolean loginUser(String username, String password) {
        if (!username.isEmpty() && !password.isEmpty()) {
            LoginRequest request = RequestFactory.makeLoginRequest(username,
                    password);
            Connection conn = connFactory.makeConnection();
            conn.sendRequest(request, userID, JSON);
            if (conn.getResponseCode() == 200) {
                LoginResponse loginResponse = ResponseParser
                        .parseLoginResponse(conn.getResponseBody());
                if (loginResponse != null) {
                    userID = loginResponse.token;
                    System.out.println(userID);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean logoutUser() {
        LogoutRequest request = RequestFactory.makeLogoutRequest();
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            userID = "";
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean uploadFile(String expName, File f, String type,
            String username, boolean isPrivate, String release) {
        AddFileToExperiment request = RequestFactory.makeAddFile(expName,
                f.getName(), type, "metameta", username, username, isPrivate,
                release);
        System.out.println(request.toJson());
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            AddFileToExperimentResponse aFTER = ResponseParser
                    .parseUploadResponse(conn.getResponseBody());
            HTTPURLUpload upload = new HTTPURLUpload(aFTER.URLupload,
                    f.getAbsolutePath(), f.getName());
            /* FOR MOCK SERVER */
            if (aFTER.URLupload.equalsIgnoreCase("url")) {
                return true;
            }
            ongoingUploads.add(upload);
            if (upload.sendFile(default_username, default_password)) {
                return true;
            }
        } else {
            System.out.println(conn.getResponseCode());
        }
        return false;

        /*
         * UploadHandler handler = new UploadHandler(aFTER.URLupload,
         * f.getAbsolutePath(), userID, "pvt:pvt"); Thread thread = new
         * Thread(handler); thread.start();
         */
    }

    @Override
    public boolean downloadFile(final String url, String fileID,
            final String path, String fileName) {
        // Use this until search works on the server
        DownloadFileRequest request = RequestFactory.makeDownloadFileRequest(
                fileID, ".wig");

        System.out.println("Test: " + fileID);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        Gson gson = new Gson();
        DownloadFileResponse response = gson.fromJson(conn.getResponseBody(),
                DownloadFileResponse.class);
        System.out.println(conn.getResponseBody());
        final DownloadHandler handler = new DownloadHandler("pvt", "pvt",
                fileName);
        if (handler != null) {
            ongoingDownloads.add(handler);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.download(url, path);
            }
        }).start();

        System.out.println("Test");
        return true;
    }

    @Override
    public ArrayList<ExperimentData> search(String pubmedString) {
        searchHistory.add(pubmedString);
        SearchRequest request = RequestFactory.makeSearchRequest(pubmedString);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            ExperimentData[] searchResponses = ResponseParser
                    .parseSearchResponse(conn.getResponseBody());
            if (searchResponses != null && searchResponses.length > 0) {
                return new ArrayList<ExperimentData>(
                        Arrays.asList(searchResponses));
            }
        }
        return null;
    }

    @Override
    public void setIp(String ip) {
        connFactory.setIP(ip);
    }

    @Override
    public void setLoginWindow(LoginWindow window) {
        connFactory.setLoginWindow(window);
    }

    @Override
    public boolean addNewAnnotation(String name, String[] categories,
            boolean forced) throws IllegalArgumentException {

        if (name.isEmpty()) {
            throw new IllegalArgumentException(
                    "Must have a name for the annotation!");
        }

        AnnotationDataType[] annotations = getAnnotations();
        if (annotations == null) {
            return false;
        }
        for (int i = 0; i < annotations.length; i++) {
            AnnotationDataType a = annotations[i];
            if (a.getName().equalsIgnoreCase(name)) {
                throw new IllegalArgumentException(
                        "Annotations must have a unique name, " + name
                                + " already exists");
            }
        }

        if (categories == null || categories.length == 0) {
            categories = new String[] { "Yes", "No", "Unknown" };
        }

        AddAnnotationRequest request = RequestFactory.makeAddAnnotationRequest(
                name, categories, forced);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 201) {
            System.err.println("addAnnotation sent succesfully!");
            return true;
        } else {
            System.err
                    .println("addAnnotaion FAILURE, did not recive 201 response");
            System.out.println("responsecode: " + conn.getResponseCode());
            return false;
        }
    }

    @Override
    public boolean editAnnotation(String name, String[] categories,
            boolean forced, AnnotationDataType oldAnnotation) {
        if (!(oldAnnotation.getName().equals(name))) {
            System.out
                    .println("Name has been changed! Calling renameAnnotationField!");
            renameAnnotationField(oldAnnotation.name, name);
        } else {
            System.out.println("No changes were made in name!");
        }

        if (!(oldAnnotation.isForced() == forced)) {
            System.out
                    .println("Forced value changed! Calling changeAnnotationForced (?)");
            // changeAnnotationForced(name);
        } else {
            System.out.println("Forced value not changed");
        }

        // TODO: If an annotation value has been added, call
        // addAnnotationValue(name, valueName)

        // TODO: If an annotation value has been removed, call
        // removeAnnotationValue(name, valueName)

        for (int i = 0; i < categories.length; i++) {
            if (!(categories[i].equals(oldAnnotation.getValues()[i]))) {
                System.out
                        .println("A change was made in annotation value! Calling renameAnnotationValue");
                renameAnnotationValue(name, oldAnnotation.getValues()[i],
                        categories[i]);
            }
        }

        return false;
    }

    @Override
    public boolean deleteAnnotation(String deleteAnnoationData) {

        RemoveAnnotationFieldRequest request = RequestFactory
                .makeDeleteAnnotationRequest(deleteAnnoationData);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            System.err.println("Annotation named " + deleteAnnoationData
                    + " deleted succesfully");
            return true;
        } else {
            System.err.println("Could not delete annotation name "
                    + deleteAnnoationData + "!");
            System.out.println("responsecode: " + conn.getResponseCode());
        }
        return false;
    }

    public synchronized AnnotationDataType[] getAnnotations() {
        GetAnnotationRequest request = RequestFactory
                .makeGetAnnotationRequest();
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            System.err.println("Sent getAnnotionrequestsuccess!");
            AnnotationDataType[] annotations = ResponseParser
                    .parseGetAnnotationResponse(conn.getResponseBody());
            return annotations;
        } else {
            System.out.println("responsecode: " + conn.getResponseCode());
            JOptionPane.showMessageDialog(null, "Could not get annotations!");
        }
        return new AnnotationDataType[] {};
    }

    public GenomeReleaseData[] getGenomeReleases() {
        GetGenomeReleasesRequest request = RequestFactory
                .makeGetGenomeReleaseRequest();
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            System.err.println("Sent getGenomerReleaseRequestSuccess!");
            GenomeReleaseData[] genomeReleases = ResponseParser
                    .parseGetGenomeReleaseResponse(conn.getResponseBody());
            return genomeReleases;
        } else {

            System.out.println("GenomeRelease responsecode: "
                    + conn.getResponseCode());
            JOptionPane
                    .showMessageDialog(null, "Could not get genomereleases!");
        }

        return new GenomeReleaseData[] {};
    }


    @Override
    public boolean uploadGenomeReleaseFile(String filePath, String specie,
            String version) {

        // String[] filepaths = new String[2];

        /*********************************************************/

        // for (String theFilePath : filepaths) {

        /** Put everything in here! */
        // }

        /**********************************************************/

        File f = new File(filePath);

        AddGenomeReleaseRequest request = RequestFactory.makeAddGenomeRelease(
                f.getName(), specie, version);
        System.out.println(request.toJson());
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 201) {

            AddGenomeReleaseResponse aGRR = ResponseParser
                    .parseGenomeUploadResponse(conn.getResponseBody());
            HTTPURLUpload upload = new HTTPURLUpload(aGRR.URLupload,
                    f.getAbsolutePath(), f.getName());

            ongoingUploads.add(upload);

            /** TODO */
            if (upload.sendFile(default_username, default_password)) {

                System.out.println("Succefully added genome release.");
                return true;

            }

        } else {

            System.out
                    .println("Something went wrong, could not add genome release: "
                            + conn.getResponseCode());
        }

        return false;
    }

    @Override
    public boolean addNewExperiment(String expName, String username,
            AnnotationDataValue[] annotations) {
        AddExperimentRequest aER = RequestFactory.makeAddExperimentRequest(
                expName, username, annotations);
        System.out.println(aER.toJson());
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(aER, getUserID(), JSON);
        if (conn.getResponseCode() == 201) {
            return true;
        }
        return false;
    }

    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads() {
        return ongoingDownloads;
    }

    public ExperimentData retrieveExperiment(String expID) {
        RetrieveExperimentRequest rER = RequestFactory
                .makeRetrieveExperimentRequest(expID);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(rER, getUserID(), "plain/text");
        System.out.println(rER.toJson());
        if (conn.getResponseCode() == 200) {
            ExperimentData ed = ResponseParser.parseRetrieveExp(conn
                    .getResponseBody());
            return ed;
        } else {
            System.out.println("responsecode: " + conn.getResponseCode());
            // JOptionPane.showMessageDialog(null,
            // "Couldn't retrieve experiment",
            // "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public CopyOnWriteArrayList<HTTPURLUpload> getOngoingUploads() {
        return ongoingUploads;
    }

    public boolean renameAnnotationField(String oldname, String newname) {
        RenameAnnotationFieldRequest request = RequestFactory
                .makeRenameAnnotationFieldRequest(oldname, newname);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            System.err.println("Sent " + request.requestName + "success!");
            return true;
        } else {
            System.out.println("responsecode: " + conn.getResponseCode());
            return false;
        }
    }

    public boolean renameAnnotationValue(String name, String oldValue,
            String newValue) {
        RenameAnnotationValueRequest request = RequestFactory
                .makeRenameAnnotationValueRequest(name, oldValue, newValue);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200 ) {
            System.err.println("Sent " + request.requestName + " success!");
            return true;
        } else {
            System.out.println("responsecode: " + conn.getResponseCode());
        }
        return false;
    }

    public boolean removeAnnotationValue(String annotationName, String valueName) {
        RemoveAnnotationValueRequest request = RequestFactory
                .makeRemoveAnnotationValueRequest(annotationName, valueName);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            System.err.println("Sent " + request.requestName + " success!");
            return true;
        } else {
            System.out.println("Response code: " + conn.getResponseCode());
        }
        return false;
    }

    public boolean addNewAnnotationValue(String annotationName, String valueName) {
        AddNewAnnotationValueRequest request = RequestFactory
                .makeAddNewAnnotationValueRequest(annotationName, valueName);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 201) {
            System.err.println("Sent " + request.requestName + " success!");
            return true;
        } else {
            System.out.println("Response code: " + conn.getResponseCode());
        }
        return false;
    }

    public ProcessFeedbackData[] getProcessFeedback() {
        ProcessFeedbackRequest request = RequestFactory
                .makeProcessFeedbackRequest();
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        System.out.println("proc feedback code: " + conn.getResponseCode());
        if (conn.getResponseCode() == 200) {
            ProcessFeedbackData[] data = ResponseParser.parseProcessFeedbackResponse(conn.getResponseBody());
            return data;
        } else {
            return null;
        }
    }

    @Override
    public boolean removeAnnotationField(String annotationName) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean deleteGenomeRelease(String specie, String version) {

        RemoveGenomeReleaseRequest request = RequestFactory
                .makeRemoveGenomeReleaseRequest(specie, version);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            System.err.println("Genome release version: " + version
                    + "successfully removed.");
            return true;
        } else {
            System.err.println("Could not remove genome release: " + version
                    + " species: " + specie);

        }
        return false;
    }

    public GenomeReleaseData[] getSpecieGenomeReleases(String specie) {

        GetGenomeSpecieReleasesRequest request = RequestFactory
                .makeGetGenomeSpecieReleaseRequest(specie);

        // GetGenomeReleasesRequest request = RequestFactory
        // .makeGetGenomeReleaseRequest();
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        // conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {

            System.err.println("Sent getGenomeSpecieReleaseRequestSuccess!");
            GenomeReleaseData[] genomeReleases = ResponseParser
                    .parseGetGenomeReleaseResponse(conn.getResponseBody());
            // for(int i = 0;i < genomeReleases.length ; i++){
            // System.out.println(genomeReleases[i].getVersion());
            // }
            return genomeReleases;
        } else {

            System.out.println("GenomeSpecieRelease responsecode: "
                    + conn.getResponseCode());
            JOptionPane.showMessageDialog(null,
                    "Could not get genomespeciereleases!");
        }

        //
        return new GenomeReleaseData[1];

    }

    @Override
    public boolean deleteFileFromExperiment(String id) {
        RemoveFileFromExperimentRequest request = RequestFactory
                .makeRemoveFileFromExperimentRequest(id);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteExperimentFromDatabase(String name) {
        RemoveExperimentRequest request = RequestFactory
                .makeRemoveExperimentRequest(name);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            return true;
        }
        return false;
    }

}
