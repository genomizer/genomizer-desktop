package requestTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import requests.LoginRequest;

public class LoginRequestTest {

    @Test
    public void testCreateLoginRequest() {
	LoginRequest login = new LoginRequest("Kalle", "123");
	assertTrue("Kalle".equals(login.username));
	assertTrue("123".equals(login.password));
	assertTrue("login".equals(login.requestName));
    }

}
