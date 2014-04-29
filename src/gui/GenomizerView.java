package gui;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

public interface GenomizerView {

    public void addLoginListener(ActionListener listener);

    public void addLogoutListener(ActionListener listener);

    public void addSearchListener(ActionListener listener);

    public void addUploadFileListener(ActionListener listener);

    public void addDownloadFileListener(ActionListener listener);

    public void addBrowseListener(ActionListener listener);

    public String getPassword();

    public String getUsername();

    public void updateLoginAccepted(String username, String pwd, String name);

    public void updateLoginNeglected(String errorMessage);

    public void updateLogout();

    public JFrame getFrame();

	public void updateFileChosen(String directoryAsString);

}
