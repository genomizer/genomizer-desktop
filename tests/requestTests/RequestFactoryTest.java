package requestTests;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import org.junit.Before;
import org.junit.Test;

import requests.Request;
import requests.RequestFactory;



public class RequestFactoryTest {

	private RequestFactory factory;

	@Before
	public void setup(){
		factory = new RequestFactory();
	}

	@Test
	public void shouldCreateNewLoginRequest() {

		Request login = factory.makeLoginRequest();
		assertThat(login).toString().contains("Username");

	}


	@Test
	public void shouldCreateNewDownloadFileRequest() {

		Request downloadFile = factory.makeDownloadFileRequest();

	}

}



