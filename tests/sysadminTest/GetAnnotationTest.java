package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gui.sysadmin.SysadminTab;
import model.Model;

public class GetAnnotationTest {

    private Model model;
    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIp("dumbledore.cs.umu.se:7000");
        model.loginUser("SysadminTests", "baguette");
        new SysadminTab();
        model.addNewAnnotation("GetAnnotationTest", new String[] { "yes", "no" }, false);
    }

    @After
    public void tearDown() throws Exception {
        model.deleteAnnotation("GetAnnotationTest");
    }

    @Test
    public void shouldGetAnnotationsShouldNotBeNull() {
        assertThat(model.getAnnotations()).isNotNull();
    }

    @Test
    public void shouldGetGetAnnotationTestAnnotation() {
        assertThat(model.getAnnotations()[0].toString()).isEqualTo(
                "GetAnnotationTest");
    }

    @Test
    public void shouldGetSpeciesValues() {
        String[] actual = model.getAnnotations()[0].getValues();
        String[] expected = new String[] { "yes", "no" };
        assertThat(actual).isEqualTo(expected);
    }

}
