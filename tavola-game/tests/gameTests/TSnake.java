package gameTests;

import game.Snake;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author sla
 * 
 */
public class TSnake extends TestCase {
    Snake snake;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	snake = new Snake();
    }

    /**
     * Test method for {@link game.Snake#changeDirection(interfaces.Direction)}.
     */
    public void testChangeDirection() {
	Assert.fail("Not yet implemented");
    }

    /**
     * Test method for {@link game.Snake#move()}.
     */
    public void testMove() {
	Assert.fail("Not yet implemented");
    }

}
