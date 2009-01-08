package applet;

import java.awt.Color;
import java.awt.Graphics;

import commons.Direction;
import commons.Position;

/**
 * @author sla + agl
 * 
 */
public class DefaultTheme {
    public final Color background = new Color(100, 100, 150);
    public final int fieldSize = 10;

    public void paintBonus(Graphics g, Position bonus) {
	if (bonus != null) {
	    g.setColor(Color.green);
	    g.fillOval(bonus.x() * fieldSize, bonus.y() * fieldSize, fieldSize,
		    fieldSize);
	}
    }

    /**
     * @param g
     * @param toPaint
     * @param reverseDirection
     */
    public void paintBodyPart(Graphics g, Position toPaint,
	    Direction reverseDirection) {
	if (toPaint != null) {
	    g.setColor(Color.pink);
	    g.fillOval(toPaint.x() * fieldSize, toPaint.y() * fieldSize,
		    fieldSize, fieldSize);
	}
    }

    /**
     * @param g
     * @param head
     * @param direction
     */
    public void paintHead(Graphics g, Position head, Direction direction) {
	if (head != null) {
	    g.setColor(Color.orange);
	    g.fillOval(head.x() * fieldSize, head.y() * fieldSize, fieldSize,
		    fieldSize);
	}
    }

}
