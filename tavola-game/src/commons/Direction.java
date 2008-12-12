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
	if (this == LEFT && d == RIGHT || this == RIGHT && d == LEFT
		|| this == UP && d == DOWN || this == DOWN && d == UP) {
	    return true;
	} else {
	    return false;
	}
    }

    public Position toPosition() {
	switch (this) {
	case RIGHT:
	    return new Position(1, 0);
	case LEFT:
	    return new Position(-1, 0);
	case UP:
	    return new Position(0, -1);
	case DOWN:
	    return new Position(0, 1);
	default:
	    return new Position(0, 0);
	}
    }
}
