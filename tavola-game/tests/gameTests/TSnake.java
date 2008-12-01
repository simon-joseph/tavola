package gameTests;
import game.Snake;
import junit.framework.TestCase;



/**
 * @author sla
 *
 */
public class TSnake extends TestCase {
  Snake snake;
  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    snake = new Snake(60, 35);
  }

  /**
   * Test method for {@link game.Snake#changeDirection(interfaces.Direction)}.
   */
  public void testChangeDirection() {
    fail("Not yet implemented");
  }

  /**
   * Test method for {@link game.Snake#move()}.
   */
  public void testMove() {
    fail("Not yet implemented");
  }

}
