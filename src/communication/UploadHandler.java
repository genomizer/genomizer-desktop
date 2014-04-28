package communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadHandler implements Runnable {
    private String url;
    private String fileName;
    private String userID;

    public UploadHandler(String url, String fileName, String userID) {
        this.url = url;
        this.fileName = fileName;
        this.userID = userID;
    }

    @Override
    public void run() {
        try {
            URL targetUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)
                    targetUrl.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestProperty("Authorization", userID);
            PrintWriter outputStream = new PrintWriter(
                    connection.getOutputStream(), true);
            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(
                    new FileReader(file));
            while (reader.ready()) {
                outputStream.println(reader.readLine());
            }
            int responseCode;
            if ((responseCode = connection.getResponseCode()) != 200) {
                System.out.println("Error wrong response code: "
                        + responseCode);
            }
            connection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Connection refused");
        }

    }
}
