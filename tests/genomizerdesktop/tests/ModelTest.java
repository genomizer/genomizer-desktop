package genomizerdesktop.tests;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
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
    
    @Before
    public void setUp() {
        m = new Model();
        m.setIp("http://genomizer.apiary-mock.com");
    }
    
    @Test
    public void ShouldHandleUploadRequests() {
        
    }
    
    @Test
    public void shouldImplementGenomizerView() {
        assertThat(m).isInstanceOf(GenomizerModel.class);
    }

    @Test
    public void shouldLogin() {
        assertThat(m.getUserID()).isEmpty();
        assertThat(m.loginUser("genomizer", "supersecretpass")).isTrue();
        assertThat(m.getUserID()).isNotEmpty();
    }

    @Test
    public void shouldLogout() {
        assertThat(m.loginUser("genomizer", "supersecretpass")).isTrue();
        assertThat(m.getUserID()).isNotEmpty();
        assertThat(m.logoutUser()).isTrue();
        assertThat(m.getUserID()).isEmpty();
    }

    @Test
    public void shouldAddExperiment() {
        AnnotationDataValue[] values = new AnnotationDataValue[1];
        values[0] = new AnnotationDataValue("test", "name", "val");
        assertThat(m.addNewExperiment("testexp", "genomizer", values)).isTrue();
    }

    @Test
    public void shouldUploadFile() {
        assertThat(m.uploadFile("test", new File("test"), "type", "user", false, "rn5")).isTrue();
    }

    @Test
    public void shouldSearch() {
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
