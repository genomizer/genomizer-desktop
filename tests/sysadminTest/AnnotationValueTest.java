package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import gui.sysadmin.SysadminTab;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import communication.Connection;
import util.AnnotationDataType;

public class AnnotationValueTest {
    
    public Model model;
    public SysadminTab sysadminTab;
    
    @Before
    public void setUp() throws Exception {
        // con = new Connection("genomizer.apiary-mock.com:80");
        model = new Model();
        model.setIp("http://scratchy.cs.umu.se:7000");
        // con.setIp("genomizer.apiary-mock.com:80");
        // con = new Connection("http://hagrid.cs.umu.se:7000");
        model.loginUser("SysadminTests", "qwerty");
        sysadminTab = new SysadminTab();
    }
    
    @Test
    public void shouldChangeNameOfAnnotationValues() {
        String nameOfAnnotation = "SpeciesTEST";
        AnnotationDataType toBeChanged = getSpecificAnnotationType(nameOfAnnotation);
        String oldValue = "humanTEST";
        String newValue = "manTEST";
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
                toBeEdited = getSpecificAnnotationType(annotationName);
                assertThat(toBeEdited.getValues().length).isEqualTo(
                        numberOfAnnotations + 1);
            } else {
                fail("Did not add new Annotationvalue!");
            }
        }
    }
    
    @Test
    public void shouldRemoveAnnotationValue() {
        // TODO: use AnnotationDataType.indexOf(String valueToBeremoved) and
        // remove a valueToBeRemoved!!!
        String nameOfAnnotation = "SpeciesTEST";
        String valueToBeRemoved = "horseTEST";
        AnnotationDataType toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
        int numberOfAnnotationValues = toBeEdited.getValues().length;
        if (toBeEdited != null) {
            if (model.removeAnnotationValue(toBeEdited.name, valueToBeRemoved)) {
                toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
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
