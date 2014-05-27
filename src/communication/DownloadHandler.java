package communication;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Christoffer on 2014-04-30.
 */
public class DownloadHandler {

    private HttpURLConnection conn;
    private String userID;
    private String fileName;
    private boolean finished;
    private int totalDownload;
    private int perSecond;

    public DownloadHandler(String userID, String fileName) {
        this.userID = userID;
        this.fileName = fileName;
    }

    public boolean download(String url, String localFilePath) {
        try {
            File file = new File(localFilePath);
            if (file.isFile()) {
                fileName = file.getName();
            }
            url = url.replaceFirst("\\u003d", "=");

            URL targetUrl = new URL(url);
            /*String authString = userID;
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);*/
            conn = (HttpURLConnection) targetUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Authorization", userID);
            int responseCode;
            responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return false;
            }
            InputStream in = conn.getInputStream();
            String buffer;
            totalDownload = 0;
            int previousDownload = 0;

            FileOutputStream fileOut = new FileOutputStream(file);
            Long previousTime = System.currentTimeMillis();
            if (!isBinaryFile()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                while ((buffer = reader.readLine()) != null && !isFinished()) {
                    writer.write(buffer);
                    totalDownload += buffer.length();
                    writer.newLine();
                    totalDownload += System.getProperty("line.separator").length();
                    if (System.currentTimeMillis() - previousTime > 1000) {
                        previousTime = System.currentTimeMillis();
                        perSecond = totalDownload - previousDownload;
                        previousDownload = totalDownload;
                    }
                }
                writer.close();
                reader.close();
            } else {
                byte[] buff = new byte[conn.getContentLength() + 1];
                int count = in.read(buff, 0, conn.getContentLength());
                totalDownload += count;
                fileOut.write(buff, 0, count);
                while ((count = in.read(buff)) != -1 && !isFinished()) {
                    totalDownload += count;
                    if (count > 0) {
                        fileOut.write(buff, 0, count);
                    }
                }
            }
            in.close();
            fileOut.close();
            finished = true;
            conn.disconnect();

        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public int getTotalSize() {
        if (conn != null) {
            return conn.getContentLength();
        }
        return -1;
    }

    public boolean isBinaryFile() {
        return fileName.endsWith(".jpg") || fileName.endsWith(".png") || fileName.endsWith(".jpeg") ||
                fileName.endsWith(".gif") || fileName.endsWith(".tar.gz") ||
                fileName.endsWith(".tar.xz");
    }

    public int getCurrentProgress() {
        return totalDownload;
    }

    public int getCurrentSpeed() {
        return perSecond;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean isFinished) {
        finished = isFinished;
    }

}
