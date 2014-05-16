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
        // con = new Connection("genomizer.apiary-mock.com:80");
        con = new Connection();
        con.setIp("http://scratchy.cs.umu.se:7000");
        // con = new Connection("http://hagrid.cs.umu.se:7000");
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
        assertThat(model.getAnnotations()[0].toString()).isEqualTo(
                "Development Stage");
    }
    
    @Test
    public void shouldGetSpeciesValues() {
        String[] actual = model.getAnnotations()[0].getValues();
        String[] expected = new String[] { "freetext" };
        assertThat(actual).isEqualTo(expected);
    }
    
    @Test
    public void shouldAddNewFreeTextAnnotation() {
        assertThat(
                model.addNewAnnotation("FREE TEXTTEST",
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
            assertThat(e)
                    .hasMessage(
                            "Annotations must have a unique name, SpeciesTEST already exists");
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
        String name = "FREE TEXTTEST";
        AnnotationDataType toBeRemoved = getSpecificAnnotationType(name);
        if (toBeRemoved != null) {
            if (model.deleteAnnotation(toBeRemoved.name)) {
                AnnotationDataType newAnnotation = getSpecificAnnotationType(name);
                assertThat(newAnnotation).isNull();
            } else {
                fail("could not do model.deleteAnnotation()");
            }
        } else {
            fail("did not find toberemoved annotation");
        }
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
    public void shouldChangeNameOfAnnotation() {
        String oldname = "SpeciesTEST";
        AnnotationDataType toBeRenamed = getSpecificAnnotationType(oldname);
        if (toBeRenamed != null) {
            String newname = "SpeciesRenamedTEST";
            if (model.renameAnnotationField(oldname, newname)) {
                AnnotationDataType renamed = getSpecificAnnotationType(newname);
                assertThat(renamed).isNotNull();
            } else {
                fail("Does not rename Annotaion");
            }
        }
    }
    
    @Test
    public void shouldChangeNameOfAnnotationValues() {
        String nameOfAnnotation = "SpeciesTEST";
        AnnotationDataType toBeChanged = getSpecificAnnotationType(nameOfAnnotation);
        String oldValue = "manTEST";
        String newValue = "humanTEST";
        model.renameAnnotationValue(toBeChanged.name, oldValue, newValue);
        AnnotationDataType actual = getSpecificAnnotationType(nameOfAnnotation);
        assertThat(actual.getValues()[0]).isEqualTo(newValue);
    }
    
    @Test
    public void shouldAddAnnotationValue() {
        String annotationName = "SpeciesTEST";
        String valueName = "horseTEST";
        AnnotationDataType toBeEdited = getSpecificAnnotationType(annotationName);
        int numberOfAnnotations = toBeEdited.getValues().length;
        if (toBeEdited != null) {
            if (model.addNewAnnotationValue(annotationName, valueName)) {
                assertThat(toBeEdited.getValues().length).isEqualTo(
                        numberOfAnnotations + 1);
            }
        }
    }
    
    @Test
    public void shouldRemoveAnnotationValue() {
        // TODO: use AnnotationDataType.indexOf(String valueToBeremoved) and
        // remove a valueToBeRemoved!!!
        String nameOfAnnotation = "SpeciesTEST";
        String valueToBeRemoved = "manTEST";
        AnnotationDataType toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
        int numberOfAnnotationValues = toBeEdited.getValues().length;
        if (toBeEdited != null) {
            if (model.removeAnnotationField(toBeEdited.name, valueToBeRemoved)) {
                assertThat(toBeEdited.getValues().length).isEqualTo(
                        numberOfAnnotationValues - 1);
            } else {
                fail("could not do model.removeAnnotationField()");
            }
        }
        fail("Not implemented yet!");
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
