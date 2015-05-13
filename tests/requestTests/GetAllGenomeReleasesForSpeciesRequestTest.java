package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.GetGenomeSpecieReleasesRequest;
import requests.RequestFactory;

public class GetAllGenomeReleasesForSpeciesRequestTest {
    GetGenomeSpecieReleasesRequest r;
    String specie;

    @Before
    public void setUp() {
        specie = "Rat";
        r = RequestFactory.makeGetGenomeSpecieReleaseRequest(specie);
    }

    @Test
    public void testNull() {
        assertNotNull(r);
    }

    @Test
    public void testType() {
        assertEquals(r.type, "GET");
    }

    @Test
    public void testUrl() {
        assertEquals(r.url, "/genomeRelease/" + specie);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "getGenomeSpecieReleases");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
