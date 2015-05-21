package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import exampleData.ExampleExperimentData;
import gui.sysadmin.SysadminTab;
import model.Model;
import model.SessionHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import communication.SSLTool;

import util.AnnotationDataType;
import util.RequestException;

public class AnnotationValueTest {

    public Model model;
    public SysadminTab sysadminTab;
    public String nameOfAnnotation;
    SessionHandler s = SessionHandler.getInstance();


    @Before
    public void setUp() throws Exception {
        SSLTool.disableCertificateValidation();
        model = new Model();
        model.setIP(ExampleExperimentData.getTestServerIP());
        s.setIP(ExampleExperimentData.getTestServerIP());
        s.loginUser(ExampleExperimentData.getTestUsername(), ExampleExperimentData.getTestPassword());
        sysadminTab = new SysadminTab();
        nameOfAnnotation = "AnnotationValueTest";

    }

    @After
    public void tearDown() throws Exception {
        model.deleteAnnotation(nameOfAnnotation);
    }

    @Test
    public void shouldChangeNameOfAnnotationValues() {
        try {
            model.addNewAnnotation(nameOfAnnotation, new String[] {"Unchanged"} , false);
            AnnotationDataType toBeChanged = getSpecificAnnotationType(nameOfAnnotation);
            String oldValue = "Unchanged";
            String newValue = "Changed";
            model.renameAnnotationValue(toBeChanged.name, oldValue, newValue);
            AnnotationDataType actual = getSpecificAnnotationType(nameOfAnnotation);
            assertThat(actual.getValues()[0]).isEqualTo(newValue);
        } catch (Exception e) {

        }

    }

    @Test
    public void shouldAddAnnotationValue() {
        try {
            model.addNewAnnotation(nameOfAnnotation, new String[] {"1","2","3"} , false);
        } catch (Exception e1) {
            fail("Exception were thrown");
        }
        String valueName = "4";
        AnnotationDataType toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
        int numberOfAnnotations = toBeEdited.getValues().length;
        if (toBeEdited != null) {
            try {
                model.addNewAnnotationValue(nameOfAnnotation, valueName);
                toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
                assertThat(toBeEdited.getValues().length).isEqualTo(
                        numberOfAnnotations + 1);
            } catch (Exception e) {
                fail("Did not add new Annotationvalue!");
            }

        }
    }

    @Test
    public void shouldRemoveAnnotationValue() {
        // TODO: use AnnotationDataType.indexOf(String valueToBeremoved) and
        // remove a valueToBeRemoved!!!
        try {
            model.addNewAnnotation(nameOfAnnotation, new String[] {"1","2","3"} , false);
        } catch (Exception e1) {
            fail("Exception were thrown");
        }
        AnnotationDataType toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
        int numberOfAnnotationValues = toBeEdited.getValues().length;
        if (toBeEdited != null) {
            try {
                model.removeAnnotationValue(toBeEdited.name, "2");
                toBeEdited = getSpecificAnnotationType(nameOfAnnotation);
                assertThat(toBeEdited.getValues().length).isEqualTo(
                        numberOfAnnotationValues - 1);
            } catch (Exception e) {
                fail("Exception were thrown");
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
