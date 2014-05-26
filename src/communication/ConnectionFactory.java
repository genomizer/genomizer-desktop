package communication;

import gui.GenomizerView;

/**
 * Created by c11dkn on 2014-05-16.
 */
public class ConnectionFactory {

    private String ip;
    private GenomizerView view;
    public ConnectionFactory() {

    }

    public Connection makeConnection() {
        Connection conn = new Connection(ip, view);
        return conn;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public void setGenomizerView(GenomizerView view) {
        this.view = view;
    }

}
