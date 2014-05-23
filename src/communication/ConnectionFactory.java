package communication;

import gui.LoginWindow;

/**
 * Created by c11dkn on 2014-05-16.
 */
public class ConnectionFactory {

    private String ip;
    private LoginWindow window;

    public ConnectionFactory() {

    }

    public Connection makeConnection() {
        Connection conn = new Connection(ip, window);
        return conn;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void setLoginWindow(LoginWindow window) {
        this.window = window;
    }

}
