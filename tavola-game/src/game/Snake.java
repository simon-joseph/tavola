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

    private Direction direction;
    private int delay;
    protected Position head;
    protected Position last;
    protected LinkedList<Position> body;

    public Snake() {
    }

    /**
     * Sets up {@link #direction}, {@link #head} and {@link #delay} from
     * parameters. Sets {@link #last} to null and {@link #body} to empty
     * {@link LinkedList}.
     * 
     * 
     * @param direction
     *            the direction to set
     * @param head
     *            the head to set
     * @param length
     *            the delay to set
     */
    protected void setUpSnake(Direction direction, Position head, int length) {
	delay = length;
	body = new LinkedList<Position>();
	this.direction = direction;
	this.head = head;
	last = null;
    }

    /**
     * @return the delay
     */
    public int getDelay() {
	return delay;
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
     * @param delay
     *            the delay to set
     */
    public void setDelay(int delay) {
	this.delay = delay;
    }
}
