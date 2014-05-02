package gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

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

	public void updateQuerySearchResults(
			ArrayList<HashMap<String, String>> searchResults);

	public String getQuerySearchString();

	public void addScheduleFileListener(ActionListener listener);

	public void addRawToProfileDataListener(ActionListener listener);

	public String getIp();

	public void addRawToRegionDataListener(ActionListener listener);

	public void addAddAnnotationListener(ActionListener addAnnotationListener);

	public void addAddPopupListener(ActionListener addPopupListener);

	public void annotationPopup();

	public String getNewAnnotationName();

	public String[] getNewAnnotionCategories();

	public boolean getNewAnnotationForcedValue();

}
