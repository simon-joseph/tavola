package data.game;

import java.util.ArrayList;

/**
 * @author Piotr Staszak
 * 
 */
public class Snake {

  private int snake_id;

  private int length;

  private Field firstField;

  private Field lastField;

  private ArrayList<Bonus> bonuses;

  /**
   * @param snake_id
   * @param startField
   */
  public Snake(int snake_id, Field startField) {
    if (startField == null) {
      throw new IllegalArgumentException("Incorrect start field of snake.");
    }
    this.snake_id = snake_id;
    firstField = startField;
    lastField = startField;
    length = 1;
    assert invariant();
  }

  /**
   * @return the snake_id
   */
  public int getSnake_id() {
    return snake_id;
  }

  /**
   * @return the length of snake
   */
  public int getLength() {
    return length;
  }

  /**
   * @return the position of snake head
   */
  public Field getFirstField() {
    return firstField;
  }

  /**
   * @return the last field of snake
   */
  public Field getLastField() {
    return lastField;
  }

  /**
   * @return the bonuses
   */
  public ArrayList<Bonus> getBonuses() {
    return bonuses;
  }

  private boolean invariant() {
    return firstField != null && lastField != null && length > 0;
  }

}