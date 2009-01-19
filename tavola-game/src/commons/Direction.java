package commons;

/**
 * Describes directions of movement in game. LEFT, RIGHT, UP, DOWN.
 * 
 * @author sla
 * 
 */
public enum Direction {
    DOWN, LEFT, UP, RIGHT, DEATH;

    /**
     * @param direction
     * @return true if direction is opposite to this, and false otherwise
     */
    public boolean opposite(Direction direction) {
	if (this == LEFT && direction == RIGHT || this == RIGHT
		&& direction == LEFT || this == UP && direction == DOWN
		|| this == DOWN && direction == UP) {
	    return true;
	} else {
	    return false;
	}
    }

    /**
     * Translates this to corresponding Position value. In horizontal LEFT means
     * lower values, and RIGHT higher values. Yet in vertical UP means lower
     * values and DOWN means higher values because the board representing
     * in-game elements are drawn from left to right, and from up to down.
     * 
     * @return translation from Direction to one step as Position
     */
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

    @Override
    public String toString() {
	if (this == UP) {
	    return "0";
	}
	if (this == RIGHT) {
	    return "1";
	}
	if (this == DOWN) {
	    return "2";
	}
	if (this == LEFT) {
	    return "3";
	}
	return "DEATH";
    }

    public static Direction fromString(String s) {
	if (s != null) {
	    if (s.equals("0")) {
		return Direction.UP;
	    }
	    if (s.equals("1")) {
		return Direction.RIGHT;
	    }
	    if (s.equals("2")) {
		return Direction.DOWN;
	    }
	    if (s.equals("3")) {
		return Direction.LEFT;
	    }
	    if (s.equals("DEATH")) {
		return Direction.DEATH;
	    }
	}
	throw new IllegalArgumentException();
    }

}
