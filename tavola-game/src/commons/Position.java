/**
 * Contains information about position in horizontal and vertical.
 */
package commons;

/**
 * @author sla
 * 
 */
public final class Position extends Object {

    private int horizontal;
    private int vertical;

    public Position(int horizontal, int vertical) {
	setVertical(vertical);
	setHorizontal(horizontal);
    }

    /**
     * Modifies this by adding position parameter.
     * 
     * @param position
     */
    public void add(Position position) {
	setVertical(y() + position.y());
	setHorizontal(x() + position.x());
    }

    public Direction directionTo(Position target) {
	if (x() == target.x()) {
	    if (y() < target.y()) {
		return Direction.DOWN;
	    } else {
		return Direction.UP;
	    }
	} else {
	    if (x() < target.x()) {
		return Direction.RIGHT;
	    } else {
		return Direction.LEFT;
	    }
	}
    }

    @Override
    public Position clone() {
	return new Position(x(), y());
    }

    /**
     * @return the horizontal
     */
    public int x() {
	return horizontal;
    }

    /**
     * @return the vertical
     */
    public int y() {
	return vertical;
    }

    /**
     * @param horizontal
     *            the horizontal to set
     */
    public void setHorizontal(int horizontal) {
	this.horizontal = horizontal;
    }

    /**
     * @param vertical
     *            the vertical to set
     */
    public void setVertical(int vertical) {
	this.vertical = vertical;
    }

    @Override
    public boolean equals(Object p) {
	return vertical == ((Position) p).vertical
		&& horizontal == ((Position) p).horizontal;
    }

    @Override
    public String toString() {
	return String.valueOf(horizontal) + ":" + String.valueOf(vertical);
    }
}
