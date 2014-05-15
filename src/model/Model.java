package model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import requests.AddAnnotationRequest;
import requests.AddExperimentRequest;
import requests.AddFileToExperiment;
import requests.DeleteAnnotationRequest;
import requests.DownloadFileRequest;
import requests.GetAnnotationRequest;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RenameAnnotationRequest;
import requests.RequestFactory;
import requests.SearchRequest;
import requests.rawToProfileRequest;
import responses.AddFileToExperimentResponse;
import responses.DownloadFileResponse;
import responses.LoginResponse;
import responses.NewExperimentResponse;
import responses.ResponseParser;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;

import com.google.gson.Gson;
import communication.Connection;
import communication.DownloadHandler;
import communication.HTTPURLUpload;

// import org.apache.http.protocol.HTTP;

public class Model implements GenomizerModel {
    
    private static final String TEXT_PLAIN = "text/plain";
    private static final String JSON = "application/json";
    private String userID = "";
    private Connection conn;
    private SearchHistory searchHistory;
    private OngoingDownloads ongoingDownloads;
    private CopyOnWriteArrayList<DownloadHandler> ongoingDownloads;
    
    public Model(Connection conn) {
        searchHistory = new SearchHistory();
        ongoingDownloads = new CopyOnWriteArrayList<DownloadHandler>();
        this.setConn(conn);
    }
    
    public String getUserID() {
        return userID;
    }
    
    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public Connection getConn() {
        return conn;
    }
    
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * Sends a rawToProfile request to the server, with which file the user
     * wants to create profile data from.
     * <p/>
     * Returns whether or not the server could create profile data or not.
     */
    public boolean rawToProfile(String fileName, String fileID, String expid,
            String processtype, String[] parameters, String metadata,
            String genomeRelease, String author) {
        
        System.out.println("RAW TO PROFILE\n");
        System.out.println("Filename: " + fileName);
        System.out.println("File ID: " + fileID);
        System.out.println("Expid: " + expid);
        System.out.println("Processtype: " + processtype);
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
                .makeRawToProfileRequest(fileName, fileID, expid, processtype,
                        parameters, metadata, genomeRelease, author);
        conn.sendRequest(rawToProfilerequest, userID, JSON);
        if (conn.getResponseCode() == 201) {
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
        conn.sendRequest(request, userID, JSON);
        String url = null;
        if (conn.getResponseCode() == 200) {
            url = conn.getResponseBody();
        }
        
        AddFileToExperimentResponse aFTER = ResponseParser
                .parseUploadResponse(conn.getResponseBody());
        HTTPURLUpload upload = new HTTPURLUpload(aFTER.URLupload,
                f.getAbsolutePath());
        upload.sendFile("pvt", "pvt");
        
        /*
         * UploadHandler handler = new UploadHandler(aFTER.URLupload,
         * f.getAbsolutePath(), userID, "pvt:pvt"); Thread thread = new
         * Thread(handler); thread.start();
         */
        
        return true;
    }
    
    @Override
    public boolean downloadFile(final String url, String fileID,
            final String path, String fileName) {
        // Use this until search works on the server
        DownloadFileRequest request = RequestFactory.makeDownloadFileRequest(
                fileID, ".wig");
        
        System.out.println("Test: " + fileID);
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
        searchHistory.addSearchToHistory(pubmedString);
        SearchRequest request = RequestFactory.makeSearchRequest(pubmedString);
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
        conn.setIp(ip);
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
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 201) {
            System.err.println("addAnnotation sent succesfully!");
            return true;
        } else {
            System.err
                    .println("addAnnotaion FAILURE, did not recive 201 response");
            return false;
        }
    }
    
    @Override
    public boolean editAnnotation(String name, String[] categories,
            boolean forced, AnnotationDataType oldAnnotation) {
        if (!(oldAnnotation.getName().equals(name))) {
            for (int i = 0; i < categories.length; i++) {
                if (!(categories[i]
                        .equalsIgnoreCase(oldAnnotation.getValues()[i]))) {
                    System.out
                            .println("A change was made in annotation properties");
                    // TODO: Code to create a request goes here
                }
            }
        }
        
        return false;
    }
    
    @Override
    public boolean deleteAnnotation(String deleteAnnoationData) {
        
        DeleteAnnotationRequest request = RequestFactory
                .makeDeleteAnnotationRequest(deleteAnnoationData);
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            System.err.println("Annotation named " + deleteAnnoationData
                    + " deleted succesfully");
            return true;
        } else {
            System.err.println("Could not delete annotation name "
                    + deleteAnnoationData + "!");
        }
        return false;
    }
    
    public synchronized AnnotationDataType[] getAnnotations() {
        GetAnnotationRequest request = RequestFactory
                .makeGetAnnotationRequest();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            System.err.println("Sent getAnnotionrequestsuccess!");
            AnnotationDataType[] annotations = ResponseParser
                    .parseGetAnnotationResponse(conn.getResponseBody());
            return annotations;
        } else {
            System.out.println("responsecode: " + conn.getResponseCode());
            System.err.println("Could not get annotations!");
        }
        return new AnnotationDataType[] {};
    }
    
    @Override
    public boolean addNewExperiment(String expName, String username,
            AnnotationDataValue[] annotations) {
        AddExperimentRequest aER = RequestFactory.makeAddExperimentRequest(
                expName, username, annotations);
        System.out.println(aER.toJson());
        conn.sendRequest(aER, getUserID(), JSON);
        Gson gson = new Gson();
        NewExperimentResponse response = gson.fromJson(conn.getResponseBody(),
                NewExperimentResponse.class);
        if (conn.getResponseCode() == 201) {
            return true;
        }
        return false;
    }
    
    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads() {
        return ongoingDownloads;
    }
    
    public boolean renameAnnotationField(String oldname, String newname) {
        RenameAnnotationRequest request = RequestFactory
                .makeRenameAnnotationRequest(oldname, newname);
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            System.err.println("Sent " + request.requestName + "success!");
            return true;
        } else {
            System.out.println("responsecode: " + conn.getResponseCode());
            return false;
        }
    }
}
