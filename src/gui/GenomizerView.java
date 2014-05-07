package gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import util.AnnotationDataType;
import util.ExperimentData;
import util.FileData;

public interface GenomizerView {

	public void addLoginListener(ActionListener listener);

    public FileData[] getSelectedFilesInSearch();

	public void addLogoutListener(ActionListener listener);

	public void addSearchListener(ActionListener listener);

	public void addDownloadFileListener(ActionListener listener);

	public void addConvertFileListener(ActionListener listener);

	public ArrayList<String> getAllMarkedFiles();

	public void addQuerySearchListener(ActionListener listener);

	public String getPassword();

	public String getUsername();

	public void updateLoginAccepted(String username, String pwd, String name);

	public void updateLoginNeglected(String errorMessage);

	public void updateLogout();

	public JFrame getFrame();

	public void updateQuerySearchResults(ExperimentData[] searchResults);

	public String getQuerySearchString();

	public void addScheduleFileListener(ActionListener listener);

	public void addRawToProfileDataListener(ActionListener listener);

	public String getIp();

	public void addRawToRegionDataListener(ActionListener listener);

	public void addAddAnnotationListener(ActionListener addAnnotationListener);

	public void addAddPopupListener(ActionListener addPopupListener);

    public void addAddToExistingExpButtonListener(
    		ActionListener addToExistingExpButtonListener);

    public void addUploadToExperimentButtonListener(ActionListener listener);

    public void addSearchResultsDownloadListener(ActionListener listener);

    public void addSelectFilesToUploadButtonListener(ActionListener listener);

    public void addUpdateSearchAnnotationsListener(ActionListener listener);

    public UploadTab getUploadTab();

	public void annotationPopup();

	public String getNewAnnotationName();

	public String[] getNewAnnotionCategories();

	public boolean getNewAnnotationForcedValue();

	public void closePopup();

	public void setAnnotationTableData(AnnotationDataType[] annotations);

    public void setSearchAnnotationTypes(AnnotationDataType[] annotationTypes);

    int getSelectedRowAtAnnotationTable();

    public AnnotationDataType getSelectedAnnoationAtAnnotationTable();

    public ArrayList<FileData> getAllMarkedFileData();

    public void addProcessFileListener(ActionListener listener);

	public void setProccessFileList();

}
