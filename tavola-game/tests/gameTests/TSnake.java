package gameTests;

import game.Snake;

import java.util.LinkedList;

import junit.framework.TestCase;

import commons.Direction;
import commons.Position;

/**
 * @author sla
 * 
 */
public class TSnake extends TestCase {
    Snake snake;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
	snake = new Snake();
    }

    /**
     * Test method for
     * {@link game.Snake#setUpSnake(commons.Direction, commons.Position, int)}.
     */
    public void testSetUpSnake() {
	Direction[] dirs = { Direction.RIGHT, Direction.LEFT, Direction.UP,
		Direction.DOWN };
	Position[] heads = { new Position(0, 0), new Position(100, 0),
		new Position(0, 100), new Position(100, 100) };
	int[] lens = { 0, 1, 10, 123 };
	for (int d = 0; d < dirs.length; d++) {
	    for (int h = 0; h < heads.length; h++) {
		for (int l = 0; l < lens.length; l++) {
		    snake.setUpSnake(dirs[d], heads[h], lens[l]);
		    assert snake.getDelay() == lens[l];
		    assert snake.getBody() != null;
		    assert snake.getDirection() == dirs[d];
		    assert snake.getHead() == heads[h];
		    assert snake.getLast() == null;
		}
	    }
	}
    }

    /**
     * Test method for {@link game.Snake#changeDirection(commons.Direction)}.
     */
    public void testChangeDirection() {
	Direction[] dirs = { Direction.RIGHT, Direction.LEFT, Direction.UP,
		Direction.DOWN };
	Direction[] oppDirs = { Direction.LEFT, Direction.RIGHT,
		Direction.DOWN, Direction.UP };
	for (int d = 0; d < 4; d++) {
	    for (int o = 0; o < 4; o++) {
		snake.setUpSnake(dirs[d], new Position(0, 0), 0);
		snake.changeDirection(oppDirs[o]);
		if (d == o) {
		    assert snake.getDirection() == dirs[d];
		} else {
		    assert snake.getDirection() == oppDirs[o];
		}
	    }
	}
    }

    /**
     * Test method for {@link game.Snake#move()}.
     */
    @SuppressWarnings("unchecked")
    public void testMove() {
	snake.setUpSnake(Direction.RIGHT, new Position(0, 0), 0);
	Position oldHead = snake.getHead().clone();
	LinkedList<Position> oldBody = (LinkedList<Position>) snake.getBody()
		.clone();
	snake.move();
	assert oldHead == snake.getBody().getFirst();
	oldHead.add(snake.getDirection().toPosition());
	assert snake.getHead() == oldHead;
	assert snake.getLast() == oldBody.getLast();
	for (int i = 0; i < snake.getBody().size() - 1; i++) {
	    assert oldBody.get(i) == snake.getBody().get(i + 1);
	}
    }
}
