package requestTests;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import requests.LoginRequest;

public class LoginRequestTest {

	@Test
	public void testCreateLoginRequest() {
		LoginRequest login = new LoginRequest("Kalle", "123");
	//	assertTrue("Kalle".equals(login.username));
	//	assertTrue("123".equals(login.password));
	//	assertTrue("login".equals(login.requestName));
		assertThat(login).isNotNull();
	}

}
