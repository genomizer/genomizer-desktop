package communication;

import gui.GUI;

// TODO: Helt on�dig och fel. F�ljer inte pattern. Ta bort?
// ehh, test
/**
 * 
 * Created by c11dkn on 2014-05-16.
 */
public class ConnectionFactory {
    
    private String ip;
    private GUI view;
    
    public ConnectionFactory() {
        
    }
    
    public Connection makeConnection() {
        return new Connection(ip, view);
    }
    
    public void setIP(String ip) {
        this.ip = ip;
    }
    
    public void setGUI(GUI view) {
        this.view = (GUI) view;
    }
    
    public String getIP() {
        return ip;
    }
    
}
