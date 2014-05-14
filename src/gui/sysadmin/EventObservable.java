package gui.sysadmin;

import java.util.Observable;

public class EventObservable extends Observable {
    
    private String message;
    
    public String getMessage() {
        return message;
    }
    
    public void changeMessage(String message) {
        this.message = message;
        setChanged();
        notifyObservers(message);
    }
    
}
