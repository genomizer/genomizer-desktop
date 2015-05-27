package model;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

import requests.AddFileToExperiment;
import requests.DownloadFileRequest;
import requests.RequestFactory;
import responses.AddFileToExperimentResponse;
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


    private TickingThread ticker;

    public UpdaterModel() {

        this.ongoingDownloads = new CopyOnWriteArrayList<>();
        this.ongoingUploads = new CopyOnWriteArrayList<>();


        this.ticker = new TickingThread();

    }

    public void uploadFile(String expName, File f, String type,
            boolean isPrivate, String release) throws RequestException,
            IllegalArgumentException, IOException {

        // TODO: Trace comments. also "metameta"
        // System.out.println("ska uploada fil: " + f.getName());

        String username = User.getInstance().getName();

        AddFileToExperiment request = RequestFactory.makeAddFile(expName,
                f.getName(), type, "metameta", username, username, isPrivate,
                release);

        Connection conn = SessionHandler.getInstance().makeConnection();

        conn.sendRequest(request, User.getInstance().getToken(), Constants.JSON);
        AddFileToExperimentResponse aFTER = ResponseParser
                .parseUploadResponse(conn.getResponseBody());
        HTTPURLUpload upload = new HTTPURLUpload(aFTER.URLupload,
                f.getAbsolutePath(), f.getName());
        ongoingUploads.add(upload);
        upload.sendFile(User.getInstance().getToken());
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

    public void downloadFile(final String url, String fileID,
            final String path, String fileName) throws RequestException {
        // TODO: Use this until search works on the server
        DownloadFileRequest request = RequestFactory.makeDownloadFileRequest(
                fileID, ".wig");

        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(),
                Constants.TEXT_PLAIN);

        final DownloadHandler handler = new DownloadHandler(User.getInstance()
                .getToken(), fileName);
        addDownload(handler);

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.download(url, path);
            }
        }).start();
    }

    /**
     * Setup new lists with downloads and uploads, and also clear the ticking
     * thread of things to do.
     */
    public void resetUpdaterModel() {

        ongoingDownloads = new CopyOnWriteArrayList<>();
        ongoingUploads = new CopyOnWriteArrayList<>();

        clearTickingThread();

    }

    /**
     * Completely stop and clear the ticking thread.
     */
    public void clearTickingThread() {
        ticker.clearTasks();
        ticker.stopTicking();

    }

    /**
     * Add a new ticking task
     *
     * @param task
     */
    public void addTickingTask(Runnable task) {
        ticker.addTask(task);
        ticker.startTicking();
    }

    private class TickingThread {

        private final int DELAY = 100; // ms
        private final Runnable tick = new Runnable() {

            @Override
            public void run() {

                while (isTicking) {

                    for (Runnable task : tasks) {
                        task.run();
                    }

                    try {
                        Thread.sleep(DELAY);
                    } catch (InterruptedException e) {
                        isTicking = false;
                    }
                }

            }
        };

        private Thread ticker;
        private boolean isTicking;
        private CopyOnWriteArrayList<Runnable> tasks;

        /**
         * Create a new unstarted ticker.
         */
        public TickingThread() {
            isTicking = false;
            ticker = null;
            tasks = new CopyOnWriteArrayList<Runnable>();
        }

        /**
         * Start a new ticking thread. Does nothing if already running.
         */
        public synchronized void startTicking() {

            if (ticker != null) return;
            isTicking = true;
            ticker = new Thread(tick);
            ticker.start();
        }

        /**
         * Stop the ticker Thread, interrupting it and joining it to current.
         */
        public synchronized void stopTicking() {

            isTicking = false;
            if (ticker == null) return;

            ticker.interrupt();
            try {
                ticker.join();
            } catch (InterruptedException e) {
                ErrorLogger.log(e);
            }
            ticker = null;
        }

        /**
         * Add a new task to be performed each tick.
         *
         * @param task
         *            to perform
         */
        public void addTask(Runnable task) {
            tasks.add(task);
        }

        /**
         * Remove all tasks in the list, but keep ticking.
         */
        public void clearTasks() {
            tasks.clear();
        }

    }

}
