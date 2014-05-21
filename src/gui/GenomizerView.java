package gui;

import gui.sysadmin.SysadminController;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JFrame;
import javax.swing.JList;

import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

import communication.HTTPURLUpload;

/**
 * A interface for the view part of an application used by genome researcher to
 * make their daily job easier.
 *
 * @author
 */

public interface GenomizerView {
    /**
     * Method adding a listener to the analyze selected button.
     *
     * @param listener
     *            The listener
     */
    public void addAnalyzeSelectedListener(ActionListener listener);

    public void refreshSearch();

    public void addLoginListener(ActionListener listener);

    public void addScheduleFileListener(ActionListener listener);

    public void addSearchToWorkspaceListener(ActionListener listener);

    public void addLogoutListener(ActionListener listener);

    public void addSearchListener(ActionListener listener);

    public void addProcessFileListener(ActionListener listener);

    public void addDownloadFileListener(ActionListener listener);

    public void addConvertFileListener(ActionListener listener);

    public void addQuerySearchListener(ActionListener listener);

    public void addRawToProfileDataListener(ActionListener listener);

    public void addRawToRegionDataListener(ActionListener listener);

    public void addAddToExistingExpButtonListener(
            ActionListener addToExistingExpButtonListener);

    public void addUploadToExperimentButtonListener(ActionListener listener);

    public void addSearchResultsDownloadListener(ActionListener listener);

    public void addSelectFilesToUploadButtonListener(ActionListener listener);

    public void addUpdateSearchAnnotationsListener(ActionListener listener);

    public void addNewExpButtonListener(ActionListener listener);

    public void addSelectButtonListener(ActionListener listener);

    public void addDeleteFromDatabaseListener(ActionListener listener);

    public void addProcessFeedbackListener(ActionListener listener);

    public void addUploadButtonListener(ActionListener listener);

    public void addToWorkspace(ArrayList<ExperimentData> experiments);

    public ArrayList<FileData> getAllMarkedFiles();

    public String getPassword();

    public String getUsername();

    public JFrame getFrame();

    public void setDownloadWindow(DownloadWindow downloadWindow);

    public DownloadWindow getDownloadWindow();

    public String getQuerySearchString();

    public String getIp();

    public ArrayList<ExperimentData> getSelectedDataInSearch();

    public UploadTab getUploadTab();

    public int getSelectedRowAtAnnotationTable();

    public void updateLoginAccepted(String username, String pwd, String name);

    public void updateLoginNeglected(String errorMessage);

    public void updateLogout();

    // public void updateQuerySearchResults(ExperimentData[] searchResults);

    public void setSearchAnnotationTypes(AnnotationDataType[] annotationTypes);

    public void setProcessFileList(ArrayList<FileData> arrayList);

    public void printToProfileText(String message);

    public void setSysadminController(SysadminController sysadminController);

    public ArrayList<ExperimentData> getSelectedDataInWorkspace();

    public ArrayList<ExperimentData> getSelectedExperimentsInWorkspace();

    public void createNewExp(AnnotationDataType[] annotations);

    public String[] getParameters();

    public void selectFilesToNewExp(File[] files);

    public void selectFilesToExistingExp(File[] files);

    public ArrayList<File> getFilesToUpload();

    public AnnotationDataValue[] getUploadAnnotations();

    public void setBowtieParameters();

    public JList getfileList();

    public String getNewExpName();

    public HashMap<String, String> getFilesToUploadTypes();

    public void updateQuerySearchResults(ArrayList<ExperimentData> searchResults);

    public void enableUploadButton(boolean b);

    public String[] getRatioCalcParameters();

    public String[] getOtherParameters();

    public void deleteUploadFileRow(File f);

    public void addRatioCalcListener(ActionListener listener);

    public void setDefaultRatioPar();

    public void setUnusedRatioPar();

    public void showRatioPopup();

    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData);

    public void setOngoingUploads(
            CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads);

    public void addOkListener(ActionListener listener);

    public void addCancelListener(ActionListener listener);

    public RatioCalcPopup getRatioCalcPopup();

    public void setGenomeFileList(GenomeReleaseData[] genome);

    public ArrayList<File> getSelectedFilesToUpload();

    public void addUploadSelectedFilesListener(ActionListener listener);

    public void removeUploadExpName();

    public void removeSelectedFromWorkspace();

    public void disableSelectedRow(File f);

    public boolean isCorrectToProcess();

    public boolean isRatioCorrectToProcess();

    public void setProfileButton(boolean bool);

    public boolean useRatio();
}
