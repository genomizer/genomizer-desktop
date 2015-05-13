package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.ProcessFeedbackRequest;
import requests.RequestFactory;

public class GetProcessStatusRequestTest {
    ProcessFeedbackRequest r;

    @Before
    public void setUp() {
        r = RequestFactory.makeProcessFeedbackRequest();
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
        assertEquals(r.url, "/process");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "processfeedback");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
