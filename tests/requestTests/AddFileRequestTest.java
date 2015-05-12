package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import exampleData.ExampleExperimentData;

import requests.AddFileToExperiment;
import requests.RequestFactory;

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
        assertEquals(r.type, "POST");
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
        assertEquals(r.toJson(), "{\"experimentID\":\""+expName+"\",\"fileName\":\""+fileName+"\",\"fileType\":\""+type+"\",\"metaData\":\""+metaData+"\",\"author\":\""+ExampleExperimentData.getTestUsername()+"\",\"uploader\":\""+ExampleExperimentData.getTestUsername()+"\",\"isPrivate\":false,\"grVersion\":\""+grVersion+"\"}");
    }
}
