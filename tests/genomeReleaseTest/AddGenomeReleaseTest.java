package genomeReleaseTest;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.fest.assertions.api.Assertions.fail;
import gui.sysadmin.SysadminController;
import gui.sysadmin.SysadminTab;
import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.GenomeReleaseData;
import communication.Connection;
import communication.ConnectionFactory;

public class AddGenomeReleaseTest {
    
    private Model model;
    private SysadminController controller;
    private final String SPECIES = "Fly";
    private final String VERSION = "TEST";
    private final String[] PATHS = new String[] {"/home/c12/c12jvr/Public/TESTgr1.test"};
    
    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIp("http://scratchy.cs.umu.se:7000");
        model.loginUser("SysadminGRTests", "umea@2014");
        controller = new SysadminController(model);
    }
    
    @Test
    public void shouldAddGenomRelease() {
        assertThat(model.uploadGenomeReleaseFile(PATHS, SPECIES, VERSION)).isTrue();
    }
    
    @After
    public void shouldRemoveAddedGenomeRelease() {
        model.deleteGenomeRelease(SPECIES, VERSION);
    }
}
