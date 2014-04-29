package gui;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JFrame;

public interface GenomizerView {

    public void addLoginListener(ActionListener listener);

    public void addLogoutListener(ActionListener listener);

    public void addSearchListener(ActionListener listener);

    public void addUploadFileListener(ActionListener listener);

    public void addDownloadFileListener(ActionListener listener);

    public void addBrowseListener(ActionListener listener);

	public void addConvertFileListener(ActionListener listener);

	public ArrayList<JCheckBox> getAllMarkedFiles();

    public String getPassword();

    public String getUsername();

    public void updateLoginAccepted(String username, String pwd, String name);

    public void updateLoginNeglected(String errorMessage);

    public void updateLogout();

    public JFrame getFrame();

	public void updateFileChosen(String directoryAsString);


}
