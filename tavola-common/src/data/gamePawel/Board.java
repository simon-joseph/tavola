package data.gamePawel;

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

  private int bonusesNumber, snakesNumber;

  private Random random;

  private ArrayList<Field> fields;

  private ArrayList<Bonus> bonuses;

  private ArrayList<Obstruction> obstructions;

  private ArrayList<Snake> snakes;

  public Board(int height, int width, int snakesNumber, int bonusesNumber,
      long seed) {
    if (height < Board.MIN_HEIGHT || height > Board.MAX_HEIGHT
        || width < Board.MIN_WIDTH || width > Board.MAX_WIDTH) {
      throw new IllegalArgumentException("Incorrect size of board.");
    }
    if (snakesNumber < Board.MIN_SNAKES_NUMBER
        || snakesNumber > Board.MAX_SNAKES_NUMBER) {
      throw new IllegalArgumentException("Incorrect number of snakes.");
    }

    this.height = height;
    this.width = width;
    this.bonusesNumber = bonusesNumber;
    this.snakesNumber = snakesNumber;
    random = new Random(seed);

    fields = new ArrayList<Field>();
    for (int i = 0; i < height * width; i++) {
      fields.add(new Field());
    }

    snakes = new ArrayList<Snake>();
    addSnakes(snakesNumber);

    assert invariant();
  }

  public void addSnakes(int snakesNumber) {
    int randomNumber;

    for (int i = 0; i < snakesNumber; i++) {
      do {
        randomNumber = random.nextInt(height * width);
      } while (getField(randomNumber).getSnakePart() != null);

      snakes.add(new Snake(snakes.size(), fields.get(randomNumber)));
      getField(randomNumber).setSnakePart(new SnakePart());
    }
    assert invariant();
  }

  public ArrayList<Snake> getSnakes() {
    return snakes;
  }

  public Snake getSnake(int snakeId) {
    return snakes.get(snakeId);
  }

  public ArrayList<Field> getFields() {
    return fields;
  }

  public Field getField(int fieldNumber) {
    return fields.get(fieldNumber);
  }

  public int getBonusesNumber() {
    return bonusesNumber;
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
        && snakesNumber >= Board.MIN_SNAKES_NUMBER
        && snakesNumber <= Board.MAX_SNAKES_NUMBER;
  }

}