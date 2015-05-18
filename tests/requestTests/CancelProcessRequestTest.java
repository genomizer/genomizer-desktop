package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.CancelProcessRequest;
import requests.RequestFactory;

public class CancelProcessRequestTest {
    CancelProcessRequest r;
    String PID;

    @Before
    public void setUp() {
        PID = "PID";
        r = RequestFactory.makeCancelProcessRequest(PID);
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
        assertEquals(r.url, "/process/cancelprocess");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "cancelprocess");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"PID\":\"PID\"}");
    }
}
