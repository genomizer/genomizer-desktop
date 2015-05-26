package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.FileConversionRequest;
import requests.RequestFactory;

public class FileConversionRequestTest {
    FileConversionRequest r;
    String fileid;
    String toformat;

    @Before
    public void setUp() {
        fileid = "id";
        toformat = "format";
        r = RequestFactory.makeFileConversionRequest(fileid, toformat);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.requestType, "PUT");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/convertfile");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "convertfile");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"fileid\":\"id\",\"toformat\":\"format\"}");
    }
}
