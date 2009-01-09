package applet;

import game.Snake;
import game.SnakeBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import commons.Direction;
import commons.Position;

/**
 * @author agl + sla
 * 
 */
@SuppressWarnings("serial")
class BoardPanel extends JPanel implements KeyListener, ActionListener {

    private DefaultTheme th;
    private SnakeBoard sb;

    public BoardPanel(SnakeBoard s) {
	super();
	init(s);
    }

    public void init(SnakeBoard s) {
	sb = s;
	th = new SmoothTheme();
	setPreferredSize(new Dimension(th.fieldSize * sb.WIDTH + 1,
		th.fieldSize * sb.HEIGHT + 1));
	setMaximumSize(new Dimension(th.fieldSize * sb.WIDTH + 1, th.fieldSize
		* sb.HEIGHT + 1));
	// setLayout(new FlowLayout());
	// add(new PauseButton(this));
	// add(new ExitButton(this));
	setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
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
		return Direction.RIGHT;
	    } else {
		return Direction.LEFT;
	    }
	}
    }

    public void keyPressed(KeyEvent e) {

	switch (e.getKeyCode()) {
	case KeyEvent.VK_UP:
	    sb.setSnakeDirection(Direction.UP);
	    break;
	case KeyEvent.VK_DOWN:
	    sb.setSnakeDirection(Direction.DOWN);
	    break;
	case KeyEvent.VK_LEFT:
	    sb.setSnakeDirection(Direction.LEFT);
	    break;
	case KeyEvent.VK_RIGHT:
	    sb.setSnakeDirection(Direction.RIGHT);
	    break;
	default:
	    break;
	}

    }

    public void keyReleased(KeyEvent arg0) {
	// TODO Auto-generated method stub

    }

    public void keyTyped(KeyEvent arg0) {
	// TODO Auto-generated method stub

    }

    public void actionPerformed(ActionEvent e) {
	if (ExitButton.cmdName.equals(e.getActionCommand())) {
	    System.exit(0);
	}
	if (PauseButton.cmdName.equals(e.getActionCommand())) {
	    SnakeBoardApplet.clicked = !SnakeBoardApplet.clicked;
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
