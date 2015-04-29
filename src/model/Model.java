package model;

import gui.ErrorDialog;
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
import util.Constants;
import util.ErrorMessageGenerator;
import util.ExperimentData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;
import util.RequestException;

import com.google.gson.Gson;
import communication.Connection;
import communication.ConnectionFactory;
import communication.DownloadHandler;
import communication.HTTPURLUpload;

// TODO Ge feedback till status bar i alla try-catch?

public class Model implements GenomizerModel {

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
        try {
            conn.sendRequest(rawToProfilerequest, User.getInstance().getToken(), Constants.JSON);
        } catch (RequestException e) {
            showErrorDialog("Data process failed", e);

        }
        return conn.getResponseCode() == 200;
    }

    @Override
    public String loginUser(String username, String password) {
        return "";
    }

    @Override
    public boolean logoutUser() {
        return false;
    }

    @Override
    public boolean uploadFile(String expName, File f, String type,
            String username, boolean isPrivate, String release) {

        System.out.println("ska uploada fil: " + f.getName());

        AddFileToExperiment request = RequestFactory.makeAddFile(expName,
                f.getName(), type, "metameta", username, username, isPrivate,
                release);

        Connection conn = connFactory.makeConnection();


        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        } catch (RequestException e) {
            showErrorDialog("Couldn't upload file", e);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            AddFileToExperimentResponse aFTER = ResponseParser
                    .parseUploadResponse(conn.getResponseBody());
            HTTPURLUpload upload = new HTTPURLUpload(aFTER.URLupload,
                    f.getAbsolutePath(), f.getName());

            /* FOR MOCK SERVER */
            if (aFTER.URLupload.equalsIgnoreCase("url")) {
                return true;
            }
            ongoingUploads.add(upload);
            boolean ok = upload.sendFile(User.getInstance().getToken());

            if (ok) {
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
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);
            Gson gson = new Gson();
            DownloadFileResponse response = gson.fromJson(
                    conn.getResponseBody(), DownloadFileResponse.class);
            final DownloadHandler handler = new DownloadHandler(User.getInstance().getToken(),
                    fileName);
            ongoingDownloads.add(handler);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.download(url, path);
                }
            }).start();
        } catch (RequestException e) {
            showErrorDialog("Couldn't download file", e);
        }

        return true;
    }

    @Override
    public ArrayList<ExperimentData> search(String pubmedString) {
        searchHistory.add(pubmedString);
        SearchRequest request = RequestFactory.makeSearchRequest(pubmedString);
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);
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
        } catch (RequestException e) {
            showErrorDialog("Search failed", e);

        }
        return null;
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
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
            if (conn.getResponseCode() == 201) {
                return true;
            } else {
                // TODO Ska det h�nda n�got h�r eller? CF
                System.err
                        .println("addAnnotaion FAILURE, did not recive 201 response");
                System.out.println("Response code: " + conn.getResponseCode()
                        + " " + conn.getResponseBody());
                return false;
            }
        } catch (RequestException e) {
            showErrorDialog("Couldn't add annotation", e);

        }
        return false;
    }

    @Override
    public boolean editAnnotation(String name, String[] categories,
            boolean forced, AnnotationDataType oldAnnotation) {
        if (!(oldAnnotation.getName().equals(name))) {
            // System.out
            // .println("Name has been changed! Calling renameAnnotationField!");
            renameAnnotationField(oldAnnotation.name, name);
        } else {
            // TODO: This if part looks messy (OO)
            // System.out.println("No changes were made in name!");
        }

        // TODO: This part looks very broken ! (OO)
        if (!(oldAnnotation.isForced() == forced)) {
            // System.out
            // .println("Forced value changed! Calling changeAnnotationForced (?)");
            // changeAnnotationForced(name);
        } else {
            // System.out.println("Forced value not changed");
        }

        // TODO: This part looks very broken ! (OO)

        // TODO: If an annotation value has been added, call
        // addAnnotationValue(name, valueName)

        // TODO: If an annotation value has been removed, call
        // removeAnnotationValue(name, valueName)

        for (int i = 0; i < categories.length; i++) {
            if (!(categories[i].equals(oldAnnotation.getValues()[i]))) {
                // System.out
                // .println("A change was made in annotation value! Calling renameAnnotationValue");
                renameAnnotationValue(name, oldAnnotation.getValues()[i],
                        categories[i]);
            }
        }

        return false;
    }

    @Override
    public boolean deleteAnnotation(String deleteAnnoationData)
            throws DeleteAnnotationException {

        RemoveAnnotationFieldRequest request = RequestFactory
                .makeDeleteAnnotationRequest(deleteAnnoationData);
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        } catch (RequestException e) {
            showErrorDialog("Couldn't delete annotation", e);

        }
        if (conn.getResponseCode() == 200) {
            return true;
        } else {
            // System.err.println("Could not delete annotation name "
            // + deleteAnnoationData + "!");
            throw new DeleteAnnotationException(conn.getResponseBody());
            // return false;
        }
    }

    public synchronized AnnotationDataType[] getAnnotations() {
        if (connFactory.getIP() == null) {
            return new AnnotationDataType[] {};
        }
        GetAnnotationRequest request = RequestFactory
                .makeGetAnnotationRequest();
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);
        } catch (RequestException e) {
            showErrorDialog("Couldn't get annotations", e);

        }
        if (conn.getResponseCode() == 200) {
            AnnotationDataType[] annotations = ResponseParser
                    .parseGetAnnotationResponse(conn.getResponseBody());
            return annotations;
        } else if (!User.getInstance().getToken().equals("")) {
            JOptionPane.showMessageDialog(null, "Could not get annotations!");
        }
        return new AnnotationDataType[] {};
    }

    public GenomeReleaseData[] getGenomeReleases() {
        GetGenomeReleasesRequest request = RequestFactory
                .makeGetGenomeReleaseRequest();
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);
        } catch (RequestException e) {
            showErrorDialog("Couldn't get genome releases", e);

        }
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
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
            if (conn.getResponseCode() == 201) {

                AddGenomeReleaseResponse[] aGRR = ResponseParser
                        .parseGenomeUploadResponse(conn.getResponseBody());

                for (int i = 0; i < files.length; i++) {
                    HTTPURLUpload upload = new HTTPURLUpload(aGRR[i].URLupload,
                            files[i].getAbsolutePath(), files[i].getName());

                    ongoingUploads.add(upload);

                    if (upload.sendFile(User.getInstance().getToken())) {

                        // TODO: This if part looks like a mess! (OO)
                        // System.out
                        // .println("Succefully added genome release file named "
                        // + files[i].getName() + ".");

                    } else {
                        // System.err
                        // .println("Could not add genome release file named "
                        // + files[i].getName() + "!");
                        return false;
                    }

                }
                return true;

            }
        } catch (RequestException e) {
            showErrorDialog("Couldn't get genome releases", e);
        }

        return false;
    }

    @Override
    public boolean addNewExperiment(String expName,
            AnnotationDataValue[] annotations) {
        AddExperimentRequest aER = RequestFactory.makeAddExperimentRequest(
                expName, annotations);

        Connection conn = connFactory.makeConnection();

        try {
            conn.sendRequest(aER, User.getInstance().getToken(), Constants.JSON);
        } catch (RequestException e) {
            showErrorDialog("Couldn't add new experiment", e);
        }
        int responseCode = conn.getResponseCode();
        return (responseCode == 201);
    }

    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads() {
        return ongoingDownloads;
    }

    public ExperimentData retrieveExperiment(String expID) {
        RetrieveExperimentRequest rER = RequestFactory
                .makeRetrieveExperimentRequest(expID);
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(rER, User.getInstance().getToken(), "plain/text");
        } catch (RequestException e) {
            showErrorDialog("Couldn't retrieve experiment", e);
        }
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
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        } catch (RequestException e) {
            showErrorDialog("Couldn't rename annotation field", e);
        }
        return conn.getResponseCode() == 200;
    }

    public boolean renameAnnotationValue(String name, String oldValue,
            String newValue) {
        RenameAnnotationValueRequest request = RequestFactory
                .makeRenameAnnotationValueRequest(name, oldValue, newValue);
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        } catch (RequestException e) {
            showErrorDialog("Couldn't rename annotation value", e);
        }
        if (conn.getResponseCode() == 201 || conn.getResponseCode() == 200) {
            return true;
        }
        return false;
    }

    public boolean removeAnnotationValue(String annotationName, String valueName) {
        RemoveAnnotationValueRequest request = RequestFactory
                .makeRemoveAnnotationValueRequest(annotationName, valueName);
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        } catch (RequestException e) {
            showErrorDialog("Couldn't remove annotation value", e);
        }
        if (conn.getResponseCode() == 200) {
            return true;
        }
        return false;
    }

    public boolean addNewAnnotationValue(String annotationName, String valueName) {
        AddNewAnnotationValueRequest request = RequestFactory
                .makeAddNewAnnotationValueRequest(annotationName, valueName);
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        } catch (RequestException e) {
            showErrorDialog("Couldn't add new annotation value", e);
        }
        if (conn.getResponseCode() == 201) {
            return true;
        }
        return false;
    }

    public ProcessFeedbackData[] getProcessFeedback() {
        ProcessFeedbackRequest request = RequestFactory
                .makeProcessFeedbackRequest();
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);
        } catch (RequestException e) {
            showErrorDialog("Couldn't get process feedback", e);
        }
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
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);JOptionPane.showMessageDialog(null, "Succesfully removed "
                    + version);
            return true;
        } catch (RequestException e) {
            showErrorDialog("Couldn't delete genome release", e);
            return false;
        }
    }

    public GenomeReleaseData[] getSpeciesGenomeReleases(String species) {

        GetGenomeSpecieReleasesRequest request = RequestFactory
                .makeGetGenomeSpecieReleaseRequest(species);

        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);
            return ResponseParser.parseGetGenomeReleaseResponse(conn
                    .getResponseBody());
        } catch (RequestException e) {
            showErrorDialog("Couldn't get genome releases", e);
            return new GenomeReleaseData[] {};
        }
    }

    @Override
    public boolean deleteFileFromExperiment(String id) {
        RemoveFileFromExperimentRequest request = RequestFactory
                .makeRemoveFileFromExperimentRequest(id);
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);
            return true;
        } catch (RequestException e) {
            showErrorDialog("Couldn't delete file from experiment", e);
            return false;
        }
    }

    @Override
    public boolean deleteExperimentFromDatabase(String name) {
        RemoveExperimentRequest request = RequestFactory
                .makeRemoveExperimentRequest(name);
        Connection conn = connFactory.makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);
            return true;
        } catch (RequestException e) {
            showErrorDialog("Couldn't delete experiment", e);
            return false;
        }
    }

    public void resetModel() {
        User.getInstance().setToken("");
        searchHistory = new ArrayList<>();
        ongoingDownloads = new CopyOnWriteArrayList<>();
        ongoingUploads = new CopyOnWriteArrayList<>();
    }

    // TODO: Not working (JH)
    @Override
    public boolean addGenomeRelease() {
        return false;
    }

    private void showErrorDialog(String title, RequestException e) {
        String responseBody = e.getResponseBody();
        String message = ResponseParser.parseErrorResponse(responseBody).message;
        String extendedMessage = ErrorMessageGenerator.generateMessage(e.getResponseCode());
        ErrorDialog errorDialog = new ErrorDialog(title, message, extendedMessage);
        errorDialog.showDialog();
    }

    @Override
    public void setIP(String ip) {
        connFactory.setIP(ip);

    }

}
