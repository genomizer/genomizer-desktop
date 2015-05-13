package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.AddNewAnnotationValueRequest;
import requests.RequestFactory;

public class AddAnnotationValueRequestTest {
    AddNewAnnotationValueRequest r;
    String valueName;
    String annotationName;

    @Before
    public void setUp() {
        valueName = "NewValue";
        annotationName = "AnnotationName";
        r = RequestFactory.makeAddNewAnnotationValueRequest(annotationName,
                valueName);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.type, "POST");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/annotation/value");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "addAnnotationValue");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"name\":\"" + annotationName
                + "\",\"value\":\"" + valueName + "\"}");
    }
}
