package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RemoveFileFromExperimentRequest;
import requests.RequestFactory;

public class DeleteFileRequestTest {
    RemoveFileFromExperimentRequest r;
    String fileId;

    @Before
    public void setUp() {

        fileId = "file";
        r = RequestFactory.makeRemoveFileFromExperimentRequest(fileId);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.requestType, "DELETE");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/file/" + fileId);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "removefile");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
