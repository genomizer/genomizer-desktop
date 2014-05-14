package requestTests;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import requests.Request;
import requests.RequestFactory;

public class RequestFactoryTest {
    
    private RequestFactory factory;
    
    @Before
    public void setup() {
        factory = new RequestFactory();
    }
    
    @Test
    public void shouldCreateNewLoginRequest() {
        
        Request login = RequestFactory.makeLoginRequest("Kalle", "123");
        assertThat(login).toString().contains("Username");
        
    }
    
    @Test
    public void shouldCreateNewDownloadFileRequest() {
        
        Request login = RequestFactory.makeLoginRequest("Kalle", "123");
        
    }
    
}
