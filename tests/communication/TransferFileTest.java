package communication;

import org.junit.Test;

import java.io.*;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by dv12csr on 2014-05-19.
 *
 */
public class TransferFileTest {
    String ip = "sterner.cc";

    @Test
    public void testDownloadIs200() throws Exception {
        DownloadHandler handler = new DownloadHandler("pvt", "pvt", "filename.txt");
        String homeDir = System.getProperty("user.home");
        assertThat(handler.download("http://www.sterner.cc", homeDir + "/.test")).isTrue();
    }

    @Test
    public void testSendFileReturns201() throws Exception {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + "/.testupload.txt");
        BufferedWriter fout = new BufferedWriter(new FileWriter(file));
        fout.write("test");
        fout.newLine();
        fout.flush();
        HTTPURLUpload upload = new HTTPURLUpload("http://" + ip + ":8000/upload.php?path=/var/www/data/test.txt",
                homeDir + "/.testupload.txt", ".testupload.txt");
        upload.sendFile("pvt", "pvt");
        assertThat(upload.getResponseCode()).isEqualTo(201);
    }

    @Test
    public void testSendAndRetrieveSameFile() throws Exception {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + "/.testtransferup.txt");
        BufferedWriter fout = new BufferedWriter(new FileWriter(file));
        fout.write("test");
        fout.newLine();
        fout.flush();
        HTTPURLUpload upload = new HTTPURLUpload("http://" + ip + ":8000/upload.php?path=/var/www/data/testup.txt",
                homeDir + "/.testupload.txt", ".testupload.txt");
        upload.sendFile("pvt", "pvt");

        DownloadHandler handler = new DownloadHandler("pvt", "pvt", "test.txt");
        handler.download("http://" + ip + ":8000/download.php?path=/var/www/data/testup.txt",
                homeDir + "/.testtransferdown.txt");
        File file2 = new File(homeDir + "/.testtransferdown.txt");
        BufferedReader fin = new BufferedReader(new FileReader(file2));
        assertThat(fin.readLine()).isEqualTo("test");
    }
}
