package data.game;

/**
 * @author Piotr Staszak
 * 
 */
public class Field {

  private Bonus bonus;

  private Obstruction obstruction;

  private SnakePart snakePart;

  /**
   * @return the bonus
   */
  public Bonus getBonus() {
    return bonus;
  }

  /**
   * @return the obstruction
   */
  public Obstruction getObstruction() {
    return obstruction;
  }

  /**
   * @return the snakeField
   */
  public SnakePart getSnakePart() {
    return snakePart;
  }

  /**
   * @param snakePart
   *          the snakePart to set
   */
  public void setSnakePart(SnakePart snakePart) {
    this.snakePart = snakePart;
  }

}