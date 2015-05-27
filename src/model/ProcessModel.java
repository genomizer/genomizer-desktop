package model;

import gui.ErrorDialog;
import gui.processing.ProcessCommand;
import requests.CancelProcessRequest;
import requests.ProcessCommandRequest;
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
                Constants.TEXT_PLAIN);
    }

}
