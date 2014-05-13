package communication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HTTPURLUpload {

    private String filePath;
    private String uploadPath;

    public HTTPURLUpload(String uploadPath, String filePath) {
        this.filePath = filePath;
        this.uploadPath = uploadPath;
    }

    public void sendFile(String username, String password) {
        // the URL where the file will be posted

        URI postReceiverUrl = null;
        try {
            postReceiverUrl = new URIBuilder().setScheme("http")
                    .setHost("scratchy.cs.umu.se:8090")
                    .setPath("/html/upload.php").build();
        } catch (URISyntaxException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        // new HttpClient
        HttpClientBuilder hcBuilder = HttpClients.custom();

        CloseableHttpClient httpClient = hcBuilder.build();

        // Authentication information
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(username + ":" + password));
        HttpClientContext localContext = HttpClientContext.create();
        localContext.setCredentialsProvider(credentialsProvider);

        // post header
        HttpPost httpPost = new HttpPost(postReceiverUrl);

        File file = new File(filePath);

        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        // add the location on the server where the file should be saved
        reqEntity.addTextBody("path", uploadPath);

        reqEntity.addBinaryBody("uploadfile", file);
        httpPost.setEntity(reqEntity.build());

        HttpResponse response;
        try {
            // execute HTTP post request
            response = httpClient.execute(httpPost, localContext);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {

                // String responseStr = EntityUtils.toString(resEntity).trim();
                // System.out.println("Response: " + responseStr);
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        HTTPURLUpload uploader = new HTTPURLUpload(
                "/var/www/html/uploads/test111.txt",
                "/home/c11/c11vlg/Downloads/test.txt");
        uploader.sendFile("pvt", "pvt");
    }

}
