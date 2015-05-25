package requestTests;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import requests.ProcessCommandRequest;
import requests.RequestFactory;
import gui.processing.ProcessCommand;
import gui.processing.ProcessParameters;
import gui.processing.RawToProfileParameters;

public class ProcessCommandRequestTest {

    RawToProfileParameters f = new RawToProfileParameters("in", "out", "-flagga", "GR", true);
    RawToProfileParameters f2 = new RawToProfileParameters("in2", "out2", "-flagga", "GR", true);

    ProcessParameters[] files = {f, f2};
    ProcessParameters[] files2 = {f};

    ProcessCommand[] processCommandList = { new ProcessCommand("bowtie", files), new ProcessCommand("bowtie2", files2) };

    String expId = "testID";
    ProcessCommandRequest p;

    @Before
    public void setUp() {
        p = RequestFactory.makeProcessCommandRequest(expId, processCommandList);
    }

    @Test
    public void testNull() {
        assertNotNull(p);
        Gson a = new Gson();
        System.out.println(a.toJson(p));
    }

    @Test
    public void testType() {
        assertEquals(p.requestType, "PUT");
    }

    @Test
    public void testUrl() {
        assertEquals(p.url, "/process/processCommands");
    }

    @Test
    public void testRequestname() {
        assertEquals(p.requestName, "process");
    }

}
