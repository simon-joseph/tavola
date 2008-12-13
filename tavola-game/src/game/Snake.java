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

public final class Snake implements IPlayer {

    private int delay;
    private Position head;
    private Position last;
    private Direction direction;
    private LinkedList<Position> body;

    public Snake() {
    }

    /**
     * Sets up {@link #direction}, {@link #head} and {@link #delay} from
     * parameters. Sets {@link #last} to null and {@link #body} to empty
     * {@link LinkedList}. It's best not to start with head position near the
     * borders. Otherwise you should remember not to face directly the nearest
     * wall.
     * 
     * @param direction
     *            the direction to set
     * @param head
     *            the head to set
     * @param length
     *            the delay to set
     */
    public void setUpSnake(Direction direction, Position head, int length) {
	delay = length;
	body = new LinkedList<Position>();
	this.direction = direction;
	this.head = head;
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
