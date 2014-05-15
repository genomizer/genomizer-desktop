package sysadminTest;

import static org.fest.assertions.api.Assertions.*;
import gui.sysadmin.SysadminTab;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import util.AnnotationDataType;
import util.DeleteAnnoationData;
import communication.Connection;

public class AnnotationTest {
    
    private Connection con;
    private Model model;
    private SysadminTab sysadminTab;
    
    @Before
    public void setUp() throws Exception {
        //con = new Connection("genomizer.apiary-mock.com:80");
        con = new Connection("http://scratchy.cs.umu.se:7000");
        model = new Model(con);
        model.loginUser("SysadminTests", "qwerty");
        sysadminTab = new SysadminTab();
    }
    
    @Test
    public void shouldGetAnnotationsShouldNotBeNull() {
        assertThat(model.getAnnotations()).isNotNull();
    }
    
    @Test
    public void shouldGetPubmedAnnotation() {
        assertThat(model.getAnnotations()[0].toString()).isEqualTo("pubmedId");
    }
    
    @Test
    public void shouldGetSpeciesValues() {
        String[] actual = model.getAnnotations()[2].getValues();
        String[] expected = new String[] { "fly", "human", "rat" };
        assertThat(actual).isEqualTo(expected);
    }
    
    @Test
    public void shouldAddNewFreeTextAnnotation() {
        assertThat(
                model.addNewAnnotation("FREETEXTTEST",
                        new String[] { "freetext" }, false)).isTrue();
    }
    
    @Test
    public void shouldAddNewAnnotation() {
        assertThat(
                model.addNewAnnotation("SpeciesTEST", new String[] { "manTEST",
                        "mouseTEST" }, false)).isTrue();
    }
    
    @Test
    public void shouldNotAddNewAnnotation() {
        try {
            model.addNewAnnotation("SpeciesTEST", null, false);
            fail("This should throw an illegalargumentexception");
        } catch (Exception e) {
            assertThat(e).hasMessage("Annotations must have a unique name, SpeciesTEST already exists");
        }
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
        String name = "FREETEXTTEST";
        //String name = "sex";
        AnnotationDataType toBeRemoved = getSpecificAnnotationType(name);
        //System.out.println(toBeRemoved.name);
        if (toBeRemoved != null) {
            if (model.deleteAnnotation(toBeRemoved.name)){
                AnnotationDataType newAnnotation = getSpecificAnnotationType(name);
                System.out.println("newannoanem" + newAnnotation.name);
                assertThat(newAnnotation).isNull();
            } else {
                fail("could not do model.deleteAnnotation()");
            }
        }
        fail("Did not get annotation?");
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
    
    @Test
    public void shouldOnlyAddUniqueAnnotations() {
        String name = model.getAnnotations()[2].getName();
        try {
            model.addNewAnnotation(name, null, false);
            fail("This is not a unique annotation");
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(
                    "Annotations must have a unique name, " + name
                            + " already exists");
        }
    }
    
    @Test
    public void shouldTestEdit() {
        fail("Not implemented");
    }
    
}
