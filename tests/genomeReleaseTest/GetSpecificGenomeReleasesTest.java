package genomeReleaseTest;

import static org.fest.assertions.api.Assertions.assertThat;
import model.Model;

import org.junit.Before;
import org.junit.Test;

import controller.SysadminController;
import exampleData.ExampleExperimentData;

public class GetSpecificGenomeReleasesTest {

    public Model model;
    private SysadminController controller;

    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIP(ExampleExperimentData.getTestServerIP());
        model.loginUser(ExampleExperimentData.getTestUsername(), ExampleExperimentData.getTestPassword());
        controller = new SysadminController(model);
    }
    // TODO: Not implemented (JH)
    @Test
    public void shouldAddGenomeRelease(){
        assertThat(controller.addGenomRelease()).isTrue();

    }
}
