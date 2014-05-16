package sysadminTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ GetAnnotationTest.class, AnnotationFieldTest.class,
        AnnotationValueTest.class, RemoveAnnotationTest.class })
public class AnnotationTestSuite {
    
}
