package gui.sysadmin.processview;

/**
 * Created by dv12ilr on 2014-05-13.
 */
public class ProcessesQueueItem {

    private String user;
    private String fromFile;
    private String toFile;
    private String status;
    private String convertType;
    private long estTimeLeft;

    private int queuePriority;
    private int processPriority;

    public ProcessesQueueItem() {
        
        /** EXAMPLE DATA ONLY */
        user = "Isak";
        fromFile = "insectFly.WIG";
        toFile = "insectFly.profile";
        status = "Processing";
    }

    public String getUser() {
        return user;
    }

    public String getFromFile() {
        return fromFile;
    }

    public String getToFile() {
        return toFile;
    }

    public String getStatus() {
        return status;
    }

    public String getConvertType() {
        return convertType;
    }

    public long getEstTimeLeft() {
        return estTimeLeft;
    }

    public int getQueuePriority() {
        return queuePriority;
    }

    public int getProcessPriority() {
        return processPriority;
    }
}
