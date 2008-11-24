package data.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Piotr Staszak
 * 
 */
public class Board {

  private final static int MIN_HEIGHT = 3;

  private final static int MIN_WIDTH = 4;

  private final static int MAX_HEIGHT = 10;

  private final static int MAX_WIDTH = 12;

  private final static int MIN_SNAKES_NUMBER = 1;

  private final static int MAX_SNAKES_NUMBER = 4;

  private int height, width;

  private int bonuses_number, snakes_number;

  private Random random;

  private ArrayList<Field> fields;

  private ArrayList<Bonus> bonuses;

  private ArrayList<Obstruction> obstructions;

  private ArrayList<Snake> snakes;

  public Board(int height, int width, int snakes_number, int bonuses_number,
      long seed) {
    if (height < Board.MIN_HEIGHT || height > Board.MAX_HEIGHT
        || width < Board.MIN_WIDTH || width > Board.MAX_WIDTH) {
      throw new IllegalArgumentException("Incorrect size of board.");
    }
    if (snakes_number < Board.MIN_SNAKES_NUMBER
        || snakes_number > Board.MAX_SNAKES_NUMBER) {
      throw new IllegalArgumentException("Incorrect number of snakes.");
    }

    this.height = height;
    this.width = width;
    this.bonuses_number = bonuses_number;
    this.snakes_number = snakes_number;
    random = new Random(seed);

    fields = new ArrayList<Field>();
    for (int i = 0; i < height * width; i++) {
      fields.add(new Field());
    }

    snakes = new ArrayList<Snake>();
    addSnakes(snakes_number);

    assert invariant();
  }

  public void addSnakes(int snakes_number) {
    int random_number;

    for (int i = 0; i < snakes_number; i++) {
      do {
        random_number = random.nextInt(height * width);
      } while (getField(random_number).getSnakePart() != null);

      snakes.add(new Snake(snakes.size(), fields.get(random_number)));
      getField(random_number).setSnakePart(new SnakePart());
    }
    assert invariant();
  }

  public ArrayList<Snake> getSnakes() {
    return snakes;
  }

  public Snake getSnake(int snake_id) {
    return snakes.get(snake_id);
  }

  public ArrayList<Field> getFields() {
    return fields;
  }

  public Field getField(int field_number) {
    return fields.get(field_number);
  }

  public int getBonuses_number() {
    return bonuses_number;
  }

  public ArrayList<Bonus> getBonuses() {
    return bonuses;
  }

  public ArrayList<Obstruction> getObstructions() {
    return obstructions;
  }

  private boolean invariant() {
    return height >= Board.MIN_HEIGHT && height <= Board.MAX_HEIGHT
        && width >= Board.MIN_WIDTH && width <= Board.MAX_WIDTH
        && snakes_number >= Board.MIN_SNAKES_NUMBER
        && snakes_number <= Board.MAX_SNAKES_NUMBER;
  }

}