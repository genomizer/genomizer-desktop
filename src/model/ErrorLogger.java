package model;
    import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import responses.Response;

    public class ErrorLogger {


        private static String logFile = System.getProperty("user.dir") + "/errorLog.txt";
        private static HashMap<String, ArrayList<Response>> usermap = new HashMap<String,ArrayList<Response>>();


        public ErrorLogger(){

        }

        /**
         * Log an error
         * @param tag - Enter class where error is triggered
         * @param logText - Explaining text about error
         */
        public static void log(String tag, String logText){

            File file = new File(logFile);
            String timeString = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(System.currentTimeMillis()));
            String text = timeString + " : " + tag + " | " + logText;
            try{

                file.createNewFile();

                BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true));
                out.write(text + "\n\n");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

        /**
         * Log an error using caller class and method as tag
         * @param logText
         */
        public static void log(String logText){
            String calClass = new Exception().getStackTrace()[1].getClassName();
            String calMethod = new Exception().getStackTrace()[1].getMethodName()+"(l:"+
                    new Exception().getStackTrace()[1].getLineNumber()+")";
            String tag = calClass+ ":" +calMethod;
            log(tag,logText);
        }

        /**
         * Log a exception
         * @param tag - Enter class where error is triggered
         * @param exc - Exception catched
         */
        public static void log(String tag, Throwable exc) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            exc.printStackTrace(pw);
            String excString = sw.toString();
            log(tag,excString);
            //Vet inte varför de ville logga rad för rad, formateringen? CF
//            String[] excLines = excString.split("\n");
//            for (String line : excLines) {
//                log(tag, line);
//                tag = "      ";
//            }
        }

        /**
         * log a exception using caller class and method as tag
         * @param exc
         */
        public static void log(Throwable exc){
            String calClass = new Exception().getStackTrace()[1].getClassName();
            String calMethod = new Exception().getStackTrace()[1].getMethodName()+"(l:"+
                    new Exception().getStackTrace()[1].getLineNumber()+")";
            String tag = calClass+ ":" +calMethod;
            log(tag,exc);
        }
    /*
        public static void log(String username, Response response){
            System.err.println("LOGFILEDIR: " + logFile);
            File file = new File(logFile);

            try{
                file.createNewFile();
                BufferedWriter out = new BufferedWriter(new FileWriter(logFile, true));
                out.write(response.toString() + "\n");
                out.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    */
        /**
         * Method for logging a response for a specific user.
         *
         * @param username - The username of the user calling the process.
         * @param response - The response to be logged.
         * @return Returns true if the
         *//*
        public static void log(String username, Response response) {

            if(!usermap.containsKey(username)){
                System.err.println("usermap did not contain: " + username);
                usermap.put(username, new ArrayList<Response>());
            }

            ArrayList<Response> tmp = usermap.get(username);
            tmp.add(response);
            System.err.println("put: " + username + ", tmpsize:" + tmp.size());
            usermap.put(username,tmp);


        }
          */

        /**
         * Method for getting a specific users log.
         *
         * @param username - The username of the user which log is to be received
         * @return Returns an ArrayList<Response>  or null if the user has no log.
         */
        public static ArrayList<Response> getUserLog(String username) {
            return usermap.get(username);
        }

        /**
         * Method for printing a specific users log.
         *
         * @param username - The username of the user which log is to be printed
         */
        public static void printUserLog(String username){
            System.err.println("printuserlog:" + username);
            if(usermap.containsKey(username)){
                System.err.println("---Printing " + username + " log---");

                int i = 0;
                for(Response r : usermap.get(username)){
                    System.err.print("Log " + i + ": " + r);
                    i++;
                }

            }else{
                System.err.println("User " + username + " does not exist in log");
            }

        }

        /**
         * Method for printing the complete log.
         */
        public static void printLog(){
            System.err.println("---Printing log--");
            for(String user : usermap.keySet()){
                System.err.println("User: " + user);
                for(Response r : usermap.get(user)){
                    System.err.println(r);
                }
            }
        }

        /**
         * Method for resetting the log.
         */
        public static void reset() {
            usermap.clear();

        }
}
