package commons;

import java.awt.Color;

/**
 * @author ania
 * 
 */
public enum SnakeColor {
    YELLOW, GREEN, PINK, RED;

    public static Color toColor(int i) {
	switch (i) {
	case 0:
	    return Color.YELLOW;
	case 1:
	    return Color.GREEN;
	case 2:
	    return Color.PINK;
	case 3:
	    return Color.RED;
	default:
	    return Color.BLUE;
	}
    }

    public static int toInt(SnakeColor c) {
	switch (c) {
	case YELLOW:
	    return 0;
	case GREEN:
	    return 1;
	case PINK:
	    return 2;
	case RED:
	    return 3;
	default:
	    return 0;
	}
    }
}
