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

  public int getSnake_id() {
    return snake_id;
  }

  public int getLength() {
    return length;
  }

  public Field getFirstField() {
    return firstField;
  }

  public Field getLastField() {
    return lastField;
  }

  public ArrayList<Bonus> getBonuses() {
    return bonuses;
  }

  private boolean invariant() {
    return firstField != null && lastField != null && length > 0;
  }

}