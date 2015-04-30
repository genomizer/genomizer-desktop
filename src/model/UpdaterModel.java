package model;

import gui.ErrorDialog;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

import requests.AddFileToExperiment;
import requests.DownloadFileRequest;
import requests.RequestFactory;
import responses.AddFileToExperimentResponse;
import responses.DownloadFileResponse;
import responses.ResponseParser;
import util.Constants;
import util.RequestException;

import communication.Connection;
import communication.ConnectionFactory;
import communication.DownloadHandler;
import communication.HTTPURLUpload;

public class UpdaterModel {


    private CopyOnWriteArrayList<DownloadHandler> ongoingDownloads;
    private CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads;

    private ConnectionFactory connFactory;

    public UpdaterModel( ConnectionFactory connFactory ) {

        ongoingDownloads = new CopyOnWriteArrayList<>();
        ongoingUploads = new CopyOnWriteArrayList<>();

        this.connFactory = connFactory;

    }

    public boolean uploadFile(String expName, File f, String type,
            boolean isPrivate, String release) {

        // TODO: Trace comments. also "metameta"
        System.out.println("ska uploada fil: " + f.getName());

        String username = User.getInstance().getName();

        AddFileToExperiment request = RequestFactory.makeAddFile(expName,
                f.getName(), type, "metameta", username, username, isPrivate,
                release);

        Connection conn = connFactory.makeConnection();

        try {
            conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
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
            boolean ok = upload.sendFile(User.getInstance().getToken());

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

    public boolean downloadFile(final String url, String fileID, final String path,
            String fileName) {
     // TODO: Use this until search works on the server
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
            addDownload(handler);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    handler.download(url, path);
                }
            }).start();
        } catch (RequestException e) {
            ErrorDialog.showRequestErrorDialog("Couldn't download file", e);
        }

        return true;
    }
}