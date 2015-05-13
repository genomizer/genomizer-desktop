package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RemoveExperimentRequest;
import requests.RequestFactory;

public class RemoveExperimentRequestTest {
    RemoveExperimentRequest r;
    String expName;

    @Before
    public void setUp() {
        expName = "DesktopTestExperiment";
        r = RequestFactory.makeRemoveExperimentRequest(expName);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.requestType, "DELETE");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/experiment/" + expName);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "removeexperiment");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
