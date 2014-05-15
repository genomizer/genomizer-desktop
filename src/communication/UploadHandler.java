package communication;

import org.apache.commons.codec.binary.Base64;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
            url = url.replaceFirst("\\u003d", "=");
            url = url.replaceFirst("scratchy.cs.umu.se", "sterner.cc");
            // url = url.replaceFirst("8000", "8050");
            URL targetUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) targetUrl
                    .openConnection();
            conn.setDoOutput(true);
            byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
            String authStringEnc = new String(authEncBytes);
            // conn.setReadTimeout(1000);
            conn.setRequestMethod("POST");
            // conn.setRequestProperty("path", url);
            String boundary = "WgiloelqGufNYwj9e2TMztCZON694FV";
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + boundary);
            conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            OutputStreamWriter outputStream = new OutputStreamWriter(conn.getOutputStream());
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            sendMultiPartFormat(urlFileName, boundary, outputStream);
            int totalSent = 0;
            char[] buff = new char[4096];
            int count;
            Long previousTime = System.currentTimeMillis();
            while ((count = reader.read(buff, 0, 4096)) != -1) {
                outputStream.write(buff);
                totalSent += count;
                if (System.currentTimeMillis() - previousTime > 1000) {
                    previousTime = System.currentTimeMillis();
                    System.out.println(totalSent / 1024 / 1024 / 5 + "MiB");
                }
                outputStream.flush();
            }
            outputStream.write("\n--" + boundary + "--" + "\n");
            outputStream.flush();
            int responseCode;
            if ((responseCode = conn.getResponseCode()) != 201) {
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
            OutputStreamWriter outputStream) {
        try {
            outputStream.write("--" + boundary + "\n");
            outputStream.write("Content-Disposition: form-data; name=\"path\"\n\n");
            String path = url.split("=")[1];
            System.out.println(path);
            outputStream.write(path + "\n");
            outputStream.write("--" + boundary + "\n");
            outputStream.write("Content-Disposition: form-data; name="
                    + "\"uploadfile\"; filename=\"" + urlFileName + "\"\n"
                    + "Content-Type: application/octet-stream\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String getFileNameFromUrl(String url) {
        String[] urlSplit = url.split("/");
        String fileName = urlSplit[urlSplit.length - 1];
        System.out.println(fileName);
        return fileName;

    }

    public void sendSetupPackage() {

    }
}
