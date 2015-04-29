package genomizerdesktop.tests;

import static org.fest.assertions.api.Assertions.assertThat;
import model.GenomizerModel;
import model.Model;

import org.junit.Before;
import org.junit.Test;
import util.AnnotationDataValue;
import util.ExperimentData;

import java.io.File;
import java.util.ArrayList;

public class ModelTest {

    Model m;
    String username = "desktoptest";
    String password = "baguette";
    @Before
    public void setUp() throws Exception {
        m = new Model();
        m.setIp("http://private-anon-b586d8d28-genomizer.apiary-mock.com");

    }

    @Test
    public void shouldImplementGenomizerView() throws Exception {
        assertThat(m).isInstanceOf(GenomizerModel.class);
    }

    @Test
    public void shouldLogin() throws Exception {
        assertThat(m.getUserID()).isEmpty();
        assertThat(m.loginUser(username, password)).isEqualToIgnoringCase("true");
        assertThat(m.getUserID()).isNotEmpty();
    }

    @Test
    public void shouldLogout() throws Exception {
        assertThat(m.loginUser(username, password)).isEqualToIgnoringCase("true");
        assertThat(m.getUserID()).isNotEmpty();
        assertThat(m.logoutUser()).isTrue();
        assertThat(m.getUserID()).isEmpty();
    }

    @Test
    public void shouldAddExperiment() throws Exception {
        AnnotationDataValue[] values = new AnnotationDataValue[1];
        values[0] = new AnnotationDataValue("test", "name", "val");
        assertThat(m.addNewExperiment("testexp", values)).isTrue();
    }

    @Test
    public void shouldUploadFile() throws Exception {
        assertThat(m.uploadFile("test", new File("test"), "type", "user", false, "fb5")).isTrue();
    }

    @Test
    public void shouldSearch() throws Exception {
        assertThat(m.search("exp1[ExpID]")).isNotNull();
    }

    @Test
    public void shouldGetUrlFromSearch() throws Exception {
        ArrayList<ExperimentData> data;
        data = m.search("exp1[ExpID]");
        assertThat(data.get(0).files.get(0).url).
                isEqualToIgnoringCase("http://scratchy.cs.umu.se:8000/download.php?" +
                        "path=/var/www/data/Exp1/raw/file1.fastq");
    }


}
