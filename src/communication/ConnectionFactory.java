package communication;


// TODO: Helt on�dig och fel. F�ljer inte pattern. Ta bort?
// ehh, test
/**
 *
 * Created by c11dkn on 2014-05-16.
 */
public class ConnectionFactory {

    private String ip;

    public ConnectionFactory() {

    }

    public Connection makeConnection() {
        return new Connection(ip);
    }

    public void setIP(String ip) {
        this.ip = ip;
    }

    public String getIP() {
        return ip;
    }

}
