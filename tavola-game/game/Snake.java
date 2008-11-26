/**
 * 
 */
package game;

import interfaces.Direction;
import interfaces.IPlayer;

import java.util.LinkedList;

/**
 * @author sla
 * 
 */
public final class Snake implements IPlayer {
	private Direction direction;
	protected Position head;
	protected LinkedList<Position> body;
	protected Position last;

	public Snake(int width, int height) {
		this.direction = Direction.RIGHT;
		this.head = new Position(width / 2, height / 2);
		this.body = new LinkedList<Position>();
		this.last = new Position(width / 2 - 4, height / 2);
		this.body.push(new Position(width / 2 - 3, height / 2));
		this.body.push(new Position(width / 2 - 2, height / 2));
		this.body.push(new Position(width / 2 - 1, height / 2));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see interfaces.Player#changeDirection(interfaces.Direction)
	 */
	public void changeDirection(Direction direction) {
		if (!this.direction.opposite(direction))
			this.direction = direction;
	}

	/*
	 * Oblicz zmianę położenia głowy na podstawie aktualnego kierunku
	 * (direction).
	 */
	private Position nextMove() {
		switch (this.direction) {
		case RIGHT:
			return new Position(1, 0);
		case LEFT:
			return new Position(-1, 0);
		case UP:
			return new Position(0, 1);
		case DOWN:
			return new Position(0, -1);
		default:
			return new Position(0, 0);
		}
	}

	public void move() {
		this.body.push(head);
		this.last = this.body.getLast();
		this.body.pop();
		this.head.add(this.nextMove());
	}
}
