package requestTests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import requests.LoginRequest;
import requests.LogoutRequest;
import requests.RequestFactory;
import exampleData.ExampleExperimentData;

public class ConnectionTest {

    @Test
    public void shouldLogin() throws Exception {
        LoginRequest r = RequestFactory.makeLoginRequest(
                ExampleExperimentData.getTestUsername(),
                ExampleExperimentData.getTestPassword());
        assertNotNull(r);
        assertEquals(r.username, ExampleExperimentData.getTestUsername());
        assertEquals(r.password, ExampleExperimentData.getTestPassword());
        assertEquals(r.type, "POST");
        assertEquals(r.requestName, "login");
        assertEquals(r.url, "/login");
        assertEquals(
                r.toJson(),
                "{\"username\":\"" + ExampleExperimentData.getTestUsername()
                        + "\",\"password\":\""
                        + ExampleExperimentData.getTestPassword() + "\"}");

    }

    @Test
    public void shouldLogout() throws Exception {
        LogoutRequest r = RequestFactory.makeLogoutRequest();
        assertNotNull(r);
        assertEquals(r.requestName, "logout");
        assertEquals(r.type, "DELETE");
        assertEquals(r.url, "/login");
        assertEquals(r.toJson(), "{}");
    }
    @Test
    public void shouldCheckToken() throws Exception{
        //TODO: Unimplemented request
        fail();
    }

}
