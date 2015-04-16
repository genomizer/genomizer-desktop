package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import gui.sysadmin.SysadminTab;
import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.AnnotationDataType;

public class RemoveAnnotationTest {
    public Model model;
    public SysadminTab sysadminTab;
    public String name;
    public AnnotationDataType toBeRemoved;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIp("dumbledore.cs.umu.se:7000");
        model.loginUser("SysadminTests", "baguette");
        sysadminTab = new SysadminTab();
        name = "REMOVEANNOTATIONTEST";
        if ((toBeRemoved = getSpecificAnnotationType(name)) == null) {
            model.addNewAnnotation(name, null, false);
        }
    }

    @After
    public void tearDown() throws Exception {
        if ((toBeRemoved = getSpecificAnnotationType(name)) != null) {
            model.deleteAnnotation(name);
        }
    }

    @Test
    public void shouldDeleteAnnotation() {
        try {
            if (model.deleteAnnotation("REMOVEANNOTATIONTEST")) {
                AnnotationDataType newAnnotation = getSpecificAnnotationType("REMOVEANNOTATIONTEST");
                assertThat(newAnnotation).isNull();
            } else {
                fail("could not do model.deleteAnnotation()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
