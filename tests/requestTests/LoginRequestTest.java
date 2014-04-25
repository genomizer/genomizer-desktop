package requestTests;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import requests.LoginRequest;

public class LoginRequestTest {

    @Test
    public void testCreateLoginRequest() {
	LoginRequest login = new LoginRequest("Kalle", "123");
	assertThat(login).isNotNull();
    }

    @Test
    public void testGetUsername() {
	LoginRequest login = new LoginRequest("Kalle", "123");
	assertEquals("Kalle", login.username);
    }

    @Test
    public void testGetPassword() {
	LoginRequest login = new LoginRequest("Kalle", "123");
	assertEquals("123", login.password);
    }
    
    @Test
    public void testGetRequestName() {
	LoginRequest login = new LoginRequest("Kalle", "123");
	assertEquals("login", login.requestName);
    }

}
