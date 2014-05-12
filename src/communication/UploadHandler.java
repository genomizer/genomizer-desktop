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
	private String filePath;
	private String userID;
	private String authString;

	public UploadHandler(String url, String fileName, String userID,
			String authString) {
		this.url = url;
		this.filePath = fileName;
		this.userID = userID;
		this.authString = authString;
	}

	@Override
	public void run() {
		try {
            String urlFileName = getFileNameFromUrl(url);
			sendSetupPackage();
			url.replaceFirst("\\u003d", "=");
			url.replaceFirst("8000", "7050");
			URL targetUrl = new URL(url);
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
					"multipart/form-data; boundary=" + boundary);
			conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
			PrintWriter outputStream = new PrintWriter(conn.getOutputStream(),
					true);
			File file = new File(filePath);
			BufferedReader reader = new BufferedReader(new FileReader(file));
            sendMultiPartFormat(urlFileName, boundary, outputStream);
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
			System.out.println("File was not found: " + filePath);
		} catch (IOException e) {
			System.out.println("Connection error: " + e.getMessage() + " "
					+ url);
		}

	}

    private void sendMultiPartFormat(String urlFileName, String boundary,
                                     PrintWriter outputStream) {
        outputStream.println("--" + boundary);
        outputStream
                .println("Content-Disposition: form-data; name=\"path\"");
        outputStream.println(url);
        outputStream.println("--" + boundary);
        outputStream
                .println("Content-Disposition: form-data; name=" +
                        "\"uploadfile\"; filename=\"" + urlFileName + "\"\n"
                        + "Content-Type: application/octet-stream\n");
    }

    private String getFileNameFromUrl(String url) {
        String[] urlSplit = url.split("/");
        String fileName = urlSplit[urlSplit.length-1];
        System.out.println(fileName);
        return fileName;


    }
	public void sendSetupPackage() {

	}
}
