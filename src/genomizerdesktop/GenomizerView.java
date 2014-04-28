package genomizerdesktop;

import java.awt.event.ActionListener;

public interface GenomizerView {

    public void addLoginListener(ActionListener listener);

    public void addLogoutListener(ActionListener listener);

    public void addSearchListener(ActionListener listener);

    public void addUploadFileListener(ActionListener listener);

    public void addDownloadFileListener(ActionListener listener);

    public String getPassword();

    public String getUsername();

    public void updateLoginAccepted(String username, String pwd);

    public void updateLoginNeglected(String username, String pwd);

}
