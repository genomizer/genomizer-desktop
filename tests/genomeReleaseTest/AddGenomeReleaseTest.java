package genomeReleaseTest;

import static org.junit.Assert.fail;
import model.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.RequestException;
import controller.SysadminController;
import exampleData.ExampleExperimentData;

public class AddGenomeReleaseTest {

    private Model model;
    private SysadminController controller;
    private final String SPECIES = "Fly";
    private final String VERSION = "TEST";
    private final String[] PATHS = new String[] { "/home/c12/c12jhn/.testupload.txt" };

    @Before
    public void setUp() throws Exception {
        model = new Model();
        model.setIP(ExampleExperimentData.getTestServerIP());
        model.loginUser(ExampleExperimentData.getTestUsername(),
                ExampleExperimentData.getTestPassword());
        controller = new SysadminController(model);
    }

    // TODO: Not implemented (JH)
    @Test
    public void shouldAddGenomRelease() {

        try {
            model.addGenomeReleaseFile(PATHS, SPECIES, VERSION);
        } catch (Exception e) {
            fail("Exception was thrown");
        }

    }

    @After
    public void shouldRemoveAddedGenomeRelease() {
        try {
            model.deleteGenomeRelease(SPECIES, VERSION);
        } catch (RequestException e) {
            fail("Exception was thrown");
        }
    }
}
