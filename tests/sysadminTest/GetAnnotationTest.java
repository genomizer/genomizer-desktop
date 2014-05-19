package sysadminTest;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import gui.sysadmin.SysadminTab;
import model.Model;
import communication.Connection;

public class GetAnnotationTest {
    
    private Model model;
    private SysadminTab sysadminTab;
    
    @Before
    public void setUp() throws Exception {
        // con = new Connection("genomizer.apiary-mock.com:80");
        // con = new Connection("http://hagrid.cs.umu.se:7000");
        model = new Model();
        model.setIp("http://scratchy.cs.umu.se:7000");
        model.loginUser("SysadminTests", "qwerty");
        sysadminTab = new SysadminTab();
    }
    
    @Test
    public void shouldGetAnnotationsShouldNotBeNull() {
        assertThat(model.getAnnotations()).isNotNull();
    }
    
    @Test
    public void shouldCellLineAnnotation() {
        assertThat(model.getAnnotations()[0].toString()).isEqualTo(
                "Cell Line");
    }
    
    @Test
    public void shouldGetSpeciesValues() {
        String[] actual = model.getAnnotations()[0].getValues();
        String[] expected = new String[] { "yes", "no" };
        assertThat(actual).isEqualTo(expected);
    }
    
}
