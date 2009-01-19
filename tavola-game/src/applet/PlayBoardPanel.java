package applet;

import game.Player;
import game.PlayerBoard;

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
public class PlayBoardPanel extends JPanel implements KeyListener,
	ActionListener {

    private LighterTheme th;
    private PlayerBoard sb;

    public PlayBoardPanel(PlayerBoard s) {
	super();
	init(s);
    }

    public void init(PlayerBoard s) {
	sb = s;
	th = new LighterTheme();
	setSize(new Dimension(th.fieldSize * sb.WIDTH + 1, th.fieldSize
		* sb.HEIGHT + 1));
	setVisible(false);
    }

    @Override
    public void paintComponent(Graphics g) {
	paintBoard(g);
    }

    private void paintBoard(Graphics g) {
	drawBackground(g);
	th.paintBoard(g, sb.WIDTH * th.fieldSize, sb.HEIGHT * th.fieldSize);
	for (int i = 0; i < sb.getSize(); i++) {
	    paintSnake(g, sb.getSnake(i), i);
	}
	th.paintBonus(g, sb.getBonuses());
	th.paintStatBoard(g, sb.WIDTH * th.fieldSize, sb.HEIGHT * th.fieldSize);
	paintStats(g);
    }

    private void drawBackground(Graphics g) {
	setBackground(th.background);
	super.paintComponent(g);
    }

    // FIXME tu coś jest źle - potrafi wygenerować BodyPart.UPUP
    private void paintSnake(Graphics g, Player s, int id) {
	Position old = s.getHead();
	Position curr;
	Position next;
	th.paintHead(g, s.getHead(), s.getDirection(), id);
	for (int i = 0; i < s.getBody().size() - 1; i++) {
	    curr = s.getBody().get(i);
	    next = s.getBody().get(i + 1);
	    BodyParts bodyPart = chooseBodyPart(curr.directionTo(old), curr
		    .directionTo(next));
	    th.paintBodyPart(g, curr, bodyPart, id);
	    old = curr;
	}
	curr = s.getBody().getLast();
	th.paintLast(g, curr, old.directionTo(curr), id);
    }

    private BodyParts chooseBodyPart(Direction leftDirection,
	    Direction rightDirection) {
	return BodyParts.valueOf(rightDirection.name() + leftDirection.name());
    }

    // TODO: tutaj to wysylamy ten ruch do serwera
    public void keyPressed(KeyEvent e) {

	switch (e.getKeyCode()) {
	case KeyEvent.VK_UP:
	    // serwerze, ruszylem sie do gory
	    // sb.setSnakeDirection(Direction.UP);
	    sb.setMyNextTurn(Direction.UP);
	    break;
	case KeyEvent.VK_DOWN:
	    // serwerze, ruszylem sie w dol
	    // sb.setSnakeDirection(Direction.DOWN);
	    sb.setMyNextTurn(Direction.DOWN);
	    break;
	case KeyEvent.VK_LEFT:
	    // serwerze, ruszylem sie w lewo
	    // sb.setSnakeDirection(Direction.LEFT);
	    sb.setMyNextTurn(Direction.LEFT);
	    break;
	case KeyEvent.VK_RIGHT:
	    // serwerze, ruszylem sie w prawo
	    // sb.setSnakeDirection(Direction.RIGHT);
	    sb.setMyNextTurn(Direction.RIGHT);
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
	// TODO predkosc ustalac bedzie server, leveli chyba nie bedzie
	// g.drawString("Speed: " + sb.getSpeed(), 610, 30);
	// g.drawString("Level: " + sb.getLevel(), 610, 50);
    }

}
