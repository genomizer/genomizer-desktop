package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.DownloadFileRequest;
import requests.RequestFactory;

public class DownloadFileRequestTest {

    DownloadFileRequest r;
    String fileName;
    String fileFormat;

    @Before
    public void setUp() {
        fileName = "Name";
        fileFormat = "Format";
        r = RequestFactory.makeDownloadFileRequest(fileName, fileFormat);
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
        assertEquals(r.url, "/file/" + fileName);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "downloadfile");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }

}
