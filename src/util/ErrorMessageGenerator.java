package util;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.User;

public class ErrorMessageGenerator {

    public static String generateMessage(int responseCode) {

        String date = getCurrentTimeStamp();
        String message = "Error code: " + responseCode + "\n" +
                         "Time of occurrence: " + date + "\n" +
                         "User name: " + User.getInstance().getName() + "\n" +
                         "User token: " + User.getInstance().getToken();
        return message;
    }

    /* http://stackoverflow.com/questions/1459656/how-to-get-the-current-time-in-yyyy-mm-dd-hhmisec-millisecond-format-in-java */
    private static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

}
