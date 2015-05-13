package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RenameAnnotationFieldRequest;
import requests.RequestFactory;

public class RenameAnnotationFieldRequestTest {
    RenameAnnotationFieldRequest r;
    String oldName;
    String newName;

    @Before
    public void setUp() {
        oldName = "OldName";
        newName = "NewName";
        r = RequestFactory.makeRenameAnnotationFieldRequest(oldName, newName);
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
        assertEquals(r.url, "/annotation/field");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "renameAnnotationRequest");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"newName\":\"" + newName
                + "\",\"oldName\":\"" + oldName + "\"}");
    }
}
