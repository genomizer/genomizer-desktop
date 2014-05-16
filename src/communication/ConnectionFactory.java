package communication;

/**
 * Created by c11dkn on 2014-05-16.
 */
public class ConnectionFactory {

    private String ip;

    public ConnectionFactory() {

    }

    public Connection getNewConnection() {
        Connection conn = new Connection();
        conn.setIp(ip);
        return conn;
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

}
