package genomizerdesktop.tests;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import model.GenomizerModel;
import model.Model;
import model.SessionHandler;
import model.User;

import org.junit.Before;
import org.junit.Test;

import requests.AddExperimentRequest;
import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RequestFactory;
import requests.RetrieveExperimentRequest;
import requests.UpdateExperimentRequest;
import util.AnnotationDataValue;
import util.ExperimentData;

import communication.SSLTool;

import exampleData.ExampleExperimentData;

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
    public void shouldAddExperiment() throws Exception {
        AddExperimentRequest r = RequestFactory.makeAddExperimentRequest(
                expName, values);
        assertNotNull(r);
        assertEquals(r.requestName, "addexperiment");
        assertEquals(r.type, "POST");
        assertEquals(r.url, "/experiment");
        assertEquals(r.name, expName);
        assertArrayEquals(r.annotations, values);
        assertEquals(r.toJson(), "{\"name\":\"" + expName
                + "\",\"annotations\":[{\"value\":\"" + values[0].getValue()
                + "\",\"name\":\"" + values[0].getName() + "\"}]}");
        
    }
    
    @Test
    public void shouldRetrieveExperiment() throws Exception {
        RetrieveExperimentRequest r = RequestFactory
                .makeRetrieveExperimentRequest(expName);
        assertNotNull(r);
        assertEquals(r.requestName, "retrieveexperiment");
        assertEquals(r.type, "GET");
        assertEquals(r.url, "/experiment/" + expName);
        assertEquals(r.toJson(), "{}");
        
    }
    
    @Test
    public void shouldUpdateExperiment() throws Exception {
        UpdateExperimentRequest r = RequestFactory.makeUpdateExperimentRequest(
                expName, "name", ExampleExperimentData.getTestUsername(), null);
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
