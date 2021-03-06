package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.AddFileToExperiment;
import requests.RequestFactory;
import exampleData.ExampleExperimentData;

public class AddFileRequestTest {
    AddFileToExperiment r;
    String expName;
    String fileName;
    String type;
    String metaData;
    String grVersion;

    @Before
    public void setUp() {
        expName = "DesktopTestExperiment";
        fileName = "File";
        type = "Raw";
        metaData = "Info";
        grVersion = "rat15";
        r = RequestFactory.makeAddFile(expName, fileName, type, metaData,
                ExampleExperimentData.getTestUsername(),
                ExampleExperimentData.getTestUsername(), false, grVersion);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.requestType, "POST");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/file");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "addfile");
    }

    @Test
    public void testJSON() {
        assertEquals(
                r.toJson(),
                "{\"experimentID\":\"" + expName + "\",\"fileName\":\""+fileName+"\",\"type\":\""+type+"\",\"metaData\":\""+metaData+"\",\"author\":\""+ExampleExperimentData.getTestUsername()+"\",\"uploader\":\""+ExampleExperimentData.getTestUsername()+"\",\"isPrivate\":false,\"grVersion\":\""+grVersion+"\"}");
    }
}
