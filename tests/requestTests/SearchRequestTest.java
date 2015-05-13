package requestTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.RequestFactory;
import requests.SearchRequest;

public class SearchRequestTest {
    SearchRequest r;
    String annotationString;

    @Before
    public void setUp() {
        annotationString = "annotations";
        r = RequestFactory.makeSearchRequest(annotationString);
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
        assertEquals(r.url, "/search/?annotations=" + annotationString);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "search");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{}");
    }
}
