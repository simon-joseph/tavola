package applet;

import game.SnakeBoard;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import javax.swing.JPanel;

import commons.Position;

/**
 * @author agl + sla
 * 
 */
@SuppressWarnings("serial")
class BoardPanel extends JPanel {

    SnakeBoard sb;
    boolean painted;

    public BoardPanel(SnakeBoard s) {
	sb = s;
	painted = false;
    }

    public void paintBoard(Graphics g) {
	paintSnake(g, sb.getSnake().getHead(), sb.getSnake().getLast(), sb
		.getSnake().getBody());
	if (sb.getBonus() != null) {
	    paintBonus(g, sb.getBonus());
	}
	paintStats(g);
    }

    @Deprecated
    private void oldPaintSnake(Graphics g) {
	int[][] board = sb.getBoard();
	for (int i = 0; i < sb.WIDTH; i++) {
	    for (int j = 0; j < sb.HEIGHT; j++) {
		if (board[i][j] > 0) {
		    g.setColor(Color.pink);
		    if (board[i][j] == 2) {
			g.setColor(Color.yellow);
		    }
		    if (board[i][j] == 3) {
			g.setColor(Color.orange);
		    }
		} else {
		    g.setColor(new Color(100, 100, 150));
		}
		g.fillOval(i * 10, j * 10, 10, 10);
	    }
	}
    }

    private void paintSnake(Graphics g, Position head, Position last,
	    LinkedList<Position> body) {
	if (!painted) {
	    if (last != null) {
		g.setColor(new Color(100, 100, 150));
		g.fillOval(last.x() * 10, last.y() * 10, 10, 10);
	    }
	    if (!body.isEmpty()) {
		g.setColor(Color.pink);
		g.fillOval(body.getFirst().x() * 10, body.getFirst().y() * 10,
			10, 10);
	    }
	    if (head != null) {
		g.setColor(Color.orange);
		g.fillOval(head.x() * 10, head.y() * 10, 10, 10);
	    }
	    painted = true;

	} else {
	    if (last != null) {
		g.setColor(new Color(100, 100, 150));
		g.fillOval(last.x() * 10, last.y() * 10, 10, 10);
	    }
	    if (body != null) {
		g.setColor(Color.pink);
		for (int i = 0; i < body.size(); i++) {
		    g.fillOval(body.get(i).x() * 10, body.get(i).y() * 10, 10,
			    10);
		}
	    }
	    if (head != null) {
		g.setColor(Color.orange);
		g.fillOval(head.x() * 10, head.y() * 10, 10, 10);
	    }
	}
    }

    private void paintBonus(Graphics g, Position bonus) {
	g.setColor(Color.green);
	g.fillOval(bonus.x() * 10, bonus.y() * 10, 10, 10);
    }

    private void paintStats(Graphics g) {
	g.setColor(Color.red);
	g.drawString("Speed: " + sb.getSpeed(), 10, 10);
	g.drawString("Level: " + sb.getLevel(), 10, 30);
    }

    @Override
    public void paintComponent(Graphics g) {
	setBackground(new Color(100, 100, 150));
	super.paintComponent(g);
	paintBoard(g);
    }
}
