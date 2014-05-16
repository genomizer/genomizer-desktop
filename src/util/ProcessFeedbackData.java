package util;

/**
 * @author 1337H4xx0r
 * Created by c11dkn on 2014-05-16.
 */
public class ProcessFeedbackData {

    public String experimentName;
    public String status;
    public int timeAdded;
    public int timeStarted;
    public int timeFinished;

    public ProcessFeedbackData(String experimentName, String status, int timeAdded, int timeFinished, int timeStarted) {
        this.experimentName = experimentName;
        this.status = status;
        this.timeAdded = timeAdded;
        this.timeFinished = timeFinished;
        this.timeStarted = timeStarted;
    }
}
