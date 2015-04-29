package gui;

import gui.sysadmin.SysadminTab;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;

import util.ActiveSearchPanel;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;
import util.GenomeReleaseData;
import util.ProcessFeedbackData;

import communication.DownloadHandler;
import communication.HTTPURLUpload;
import controller.SysadminController;

/**
 * A interface for the view part of an application used by genome researcher to
 * make their daily job easier.
 *
 * @author
 */

// TODO: Huge uncommented interface... Seems to fill literally no purpose
// whatsoever. OO

public interface GenomizerView {
    /**
     * Set a new statusmessage
     * @param status
     *              New status
     * @author JH
     */
    public void setStatusPanel(String status);
    /**
     * Method adding a listener to the analyze selected button.
     * 
     * @param listener
     *            The listener
     */


    public void addUploadToListener(ActionListener listener);
    
    public void refreshSearch(); //unused
    
    LoginWindow getLoginWindow(); // unused
    
    public void addLoginListener(ActionListener listener); // unused
    
    public void addSearchToWorkspaceListener(ActionListener listener); // unused
    
    public void addLogoutListener(ActionListener listener); // used
    
    public void addSearchListener(ActionListener listener); // unused
    
    public void addProcessFileListener(ActionListener listener); // unused
    
    public void addDownloadFileListener(ActionListener listener); // unused
    
    public void addQuerySearchListener(ActionListener listener); // unused
    
    public void addRawToProfileDataListener(ActionListener listener); // unused
    
    public void addAddToExistingExpButtonListener(
            ActionListener addToExistingExpButtonListener); // unused
    
    public void addUploadToExperimentButtonListener(ActionListener listener); // unused
    
    //TODO unimplemented listener
    public void addSearchResultsDownloadListener(ActionListener listener); // unused
    
    public void addSelectFilesToUploadButtonListener(ActionListener listener); // unused
    
    public void addUpdateSearchAnnotationsListener(ActionListener listener); // unused
    
    public void addNewExpButtonListener(ActionListener listener); // unused
    
    public void addSelectButtonListener(ActionListener listener); // unused
    
    public void addDeleteFromDatabaseListener(ActionListener listener); // unused
    
    public void addProcessFeedbackListener(ActionListener listener); // unused
    
    public void addUploadButtonListener(ActionListener listener); // unused
    
    public void addToWorkspace(ArrayList<ExperimentData> experiments); // unused
    
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
    
    public QuerySearchTab getQuerySearchTab();
    
    public SysadminTab getSysAdminTab();
    
    public WorkspaceTab getWorkSpaceTab();
    
    public ProcessTab getProcessTab();
    
    public int getSelectedRowAtAnnotationTable();
    
    public void updateLoginAccepted(String username, String pwd, String name);
    
    public void updateLoginNeglected(String errorMessage);
    
    public void updateLogout();
    
    public void setSearchAnnotationTypes(AnnotationDataType[] annotationTypes);
    
    public void setProcessFileList(ArrayList<FileData> arrayList);
    
    public void printToConsole(String message);
    
    public void setSysadminController(SysadminController sysadminController);
    
    public ArrayList<ExperimentData> getSelectedDataInWorkspace();
    
    public ArrayList<ExperimentData> getSelectedExperimentsInWorkspace();
    
    /**
     * Creates a new experiment to upload to using the provided annotations.
     * 
     * @param annotations
     *            The annotations of the new experiment.
     */
    public void createNewExp(AnnotationDataType[] annotations);
    
    public String[] getParameters();
    
    /**
     * Add the selected files as UploadFileRow to the NewExp Panel.
     * 
     * @param files
     *            [] for each to add
     */
    public void selectFilesToNewExp(File[] files);
    
    public void selectFilesToExistingExp(File[] files);
    
    public ArrayList<File> getFilesToUpload();
    
    public AnnotationDataValue[] getUploadAnnotations();
    
    public void setBowtieParameters();
    
    public JList getfileList();
    
    public String getNewExpName();
    
    public HashMap<String, String> getFilesToUploadTypes();
    
    public void updateQuerySearchResults(ArrayList<ExperimentData> searchResults);
    
    /**
     * Calls the uploadPanel's enableUploadButton method to try to either make
     * the upload button enabled or disabled. If all of the required annotation
     * fields are NOT filled, this method won't set it to true.
     * 
     * @param b
     *            Whether it should try to make the button enabled (true) or
     *            disabled (false).
     */
    public void enableUploadButton(boolean b);
    
    public String[] getRatioCalcParameters();
    
    /**
     * Deletes a file row.
     * 
     * @param f
     *            Used to identify which fileRow to be deleted.
     */
    public void deleteUploadFileRow(File f);
    
    public void addRatioCalcListener(ActionListener listener);
    
    public void setDefaultRatioPar();
    
    public void setUnusedRatioPar();
    
    public void showRatioPopup();
    
    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData);
    
    public void setOngoingUploads(
            CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads);
    
    public void setOngoingDownloads(
            CopyOnWriteArrayList<DownloadHandler> ongoingDownloads);
    
    public void addOkListener(ActionListener listener);
    
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
    
    public ActiveSearchPanel getActiveSearchPanel();
    
    public JButton getBackButton();
    
    /**
     * Remove and re-add each tab in the GUI. For now **ONLY TABS** are reset:
     * If this changes some other methods will need updating (logoutlistener)
     */
    public void resetGUI();
    
    public void changeTabInWorkspace(int tabIndex);
    
    public JTabbedPane getTabbedPane();
    
    public String getSelectedSpecies();
    
    public void addSpeciesSelectedListener(ActionListener listener);
    
    public void setGenomeReleases(GenomeReleaseData[] grd);
    
    public String getGenomeVersion(File f);
    
    public void addDeleteSelectedListener(ActionListener listener);
    
    public ArrayList<ExperimentData> getFileInfo();
    
    public void setFileInfo(ArrayList<ExperimentData> fileInfo);
    
    public void clearSearchSelection();
    
    public int getSelectedIndex();
    
    public void addChangedTabListener(ChangeListener listener);
    
    public void addAddToExistingExpButtonListenerInSearch(
            ActionListener listener);
    
    public void addUploadToListenerSearchTab(ActionListener listener);
}
