package requestTests;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import requests.LoginRequest;

public class LoginRequestTest {
    
    private LoginRequest login;
    
    @Before
    public void setUp() {
        login = new LoginRequest("Kalle", "123");
    }
    
    @Test
    public void testCreateLoginRequest() {
        assertThat(login).isNotNull();
    }
    
    @Test
    public void testGetUsername() {
        assertEquals("Kalle", login.username);
    }
    
    @Test
    public void testGetPassword() {
        assertEquals("123", login.password);
    }
    
    @Test
    public void testGetRequestName() {
        assertEquals("login", login.requestName);
    }
    
}
