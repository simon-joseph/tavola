/**
 * 
 */
package game;

import interfaces.IBoard;

import java.util.Random;

import commons.Direction;
import commons.Position;

/**
 * @author sla + agl
 */
public final class SnakeBoard implements IBoard {

    public final long INITIAL_MOVE_TIME = 101;
    private long speed = 1;
    private int level = 1;
    public final int WIDTH = 60;
    public final int HEIGHT = 35;
    private boolean gameOver;
    private boolean levelChanged;
    private Snake snake;
    private Random generator;
    private int[][] board;
    private Direction snakeDirection;

    public SnakeBoard() {
	generator = new Random();
	snakeDirection = Direction.RIGHT;
	initBoard();
	snake = new Snake();
	snake.setUpSnake(Direction.RIGHT, new Position(WIDTH / 2, HEIGHT / 2),
		3);
	gameOver = false;

    }

    public int[][] getBoard() {
	return board;
    }

    private void generateBonus() {
	while (true) {
	    int i = generator.nextInt(WIDTH - 1);
	    int j = generator.nextInt(HEIGHT - 1);
	    if (board[i][j] == 0) {
		board[i][j] = 3;
		break;
	    }
	}
    }

    private void initBoard() {
	board = new int[WIDTH][HEIGHT];
	for (int w = 0; w < WIDTH; w++) {
	    for (int h = 0; h < HEIGHT; h++) {
		board[w][h] = 0;
	    }
	}
	generateBonus();
    }

    public void run() {
	// TODO
    }

    public void update() {
	snake.changeDirection(snakeDirection);
	snake.move();
	if (snake.body.size() % 5 == 0 && !levelChanged) {
	    speedup();
	    levelChanged = true;
	}
	for (int i = 0; i < snake.body.size(); i++) {
	    board[snake.body.get(i).horizontal][snake.body.get(i).vertical] = 1;
	}
	// System.out.println("Pozycja head: " + snake.head.horizontal + " "
	// + snake.head.vertical);
	// System.out.println("Pozycja last: " + snake.last.horizontal + " "
	// + snake.last.vertical);
	board[snake.last.horizontal][snake.last.vertical] = 0;
	if (stopConditions()) {
	    gameOver = true;
	    System.out.println("przegrales");
	} else {
	    if (board[snake.head.horizontal][snake.head.vertical] == 3) {
		snake.setDelay(snake.getDelay() + 1);
		levelChanged = false;
		generateBonus();
	    }
	    board[snake.head.horizontal][snake.head.vertical] = 2;
	}
    }

    private void speedup() {
	if (speed < 70) {
	    speed += 4;
	    level++;
	}
    }

    private boolean stopConditions() {
	Position p = snake.head;
	// System.out.println("Pozycja glowy: " + p.horizontal + " " +
	// p.vertical);
	return p.horizontal < 0 || p.horizontal >= WIDTH || p.vertical < 0
		|| p.vertical >= HEIGHT || board[p.horizontal][p.vertical] != 0
		&& board[p.horizontal][p.vertical] != 3;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public Snake getSnake() {
	return snake;
    }

    public void setSnakeDirection(Direction d) {
	snakeDirection = d;
    }

    public long getSpeed() {
	return speed;
    }

    public int getLevel() {
	return level;
    }
}
