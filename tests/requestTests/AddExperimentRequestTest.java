package requestTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import requests.AddExperimentRequest;
import requests.RequestFactory;
import util.AnnotationDataValue;

public class AddExperimentRequestTest {
    AddExperimentRequest r;
    String expName;
    AnnotationDataValue[] values;

    @Before
    public void setUp() {
        values = new AnnotationDataValue[1];
        values[0] = new AnnotationDataValue("test", "name", "val");
        expName = "DesktopTestExperiment";
        r = RequestFactory.makeAddExperimentRequest(expName, values);
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
        assertEquals(r.url, "/experiment");
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "addexperiment");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"name\":\"" + expName
                + "\",\"annotations\":[{\"value\":\"" + values[0].getValue()
                + "\",\"name\":\"" + values[0].getName() + "\"}]}");
    }

    @Test
    public void testName(){
        assertEquals(r.name, expName);
    }

    @Test
    public void testAnnotations(){
        assertArrayEquals(r.annotations, values);
    }
}
