package model;

import gui.ErrorDialog;
import requests.CancelProcessRequest;
import requests.ProcessFeedbackRequest;
import requests.RequestFactory;
import responses.ResponseParser;
import util.Constants;
import util.ProcessFeedbackData;
import util.RequestException;

import communication.Connection;
import communication.ConnectionFactory;

public class ProcessModel {



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

        // TODO: Remove print
        System.out.println( "ABORTING: " + PID);

        CancelProcessRequest request = RequestFactory
                .makeCancelProcessRequest(PID);
        Connection conn = SessionHandler.getInstance().makeConnection();
        conn.sendRequest(request, User.getInstance().getToken(),
                Constants.TEXT_PLAIN);

    }

    /**
     * Sends a rawToProfile request to the server, with which file the user
     * wants to create profile data from.
     * <p/>
     * Returns whether or not the server could create profile data or not.
     *
     * @throws RequestException
     */
    public void rawToProfile(String expid, String[] parameters,
            String metadata, String genomeRelease, String author)
            throws RequestException {
        // rawToProfileRequest rawToProfilerequest = RequestFactory
        // .makeRawToProfileRequest(expid, parameters, metadata,
        // genomeRelease, author);
        // Connection conn = connFactory.makeConnection();
        // conn.sendRequest(rawToProfilerequest, User.getInstance().getToken(),
        // Constants.JSON);

    }

}
