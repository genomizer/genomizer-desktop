package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import exampleData.ExampleExperimentData;
import gui.sysadmin.SysadminTab;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import util.AnnotationDataType;

public class AnnotationFieldTest {

    public Model model;
    public SysadminTab sysadminTab;

    @Before
    public void setUp() throws Exception {

        model = new Model();
        model.setIP(ExampleExperimentData.getTestServerIP());
        model.loginUser(ExampleExperimentData.getTestUsername(), ExampleExperimentData.getTestPassword());
        sysadminTab = new SysadminTab();
    }

    @Test
    public void shouldAddNewFreeTextAnnotation() throws Exception {
        String oldname = "FREETEXTTEST";
        if (getSpecificAnnotationType(oldname) != null) {
            model.deleteAnnotation(oldname);
        }
        assertThat(
                model.addNewAnnotation("FREETEXTTEST",
                        new String[] { "freetext" }, false)).isTrue();
    }

    @Test
    public void shouldAddNewAnnotation() throws Exception {
        String oldname = "SpeciesTEST";
        if (getSpecificAnnotationType(oldname) != null) {
            model.deleteAnnotation(oldname);
        }
        assertThat(
                model.addNewAnnotation(oldname, new String[] { "manTEST",
                        "mouseTEST" }, false)).isTrue();

    }

    @Test
    public void shouldNotAddNewAnnotation() {
        try {
            model.addNewAnnotation("SpeciesTEST", null, false);
            model.addNewAnnotation("SpeciesTEST", null, false);
            fail("This should throw an illegalargumentexception");
        } catch (Exception e) {
            assertThat(e)
                    .hasMessage(
                            "Annotations must have a unique name, SpeciesTEST already exists");
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
    public void shouldChangeNameOfAnnotation() throws Exception {
        String oldname = "SpeciesTEST";
        String newname = "SpeciesRenamedTEST";
        if (getSpecificAnnotationType(oldname) != null) {
            model.deleteAnnotation(oldname);
        }
        if (getSpecificAnnotationType(newname) != null) {
            model.deleteAnnotation(newname);
        }

        model.addNewAnnotation(oldname,
                new String[] { "manTEST", "mouseTEST" }, false);
        AnnotationDataType toBeRenamed = getSpecificAnnotationType(oldname);
        if (toBeRenamed != null) {

            if (model.renameAnnotationField(oldname, newname)) {
                AnnotationDataType renamed = getSpecificAnnotationType(newname);
                assertThat(renamed).isNotNull();
            } else {
                fail("Does not rename Annotation");
            }
        }

    }

    @Test
    public void shouldRenameAnnotationField() throws Exception {
        String oldAnnotationName = "FREETEXTTEST";
        String newAnnotationName = "RENAMEDFREETEXTTEST";
        AnnotationDataType annotation = getSpecificAnnotationType(oldAnnotationName);
        if (getSpecificAnnotationType(newAnnotationName) != null) {
            model.deleteAnnotation(newAnnotationName);
        }
        if (annotation == null) {
            model.addNewAnnotation(oldAnnotationName, null, false);
        }
        if (model.renameAnnotationField(oldAnnotationName, newAnnotationName)) {
            annotation = getSpecificAnnotationType(newAnnotationName);
            assertThat(annotation.name).isEqualTo(newAnnotationName);
        } else {
            fail("model does not complete operation.");
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
