package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.LogoutRequest;
import requests.RequestFactory;

public class LogoutRequestTest {

    LogoutRequest r;

    @Before
    public void setUp() {
        r = RequestFactory.makeLogoutRequest();
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.type, "DELETE");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/login");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "logout");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
