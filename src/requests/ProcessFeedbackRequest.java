package requests;

/**
 * Created by c11dkn on 2014-05-16.
 */
public class ProcessFeedbackRequest extends Request {
    public ProcessFeedbackRequest() {
        super("processfeedback", "/process/dummy", "GET");
    }
}
