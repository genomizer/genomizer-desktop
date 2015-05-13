package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RemoveAnnotationValueRequest;
import requests.RequestFactory;

public class RemoveAnnotationValueRequestTest {
    RemoveAnnotationValueRequest r;
    String annotationName;
    String valueName;

    @Before
    public void setUp() {
        annotationName = "AnnotationName";
        valueName = "Value";
        r = RequestFactory.makeRemoveAnnotationValueRequest(annotationName,
                valueName);
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
        assertEquals(r.url, "/annotation/value/" + annotationName + "/"
                + valueName);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "removeAnnotationValue");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
