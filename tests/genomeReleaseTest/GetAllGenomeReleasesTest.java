package genomeReleaseTest;

import static org.fest.assertions.api.Assertions.assertThat;
import model.Model;
import model.SessionHandler;

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
        SessionHandler.getInstance().setIP(ExampleExperimentData.getTestServerIP());
        SessionHandler.getInstance().loginUser(ExampleExperimentData.getTestUsername(), ExampleExperimentData.getTestPassword());
        controller = new SysadminController(model,null);
    }

    @Test
    public void shouldGetAllSpecies(){
        //System.out.println(controller.getGenomeReleases()[0].getFilenames());
        assertThat(controller.getGenomeReleases()).isNotEmpty();
    }

    @Test
    public void shouldGetFileNames(){
        GenomeReleaseData[] gr = controller.getGenomeReleases();
//        System.out.println("l�ngd: " + gr.length + "f�rsta species: " + gr[0].getSpecies());
        assertThat(gr[0].getFilenames()).isNotEmpty();
    }
}
