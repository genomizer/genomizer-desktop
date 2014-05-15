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

        //Assert
        assertTrue(uploadTab.forcedAnnotationCheck());
    }

    @Test
    public void shouldReturnFalseIfForcedAnnotationFieldsAreNotFilled() {
        assertFalse(uploadTab.forcedAnnotationCheck());
    }
}
