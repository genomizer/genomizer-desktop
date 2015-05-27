package requestTests;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import exampleData.ExampleExperimentData;

import requests.ChangeExperimentRequest;
import requests.RequestFactory;
import util.AnnotationDataValue;

public class ChangeExperimentRequestTest {
    ChangeExperimentRequest r;
    String expName;
    AnnotationDataValue[] annotations;

    @Before
    public void setUp() {
        annotations = new AnnotationDataValue[1];
        annotations[0] = new AnnotationDataValue("test", "name", "val");
        expName = "DesktopTestExperiment";
        r = RequestFactory.makeChangeExperimentRequest(expName, annotations);
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
        assertEquals(r.url, "/experiment/"+expName);
    }

    @Test
    public void testRequestname() {
        assertEquals(r.requestName, "changeexperiment");
    }

    @Test
    public void testJSON() {
        assertEquals(r.toJson(), "{\"name\":\"" + expName
                + "\",\"annotations\":[{\"value\":\"" + annotations[0].getValue()
                + "\",\"name\":\"" + annotations[0].getName() + "\"}]}");
    }

    @Test
    public void testName(){
        assertEquals(r.name, expName);
    }

    @Test
    public void testAnnotations(){
        assertArrayEquals(r.annotations, annotations);
    }
}
