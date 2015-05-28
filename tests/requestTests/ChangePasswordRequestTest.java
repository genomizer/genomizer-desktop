package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.ChangePasswordRequest;
import requests.RequestFactory;
import requests.UpdateUserRequest;

public class ChangePasswordRequestTest {
    ChangePasswordRequest r;
    String oldPassword;
    String newPassword;
    String name;
    String email;

    @Before
    public void setUp() {
        oldPassword = "old";
        newPassword = "new";
        name = "name";
        email = "email";
        r = RequestFactory.makeChangePasswordRequest(oldPassword, newPassword, name, email);
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
        assertEquals(r.url, "/user");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "updateusersettings");
    }

    @Test
    public void testJSON() {
        assertEquals(
                r.toJson(),
                "{\"oldPassword\":\"old\",\"newPassword\":\"new\",\"email\":\"email\",\"name\":\"name\"}");
    }
}
