package gameTests;

import game.SnakeBoard;
import junit.framework.TestCase;

/**
 * @author sla
 * 
 */
public class TSnakeBoard extends TestCase {

  SnakeBoard snakeBoard;

  /*
   * (non-Javadoc)
   * 
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  protected void setUp() throws Exception {
    snakeBoard = new SnakeBoard();
  }

  /**
   * Test method for {@link game.SnakeBoard#update()}.
   */
  public void testUpdate() {
    int updates = 0;
    while (!snakeBoard.isGameOver()) {
      snakeBoard.update();
      updates++;
    }
    System.out.println("testUpdate\nupdates:" + updates);
  }

}
