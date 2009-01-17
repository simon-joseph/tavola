/**
 * 
 */
package game;

import interfaces.IPlayer;

import java.util.LinkedList;

import commons.Direction;
import commons.Position;

/**
 * @author sla + agl
 * 
 */

public final class Player implements IPlayer {

    private int delay;
    private Position head;
    private Position last;
    private Direction direction;
    private LinkedList<Position> body;

    // private int id;
    // TODO: id weza

    public Player(int i, int length, int w, int h) {
	// id = i;
	delay = length;
	body = new LinkedList<Position>();
	if (i < 2) {
	    direction = Direction.RIGHT;
	} else {
	    direction = Direction.LEFT;
	}
	System.out.println("NUMER WEZA: " + i);
	System.out.println("SZEROKOSC: " + (w / 4 + w * (i / 2) / 2));
	System.out.println("WYSOKOSC: " + (h / 4 + h * (i % 2) / 2));
	head = new Position(w / 4 + w * (i / 2) / 2, h / 4 + h * (i % 2) / 2);
	last = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see interfaces.IPlayer#changeDirection(commons.Direction)
     */
    public void changeDirection(Direction direction) {
	if (!this.direction.opposite(direction)) {
	    this.direction = direction;
	}
    }

    /**
     * Adds {@link #head} as first {@link #body} element and sets {@link #last}
     * position to last {@link #body} element. If {@link #delay} is 0 it means
     * we are not getting longer so we must remove the last {@link #body}
     * element.
     */
    public void move() {
	body.addFirst(head.clone());
	last = body.getLast();
	if (delay == 0) {
	    body.removeLast();
	} else {
	    delay--;
	}
	assert direction != null;
	head.add(direction.toPosition());
    }

    /**
     * @return the delay
     */
    public int getDelay() {
	return delay;
    }

    /**
     * @param delay
     *            the delay to set
     */
    public void setDelay(int delay) {
	this.delay = delay;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
	return direction;
    }

    /**
     * @return the head
     */
    public Position getHead() {
	return head;
    }

    /**
     * @return the last
     */
    public Position getLast() {
	return last;
    }

    /**
     * @return the body
     */
    public LinkedList<Position> getBody() {
	return body;
    }
}
