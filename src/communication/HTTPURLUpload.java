package communication;

import java.io.*;
import java.net.URLEncoder;

import model.ErrorLogger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

// TODO: byt till vettigt namn.
// TODO: not thread safe?
/**
 * Class for handling file uploads.
 *
 */
public class HTTPURLUpload {

    private String filePath;
    private String uploadPath;
    private String fileName;
    private float currentProgress;
    private int responseCode;

    /**
     * Constructs a new HTTPURLUpload object
     *
     * @param uploadPath
     *            the target location
     * @param filePath
     *            the source location
     * @param fileName
     *            the filename
     */
    public HTTPURLUpload(String uploadPath, String filePath, String fileName) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadPath = uploadPath;

    }

    /**
     * Uploads a file to
     *
     * @param userID
     * @return
     * @throws IllegalArgumentException
     */
    public boolean sendFile(String userID) throws IllegalArgumentException {
        uploadPath = uploadPath.replaceFirst("\\u003d", "=");
        String path;
        if (uploadPath.contains("=")) {
            path = uploadPath.split("=")[1];
        } else {
            path = uploadPath;
        }

        // new HttpClient
        HttpClientBuilder hcBuilder = HttpClients.custom();

        CloseableHttpClient httpClient = hcBuilder.build();

        // Authentication information
        /*
         * CredentialsProvider credentialsProvider = new
         * BasicCredentialsProvider();
         * credentialsProvider.setCredentials(AuthScope.ANY, new
         * UsernamePasswordCredentials(username + ":" + password));
         */
        HttpClientContext localContext = HttpClientContext.create();
        // localContext.setCredentialsProvider(credentialsProvider);

        // post header
        File file = new File(filePath);
        try {
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            uploadPath = uploadPath.replaceFirst(file.getName(),
                    encodedFileName);
        } catch (UnsupportedEncodingException e) {
            ErrorLogger.log(e);
            e.printStackTrace();
        }


        HttpPost httpPost = new HttpPost(uploadPath);

        httpPost.addHeader("Authorization", userID);
        // HttpPost httpPost = new HttpPost(filePath);

        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        // add the location on the server where the file should be saved
        reqEntity.addTextBody("path", path);

        reqEntity.addBinaryBody("uploadfile", file);
        ProgressHttpEntityWrapper.ProgressCallback progressCallback = new ProgressHttpEntityWrapper.ProgressCallback() {

            @Override
            public void progress(float progress) {
                if (progress != -1) {
                    currentProgress = progress;
                }
            }

        };
        httpPost.setEntity(new ProgressHttpEntityWrapper(reqEntity.build(),
                progressCallback));

        try {
            HttpResponse response;
            // execute HTTP post request
            //System.out.println(httpPost.toString());
            response = httpClient.execute(httpPost, localContext);
            HttpEntity resEntity = response.getEntity();
            responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != 200) {
                return false;
            }
            if (resEntity != null) {

                // TODO: Anv�nds ej
                String responseStr = EntityUtils.toString(resEntity).trim();
            }
        } catch (ClientProtocolException e) {
            ErrorLogger.log(e);
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            ErrorLogger.log(e);
            return false;
        } catch (IOException e) {
            ErrorLogger.log(e);
            e.printStackTrace();
        }
        return true;
    }

    // TODO: Anv�nds ej.
    private String getFileNameFromUrl(String url) {
        String[] urlSplit = url.split("/");
        return urlSplit[urlSplit.length - 1];

    }

    // TODO: vad i helvete? ta bort?
    public static void main(String args[]) {
        HTTPURLUpload upload = new HTTPURLUpload(
                "http://scratchy.cs.umu.se:8000/upload.php?path=/var/www/test_hack.php",
                "/home/dv12/dv12csr/test_hack.php", "test_hack.php");
        upload.sendFile("test");
    }

    /**
     * Returns the filename of the file to upload
     *
     * @return the filename
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Return the current progress of the file upload
     *
     * @return the current progress
     */
    public float getCurrentProgress() {
        return currentProgress;
    }

    /**
     * Returns the response code of the upload request
     *
     * @return the response code of the upload request
     */
    public int getResponseCode() {
        return responseCode;
    }
}
