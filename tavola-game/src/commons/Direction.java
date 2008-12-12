/**
 * Opisuje kierunki ruchu. LEFT, RIGHT, UP, DOWN.
 */
package commons;

/**
 * @author sla
 * 
 */
public enum Direction {
  LEFT, RIGHT, UP, DOWN;

  public boolean opposite(Direction d) {
    if (this == LEFT && d == RIGHT || this == RIGHT && d == LEFT || this == UP
        && d == DOWN || this == DOWN && d == UP)
      return true;
    else
      return false;
  }
}
