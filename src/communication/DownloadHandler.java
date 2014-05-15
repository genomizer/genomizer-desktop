package communication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Christoffer on 2014-04-30.
 */
public class DownloadHandler {
    
    private HttpURLConnection conn;
    private String username;
    private String password;
    private String fileID;
    private boolean finished;
    private int totalDownload;
    private int perSecond;
    
    public DownloadHandler(String username, String password, String fileID) {
        this.username = username;
        this.password = password;
        this.fileID = fileID;
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
            conn = (HttpURLConnection) targetUrl.openConnection();
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
            totalDownload = 0;
            int previousDownload = 0;
            
            BufferedWriter fileOut = new BufferedWriter(new FileWriter(file));
            Long previousTime = System.currentTimeMillis();
            while ((buffer = in.readLine()) != null) {
                fileOut.write(buffer);
                totalDownload += buffer.length();
                fileOut.newLine();
                totalDownload += System.getProperty("line.separator").length();
                if (System.currentTimeMillis() - previousTime > 1000) {
                    previousTime = System.currentTimeMillis();
                    System.out.println("Downloaded " + totalDownload / 1024
                            / 1024 + "MiB");
                    perSecond = totalDownload - previousDownload;
                    previousDownload = totalDownload;
                    System.out.println(perSecond / 1024 / 1024 / 1 + "MiB/s");
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
        finished = true;
        return true;
    }
    
    public int getTotalSize() {
        if (conn != null) {
            return conn.getContentLength();
        }
        return -1;
    }
    
    public int getCurrentProgress() {
        return totalDownload;
    }
    
    public int getCurrentSpeed() {
        return perSecond;
    }
    
    public String getFileID() {
        return fileID;
    }
    
    public boolean isFinished() {
        return finished;
    }
    
}
