package requestTests;

import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import requests.ChangeAnnotationRequest;
import requests.Request;
import requests.RequestFactory;



public class RequestFactoryTest {

	private RequestFactory factory;
	private HashMap allAnnotations;

	@Before
	public void setup(){
		factory = new RequestFactory();
		allAnnotations.put("Sex", "Male");
		allAnnotations.put("Experiment id", "01234");
	}

	@Test
	public void shouldCreateNewLoginRequest() {

		Request login = factory.makeLoginRequest("Anton","Banton");
		assertThat(login).toString().contains("Username");

	}

	@Test
	public void shouldCreateNewDownloadFileRequest() {

		Request downloadFile = factory.makeDownloadFileRequest("File", "Format");
		//assertThat(downloadFile).

	}

	@Test
	public void shouldCreateNewChangeAnnotaionRequest() {

		Request changeAnnotaion = new ChangeAnnotationRequest("id", allAnnotations );
		//assertThat(changeAnnotation).

	}

}



