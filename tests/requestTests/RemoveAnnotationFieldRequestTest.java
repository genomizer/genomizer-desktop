package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RemoveAnnotationFieldRequest;
import requests.RequestFactory;

public class RemoveAnnotationFieldRequestTest {
    RemoveAnnotationFieldRequest r;
    String annotationName;

    @Before
    public void setUp() {
        annotationName = "Annotation";
        r = RequestFactory.makeDeleteAnnotationRequest(annotationName);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.type, "DELETE");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/annotation/field/" + annotationName);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "deleteAnnotation");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
