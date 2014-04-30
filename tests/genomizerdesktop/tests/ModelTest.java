package genomizerdesktop.tests;

import static org.junit.Assert.*;

import model.Model;
import communication.Connection;

import org.junit.Before;
import org.junit.Test;

public class ModelTest {

    Model m;

    @Before
    public void setUp() {
	Connection conn = null;
	 m = new Model(conn);
    }

    @Test
    public void ShouldHandleUploadRequests() {

    }
}
