package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RequestFactory;
import requests.RetrieveExperimentRequest;

public class RetrieveExperimentRequestTest {
    RetrieveExperimentRequest r;
    String expName;

    @Before
    public void setUp() {
        expName = "DesktopTestExperiment";
        r = RequestFactory.makeRetrieveExperimentRequest(expName);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.requestType, "GET");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/experiment/"+ expName);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "retrieveexperiment");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
