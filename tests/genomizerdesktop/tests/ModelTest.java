package genomizerdesktop.tests;

import static org.junit.Assert.assertTrue;
import model.GenomizerModel;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import communication.Connection;

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

    @Test
    public void shouldImplementGenomizerView() {
	assertTrue(m instanceof GenomizerModel);
    }
}
