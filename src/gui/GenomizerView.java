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
 * make their daily job easier.<br>
 * ahaha, what does that even mean<br>
 * how could an interface even help the client?
 *
 * @author
 */

// TODO: Huge uncommented interface... Seems to fill literally no purpose
// whatsoever. OO

public interface GenomizerView {
    /**
     * Set a new statusmessage
     *
     * @param status
     *            New status
     * @author JH
     */
    public void setStatusPanel(String status); // used~ish, can't be delegated

    /**
     * Method adding a listener to the analyze selected button.
     *
     * @param listener
     *            The listener
     */

    public void addUploadToListener(ActionListener listener); // unused commented

    public void refreshSearch(); // unused commented

    LoginWindow getLoginWindow(); // used N/A

    public void addLoginListener(ActionListener listener); // unused commented

    public void addSearchToWorkspaceListener(ActionListener listener); // unused commented

    public void addLogoutListener(ActionListener listener); // used N/A

    public void addSearchListener(ActionListener listener); // unimplemented unused N/A

    public void addProcessFileListener(ActionListener listener); // unused commented

    public void addDownloadFileListener(ActionListener listener); // unused commented

    public void addQuerySearchListener(ActionListener listener); // unused commented

    public void addRawToProfileDataListener(ActionListener listener); // unused good enough

    public void addAddToExistingExpButtonListener(
            ActionListener addToExistingExpButtonListener); // unused commented

    public void addUploadToExperimentButtonListener(ActionListener listener); // unused commented

    // TODO unimplemented listener-assigning method
    public void addSearchResultsDownloadListener(ActionListener listener); // unused N/A? sort of

    public void addSelectFilesToUploadButtonListener(ActionListener listener); // unused commented

    public void addUpdateSearchAnnotationsListener(ActionListener listener); // unused commented

    public void addNewExpButtonListener(ActionListener listener); // unused commented

    public void addSelectButtonListener(ActionListener listener); // unused commented

    public void addDeleteFromDatabaseListener(ActionListener listener); // unused commented

    public void addProcessFeedbackListener(ActionListener listener); // unused commented

    public void addUploadButtonListener(ActionListener listener); // unused commented

    public void addToWorkspace(ArrayList<ExperimentData> experiments); // unused commented

    public ArrayList<FileData> getAllMarkedFiles(); // unused

    public String getPassword(); // unused commented-collision

    public String getUsername(); // unused commented-collision

    public JFrame getFrame(); //used commented

    public void setDownloadWindow(DownloadWindow downloadWindow); // used commented

    public DownloadWindow getDownloadWindow(); // used commented

    public String getQuerySearchString(); // unused commented

    public String getIp(); // unused commented-collision

    public ArrayList<ExperimentData> getSelectedDataInSearch(); // unused commented

    public UploadTab getUploadTab(); // used commented

    public QuerySearchTab getQuerySearchTab(); // used commented

    public SysadminTab getSysAdminTab(); // used commented

    public WorkspaceTab getWorkSpaceTab(); // used commented

    public ProcessTab getProcessTab(); // used commented

    //TODO unimplemented getter of...?
    public int getSelectedRowAtAnnotationTable(); // unused N/A

    public void updateLoginAccepted(String username, String pwd, String name); // used commented

    public void updateLoginNeglected(String errorMessage); // unused commented

    public void updateLogout(); // used commented

    public void setSearchAnnotationTypes(AnnotationDataType[] annotationTypes); // unused commented

    public void setProcessFileList(ArrayList<FileData> arrayList); // used TODO unfinished

    public void printToConsole(String message); // unused commented

    public void setSysadminController(SysadminController sysadminController); // unused commented

    public ArrayList<ExperimentData> getSelectedDataInWorkspace(); // unused commented-collision

    public ArrayList<ExperimentData> getSelectedExperimentsInWorkspace(); // unused commented

    /**
     * Creates a new experiment to upload to using the provided annotations.
     *
     * @param annotations
     *            The annotations of the new experiment.
     */
    public void createNewExp(AnnotationDataType[] annotations); // unused commented-collision

    public String[] getParameters(); // unused commented

    /**
     * Add the selected files as UploadFileRow to the NewExp Panel.
     *
     * @param files
     *            [] for each to add
     */
    public void selectFilesToNewExp(File[] files); // unused commented-collision

    public void selectFilesToExistingExp(File[] files); // unused commented

    public ArrayList<File> getFilesToUpload(); // unused commented

    public AnnotationDataValue[] getUploadAnnotations(); // unused commented

    public void setBowtieParameters(); // unused commented

    public JList getfileList(); // unused commented~

    public String getNewExpName(); // unused commented

    public HashMap<String, String> getFilesToUploadTypes(); // unused commented

    public void updateQuerySearchResults(ArrayList<ExperimentData> searchResults); // unused commented

    /**
     * Calls the uploadPanel's enableUploadButton method to try to either make
     * the upload button enabled or disabled. If all of the required annotation
     * fields are NOT filled, this method won't set it to true.
     *
     * @param b
     *            Whether it should try to make the button enabled (true) or
     *            disabled (false).
     */
    public void enableUploadButton(boolean b); // unused commented-collision

    public String[] getRatioCalcParameters(); // unused commented

    /**
     * Deletes a file row.
     *
     * @param f
     *            Used to identify which fileRow to be deleted.
     */
    public void deleteUploadFileRow(File f); // unused commented-collision

    public void addRatioCalcListener(ActionListener listener); // unused commented

    public void setDefaultRatioPar(); // unused not commented

    public void setUnusedRatioPar(); // unused not commented

    public void showRatioPopup(); // unused commented

    public void showProcessFeedback(ProcessFeedbackData[] processFeedbackData); // unused commented~

    public void setOngoingUploads(
            CopyOnWriteArrayList<HTTPURLUpload> ongoingUploads); // unused commented

    public void setOngoingDownloads(
            CopyOnWriteArrayList<DownloadHandler> ongoingDownloads); // unused commented

    public void addOkListener(ActionListener listener); // unused commented

    public RatioCalcPopup getRatioCalcPopup(); // used commented

    public void setGenomeFileList(GenomeReleaseData[] genome); // unused commented

    public ArrayList<File> getSelectedFilesToUpload(); // unused commented~

    public void addUploadSelectedFilesListener(ActionListener listener); // unused commented

    public void removeUploadExpName(); // unused

    public void removeSelectedFromWorkspace(); // unused

    public void disableSelectedRow(File f); // used? HOW TODO why is this now used?, and doesn't do anything,
                                            // at all

    public boolean isCorrectToProcess(); // used not commented

    public boolean isRatioCorrectToProcess(); // used not commented

    public void setProfileButton(boolean bool); // unused doesn't do anything

    public boolean useRatio(); // unused commented

    public ActiveSearchPanel getActiveSearchPanel(); // unused not commented

    public JButton getBackButton(); // used commented

    /**
     * Remove and re-add each tab in the GUI. For now **ONLY TABS** are reset:
     * If this changes some other methods will need updating (logoutlistener)
     */
    public void resetGUI(); // used commented

    public void changeTabInWorkspace(int tabIndex); // unused commented

    public JTabbedPane getTabbedPane(); // used commented~

    public String getSelectedSpecies(); // unused commented

    public void addSpeciesSelectedListener(ActionListener listener); // unused commented

    public void setGenomeReleases(GenomeReleaseData[] grd); // unused not commented

    public String getGenomeVersion(File f); // unused not commented

    public void addDeleteSelectedListener(ActionListener listener); // unused commented

    public ArrayList<ExperimentData> getFileInfo(); // unused commented~

    public void setFileInfo(ArrayList<ExperimentData> fileInfo); // unused commented~

    public void clearSearchSelection(); // unused not commented

    public int getSelectedIndex(); // unused commented~

    public void addChangedTabListener(ChangeListener listener); // unused N/A

    public void addAddToExistingExpButtonListenerInSearch(
            ActionListener listener); // unused commented

    public void addUploadToListenerSearchTab(ActionListener listener); // unused commented

    public void addConvertFileListener(ActionListener listener); //unused N/A

    boolean getIsNewExp(); //unused commented
}
