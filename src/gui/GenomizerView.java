package gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import util.ExperimentData;

public interface GenomizerView {

	public void addLoginListener(ActionListener listener);

	public void addLogoutListener(ActionListener listener);

	public void addSearchListener(ActionListener listener);

	public void addUploadFileListener(ActionListener listener);

	public void addDownloadFileListener(ActionListener listener);

	public void addBrowseListener(ActionListener listener);

	public void addConvertFileListener(ActionListener listener);

	public ArrayList<String> getAllMarkedFiles();

	public void addQuerySearchListener(ActionListener listener);

	public String getPassword();

	public String getUsername();

	public void updateLoginAccepted(String username, String pwd, String name);

	public void updateLoginNeglected(String errorMessage);

	public void updateLogout();

	public JFrame getFrame();

	public void updateFileChosen(String directoryAsString);

	public void updateQuerySearchResults(ExperimentData[] searchResults);

	public String getQuerySearchString();

	public void addScheduleFileListener(ActionListener listener);

	public void addRawToProfileDataListener(ActionListener listener);

	public String getIp();

	public void addRawToRegionDataListener(ActionListener listener);

	public void addAddAnnotationListener(ActionListener addAnnotationListener);

	public void addAddPopupListener(ActionListener addPopupListener);

    public void addAddToExistingExpButtonListener(ActionListener addToExistingExpButtonListener);

    public void addSelectFilesToUploadButtonListener(ActionListener listener);

    public void addUploadToExperimentButtonListener(ActionListener listener);

	public void annotationPopup();

    public UploadTab getUploadTab();

	public String getNewAnnotationName();

	public String[] getNewAnnotionCategories();

	public boolean getNewAnnotationForcedValue();

	public void closePopup();

}
