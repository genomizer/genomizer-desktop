package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.AnnotationDataType;

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
        assertThat(getSpecificAnnotationType("GetAnnotationTest")).isNotNull();
    }

    @Test
    public void shouldGetSpeciesValues() {
        String[] actual = getSpecificAnnotationType("GetAnnotationTest").values;
        String[] expected = new String[] { "yes", "no" };
        assertThat(actual).isEqualTo(expected);
    }
    
    protected AnnotationDataType getSpecificAnnotationType(String name) {
        AnnotationDataType[] annotations = model.getAnnotations();
        AnnotationDataType specificAnnotation = null;
        for (int i = 0; i < annotations.length; i++) {
            if (annotations[i].name.equals(name)) {
                specificAnnotation = annotations[i];
            }
        }
        return specificAnnotation;
    }

}
