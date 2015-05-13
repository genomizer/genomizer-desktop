package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import exampleData.ExampleExperimentData;

import requests.RequestFactory;
import requests.rawToProfileRequest;

public class RawToProfileRequestTest {
    rawToProfileRequest r;
    String expName;
    String[] parameters;
    String genomeRelease;
    String metadata;
    String author;
    

    @Before
    public void setUp() {
        expName = "DesktopTestExperiment";
        parameters = new String[] {"first", "second"};
        genomeRelease = "rat15";
        metadata = "metaData";
        author = ExampleExperimentData.getTestUsername();
        r = RequestFactory.makeRawToProfileRequest(expName, parameters, metadata, genomeRelease, author);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.requestType, "PUT");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/process/rawtoprofile");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "rawtoprofile");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"expid\":\""+expName+"\",\"parameters\":[\""+parameters[0]+"\",\""+parameters[1]+"\"],\"metadata\":\""+metadata+"\",\"genomeVersion\":\""+genomeRelease+"\",\"author\":\""+author+"\"}");
    }
}

