package applet;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import commons.Position;

/**
 * @author sla + agl
 * 
 */
public class DefaultTheme {
    public final Color background = new Color(100, 100, 150);
    public final int fieldSize = 10;

    public void eraseLast(Graphics g, Position last) {
	if (last != null) {
	    g.setColor(new Color(100, 100, 150));
	    g.fillOval(last.x() * fieldSize, last.y() * fieldSize, fieldSize,
		    fieldSize);
	}

    }

    public void changeOldHead(Graphics g, LinkedList<Position> body) {
	if (!body.isEmpty()) {
	    g.setColor(Color.pink);
	    g.fillOval(body.getFirst().x() * fieldSize, body.getFirst().y()
		    * fieldSize, fieldSize, fieldSize);
	}
    }

    public void paintNewHead(Graphics g, Position head) {
	if (head != null) {
	    g.setColor(Color.orange);
	    g.fillOval(head.x() * fieldSize, head.y() * fieldSize, fieldSize,
		    fieldSize);
	}
    }

    public void paintBonus(Graphics g, Position bonus) {
	if (bonus != null) {
	    g.setColor(Color.green);
	    g.fillOval(bonus.x() * fieldSize, bonus.y() * fieldSize, fieldSize,
		    fieldSize);
	}
    }

}
