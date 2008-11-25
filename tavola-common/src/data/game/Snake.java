package data.game;

import java.util.ArrayList;

/**
 * @author Piotr Staszak
 * 
 */
public class Snake {

  private int snakeId;

  private int length;

  private Field firstField;

  private Field lastField;

  private ArrayList<Bonus> bonuses;

  public Snake(int snakeId, Field startField) {
    if (startField == null) {
      throw new IllegalArgumentException("Incorrect start field of snake.");
    }
    this.snakeId = snakeId;
    firstField = startField;
    lastField = startField;
    length = 1;
    assert invariant();
  }

  public int getSnakeId() {
    return snakeId;
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