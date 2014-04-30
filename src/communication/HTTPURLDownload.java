package communication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

public class HTTPURLDownload {

	private String downloadPath;
	private String localFilePath;

	public HTTPURLDownload(String downloadPath, String localFilePath) {
		this.downloadPath = downloadPath;
		this.localFilePath = localFilePath;
	}

	public void getFile(String username, String password) {
		// the URL where the file will be posted
		URI getReceiverUrl = null;
		try {
			getReceiverUrl = new URIBuilder()
			.setScheme("http")
			.setHost("scratchy.cs.umu.se:8090")
			.setPath("/html/download.php")
			.setParameter("path", downloadPath)
			.build();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String getReceiverUrl = "http://scratchy.cs.umu.se:8090/download.php";

		// new HttpClient
		HttpClientBuilder hcBuilder = HttpClients.custom();

		CloseableHttpClient httpClient = hcBuilder.build();

		//Authentication information
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username + ":" + password));
		HttpClientContext localContext = HttpClientContext.create();
		localContext.setCredentialsProvider(credentialsProvider);

		// get header
		HttpGet httpGet = new HttpGet(getReceiverUrl);

		HttpResponse response;
		try {
			response = httpClient.execute(httpGet, localContext);
			HttpEntity resEntity = response.getEntity();
			if (resEntity != null) {


				//If response is OK, try to download the file to the computer
				if(response.getStatusLine().getStatusCode() == 200) {

					InputStream inputStream = resEntity.getContent();

					File file = new File(localFilePath);
					FileOutputStream outputStream = new FileOutputStream(file);
					int contentLength = (int) resEntity.getContentLength();
					byte[] infile = new byte[contentLength];
					inputStream.read(infile, 0, contentLength);
					outputStream.write(infile);

					outputStream.close();
					inputStream.close();
				} else {
					//Not OK
					System.out.println("ERROR: " + response.getStatusLine().getStatusCode() + "\n");
				}
			    // you can add an if statement here and do other actions based on the response
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
		HTTPURLDownload downloader = new HTTPURLDownload("/var/www/html/uploads/test111.txt", "/home/oi11/oi11ejn/Downloads/test.txt");
		downloader.getFile("pvt", "pvt");
	}
}
