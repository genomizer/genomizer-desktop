package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.GetAnnotationRequest;
import requests.RequestFactory;

public class GetAnnotationRequestTest {
    GetAnnotationRequest r;

    @Before
    public void setUp() {
        r = RequestFactory.makeGetAnnotationRequest();
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.requestType, "GET");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/annotation");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "getAnnotation");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
