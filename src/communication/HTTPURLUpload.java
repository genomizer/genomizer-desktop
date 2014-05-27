package communication;

import java.io.*;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HTTPURLUpload {

    private String filePath;
    private String uploadPath;
    private String fileName;
    private float currentProgress;
    private int responseCode;

    public HTTPURLUpload(String uploadPath, String filePath, String fileName) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadPath = uploadPath;
    }

    public boolean sendFile(String userID) {
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
        /*CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username + ":" + password));*/
        HttpClientContext localContext = HttpClientContext.create();
        //localContext.setCredentialsProvider(credentialsProvider);

        // post header
        File file = new File(filePath);
        try {
            String encodedFileName = URLEncoder.encode(file.getName(), "UTF-8");
            uploadPath = uploadPath.replaceFirst(file.getName(), encodedFileName);
        } catch (UnsupportedEncodingException e) {
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
        httpPost.setEntity(new ProgressHttpEntityWrapper(reqEntity.build(), progressCallback));

        try {
            HttpResponse response;
            // execute HTTP post request
            response = httpClient.execute(httpPost, localContext);
            HttpEntity resEntity = response.getEntity();
            responseCode = response.getStatusLine().getStatusCode();
            if (responseCode != 201) {
                return false;
            }
            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private String getFileNameFromUrl(String url) {
        String[] urlSplit = url.split("/");
        String fileName = urlSplit[urlSplit.length - 1];
        return fileName;

    }

    public static void main(String args[]) {
        HTTPURLUpload upload = new HTTPURLUpload(
                "http://scratchy.cs.umu.se:8000/upload.php?path=/var/www/test_hack.php",
                "/home/dv12/dv12csr/test_hack.php", "test_hack.php");
        upload.sendFile("test");
    }

    public String getFileName() {
        return fileName;
    }

    public float getCurrentProgress() {
        return currentProgress;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
