package genomeReleaseTest;

import static org.fest.assertions.api.Assertions.assertThat;
import gui.sysadmin.SysadminController;
import model.Model;

import org.junit.Before;
import org.junit.Test;

public class GetSpecificGenomeReleasesTest {
    
    public Model model;
    private SysadminController controller;
    
    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIp("http://scratchy.cs.umu.se:7000");
        model.loginUser("SysadminGRTests", "umea@2014");
        controller = new SysadminController(model);
    }
    
    @Test
    public void shouldAddGenomeRelease(){
        assertThat(controller.addGenomRelease()).isTrue();
        
    }
}
