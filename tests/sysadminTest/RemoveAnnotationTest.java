package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import gui.sysadmin.SysadminTab;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import util.AnnotationDataType;
import communication.Connection;

public class RemoveAnnotationTest {
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