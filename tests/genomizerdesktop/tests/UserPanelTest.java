package genomizerdesktop.tests;

import static org.junit.Assert.*;
import genomizerdesktop.UserPanel;

import org.junit.Before;
import org.junit.Test;

public class UserPanelTest {

	UserPanel userPanel;

	@Before
	public void setUp() throws Exception {
		userPanel = new UserPanel("arne", false);
	}

	@Test
	public void shouldGetUsername() {
		assertEquals("arne", userPanel.getUsername());
	}

}
