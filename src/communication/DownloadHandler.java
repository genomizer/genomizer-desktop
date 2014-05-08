package communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by Christoffer on 2014-04-30.
 *
 */
public class DownloadHandler {

	private String username;
	private String password;

	public DownloadHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean download(String url, String localFilePath, String userID) {
		try {
			// Use this url in the real version. vvv
			/*URL targetUrl = new URL(
					"http://scratchy.cs.umu.se:8090/html/download.php?path="
							+ url);*/
            url = url.replaceFirst("\\u003d", "=");

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
			String buffer;
			File file = new File(localFilePath);
			PrintWriter fileOut = new PrintWriter(new FileWriter(file));
			while ((buffer = in.readLine()) != null) {
				fileOut.print(buffer);
				fileOut.flush();
			}
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
