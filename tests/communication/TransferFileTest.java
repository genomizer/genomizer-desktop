package communication;

import model.Model;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by dv12csr on 2014-05-19.
 *
 */
public class TransferFileTest {
    String ip = "dumbledore.cs.umu.se";
    Model m = new Model();

    @Before
    public void setUp() throws Exception {
        m.setIp("http://" + ip + ":7000");
        m.loginUser("desktoptest", "baguette");
    }
    @Test
    public void testDownloadIs200() throws Exception {
        DownloadHandler handler = new DownloadHandler(m.getUserID(), "filename.txt");
        String homeDir = System.getProperty("user.home");
        assertThat(handler.download("http://www8.cs.umu.se/~c12jhn/", homeDir + "/.test")).isTrue();
    }

    @Test
    public void testSendFileReturns201() throws Exception {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + "/.testupload.txt");
        BufferedWriter fout = new BufferedWriter(new FileWriter(file));
        fout.write("test");
        fout.newLine();
        fout.flush();
        HTTPURLUpload upload = new HTTPURLUpload("http://" + ip + ":7000/upload.php?path=/var/www/data/test.txt",
                homeDir + "/.testupload.txt", ".testupload.txt");
        upload.sendFile(m.getUserID());
        assertThat(upload.getResponseCode()).isEqualTo(201);
        fout.close();
    }

    @Test
    public void testSendAndRetrieveSameFile() throws Exception {
        String homeDir = System.getProperty("user.home");
        File file = new File(homeDir + "/.testtransferup.txt");
        BufferedWriter fout = new BufferedWriter(new FileWriter(file));
        fout.write("test");
        fout.newLine();
        fout.flush();
        HTTPURLUpload upload = new HTTPURLUpload("http://" + ip + ":7000/upload.php?path=/var/www/data/testup.txt",
                homeDir + "/.testupload.txt", ".testupload.txt");
        upload.sendFile(m.getUserID());

        DownloadHandler handler = new DownloadHandler(m.getUserID(), "test.txt");
        handler.download("http://" + ip + ":7000/download.php?path=/var/www/data/testup.txt",
                homeDir + "/.testtransferdown.txt");
        File file2 = new File(homeDir + "/.testtransferdown.txt");
        BufferedReader fin = new BufferedReader(new FileReader(file2));
        assertThat(fin.readLine()).isEqualTo("test");
        fout.close();
        fin.close();
    }
}
