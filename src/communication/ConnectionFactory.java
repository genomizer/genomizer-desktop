package communication;

/**
 * Created by c11dkn on 2014-05-16.
 */
public class ConnectionFactory {

    private String ip;

    public ConnectionFactory() {

    }

    public Connection makeConnection() {
        Connection conn = new Connection(ip);
        return conn;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

}
