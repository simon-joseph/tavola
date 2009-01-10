package applet;

import java.awt.Color;
import java.awt.Graphics;

import commons.BodyParts;
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
     * @param direction
     */
    public void paintBodyPart(Graphics g, Position part, BodyParts bodyPart) {
	if (part != null) {
	    g.setColor(Color.pink);
	    g.fillOval(part.x() * fieldSize, part.y() * fieldSize, fieldSize,
		    fieldSize);
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

    /**
     * @param g
     * @param last
     */
    public void paintLast(Graphics g, Position last, Direction direction) {
	if (last != null) {
	    g.setColor(Color.pink);
	    g.fillOval(last.x() * fieldSize, last.y() * fieldSize, fieldSize,
		    fieldSize);
	}
    }

    public void paintBoard(Graphics g, int width, int height) {
	g.setColor(Color.BLUE);
	g.fillRect(0, 0, width, height);
	g.setColor(Color.BLACK);
    }

    public void paintStatBoard(Graphics g, int width, int height) {
	g.setColor(Color.GRAY);
	g.fillRect(width, 0, 100, height);
    }

}
