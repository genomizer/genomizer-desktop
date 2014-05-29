package model;

import gui.GenomizerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

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
import responses.ErrorResponse;
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

public class Model implements GenomizerModel {

    private static final String TEXT_PLAIN = "text/plain";
    private static final String JSON = "application/json";
    private String userID = "";
    private ArrayList<String> searchHistory;
    private CopyOnWriteArrayList<DownloadHandler> ongoingDownloads;
    private CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads;
    private ConnectionFactory connFactory;

    public Model() {
        connFactory = new ConnectionFactory();
        searchHistory = new ArrayList<>();
        ongoingDownloads = new CopyOnWriteArrayList<>();
        ongoingUploads = new CopyOnWriteArrayList<>();
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

        rawToProfileRequest rawToProfilerequest = RequestFactory
                .makeRawToProfileRequest(expid, parameters, metadata,
                        genomeRelease, author);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(rawToProfilerequest, userID, JSON);
        if (conn.getResponseCode() == 200) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String loginUser(String username, String password) {
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
                    return "true";
                }
            } else {
                ErrorResponse response = ResponseParser.parseErrorResponse(conn
                        .getResponseBody());
                if (response != null) {
                    return response.message;
                } else {
                    return "Server not found";
                }
            }
        }

        return "No username and/or password inserted";
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
            if (upload.sendFile(userID)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean downloadFile(final String url, String fileID,
            final String path, String fileName) {
        // Use this until search works on the server
        DownloadFileRequest request = RequestFactory.makeDownloadFileRequest(
                fileID, ".wig");

        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        Gson gson = new Gson();
        DownloadFileResponse response = gson.fromJson(conn.getResponseBody(),
                DownloadFileResponse.class);
        final DownloadHandler handler = new DownloadHandler(userID, fileName);
        ongoingDownloads.add(handler);

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.download(url, path);
            }
        }).start();

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
                for (int i = 0; i < searchResponses.length; i++) {
                    searchResponses[i].convertToUTF8();
                }
                return new ArrayList<>(Arrays.asList(searchResponses));
            }
        }
        return null;
    }

    @Override
    public void setIp(String ip) {
        connFactory.setIP(ip);
    }

    @Override
    public void setGenomizerView(GenomizerView view) {
        connFactory.setGenomizerView(view);
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
            // System.err.println("addAnnotation sent succesfully!");
            return true;
        } else {
//            System.err
//                    .println("addAnnotaion FAILURE, did not recive 201 response");
            // System.out.println("Response code: " + conn.getResponseCode() +
            // " "
            // + conn.getResponseBody());
            return false;
        }
    }

    @Override
    public boolean editAnnotation(String name, String[] categories,
            boolean forced, AnnotationDataType oldAnnotation) {
        if (!(oldAnnotation.getName().equals(name))) {
//            System.out
//                    .println("Name has been changed! Calling renameAnnotationField!");
            renameAnnotationField(oldAnnotation.name, name);
        } else {
//            System.out.println("No changes were made in name!");
        }

        if (!(oldAnnotation.isForced() == forced)) {
//            System.out
//                    .println("Forced value changed! Calling changeAnnotationForced (?)");
            // changeAnnotationForced(name);
        } else {
//            System.out.println("Forced value not changed");
        }

        // TODO: If an annotation value has been added, call
        // addAnnotationValue(name, valueName)

        // TODO: If an annotation value has been removed, call
        // removeAnnotationValue(name, valueName)

        for (int i = 0; i < categories.length; i++) {
            if (!(categories[i].equals(oldAnnotation.getValues()[i]))) {
//                System.out
//                        .println("A change was made in annotation value! Calling renameAnnotationValue");
                renameAnnotationValue(name, oldAnnotation.getValues()[i],
                        categories[i]);
            }
        }

        return false;
    }

    @Override
    public boolean deleteAnnotation(String deleteAnnoationData)
            throws Exception {

        RemoveAnnotationFieldRequest request = RequestFactory
                .makeDeleteAnnotationRequest(deleteAnnoationData);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
//            System.err.println("Annotation named " + deleteAnnoationData
//                    + " deleted succesfully");
            return true;
        } else {
//            System.err.println("Could not delete annotation name "
//                    + deleteAnnoationData + "!");
            throw new Exception(conn.getResponseBody());
            // return false;
        }
    }

    public synchronized AnnotationDataType[] getAnnotations() {
        if (connFactory.GetIP() == null) {
            return new AnnotationDataType[] {};
        }
        GetAnnotationRequest request = RequestFactory
                .makeGetAnnotationRequest();
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            AnnotationDataType[] annotations = ResponseParser
                    .parseGetAnnotationResponse(conn.getResponseBody());
            return annotations;
        } else if (userID != "") {
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
            GenomeReleaseData[] genomeReleases = ResponseParser
                    .parseGetGenomeReleaseResponse(conn.getResponseBody());
            return genomeReleases;
        }

        return new GenomeReleaseData[] {};
    }

    @Override
    public boolean addGenomeReleaseFile(String[] filePaths, String species,
            String version) {
        File[] files = new File[filePaths.length];
        String[] names = new String[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            files[i] = new File(filePaths[i]);
            names[i] = files[i].getName();
        }

        AddGenomeReleaseRequest request = RequestFactory.makeAddGenomeRelease(
                names, species, version);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 201) {

            AddGenomeReleaseResponse[] aGRR = ResponseParser
                    .parseGenomeUploadResponse(conn.getResponseBody());

            for (int i = 0; i < files.length; i++) {
                HTTPURLUpload upload = new HTTPURLUpload(aGRR[i].URLupload,
                        files[i].getAbsolutePath(), files[i].getName());

                ongoingUploads.add(upload);

                if (upload.sendFile(userID)) {

//                    System.out
//                            .println("Succefully added genome release file named "
//                                    + files[i].getName() + ".");

                } else {
//                    System.err
//                            .println("Could not add genome release file named "
//                                    + files[i].getName() + "!");
                    return false;
                }

            }
            return true;

        } else {
            JOptionPane.showMessageDialog(null, conn.getResponseBody());
//            System.out
//                    .println("Something went wrong, could not add genome release: "
//                            + conn.getResponseCode()
//                            + "\n"
//                            + conn.getResponseBody());
        }

        return false;
    }

    @Override
    public boolean addNewExperiment(String expName,
            AnnotationDataValue[] annotations) {
        AddExperimentRequest aER = RequestFactory.makeAddExperimentRequest(
                expName, annotations);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(aER, getUserID(), JSON);
        return (conn.getResponseCode() == 201);
    }

    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads() {
        return ongoingDownloads;
    }

    public ExperimentData retrieveExperiment(String expID) {
        RetrieveExperimentRequest rER = RequestFactory
                .makeRetrieveExperimentRequest(expID);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(rER, getUserID(), "plain/text");
        if (conn.getResponseCode() == 200) {
            return ResponseParser.parseRetrieveExp(conn.getResponseBody());
        } else {
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
            // System.err.println("Sent " + request.requestName + "success!");
            return true;
        } else {
            return false;
        }
    }

    public boolean renameAnnotationValue(String name, String oldValue,
            String newValue) {
        RenameAnnotationValueRequest request = RequestFactory
                .makeRenameAnnotationValueRequest(name, oldValue, newValue);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
            // System.err.println("Sent " + request.requestName + " success!");
            return true;
        }
        return false;
    }

    public boolean removeAnnotationValue(String annotationName, String valueName) {
        RemoveAnnotationValueRequest request = RequestFactory
                .makeRemoveAnnotationValueRequest(annotationName, valueName);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            // System.err.println("Sent " + request.requestName + " success!");
            return true;
        }
        return false;
    }

    public boolean addNewAnnotationValue(String annotationName, String valueName) {
        AddNewAnnotationValueRequest request = RequestFactory
                .makeAddNewAnnotationValueRequest(annotationName, valueName);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 201) {
            // System.err.println("Sent " + request.requestName + " success!");
            return true;
        }
        return false;
    }

    public ProcessFeedbackData[] getProcessFeedback() {
        ProcessFeedbackRequest request = RequestFactory
                .makeProcessFeedbackRequest();
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {
            return ResponseParser.parseProcessFeedbackResponse(conn
                    .getResponseBody());
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteGenomeRelease(String specie, String version) {

        RemoveGenomeReleaseRequest request = RequestFactory
                .makeRemoveGenomeReleaseRequest(specie, version);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, JSON);
        if (conn.getResponseCode() == 200) {
            JOptionPane.showMessageDialog(null, "Succesfully removed "
                    + version);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, conn.getResponseBody());
//            System.err.println("Could not remove genome release: " + version
//                    + " species: " + specie);
//            System.err.println(conn.getResponseBody());

        }
        return false;
    }

    public GenomeReleaseData[] getSpeciesGenomeReleases(String species) {

        GetGenomeSpecieReleasesRequest request = RequestFactory
                .makeGetGenomeSpecieReleaseRequest(species);

        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        if (conn.getResponseCode() == 200) {

            // System.err.println("Sent getGenomeSpecieReleaseRequestSuccess!");
            // for(int i = 0;i < genomeReleases.length ; i++){
            // System.out.println(genomeReleases[i].getVersion());
            // }
            System.out.println(conn.getResponseBody());
            return ResponseParser.parseGetGenomeReleaseResponse(conn
                    .getResponseBody());
        } else {
            JOptionPane.showMessageDialog(null,
                    "Could not get genomespeciereleases!");
        }

        //
        return new GenomeReleaseData[] {};

    }

    @Override
    public boolean deleteFileFromExperiment(String id) {
        RemoveFileFromExperimentRequest request = RequestFactory
                .makeRemoveFileFromExperimentRequest(id);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        return (conn.getResponseCode() == 200);
    }

    @Override
    public boolean deleteExperimentFromDatabase(String name) {
        RemoveExperimentRequest request = RequestFactory
                .makeRemoveExperimentRequest(name);
        Connection conn = connFactory.makeConnection();
        conn.sendRequest(request, userID, TEXT_PLAIN);
        return (conn.getResponseCode() == 200);
    }

    public void resetModel() {
        userID = "";
        searchHistory = new ArrayList<>();
        ongoingDownloads = new CopyOnWriteArrayList<>();
        ongoingUploads = new CopyOnWriteArrayList<>();
    }

    @Override
    public boolean addGenomeRelease() {
        return false;
    }

}
