package genomeReleaseTest;

import static org.fest.assertions.api.Assertions.assertThat;
import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controller.SysadminController;
import exampleData.ExampleExperimentData;

public class AddGenomeReleaseTest {

    private Model model;
    private SysadminController controller;
    private final String SPECIES = "Fly";
    private final String VERSION = "TEST";
    private final String[] PATHS = new String[] {"/home/c12/c12jhn/.testupload.txt"};

    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIP(ExampleExperimentData.getTestServerIP());
        model.loginUser(ExampleExperimentData.getTestUsername(), ExampleExperimentData.getTestPassword());
        controller = new SysadminController(model);
    }

    // TODO: Not implemented (JH)
    @Test
    public void shouldAddGenomRelease() {
        assertThat(model.addGenomeReleaseFile(PATHS, SPECIES, VERSION)).isTrue();
    }

    @After
    public void shouldRemoveAddedGenomeRelease() {
        model.deleteGenomeRelease(SPECIES, VERSION);
    }
}
