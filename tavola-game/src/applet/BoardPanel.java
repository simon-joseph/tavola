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

import commons.BodyParts;
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
	th = new LighterTheme();
	// setPreferredSize(new Dimension(th.fieldSize * sb.WIDTH + 1,
	// th.fieldSize * sb.HEIGHT + 1));
	// setMaximumSize(new Dimension(th.fieldSize * sb.WIDTH + 1,
	// th.fieldSize
	// * sb.HEIGHT + 1));
	setSize(new Dimension(th.fieldSize * sb.WIDTH + 1, th.fieldSize
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
	th.paintBoard(g, sb.WIDTH * th.fieldSize, sb.HEIGHT * th.fieldSize);
	paintSnake(g, sb.getSnake());
	//th.paintBonus(g, sb.getBonuses());
	th.paintStatBoard(g, sb.WIDTH * th.fieldSize, sb.HEIGHT * th.fieldSize);
	paintStats(g);
    }

    private void drawBackground(Graphics g) {
	setBackground(th.background);
	super.paintComponent(g);
    }

    private void paintSnake(Graphics g, Snake s) {
	Position old = s.getHead();
	Position curr;
	Position next;
	th.paintHead(g, s.getHead(), s.getDirection(), 0);
	for (int i = 0; i < s.getBody().size() - 1; i++) {
	    curr = s.getBody().get(i);
	    next = s.getBody().get(i + 1);
	    BodyParts bodyPart = chooseBodyPart(curr.directionTo(old), curr
		    .directionTo(next));
	    th.paintBodyPart(g, curr, bodyPart, 0);
	    old = curr;
	}
	curr = s.getBody().getLast();
	th.paintLast(g, curr, old.directionTo(curr), 0);
    }

    private BodyParts chooseBodyPart(Direction leftDirection,
	    Direction rightDirection) {
	return BodyParts.valueOf(rightDirection.name() + leftDirection.name());
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
	g.drawString("Speed: " + sb.getSpeed(), 610, 30);
	g.drawString("Level: " + sb.getLevel(), 610, 50);
    }

}
