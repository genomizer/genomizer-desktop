package genomizerdesktop.tests;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.*;
import model.GenomizerModel;
import model.Model;
import model.SessionHandler;
import model.User;

import org.junit.Before;
import org.junit.Test;

import requests.LoginRequest;
import requests.RequestFactory;

import communication.SSLTool;

import exampleData.ExampleExperimentData;
import util.AnnotationDataValue;
import util.ExperimentData;

import java.io.File;
import java.util.ArrayList;

public class ModelTest {

    Model m;
    User u = User.getInstance();
    SessionHandler s = SessionHandler.getInstance();
    String expName;
    AnnotationDataValue[] values;

    @Before
    public void setUp() throws Exception {
        SSLTool.disableCertificateValidation();
        m = new Model();
        m.setIP(ExampleExperimentData.getTestServerIP());
        s.setIP(ExampleExperimentData.getTestServerIP());
        values = new AnnotationDataValue[1];
        values[0] = new AnnotationDataValue("test", "name", "val");
        expName = "DesktopTestExperiment";
    }

    @Test
    public void shouldImplementGenomizerView() throws Exception {
        assertThat(m).isInstanceOf(GenomizerModel.class);
    }

    @Test
    public void shouldLogin() throws Exception {
        LoginRequest r = RequestFactory.makeLoginRequest(
                ExampleExperimentData.getTestUsername(),
                ExampleExperimentData.getTestPassword());

        assertEquals(r.username,ExampleExperimentData.getTestUsername());
        assertEquals(r.password,ExampleExperimentData.getTestPassword());
        assertEquals(r.type, "POST");
        assertEquals(r.requestName,"login");
        assertEquals(r.url, "/login");
        assertEquals(r.toJson(), "HEJ");

    }

    @Test
    public void shouldLogout() throws Exception {
        try {
            s.loginUser(ExampleExperimentData.getTestUsername(),
                    ExampleExperimentData.getTestPassword());
        } catch (Exception e) {
            fail("Login failed");
        }
        assertThat(u.getToken()).isNotEmpty();
        assertThat(s.logoutUser()).isTrue();
        assertThat(u.getToken()).isEmpty();
    }

    @Test
    public void shouldAddAndRemoveExperiment() throws Exception {
        s.loginUser(ExampleExperimentData.getTestUsername(),
                ExampleExperimentData.getTestPassword());
        if (m.retrieveExperiment(expName) != null) {
            assertThat(m.deleteExperimentFromDatabase(expName)).isTrue();
            assertThat(m.addNewExperiment(expName, values)).isTrue();
            assertThat(m.deleteExperimentFromDatabase(expName)).isTrue();
        } else {
            assertThat(m.addNewExperiment(expName, values)).isTrue();
            assertThat(m.deleteExperimentFromDatabase(expName)).isTrue();
        }
    }

    @Test
    public void shouldUploadFile() throws Exception {
        assertThat(m.addNewExperiment(expName, values)).isTrue();
        assertThat(m.uploadFile("test", new File("test"), "type", false, "fb5"))
                .isTrue();
    }

    @Test
    public void shouldSearch() throws Exception {
        assertThat(m.search("exp1[ExpID]")).isNotNull();
    }

    @Test
    public void shouldGetUrlFromSearch() throws Exception {
        ArrayList<ExperimentData> data;
        data = m.search("exp1[ExpID]");
        assertThat(data.get(0).files.get(0).url).isEqualToIgnoringCase(
                "http://scratchy.cs.umu.se:8000/download.php?"
                        + "path=/var/www/data/Exp1/raw/file1.fastq");
    }

}
