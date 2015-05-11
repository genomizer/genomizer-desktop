package communication;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import util.RequestException;

import model.ErrorLogger;

// TODO: Borde denna klass vara en runnable?
/**
 * Class for handling file downloads.
 *
 * Created by Christoffer on 2014-04-30.
 */
public class DownloadHandler {

    private HttpsURLConnection conn;
    private String userID;
    private String fileName;
    private boolean finished;
    private int totalDownload;
    private int perSecond;

    // TODO: userID = användarnamn? varför behövs den här?
    /**
     * Constructs a new DownloadHandler object with a given user ID and
     * filename.
     *
     * @param userID
     *            the user id
     * @param fileName
     *            the filename
     */
    public DownloadHandler(String userID, String fileName) {
        this.userID = userID;
        this.fileName = fileName;
    }

    // TODO: returvärdet används aldrig.
    /**
     * Downloads a file from a given URL, to a given local file path
     *
     * @param url
     *            the URL
     * @param localFilePath
     *            the local file path
     * @return true if successful, false if URL is bad or IO error occurs
     * @throws RequestException
     */
    public boolean download(String url, String localFilePath) {
        try {

            // TODO: JSON encoded '=' may need to be escaped (OO)
            url = url.replaceFirst("\\u003d", "=");

            URL targetUrl = new URL(url);

            conn = (HttpsURLConnection) targetUrl.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "text/plain");
            conn.setRequestProperty("Authorization", userID);

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                return false;
            }

            File file = new File(localFilePath);
            if (file.isFile()) {
                fileName = file.getName();
            }

            totalDownload = 0;

            /* If not "binary file", treat as text file and copy to local file */
            if (isBinaryFile()) {
                downloadBinaryFile(file);
            } else {
                downloadTextFile(file);
            }

            finished = true;
            conn.disconnect();

        } catch (MalformedURLException e) {
            ErrorLogger.log(e);
            return false;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            ErrorLogger.log(e);
            return false;
        }
        return true;
    }

    private void downloadTextFile(File file) throws IOException {
        int previousDownload = 0;
        Long previousTime = System.currentTimeMillis();
        InputStream in = conn.getInputStream();
        String buffer;
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in));
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        while ((buffer = reader.readLine()) != null && !isFinished()) {
            writer.write(buffer);
            totalDownload += buffer.length();
            writer.newLine();
            totalDownload += System.getProperty("line.separator")
                    .length();
            if (System.currentTimeMillis() - previousTime > 1000) {
                previousTime = System.currentTimeMillis();
                perSecond = totalDownload - previousDownload;
                previousDownload = totalDownload;
            }
        }
        writer.close();
        reader.close();
        in.close();
    }

    private void downloadBinaryFile(File file) throws IOException,
            FileNotFoundException {
        InputStream in = conn.getInputStream();
        FileOutputStream fileOut = new FileOutputStream(file);
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
        fileOut.close();
        in.close();
    }

    /**
     * Returns the total size of the file
     *
     * @return the total size, or -1 if no file is being downloaded
     */
    public int getTotalSize() {
        if (conn != null) {
            return conn.getContentLength();
        }
        return -1;
    }

    // TODO: Fixa namnet? borde vara private (används bara i denna klass)?
    /**
     * Checks if the requested file to download is "binary"
     *
     * @return true if filename ends with .jpg, .png, .jpeg, .gif, .tar.gz, or
     *         .tar.xz, false otherwise.
     */
    public boolean isBinaryFile() {
        return fileName.endsWith(".jpg") || fileName.endsWith(".png")
                || fileName.endsWith(".jpeg") || fileName.endsWith(".gif")
                || fileName.endsWith(".tar.gz") || fileName.endsWith(".tar.xz");
    }

    /**
     * Return the download progress
     *
     * @return the download progress
     */
    public int getCurrentProgress() {
        return totalDownload;
    }

    /**
     * Returns the download speed in bytes per second?
     *
     * @return the download speed in bytes per second
     */
    public int getCurrentSpeed() {
        return perSecond;
    }

    /**
     * Returns the filename of the file being downloaded
     *
     * @return the filename
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Checks if the handler is finished downloading
     *
     * @return true if finished, otherwise false
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets the download finished status
     *
     * @param isFinished
     */
    public void setFinished(boolean isFinished) {
        finished = isFinished;
    }

}
