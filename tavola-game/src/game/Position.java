/**
 * Przechowuje dane na temat pozycji.
 */
package game;

/**
 * @author sla
 * 
 */
public final class Position {

  public int horizontal;

  public int vertical;

  public Position(int horizontal, int vertical) {
    this.vertical = vertical;
    this.horizontal = horizontal;
  }

  public void add(Position p) {
    vertical += p.vertical;
    horizontal += p.horizontal;
  }

  @Override
  public Position clone() {
    return new Position(horizontal, vertical);
  }
}
