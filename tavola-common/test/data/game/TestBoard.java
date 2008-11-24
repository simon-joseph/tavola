package data.game;

import java.util.Random;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author Piotr Staszak
 * 
 */
public class TestBoard extends TestCase {

  private int height;

  private int width;

  private int bonuses_number;

  private int snakes_number;

  private int seed;

  private Random random;

  private Board board;

  @Override
  protected void setUp() throws Exception {
    seed = 10;
    random = new Random(seed);
    snakes_number = 3;
    bonuses_number = 1;
    height = 5;
    width = 6;
    board = new Board(height, width, snakes_number, bonuses_number, seed);
  }

  public void testBoard() {
    for (int i = 0; i < snakes_number; i++) {
      Assert.assertTrue(board.getSnake(i) != null);
    }
    for (int i = 0; i < height * width; i++) {
      Assert.assertTrue(board.getField(i) != null);
    }
  }

  public void testSnakes() {
    Field headSnakeNr0 = board.getSnake(0).getFirstField();
    int field_number = board.getFields().indexOf(headSnakeNr0);
    Assert.assertTrue(field_number == random.nextInt(height * width));

    for (int i = 0; i < snakes_number; i++) {
      Field headSnake = board.getSnake(i).getFirstField();
      Assert.assertTrue(headSnake.getSnakePart() != null
          && headSnake.getSnakePart().isFirstPart());
      Field endSnake = board.getSnake(i).getLastField();
      Assert.assertTrue(endSnake.getSnakePart() != null
          && endSnake.getSnakePart().isLastPart());

      Assert.assertTrue(board.getSnake(i).getLength() > 1
          && !headSnake.getSnakePart().isLastPart()
          && !endSnake.getSnakePart().isFirstPart()
          || board.getSnake(i).getLength() == 1
          && headSnake.getSnakePart().isLastPart());
    }
  }

}