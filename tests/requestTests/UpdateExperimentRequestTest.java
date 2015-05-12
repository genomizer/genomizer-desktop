package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import exampleData.ExampleExperimentData;

import requests.RequestFactory;
import requests.UpdateExperimentRequest;

public class UpdateExperimentRequestTest {
    UpdateExperimentRequest r;
    String expName;
    
    @Before
    public void setUp() {
        expName = "DesktopTestExperiment";
        r = RequestFactory.makeUpdateExperimentRequest(expName, null,
                ExampleExperimentData.getTestUsername(), null);
    }
    
    @Test
    public void testNull() {
        assertNotNull(r);
    }
    
    @Test
    public void testType() {
        assertEquals(r.type, "PUT");
    }
    
    @Test
    public void testUrl() {
        assertEquals(r.url, "/experiment/" + expName);
    }
    
    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "updateexperiment");
    }
    
    @Test
    public void testJSON() {
        assertEquals(r.toJson(),
                "{\"experimentID\":\"" + expName + "\",\"createdBy\":\""
                        + ExampleExperimentData.getTestUsername() + "\"}");
    }
}
