package genomeReleaseTest;

import static org.fest.assertions.api.Assertions.assertThat;
import gui.sysadmin.SysadminController;
import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AddGenomeReleaseTest {
    
    private Model model;
    private SysadminController controller;
    private final String SPECIES = "Fly";
    private final String VERSION = "TEST";
    //private final String[] PATHS = new String[] {"/home/c12/c12jvr/Public/TESTgr1.test"};
    private final String[] PATHS = new String[] {"C:/TEST/TESTgr1.txt"};
    
    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIp("http://scratchy.cs.umu.se:7000");
        model.loginUser("SysadminGRTests", "umea@2014");
        controller = new SysadminController(model);
    }
    
    @Test
    public void shouldAddGenomRelease() {
        assertThat(model.addGenomeReleaseFile(PATHS, SPECIES, VERSION)).isTrue();
    }
    
    @After
    public void shouldRemoveAddedGenomeRelease() {
        model.deleteGenomeRelease(SPECIES, VERSION);
    }
}
