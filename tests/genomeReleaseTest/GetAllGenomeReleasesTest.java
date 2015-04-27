package genomeReleaseTest;

import static org.fest.assertions.api.Assertions.assertThat;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import controller.SysadminController;
import exampleData.ExampleExperimentData;

import util.GenomeReleaseData;

public class GetAllGenomeReleasesTest {

    public Model model;
    private SysadminController controller;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIp(ExampleExperimentData.getTestServerIP());
        model.loginUser(ExampleExperimentData.getTestUsername(), ExampleExperimentData.getTestPassword());
        controller = new SysadminController(model);
    }

    @Test
    public void shouldGetAllSpecies(){
        //System.out.println(controller.getGenomeReleases()[0].getFilenames());
        assertThat(controller.getGenomeReleases()).isNotEmpty();
    }

    @Test
    public void shouldGetFileNames(){
        GenomeReleaseData[] gr = controller.getGenomeReleases();
//        System.out.println("längd: " + gr.length + "första species: " + gr[0].getSpecies());
        assertThat(gr[0].getFilenames()).isNotEmpty();
    }
}
