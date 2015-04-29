package model;

import gui.ErrorDialog;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

import requests.AddFileToExperiment;
import requests.RequestFactory;
import responses.AddFileToExperimentResponse;
import responses.ResponseParser;
import util.RequestException;

import communication.Connection;
import communication.ConnectionFactory;
import communication.DownloadHandler;
import communication.HTTPURLUpload;

public class UpdaterModel {


    private static final String TEXT_PLAIN = "text/plain";
    private static final String JSON = "application/json";

    private CopyOnWriteArrayList<DownloadHandler> ongoingDownloads;
    private CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads;

    private String userID;
    private ConnectionFactory connFactory;

    public UpdaterModel( String userID, ConnectionFactory connFactory ) {

        ongoingDownloads = new CopyOnWriteArrayList<>();
        ongoingUploads = new CopyOnWriteArrayList<>();

        // Should perhaps take a user instead
        this.userID = userID;
        this.connFactory = connFactory;

    }

    public boolean uploadFile(String expName, File f, String type,
            boolean isPrivate, String release) {

        // TODO: Trace comments. also "metameta"
        System.out.println("ska uploada fil: " + f.getName());

        // TODO: SHOULD ACTUALLY BE getLoginWindow().getUsernameInput()
        // USERNAME! Change for user-username later and fix
        String username = userID.substring(0,30);

        AddFileToExperiment request = RequestFactory.makeAddFile(expName,
                f.getName(), type, "metameta", username, username, isPrivate,
                release);

        Connection conn = connFactory.makeConnection();

        try {
            conn.sendRequest(request, userID, JSON);
        } catch (RequestException e) {
            ErrorDialog.showRequestErrorDialog("Couldn't upload file", e);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == 200) {
            AddFileToExperimentResponse aFTER = ResponseParser
                    .parseUploadResponse(conn.getResponseBody());
            HTTPURLUpload upload = new HTTPURLUpload(aFTER.URLupload,
                    f.getAbsolutePath(), f.getName());

            ongoingUploads.add(upload);
            boolean ok = upload.sendFile(userID);

            if (ok) {
                return true;
            }
        }
        return false;
    }

    public void addUpload(HTTPURLUpload upload) {
        ongoingUploads.add(upload);

    }

    public void addDownload(DownloadHandler handler) {
        ongoingDownloads.add(handler);

    }

    public CopyOnWriteArrayList<DownloadHandler> getOngoingDownloads() {
        return ongoingDownloads;
    }

    public CopyOnWriteArrayList<HTTPURLUpload> getOngoingUploads() {

        return ongoingUploads;
    }
}