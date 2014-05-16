package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import gui.sysadmin.SysadminTab;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import util.AnnotationDataType;
import communication.Connection;

public class AnnotationFieldTest {
    
    public Connection con;
    public Model model;
    public SysadminTab sysadminTab;
    
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
    public void shouldRenameAnnotationField(){
        String oldAnnotationName = "FREETEXTTEST";
        String newAnnotationName = "FREE TEXTTEST";
        AnnotationDataType annotation = getSpecificAnnotationType(oldAnnotationName);
        if (annotation != null) {
            if(model.renameAnnotationField(oldAnnotationName, newAnnotationName)) {
                annotation = getSpecificAnnotationType(newAnnotationName);
                assertThat(annotation.name).isEqualTo(newAnnotationName);
            } else {
                fail("model does not complete operation.");
            }
        } else {
            fail("Does not exist annotation named " + oldAnnotationName);
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
