package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.GetGenomeReleasesRequest;
import requests.RequestFactory;

public class GetGenomeReleasesRequestTest {
    GetGenomeReleasesRequest r;

    @Before
    public void setUp() {

        r = RequestFactory.makeGetGenomeReleaseRequest();
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
        assertEquals(r.url, "/genomeRelease");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "getGenomeReleases");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
