package genomeReleaseTest;

import static org.fest.assertions.api.Assertions.assertThat;
import gui.sysadmin.SysadminController;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import util.GenomeReleaseData;

public class GetAllGenomeReleasesTest {
    
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
    public void shouldGetAllSpecies(){
        System.out.println(controller.getGenomeReleases()[0].getFilenames());
        assertThat(controller.getGenomeReleases()).isNotEmpty();
    }
    
    @Test
    public void shouldGetFileNames(){
        GenomeReleaseData[] gr = controller.getGenomeReleases();
//        System.out.println("längd: " + gr.length + "första species: " + gr[0].getSpecies());
        assertThat(gr[0].getFilenames()).isNotEmpty();
    }
}
