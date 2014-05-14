package model;

import com.google.gson.Gson;
import communication.Connection;
import communication.DownloadHandler;
import communication.UploadHandler;
import requests.*;
import responses.DownloadFileResponse;
import responses.LoginResponse;
import responses.NewExperimentResponse;
import responses.ResponseParser;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.DeleteAnnoationData;
import util.ExperimentData;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class Model implements GenomizerModel {
    
    private String        userID = "";
    private Connection    conn;
    private SearchHistory searchHistory;
    
    public Model(Connection conn) {
        searchHistory = new SearchHistory();
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
    
    @Override
    public boolean rawToProfile(String fileName, String fileID, String expid,
            String processtype, String[] parameters, String metadata,
            String genomeRelease, String author) {
        
        rawToProfileRequest rawToProfilerequest = RequestFactory
                .makeRawToProfileRequest(fileName, fileID, expid, processtype,
                        parameters, metadata, genomeRelease, author);
        
        conn.sendRequest(rawToProfilerequest, userID, "application/json");
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
            conn.sendRequest(request, userID, "application/json");
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
        conn.sendRequest(request, userID, "text/plain");
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
        conn.sendRequest(request, userID, "application/json");
        String url = null;
        if (conn.getResponseCode() == 200) {
            url = conn.getResponseBody();
        }
        // AddFileToExperimentResponse aFTER =
        // ResponseParser.parseUploadResponse(conn.getResponseBody());
        UploadHandler handler = new UploadHandler(url, f.getAbsolutePath(),
                userID, "pvt:pvt");
        Thread thread = new Thread(handler);
        thread.start();
        
        return true;
    }
    
    @Override
    public boolean downloadFile(String url, String fileID, String path) {
        // Use this until search works on the server
        DownloadFileRequest request = RequestFactory.makeDownloadFileRequest(
                fileID, ".wig");
        
        System.out.println("Test: " + fileID);
        conn.sendRequest(request, userID, "text/plain");
        Gson gson = new Gson();
        DownloadFileResponse response = gson.fromJson(conn.getResponseBody(),
                DownloadFileResponse.class);
        System.out.println(conn.getResponseBody());
        DownloadHandler handler = new DownloadHandler("pvt", "pvt");
        handler.download(url, path);
        System.out.println("Test");
        return true;
    }
    
    @Override
    public ArrayList<ExperimentData> search(String pubmedString) {
        searchHistory.addSearchToHistory(pubmedString);
        SearchRequest request = RequestFactory.makeSearchRequest(pubmedString);
        conn.sendRequest(request, userID, "text/plain");
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
        conn.sendRequest(request, userID, "application/json");
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
    public boolean deleteAnnotation(DeleteAnnoationData deleteAnnoationData) {
        
        DeleteAnnotationRequest request = RequestFactory
                .makeDeleteAnnotationRequest(deleteAnnoationData);
        conn.sendRequest(request, userID, "application/json");
        if (conn.getResponseCode() == 200) {
            System.err.println("Annotation named " + deleteAnnoationData
                    + " deleted succesfully");
            return true;
        } else {
            System.err.println("Could not delete annotation name "
                    + deleteAnnoationData.name + "!");
        }
        return false;
    }
    
    public synchronized AnnotationDataType[] getAnnotations() {
        GetAnnotationRequest request = RequestFactory
                .makeGetAnnotationRequest();
        conn.sendRequest(request, userID, "text/plain");
        if (conn.getResponseCode() == 200) {
            System.err.println("Sent getAnnotionrequestsuccess!");
            AnnotationDataType[] annotations = ResponseParser
                    .parseGetAnnotationResponse(conn.getResponseBody());
            for (int i = 0; i < annotations.length; i++) {
                // System.out.println(annotations[i].name);
            }
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
        AddExperimentRequest aER = new RequestFactory()
                .makeAddExperimentRequest(expName, username, annotations);
        System.out.println(aER.toJson());
        conn.sendRequest(aER, getUserID(), "application/json");
        Gson gson = new Gson();
        NewExperimentResponse response = gson.fromJson(conn.getResponseBody(),
                NewExperimentResponse.class);
        if (conn.getResponseCode() == 201) {
            return true;
        }
        return false;
    }
    
}
