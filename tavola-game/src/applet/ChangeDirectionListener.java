package applet;

import game.SnakeBoard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import commons.Direction;

/**
 * @author sla + agl
 * 
 */
class ChangeDirectionListener implements KeyListener {

    SnakeBoard snakeBoard;

    public ChangeDirectionListener(SnakeBoard snakeBoard) {
	super();
	this.snakeBoard = snakeBoard;
    }

    public void keyPressed(KeyEvent e) {

	switch (e.getKeyCode()) {
	case KeyEvent.VK_UP:
	    snakeBoard.setSnakeDirection(Direction.UP);
	    break;
	case KeyEvent.VK_DOWN:
	    snakeBoard.setSnakeDirection(Direction.DOWN);
	    break;
	case KeyEvent.VK_LEFT:
	    snakeBoard.setSnakeDirection(Direction.LEFT);
	    break;
	case KeyEvent.VK_RIGHT:
	    snakeBoard.setSnakeDirection(Direction.RIGHT);
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
}
