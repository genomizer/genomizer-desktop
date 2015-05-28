package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.DeleteUserRequest;
import requests.RequestFactory;

public class DeleteUserRequestTest {
    DeleteUserRequest r;
    String username;

    @Before
    public void setUp() {
        username = "user";

        r = RequestFactory.makeDeleteUserRequest(username);
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
        assertEquals(r.url, "/admin/user/" + username);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "deleteuser");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
