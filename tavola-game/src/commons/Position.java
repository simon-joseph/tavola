/**
 * Contains information about position in horizontal and vertical.
 */
package commons;

/**
 * @author sla
 * 
 */
public final class Position {

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
}
