package data.game;

/**
 * @author Piotr Staszak
 * 
 */
public class Field {

  private Bonus bonus;

  private Obstruction obstruction;

  private SnakePart snakePart;

  public Bonus getBonus() {
    return bonus;
  }

  public Obstruction getObstruction() {
    return obstruction;
  }

  public SnakePart getSnakePart() {
    return snakePart;
  }

  public void setSnakePart(SnakePart snakePart) {
    this.snakePart = snakePart;
  }

}