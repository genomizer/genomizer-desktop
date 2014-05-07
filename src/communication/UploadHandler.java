package communication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.codec.binary.Base64;

public class UploadHandler implements Runnable {
	private String url;
	private String fileName;
	private String userID;
	private String authString;

	public UploadHandler(String url, String fileName, String userID,
			String authString) {
		this.url = url;
		this.fileName = fileName;
		this.userID = userID;
		this.authString = authString;
	}

	@Override
	public void run() {
		try {
			sendSetupPackage();
			URL targetUrl = new URL(
					"http://scratchy.cs.umu.se:8090/html/upload.php");
			HttpURLConnection conn = (HttpURLConnection) targetUrl
					.openConnection();
			conn.setDoOutput(true);
			byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
			String authStringEnc = new String(authEncBytes);
			conn.setReadTimeout(1000);
			conn.setRequestMethod("POST");
			// conn.setRequestProperty("path", url);
			String boundary = "WgiloelqGufNYwj9e2TMztCZON694FV";
			conn.setRequestProperty("Content-Type",
					"multipart-formdata; boundary=" + boundary);
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
			PrintWriter outputStream = new PrintWriter(conn.getOutputStream(),
					true);
			File file = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			outputStream.println("--" + boundary);
			outputStream
					.println("Content-Disposition: form-data; name=\"path\"");
			outputStream.println(url);
			outputStream.println("--" + boundary);
			outputStream
					.println("Content-Disposition: form-data; name=\"uploadfile\"; filename=\"test321.txt\"\n"
							+ "Content-Type: application/octet-stream\n");
			while (reader.ready()) {
				outputStream.println(reader.readLine());
				outputStream.flush();
			}
			outputStream.println("--" + boundary + "--");
			int responseCode;
			if ((responseCode = conn.getResponseCode()) != 200) {
				System.out
						.println("Error wrong response code: " + responseCode);
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			while (in.ready()) {
				System.out.println(in.readLine());
			}
			conn.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println("File was not found: " + fileName);
		} catch (IOException e) {
			System.out.println("Connection error: " + e.getMessage() + " "
					+ url);
		}

	}

	public void sendSetupPackage() {

	}
}
