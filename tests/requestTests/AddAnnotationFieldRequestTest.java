package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.AddAnnotationRequest;
import requests.RequestFactory;

public class AddAnnotationFieldRequestTest {
    AddAnnotationRequest r;
    String name;
    Boolean forced;
    String[] categories;

    @Before
    public void setUp() {
        name = "AnnotationName";
        forced = false;
        categories = new String[] { "Yes", "No", "Unknown" };
        r = RequestFactory.makeAddAnnotationRequest(name, categories, forced);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.requestType, "POST");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/annotation/field");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "addAnnotation");
    }

    @Test
    public void testJSON() {
        assertEquals(
                r.toJson(),
                "{\"name\":\"AnnotationName\",\"typeList\":[\"Yes\",\"No\",\"Unknown\"],\"default\":\"Unknown\",\"forced\":false}");
    }
}
