package gui;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JList;

import gui.sysadmin.SysadminController;
import util.AnnotationDataType;
import util.AnnotationDataValue;
import util.ExperimentData;
import util.FileData;

public interface GenomizerView {

    public void addAnalyzeSelectedListener(ActionListener listener);

    public void addLoginListener(ActionListener listener);

    public void addSearchToWorkspaceListener(ActionListener listener);

    public void addLogoutListener(ActionListener listener);

    public void addSearchListener(ActionListener listener);

    public void addProcessFileListener(ActionListener listener);

    public void addDownloadFileListener(ActionListener listener);

    public void addConvertFileListener(ActionListener listener);

    public void addQuerySearchListener(ActionListener listener);

    public void addRawToProfileDataListener(ActionListener listener);

    public void addRawToRegionDataListener(ActionListener listener);

    public void addAddAnnotationListener(ActionListener addAnnotationListener);

    public void addAddPopupListener(ActionListener addPopupListener);

    public void addAddToExistingExpButtonListener(
	    ActionListener addToExistingExpButtonListener);

    public void addUploadToExperimentButtonListener(ActionListener listener);

    public void addSearchResultsDownloadListener(ActionListener listener);

    public void addSelectFilesToUploadButtonListener(ActionListener listener);

    public void addUpdateSearchAnnotationsListener(ActionListener listener);

    public void addNewExpButtonListener(ActionListener listener);

    public void addSelectButtonListener(ActionListener listener);

    public void addUploadButtonListener(ActionListener listener);

    public void addToWorkspace(ArrayList<ExperimentData> experiments);


    public ArrayList<String> getAllMarkedFiles();

    public String getPassword();

    public String getUsername();

    public JFrame getFrame();

    public void setDownloadWindow(DownloadWindow downloadWindow);

    public DownloadWindow getDownloadWindow();

	public String getQuerySearchString();

    public String getIp();

    public UploadTab getUploadTab();
	
    public int getSelectedRowAtAnnotationTable();


    public ArrayList<FileData> getAllMarkedFileData();

    public void updateLoginAccepted(String username, String pwd, String name);

    public void updateLoginNeglected(String errorMessage);
    

    public void updateLogout();

	public void updateQuerySearchResults(ExperimentData[] searchResults);
	
	public void setAnnotationTableData(AnnotationDataType[] annotations);

    public void setSearchAnnotationTypes(AnnotationDataType[] annotationTypes);

    public void setProccessFileList(ArrayList<FileData> arrayList);

	public void printToConvertText(String message);
	

    public void setSysadminController(SysadminController sysadminController);

	//public void addDeleteAnnotationListener(ActionListener listener);
    public ArrayList<ExperimentData> getSelectedDataInWorkspace();

    public void addDeleteAnnotationListener(ActionListener listener);

    public void createNewExp(AnnotationDataType[] annotations);

    public String[] getBowtieParameters();

    public void selectFilesToNewExp(String[] fileNames, File[] files);

    public void selectFilesToExistingExp(String[] fileNames, File[] files);

    public File[] getFilesToUpload();

    public AnnotationDataValue[] getUploadAnnotations();

	public void setBowtieParameters();

	public JList getfileList();

	public String getNewExpName();

	public HashMap<String, String> getFilesToUploadTypes();

	void updateQuerySearchResults(ArrayList<ExperimentData> searchResults);
}

