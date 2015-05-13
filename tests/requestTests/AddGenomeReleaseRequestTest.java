package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.AddGenomeReleaseRequest;
import requests.RequestFactory;

public class AddGenomeReleaseRequestTest {
    AddGenomeReleaseRequest r;
    String[] files;
    String version;
    String species;

    @Before
    public void setUp() {
        files = new String[] { "File1", "File2" };
        version = "v1";
        species = "Rat";
        r = RequestFactory.makeAddGenomeRelease(files, species, version);
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
        assertEquals(r.url, "/genomeRelease");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "AddGenomeRelease");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"genomeVersion\":\"" + version
                + "\",\"specie\":\"" + species + "\",\"files\":[\"" + files[0]
                + "\",\"" + files[1] + "\"]}");
    }
}
