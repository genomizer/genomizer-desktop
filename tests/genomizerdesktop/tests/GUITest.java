package genomizerdesktop.tests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import genomizerdesktop.GUI;
import genomizerdesktop.GenomizerView;

import org.junit.Before;
import org.junit.Test;

public class GUITest {

    private GUI gui;

    @Before
    public void setUp() throws Exception {
	gui = new GUI();
    }

    @Test
    public void testConstructor() {
	assertNotNull(gui);
	assertNotNull(gui.getFrame());
    }

    @Test
    public void shouldImplementGenomizerView() {
	assertTrue(gui instanceof GenomizerView);
    }
}
