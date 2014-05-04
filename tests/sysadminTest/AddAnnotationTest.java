package sysadminTest;

import model.Model;

import org.junit.Before;
import org.junit.Test;
import static org.fest.assertions.api.Assertions.*;
import communication.Connection;

public class AddAnnotationTest {

	private Connection con;
	private Model model;

	@Before
	public void setUp() throws Exception {
		con = new Connection("genomizer.apiary-mock.com:80");
		model = new Model(con);
	}
	/*
	 * This is how TDD is done!
	 * */
	@Test
	public void shouldAddNewAnnotation() {
		assertThat(model.addNewAnnotation("SpeciesTEST", new String[] {"manTEST", "mouseTEST"}, false)).isTrue();
	}
}
