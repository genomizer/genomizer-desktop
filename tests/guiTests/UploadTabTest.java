package guiTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gui.UploadTab;
import model.Model;

import org.junit.Before;
import org.junit.Test;

public class UploadTabTest {

    private UploadTab uploadTab;
    private Model model;

    @Before
    public void setUp() {
        uploadTab = new UploadTab();

    }

    @Test
    public void shouldReturnTrueIfForcedAnnotationFieldsAreFilled() {
        //Fill fields
        // TODO: Fill fields is not done. OO

        //Assert
        assertTrue(uploadTab.getNewExpPanel().forcedAnnotationCheck());
    }

    @Test
    public void shouldReturnFalseIfForcedAnnotationFieldsAreNotFilled() {
        assertFalse(uploadTab.getNewExpPanel().forcedAnnotationCheck());
    }
}
