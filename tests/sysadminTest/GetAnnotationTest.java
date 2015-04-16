package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;

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
    }

    @Test
    public void shouldGetAnnotationsShouldNotBeNull() {
        assertThat(model.getAnnotations()).isNotNull();
    }

    @Test
    public void shouldCellLineAnnotation() {
        assertThat(model.getAnnotations()[0].toString()).isEqualTo(
                "Cell Line");
    }

    @Test
    public void shouldGetSpeciesValues() {
        String[] actual = model.getAnnotations()[0].getValues();
        String[] expected = new String[] { "yes", "no" };
        assertThat(actual).isEqualTo(expected);
    }

}
