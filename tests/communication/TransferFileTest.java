package communication;

import model.Model;
import model.User;

import org.junit.Before;
import org.junit.Test;

import exampleData.ExampleExperimentData;

import java.io.*;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by dv12csr on 2014-05-19.
 *
 */
public class TransferFileTest {
    String ip = ExampleExperimentData.getTestServerIP();
    Model m = new Model();
    User u;
    @Before
    public void setUp() throws Exception {
        m.setIp("http://" + ip);
        m.loginUser(ExampleExperimentData.getTestUsername(), ExampleExperimentData.getTestPassword());
        User user = User.getInstance();
    }
    @Test
    public void testDownloadIs200() throws Exception {
        DownloadHandler handler = new DownloadHandler(u.getName(), "filename.txt");
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
        HTTPURLUpload upload = new HTTPURLUpload("http://private-anon-1ade64f50-genomizer.apiary-mock.com/file/.testupload.txt",
                homeDir + "/.testupload.txt", ".testupload.txt");
        upload.sendFile(u.getName());
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
        HTTPURLUpload upload = new HTTPURLUpload("http://" + ip + "/upload.php?path=/var/www/data/testup.txt",
                homeDir + "/.testupload.txt", ".testupload.txt");
        upload.sendFile(u.getName());

        DownloadHandler handler = new DownloadHandler(u.getName(), "test.txt");
        handler.download("http://" + ip + "/download.php?path=/var/www/data/testup.txt",
                homeDir + "/.testtransferdown.txt");
        File file2 = new File(homeDir + "/.testtransferdown.txt");
        BufferedReader fin = new BufferedReader(new FileReader(file2));
        assertThat(fin.readLine()).isEqualTo("test");
        fout.close();
        fin.close();
    }
}
