package communication;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Christoffer on 2014-04-30.
 */
public class DownloadHandler {

    private String username;
    private String password;

    public DownloadHandler(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean download(String url, String localFilePath) {
        try {
            // Use this url in the real version. vvv
            /*
             * URL targetUrl = new URL(
             * "http://scratchy.cs.umu.se:8090/html/download.php?path=" + url);
             */
            url = url.replaceFirst("\\u003d", "=");
            url = url.replaceFirst("scratcy", "scratchy");
            url = url.replaceFirst("8090", "8000");

            URL targetUrl = new URL(url);
            String authString = username + ":" + password;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);
            HttpURLConnection conn = (HttpURLConnection) targetUrl
                    .openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            int responseCode;
            responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                System.out
                        .println("Error wrong response code: " + responseCode);
                return false;
            }
            System.out.println(responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            File file = new File(localFilePath);
            String buffer;
            int totalDownload = 0;
            BufferedWriter fileOut = new BufferedWriter(new FileWriter(file));
            Long previousTime = System.currentTimeMillis();
            while ((buffer = in.readLine()) != null) {
                fileOut.write(buffer);
                totalDownload += buffer.length();
                fileOut.newLine();
                totalDownload += System.getProperty("line.separator")
                        .length();
                if (System.currentTimeMillis() - previousTime > 5000) {
                    previousTime = System.currentTimeMillis();
                    System.out.println(
                            "Downloaded " + totalDownload / 1024 / 1024
                                    + "MiB");
                    System.out
                            .println(totalDownload / 1024 / 1024 / 5 + "MiB/s");
                }
            }
            fileOut.close();
            System.out.println("Size: " + totalDownload + " Expected: "
                    + conn.getContentLength());
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
