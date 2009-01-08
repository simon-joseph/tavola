package applet;

import game.Snake;
import game.SnakeBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import commons.Direction;
import commons.Position;

/**
 * @author agl + sla
 * 
 */
@SuppressWarnings("serial")
class BoardPanel extends JPanel {

    private DefaultTheme th;
    private SnakeBoard sb;

    // private boolean painted;

    public BoardPanel() {
    }

    public void init(SnakeBoard s) {
	sb = s;
	// painted = false;
	th = new DefaultTheme();
	setPreferredSize(new Dimension(th.fieldSize * sb.WIDTH + 1,
		th.fieldSize * sb.HEIGHT + 1));
	setMaximumSize(new Dimension(th.fieldSize * sb.WIDTH + 1, th.fieldSize
		* sb.HEIGHT + 1));
	setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
	// if (painted) {
	// updateBoard(g);
	// } else {
	// paintBoard(g);
	// painted = true;
	// }
	paintBoard(g);
    }

    private void paintBoard(Graphics g) {
	drawBackground(g);
	paintSnake(g, sb.getSnake());
	th.paintBonus(g, sb.getBonus());
	paintStats(g);
    }

    private void drawBackground(Graphics g) {
	setBackground(th.background);
	super.paintComponent(g);
    }

    private void paintSnake(Graphics g, Snake s) {
	// th.eraseLast(g, s.getLast());
	// th.changeOldHead(g, s.getBody());
	// th.paintNewHead(g, s.getHead());
	Position oldPosition = s.getHead();
	th.paintHead(g, s.getHead(), s.getDirection());
	for (int i = 0; i < s.getBody().size(); i++) {
	    oldPosition = paintSnakeBody(g, s.getBody().get(i), oldPosition);
	}
    }

    private Position paintSnakeBody(Graphics g, Position toPaint, Position old) {
	th.paintBodyPart(g, toPaint, BoardPanel.reverseDirection(toPaint, old));
	return toPaint;
    }

    static Direction reverseDirection(Position f, Position s) {
	if (f.x() == s.x()) {
	    if (f.y() < s.y()) {
		return Direction.DOWN;
	    } else {
		return Direction.UP;
	    }
	} else {
	    if (f.x() < s.x()) {
		return Direction.LEFT;
	    } else {
		return Direction.RIGHT;
	    }
	}
    }

    // FIXME ramka ze statystykami będzie się znajdować obok planszy
    @Deprecated
    private void paintStats(Graphics g) {
	g.setColor(Color.red);
	g.drawString("Speed: " + sb.getSpeed(), 10, 10);
	g.drawString("Level: " + sb.getLevel(), 10, 30);
    }

}
