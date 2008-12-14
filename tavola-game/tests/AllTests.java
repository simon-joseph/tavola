import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author sla
 * 
 */
public class AllTests {

    public static Test suite() {
	TestSuite suite = new TestSuite("Test for default package");
	// $JUnit-BEGIN$
	suite.addTestSuite(TPosition.class);
	suite.addTestSuite(TSnake.class);
	suite.addTestSuite(TPosition.class);
	// $JUnit-END$
	return suite;
    }

}
