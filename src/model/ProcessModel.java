package model;

import java.util.ArrayList;
import java.util.Iterator;

import gui.ErrorDialog;
import gui.processing.ProcessCommand;
import requests.CancelProcessRequest;
import requests.ProcessCommandRequest;
import requests.ProcessFeedbackRequest;
import requests.RequestFactory;
import responses.ResponseParser;
import util.Constants;
import util.ExperimentData;
import util.FileData;
import util.ProcessFeedbackData;
import util.RequestException;

import communication.Connection;
import communication.ConnectionFactory;

public class ProcessModel {

    ExperimentData selectedExperiment;

    public ProcessModel() {
    }

    public ProcessFeedbackData[] getProcessFeedback() {
        ProcessFeedbackRequest request = RequestFactory
                .makeProcessFeedbackRequest();
        Connection conn = SessionHandler.getInstance().makeConnection();
        try {
            conn.sendRequest(request, User.getInstance().getToken(),
                    Constants.TEXT_PLAIN);
            return ResponseParser.parseProcessFeedbackResponse(conn
                    .getResponseBody());
        } catch (RequestException e) {
            new ErrorDialog("Couldn't get process feedback", e).showDialog();
            return null;
        }
    }

    public void abortProcess(String PID) throws RequestException {

        CancelProcessRequest request = RequestFactory
                .makeCancelProcessRequest(PID);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(),
                Constants.TEXT_PLAIN);

    }

    /**
     * Sends a processing request to the server, with which file the user wants
     * to create profile data from. All the commants will get sent.
     *
     * @param commandList
     *
     * @throws RequestException
     */
    public void startProcessing(String pid, ProcessCommand[] commandList)
            throws RequestException {
        // TODO Auto-generated method stub

        ProcessCommandRequest request = RequestFactory
                .makeProcessCommandRequest(pid, commandList);
        Connection conn = SessionHandler.getInstance().makeConnection();

        conn.sendRequest(request, User.getInstance().getToken(),
                Constants.JSON);
    }

    public void setSelectedExperiment(ExperimentData selectedExperiment) {
        this.selectedExperiment = selectedExperiment;
    }

    public String[] getGenomeReleases() {

        ArrayList<String> genomeReleaseList = new ArrayList<String>();
        Iterator<FileData> fileIterator = selectedExperiment.files.iterator();
        for (int i = 0; fileIterator.hasNext(); i++) {
            String genomeRelease = fileIterator.next().grVersion;
            if (!genomeReleaseList.contains(genomeRelease)) {
                genomeReleaseList.add(genomeRelease);
            }
        }
        String[] genomeReleases = new String[genomeReleaseList.size()];
        genomeReleases = (String[]) genomeReleaseList.toArray(genomeReleases);
        return genomeReleases;
    }

    /**
     * Get the names of all files in the current experiment. Also provide type
     * argument to filter for this.
     *
     * @return array of String filenames
     */
    public String[] getFileNames() {

        ArrayList<String> fileNames = new ArrayList<String>();
        for (FileData file : selectedExperiment.files) {
            fileNames.add(file.getName());
        }
        return fileNames.toArray(new String[fileNames.size()]);
    }

    /**
     * Get the names of all files in the current experiment. Also provide type
     * argument to filter for this.
     *
     * @param type String to fileter for ("all" or null filter allows all)
     * @return array of String filenames
     */
    public String[] getFileNames(String type) {

        ArrayList<String> fileNames = new ArrayList<String>();
        Iterator<FileData> fileIterator = selectedExperiment.files.iterator();

        for (FileData file : selectedExperiment.files) {
            if (type == null || type.equalsIgnoreCase("all")
                    || type.equalsIgnoreCase(file.type)) {
                fileNames.add(file.getName());
            }
        }

        return fileNames.toArray(new String[fileNames.size()]);
    }

    public String getSelectedExperimentName() {
        return selectedExperiment.getName();
    }

}
