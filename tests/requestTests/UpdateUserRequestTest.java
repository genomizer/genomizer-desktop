package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.CreateUserRequest;
import requests.RequestFactory;
import requests.UpdateUserRequest;

public class UpdateUserRequestTest {
    UpdateUserRequest r;
    String username;
    String password;
    String privileges;
    String name;
    String email;

    @Before
    public void setUp() {
        username = "user";
        password = "pass";
        privileges = "role";
        name = "name";
        email = "email";
        r = RequestFactory.makeUpdateUserRequest(username, password,
                privileges, name, email);
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
        assertEquals(r.url, "/admin/user");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "updateuser");
    }

    @Test
    public void testJSON() {
        assertEquals(
                r.toJson(),
                "{\"username\":\"user\",\"password\":\"pass\",\"privileges\":\"role\",\"name\":\"name\",\"email\":\"email\"}");
    }
}
