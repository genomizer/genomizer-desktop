package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RemoveGenomeReleaseRequest;
import requests.RequestFactory;

public class DeleteGenomeReleaseRequestTest {
    RemoveGenomeReleaseRequest r;
    String specie;
    String version;

    @Before
    public void setUp() {
        specie = "Rat";
        version = "v1";
        r = RequestFactory.makeRemoveGenomeReleaseRequest(specie, version);
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
        assertEquals(r.url, "/genomeRelease/" + specie + "/" + version);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "deleteGenomeRelease");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
