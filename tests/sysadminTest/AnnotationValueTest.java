package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import gui.sysadmin.SysadminTab;
import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.AnnotationDataType;

public class AnnotationValueTest {

    public Model model;
    public SysadminTab sysadminTab;
    public String nameOfAnnotation;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIp("dumbledore.cs.umu.se:7000");
        model.loginUser("SysadminTests", "baguette");
        sysadminTab = new SysadminTab();
        nameOfAnnotation = "AnnotationValueTest";

    }

    @After
    public void tearDown() throws Exception {
        model.deleteAnnotation(nameOfAnnotation);
    }

    @Test
    public void shouldChangeNameOfAnnotationValues() {
        model.addNewAnnotation(nameOfAnnotation, new String[] {"Unchanged"} , false);
        AnnotationDataType toBeChanged = getSpecificAnnotationType(nameOfAnnotation);
        String oldValue = "Unchanged";
        String newValue = "Changed";
        model.renameAnnotationValue(toBeChanged.name, oldValue, newValue);
        AnnotationDataType actual = getSpecificAnnotationType(nameOfAnnotation);
        assertThat(actual.getValues()[0]).isEqualTo(newValue);
    }

    @Test
    public void shouldAddAnnotationValue() {
        model.addNewAnnotation(nameOfAnnotation, new String[] {"1","2","3"} , false);
        String valueName = "4";
        AnnotationDataType toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
        int numberOfAnnotations = toBeEdited.getValues().length;
        if (toBeEdited != null) {
            if (model.addNewAnnotationValue(nameOfAnnotation, valueName)) {
                toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
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
        model.addNewAnnotation(nameOfAnnotation, new String[] {"1","2","3"} , false);
        AnnotationDataType toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
        int numberOfAnnotationValues = toBeEdited.getValues().length;
        if (toBeEdited != null) {
            if (model.removeAnnotationValue(toBeEdited.name, "2")) {
                toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
                assertThat(toBeEdited.getValues().length).isEqualTo(
                        numberOfAnnotationValues - 1);
            } else {
                fail("could not do model.removeAnnotationField()");
            }
        }
        assertThat(toBeEdited.getValues()[0].equals("1")).isTrue();
        assertThat(toBeEdited.getValues()[1].equals("3")).isTrue();
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
