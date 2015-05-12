package requestTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import requests.LoginRequest;
import requests.RequestFactory;
import exampleData.ExampleExperimentData;

public class LoginRequestTest {
    LoginRequest r;

    @Before
    public void setUp() {
        r = RequestFactory.makeLoginRequest(
                ExampleExperimentData.getTestUsername(),
                ExampleExperimentData.getTestPassword());
    }
    @Test
    public void testNull(){
        assertNotNull(r);
    }

    @Test
    public void testType(){
        assertEquals(r.type,"POST");
    }

    @Test
    public void testUrl(){
        assertEquals(r.url, "/login");
    }

    @Test
    public void testRequestname(){
        assertEquals(r.requestName, "login");
    }

    @Test
    public void testJSON(){
        assertEquals(
                r.toJson(),
                "{\"username\":\"" + ExampleExperimentData.getTestUsername()
                        + "\",\"password\":\""
                        + ExampleExperimentData.getTestPassword() + "\"}");
    }

    @Test
    public void testUserName(){
        assertEquals(r.username, ExampleExperimentData.getTestUsername());
    }

    @Test
    public void testPassword(){
        assertEquals(r.password, ExampleExperimentData.getTestPassword());
    }

}
