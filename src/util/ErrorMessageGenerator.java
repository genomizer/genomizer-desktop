package util;

import java.text.SimpleDateFormat;
import java.util.Date;
import model.User;

/**
 * A class for generating error messages based on a HTML response code.
 *
 * @author oi12mlw
 *
 */
public class ErrorMessageGenerator {

    /**
     * Generates an error message string based on a given HTML response code.
     * The string will contain information about the time this message was
     * created and under which user token the message was created.
     *
     * @param responseCode
     *            the HTML response code
     * @return the error message string
     */
    public static String generateMessage(int responseCode) {
        String date = getCurrentTimeStamp();
        String message = "Error code: " + responseCode + "\n"
                + "Time of occurrence: " + date + "\n" + "User name: "
                + User.getInstance().getName() + "\n" + "User token: "
                + User.getInstance().getToken();
        return message;
    }

    /**
     * Returns a string representing the current time and date. Code from:
     * http:/
     * /stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-
     * yyyy-mm-dd-hhmisec-millisecond-format-in-java
     */
    private static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}
