/**
 * Przechowuje dane na temat pozycji.
 */
package game;

/**
 * @author sla
 * 
 */
public final class Position {
	public int vertical;
	public int horizontal;

	public Position(int vertical, int horizontal) {
		this.vertical = vertical;
		this.horizontal = horizontal;
	}

	public void add(Position p) {
		this.vertical += p.vertical;
		this.horizontal += p.horizontal;
	}
}
