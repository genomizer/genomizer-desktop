package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RenameAnnotationValueRequest;
import requests.RequestFactory;

public class RenameAnnotationValueRequestTest {
    RenameAnnotationValueRequest r;
    String oldValue;
    String name;
    String newValue;

    @Before
    public void setUp() {
        name = "AnnotationName";
        oldValue = "Old";
        newValue = "New";
        r = RequestFactory.makeRenameAnnotationValueRequest(name, oldValue,
                newValue);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.type, "PUT");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/annotation/value");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "renameAnnotationValueRequest");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"name\":\"" + name + "\",\"oldValue\":\""
                + oldValue + "\",\"newValue\":\"" + newValue + "\"}");
    }
}
