package requests;
/**
 * This class represents a request to cancel a running process
 * @author JH
 *
 */
public class CancelProcessRequest extends Request {
    /**
     * Attributes needed to create the request.
     *
     */
    public String PID;
    /**
     * A constructor creating the request
     * @param PID
     *          Process-ID
     */
    public CancelProcessRequest(String PID) {
        super("cancelprocess","/process/cancelProcess" , "PUT");
        this.PID = PID;
    }

}
