package model;

import java.util.ArrayList;
import java.util.Iterator;

import gui.ErrorDialog;
import gui.processing.ProcessCommand;
import requests.CancelProcessRequest;
import requests.GetGenomeReleasesRequest;
import requests.ProcessCommandRequest;
import requests.ProcessFeedbackRequest;
import requests.RequestFactory;
import responses.ResponseParser;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.Constants;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
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
                Constants.JSON);

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

        // Emergency copypasta from (actual) model version of this method.
        // Get all possible genome releases
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
            return new String[] {};
        }

        ArrayList<String> genomeReleaseList = new ArrayList<String>();

        // Get annotation specie
        String expSpecies = null;
        ArrayList<AnnotationDataValue> expAnnots = selectedExperiment.getAnnotations();
        for ( AnnotationDataValue annotVal : expAnnots ){
            if ( annotVal.getName().equalsIgnoreCase("species") ){
                expSpecies = annotVal.getValue();
                break;
            }
        }
        if ( expSpecies == null ){
            return new String[] {};
        }

        // Get matching gr versions
        String grVersion;
        String grSpecies;
        for ( GenomeReleaseData grd: genomeReleases ){
            grVersion = grd.getVersion();
            grSpecies = grd.getSpecies();
            if ( grSpecies.equalsIgnoreCase( expSpecies ) ){
                if ( ! genomeReleaseList.contains( grVersion )){
                    genomeReleaseList.add(grVersion);
                }
            }
        }

        String[] genomeReleaseStrings = new String[genomeReleaseList.size()];
        genomeReleaseStrings = (String[]) genomeReleaseList.toArray(genomeReleaseStrings);

        return genomeReleaseStrings;
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
