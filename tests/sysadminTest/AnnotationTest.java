package sysadminTest;

import model.Model;

import org.junit.Before;
import org.junit.Test;
import static org.fest.assertions.api.Assertions.*;
import communication.Connection;

public class AnnotationTest {

	private Connection con;
	private Model model;

	@Before
	public void setUp() throws Exception {
		con = new Connection("genomizer.apiary-mock.com:80");
		model = new Model(con);
	}

	/*
	 * This is how TDD is done!
	 */
	@Test
	public void shouldAddNewAnnotation() {
		assertThat(
				model.addNewAnnotation("SpeciesTEST", new String[] { "manTEST",
						"mouseTEST" }, false)).isTrue();
	}

	@Test
	public void shouldNotAddNewAnnotation() {
		assertThat(model.addNewAnnotation("SpeciesTEST", null, false)).isTrue();
	}

	@Test
	public void shouldNotAddNewAnnotationDueToEmptyString() {
		try {
			model.addNewAnnotation("", new String[] { "manTEST", "mouseTEST" },
					false);
			fail("This should throw an illegalargumentexception");
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("Must have a name for the annotation!");
		}
	}

	@Test
	public void shouldDeleteAnnotation() { // TODO: how do you delete data???
		assertThat(model.deleteAnnotation(new String[] { "SpeciesTEST" }))
				.isTrue();
	}

	@Test
	public void shouldGetAnnotationsShouldNotBeNull() {
		System.out.println(model.getAnnotations()[0].toString());
		assertThat(model.getAnnotations()).isNotNull();
	}

}
