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
    public final int WIDTH = 60;
    public final int HEIGHT = 35;

    private long speed;
    private int level;
    private boolean gameOver;
    private boolean levelChanged;
    private Snake snake;
    private Position bonus;
    private Random generator;
    private int[][] board;
    private Direction snakeDirection;

    public SnakeBoard() {
    }

    /**
     * Initiates fields: {@link #generator}, {@link #snakeDirection},
     * {@link #snake}, {@link #gameOver}, {@link #speed} and {@link #level}.
     * Sets snakes position in the center of the board. Initial length of snake
     * is set at 3. Both {@link #speed} and {@link #level} are set at 1;
     * {@link #gameOver} is set to false . Calls method {@link #initBoard()}.
     * 
     * @param direction
     *            the direction of snake to set
     */
    public void initialize(Direction direction) {
	generator = new Random();
	snakeDirection = direction;
	snake = new Snake();
	snake.setUpSnake(direction, new Position(WIDTH / 2, HEIGHT / 2), 3);
	gameOver = false;
	speed = 1;
	level = 1;
	initBoard();
    }

    // TODO javadoc
    /**
     * Initiates an empty board with one bonus.
     */
    private void initBoard() {
	board = new int[WIDTH][HEIGHT];
	for (int w = 0; w < WIDTH; w++) {
	    for (int h = 0; h < HEIGHT; h++) {
		board[w][h] = 0;
	    }
	}
	generateBonus();
    }

    /**
     * Randomly selects an empty field on the board and puts a bonus there.
     */
    private void generateBonus() {
	while (true) {
	    int i = generator.nextInt(WIDTH - 1);
	    int j = generator.nextInt(HEIGHT - 1);
	    if (board[i][j] == 0) {
		board[i][j] = 3;
		bonus = new Position(i, j);
		break;
	    }
	}
    }

    @Deprecated
    public void run() {
	// TODO delete or not?
    }

    // TODO javadoc
    public void update() {
	snake.changeDirection(snakeDirection);
	snake.move();
	if (snake.getBody().size() % 5 == 0 && !levelChanged) {
	    speedup(4);
	    levelChanged = true;
	}
	for (int i = 0; i < snake.getBody().size(); i++) {
	    board[snake.getBody().get(i).x()][snake.getBody().get(i).y()] = 1;
	}
	board[snake.getLast().x()][snake.getLast().y()] = 0;
	if (stopConditions()) {
	    gameOver = true;
	    System.out.println("przegrales");
	} else {
	    if (board[snake.getHead().x()][snake.getHead().y()] == 3) {
		snake.setDelay(snake.getDelay() + 1);
		levelChanged = false;
		generateBonus();
	    }
	    board[snake.getHead().x()][snake.getHead().y()] = 2;
	}
    }

    /**
     * Increases speed of snake by adding value of jump parameter to current
     * {@link #speed}
     * 
     * @param jump
     */
    private void speedup(int jump) {
	if (speed < 70) {
	    speed += jump;
	    level++;
	}
    }

    /**
     * Checks whether at least one of the conditions of termination is
     * satisfied. The condition is the collision of snakes head with other part
     * of snake or edge of board.
     * 
     * @return true if at least one condition is satisfied, false if none
     */
    private boolean stopConditions() {
	Position p = snake.getHead();
	return p.x() < 0 || p.x() >= WIDTH || p.y() < 0 || p.y() >= HEIGHT
		|| board[p.x()][p.y()] != 0 && board[p.x()][p.y()] != 3;
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public Snake getSnake() {
	return snake;
    }

    public void setSnakeDirection(Direction direction) {
	if (!snakeDirection.opposite(direction)) {
	    snakeDirection = direction;
	}
    }

    public long getSpeed() {
	return speed;
    }

    public int getLevel() {
	return level;
    }

    public int[][] getBoard() {
	return board;
    }

    /**
     * @return the bonus
     */
    public Position getBonus() {
	return bonus;
    }
}
