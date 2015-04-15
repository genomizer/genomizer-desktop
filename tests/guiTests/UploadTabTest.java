package guiTests;

import gui.UploadTab;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UploadTabTest {

    private UploadTab uploadTab;

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
