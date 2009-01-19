package applet;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

import commons.BodyParts;
import commons.Direction;
import commons.Position;
import commons.SnakeColor;

/**
 * @author sla + agl
 * 
 */
public class DefaultTheme {
    public final Color background = new Color(100, 100, 150);
    public final int fieldSize = 10;

    public void paintBonus(Graphics g, Vector<Position> bonuses) {
	if (bonuses != null) {
	    g.setColor(Color.green);
	    for(Position bonus : bonuses)
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
    public void paintBodyPart(Graphics g, Position part, BodyParts bodyPart,
	    int id) {
	if (part != null) {
	    g.setColor(SnakeColor.toColor(id));
	    g.fillOval(part.x() * fieldSize, part.y() * fieldSize, fieldSize,
		    fieldSize);
	}
    }

    public void paintHead(Graphics g, Position head, Direction direction, int id) {
	if (head != null) {
	    g.setColor(SnakeColor.toColor(id));
	    g.fillOval(head.x() * fieldSize, head.y() * fieldSize, fieldSize,
		    fieldSize);
	}
    }

    public void paintLast(Graphics g, Position last, Direction direction, int id) {
	if (last != null) {
	    g.setColor(SnakeColor.toColor(id));
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
