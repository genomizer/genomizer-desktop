package genomizerdesktop.tests;

import static org.junit.Assert.*;
import genomizerdesktop.LoginPanel;

import org.junit.Before;
import org.junit.Test;

public class UserPanelTest {

	LoginPanel userPanel;

	@Before
	public void setUp() throws Exception {
		userPanel = new LoginPanel("arne", false);
	}

	@Test
	public void shouldGetUsername() {
		assertEquals("arne", userPanel.getUsername());
	}

}
