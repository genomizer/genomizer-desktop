package genomizerdesktop.tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.swing.JPanel;

import genomizerdesktop.GUI;
import genomizerdesktop.GenomizerView;
import genomizerdesktop.SearchTab;

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

    @Test
    public void shouldSetSearchPanel() {
    JPanel oldPanel = gui.getSearchPanel();
    gui.setSearchTab(new SearchTab());
    assertFalse(oldPanel.equals(gui.getSearchPanel()));
    }
}
