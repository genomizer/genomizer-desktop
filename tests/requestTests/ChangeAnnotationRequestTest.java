package requestTests;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import requests.ChangeAnnotationRequest;

public class ChangeAnnotationRequestTest {
    private ChangeAnnotationRequest change;

    @Before
    public void setUp() throws Exception {
	HashMap map = new HashMap();
	map.put("Sex", "F");
	map.put("Species", "Xenomorph");
	change = new ChangeAnnotationRequest("file1", map, "1");
    }

    @Test
    public void testCreateChangeRequest() {
	assertThat(change).isNotNull();
    }

    @Test
    public void testGetFileID() {
	assertEquals("file1", change.id);
    }

    @Test
    public void testGetAnnotations() {
	HashMap map = change.annotations;
	assertEquals("F", map.get("Sex"));
	assertEquals("Xenomorph", map.get("Species"));
    }

    @Test
    public void testGetRequestName() {
	assertEquals("changeannotation", change.requestName);
    }

}
