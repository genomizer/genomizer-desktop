package model;

import gui.ErrorDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JOptionPane;

import requests.AddAnnotationRequest;
import requests.AddExperimentRequest;
import requests.AddGenomeReleaseRequest;
import requests.AddNewAnnotationValueRequest;
import requests.CancelProcessRequest;
import requests.ChangeExperimentRequest;
import requests.ChangePasswordRequest;
import requests.CreateUserRequest;
import requests.DeleteUserRequest;
import requests.FileConversionRequest;
import requests.GetAnnotationRequest;
import requests.GetGenomeReleasesRequest;
import requests.GetGenomeSpecieReleasesRequest;
import requests.GetUserNamesRequest;
import requests.LoginRequest;
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
import requests.UpdateUserRequest;
import responses.ResponseParser;
import responses.sysadmin.AddGenomeReleaseResponse;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.Constants;
import util.ExperimentData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;
import util.RequestException;

import communication.Connection;
import communication.ConnectionFactory;
import communication.DownloadHandler;
import communication.HTTPURLUpload;

// TODO Ge feedback till status bar i alla try-catch?
public class Model implements GenomizerModel {
    private ArrayList<String> searchHistory;
    private UpdaterModel updateTabModel;
    private ProcessModel processingModel;

    public Model() {

        searchHistory = new ArrayList<>();
        updateTabModel = new UpdaterModel();
        processingModel = new ProcessModel();

    }

    @Override
    public void uploadFile(String expName, File f, String type,
            boolean isPrivate, String release) throws IllegalArgumentException,
            RequestException, IOException {
        // TODO: Remove indirection when ready
        updateTabModel.uploadFile(expName, f, type, isPrivate, release);
    }

    @Override
    public void downloadFile(final String url, String fileID,
            final String path, String fileName) throws RequestException {
        // TODO: Remove indirection when ready
        updateTabModel.downloadFile(url, fileID, path, fileName);
    }

    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads() {
        // TODO: Move to tabmodel when ready
        return updateTabModel.getOngoingDownloads();
    }

    public CopyOnWriteArrayList<HTTPURLUpload> getOngoingUploads() {
        // TODO: Move to tabmodel when ready
        return updateTabModel.getOngoingUploads();
    }

    @Override
    public ArrayList<ExperimentData> search(String pubmedString)
            throws RequestException {
        searchHistory.add(pubmedString);
        SearchRequest request = RequestFactory.makeSearchRequest(pubmedString);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(),
                Constants.TEXT_PLAIN);
        ExperimentData[] searchResponses = ResponseParser
                .parseSearchResponse(conn.getResponseBody());
        if (searchResponses != null && searchResponses.length > 0) {
            for (int i = 0; i < searchResponses.length; i++) {
                searchResponses[i].convertToUTF8();
                searchResponses[i].updateFileSize();
            }
        }

        if (searchResponses == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(searchResponses));
    }

    @Override
    public void addNewAnnotation(String name, String[] categories,
            boolean forced) throws IllegalArgumentException, RequestException {
        if (name.isEmpty()) {
            throw new IllegalArgumentException(
                    "Must have a name for the annotation!");
        }
        AnnotationDataType[] annotations = getAnnotations();
        if (annotations == null) {
            throw new NullPointerException("Annotations not initialized");
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
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
    }

    @Override
    public boolean editAnnotation(String name, String[] categories,
            boolean forced, AnnotationDataType oldAnnotation)
            throws RequestException {
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
    public void deleteAnnotation(String deleteAnnoationData)
            throws RequestException {
        RemoveAnnotationFieldRequest request = RequestFactory
                .makeDeleteAnnotationRequest(deleteAnnoationData);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);

    }

    public synchronized AnnotationDataType[] getAnnotations() {
        AnnotationDataType[] annotations = null;
        try {
            GetAnnotationRequest request = RequestFactory
                    .makeGetAnnotationRequest();
            Connection conn = SessionHandler.getInstance().makeConnection();
            conn.sendRequest(request, User.getInstance().getToken(),
                    Constants.TEXT_PLAIN);
            annotations = ResponseParser.parseGetAnnotationResponse(conn
                    .getResponseBody());
        } catch (RequestException e) {
            new ErrorDialog("Couldn't get annotations", e).showDialog();
        }
        if (annotations == null) {
            annotations = new AnnotationDataType[] {};
        }
        return annotations;
    }

    public GenomeReleaseData[] getGenomeReleases() {
        GenomeReleaseData[] genomeReleases;
        try {
            GetGenomeReleasesRequest request = RequestFactory
                    .makeGetGenomeReleaseRequest();
            Connection conn = SessionHandler.getInstance().makeConnection();
            conn.sendRequest(request, User.getInstance().getToken(),
                    Constants.TEXT_PLAIN);
            genomeReleases = ResponseParser.parseGetGenomeReleaseResponse(conn
                    .getResponseBody());

        } catch (RequestException e) {
            new ErrorDialog("Couldn't get genome releases", e).showDialog();
            genomeReleases = new GenomeReleaseData[] {};
        }

        return genomeReleases;
    }

    @Override
    public void addGenomeReleaseFile(String[] filePaths, String species,
            String version) throws RequestException, IllegalArgumentException,
            IOException {
        File[] files = new File[filePaths.length];
        String[] names = new String[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            files[i] = new File(filePaths[i]);
            names[i] = files[i].getName();
        }
        AddGenomeReleaseRequest request = RequestFactory.makeAddGenomeRelease(
                names, species, version);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        AddGenomeReleaseResponse[] aGRR = ResponseParser
                .parseGenomeUploadResponse(conn.getResponseBody());
        for (int i = 0; i < files.length; i++) {
            HTTPURLUpload upload = new HTTPURLUpload(aGRR[i].URLupload,
                    files[i].getAbsolutePath(), files[i].getName());
            updateTabModel.addUpload(upload);
            upload.sendFile(User.getInstance().getToken());
        }
    }

    @Override
    public void addNewExperiment(String expName,
            AnnotationDataValue[] annotations) throws RequestException {

        AddExperimentRequest aER = RequestFactory.makeAddExperimentRequest(
                expName, annotations);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(aER, User.getInstance().getToken(), Constants.JSON);
    }

    public void changeExperiment(String expName,
            AnnotationDataValue[] annotations) throws RequestException {
        ChangeExperimentRequest cER = RequestFactory
                .makeChangeExperimentRequest(expName, annotations);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(cER, User.getInstance().getToken(), Constants.JSON);
    }

    public ExperimentData retrieveExperiment(String expID) {
        try {
            RetrieveExperimentRequest rER = RequestFactory
                    .makeRetrieveExperimentRequest(expID);
            Connection conn = SessionHandler.getInstance().makeConnection();
            conn.sendRequest(rER, User.getInstance().getToken(), "plain/text");
            return ResponseParser.parseRetrieveExp(conn.getResponseBody());
        } catch (RequestException e) {
            new ErrorDialog("Couldn't retrieve experiment", e).showDialog();
            return new ExperimentData();
        }
    }

    public void renameAnnotationField(String oldname, String newname)
            throws RequestException {
        RenameAnnotationFieldRequest request = RequestFactory
                .makeRenameAnnotationFieldRequest(oldname, newname);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
    }

    public void renameAnnotationValue(String name, String oldValue,
            String newValue) throws RequestException {
        RenameAnnotationValueRequest request = RequestFactory
                .makeRenameAnnotationValueRequest(name, oldValue, newValue);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
    }

    public void removeAnnotationValue(String annotationName, String valueName)
            throws RequestException {
        RemoveAnnotationValueRequest request = RequestFactory
                .makeRemoveAnnotationValueRequest(annotationName, valueName);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
    }

    public void addNewAnnotationValue(String annotationName, String valueName)
            throws RequestException {
        AddNewAnnotationValueRequest request = RequestFactory
                .makeAddNewAnnotationValueRequest(annotationName, valueName);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
    }

    @Override
    public void deleteGenomeRelease(String specie, String version)
            throws RequestException {
        RemoveGenomeReleaseRequest request = RequestFactory
                .makeRemoveGenomeReleaseRequest(specie, version);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        JOptionPane.showMessageDialog(null, "Succesfully removed " + version);
    }

    public GenomeReleaseData[] getSpeciesGenomeReleases(String species) {
        GetGenomeSpecieReleasesRequest request = RequestFactory
                .makeGetGenomeSpecieReleaseRequest(species);
        Connection conn = SessionHandler.getInstance().makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(),
                    Constants.TEXT_PLAIN);
            return ResponseParser.parseGetGenomeReleaseResponse(conn
                    .getResponseBody());
        } catch (RequestException e) {
            new ErrorDialog("Couldn't get genome releases", e).showDialog();
            return new GenomeReleaseData[] {};
        }
    }

    @Override
    public void deleteFileFromExperiment(String id) throws RequestException {
        RemoveFileFromExperimentRequest request = RequestFactory
                .makeRemoveFileFromExperimentRequest(id);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(),
                Constants.TEXT_PLAIN);
    }

    @Override
    public void deleteExperimentFromDatabase(String name)
            throws RequestException {
        RemoveExperimentRequest request = RequestFactory
                .makeRemoveExperimentRequest(name);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(),
                Constants.TEXT_PLAIN);
    }

    public void resetModel() {
        User.getInstance().setToken("");
        searchHistory = new ArrayList<>();
        // TODO: probably a lot of other things needs doing !!!
        updateTabModel.resetUpdaterModel();
    }

    // TODO: Not working (JH)
    @Override
    public boolean addGenomeRelease() {
        return false;
    }

    @Override
    public void setIP(String ip) {
        SessionHandler.getInstance().setIP(ip);
    }

    public void addTickingTask(Runnable task) {
        updateTabModel.addTickingTask(task);
    }

    public void clearTickingTasks() {
        updateTabModel.clearTickingThread();
    }

    public void setProcessingExperiment(ExperimentData selectedExperiment) {
        processingModel.setSelectedExperiment(selectedExperiment);
    }

    public ProcessModel getProcessingModel() {
        return this.processingModel;
    }

    public boolean convertFile(String fileid, String toformat)
            throws RequestException {
        FileConversionRequest request = RequestFactory
                .makeFileConversionRequest(fileid, toformat);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);

        return conn.getResponseCode() == 200;
    }

    public boolean createUser(String username, String password,
            String privileges, String name, String email)
            throws RequestException {

        CreateUserRequest request = RequestFactory.makeCreateUserRequest(
                username, password, privileges, name, email);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        return conn.getResponseCode() == 200;
    }

    public boolean updateUser(String username, String password,
            String privileges, String name, String email)
            throws RequestException {
        UpdateUserRequest request = RequestFactory.makeUpdateUserRequest(
                username, password, privileges, name, email);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        return conn.getResponseCode() == 200;
    }

    public boolean deleteUser(String username) throws RequestException {
        DeleteUserRequest request = RequestFactory
                .makeDeleteUserRequest(username);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(),
                Constants.TEXT_PLAIN);
        return conn.getResponseCode() == 200;
    }

    public boolean updateUserSettings(String oldPass, String newPass,
            String name, String email) throws RequestException {
        ChangePasswordRequest request = RequestFactory
                .makeChangePasswordRequest(oldPass, newPass, name, email);

        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        return conn.getResponseCode() == 200;
    }

    public String[] getUserNames() throws RequestException {

        GetUserNamesRequest request = RequestFactory.getUserNamesRequest();

        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(), Constants.TEXT_PLAIN);

        return ResponseParser.getUserNamesResponse(conn.getResponseBody());

    }

}
