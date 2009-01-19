package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import commons.Direction;
import commons.Position;

/**
 * @author ania
 * 
 */
public class PlayerBoard {
    public final long INITIAL_MOVE_TIME = 101;
    public final int WIDTH = 60;
    public final int HEIGHT = 35;

    private int playerId;
    private boolean gameOver;
    private int size;
    private Player[] snakes; // TODO: tablica wezy
    private Vector<Position> bonuses;
    private int[][] board;
    private Direction[] snakesDirections; // TODO: tablica kierunkow wezy
    private Direction myNextTurn = Direction.RIGHT;
    private Random generator;
    private int maxBonuses;
    private int bonusesOnBoard;

    public PlayerBoard(int all, int me, int seed, int mb) {
	maxBonuses = mb;
	bonusesOnBoard = 0;
	generator = new Random(seed);
	bonuses = new Vector<Position>();
	playerId = me;
	size = all;
	snakes = new Player[all];
	snakesDirections = new Direction[all];
	for (int i = 0; i < all; i++) {
	    snakesDirections[i] = Direction.RIGHT;
	}
	initialize();
	generateBonus();
    }

    // TODO: rozszerzyc na wszystkie weze
    public void initialize() {
	for (int i = 0; i < size; i++) {
	    snakes[i] = new Player(i, 3, WIDTH, HEIGHT);
	}
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
    }

    // TODO javadoc
    // TODO ruszyc wszystkie weze
    public void update() {
	for (int i = 0; i < size; i++) {
	    if (snakes[i].isAlive()) {
		snakes[i].changeDirection(snakesDirections[i]);
		snakes[i].move();
		for (int j = 0; j < snakes[i].getBody().size(); j++) {
		    board[snakes[i].getBody().get(j).x()][snakes[i].getBody()
			    .get(j).y()] = 1;
		}
		if (maxBonuses == 0) {
		    snakes[i].setDelay(snakes[i].getDelay() + 1);
		}
		board[snakes[i].getLast().x()][snakes[i].getLast().y()] = 0;
		if (stopConditions(i)) {
		    snakes[i].setAlive(false);
		    if (i == playerId) {
			myNextTurn = Direction.DEATH;
			gameOver = true;
		    }
		} else {
		    if (board[snakes[i].getHead().x()][snakes[i].getHead().y()] == 3) {
			snakes[i].setDelay(snakes[i].getDelay() + 1);
			bonuses.remove(new Position(snakes[i].getHead().x(),
				snakes[i].getHead().y()));
			bonusesOnBoard--;
		    }
		    board[snakes[i].getHead().x()][snakes[i].getHead().y()] = 2;
		}

	    }
	}
	generateBonus();
    }

    private boolean stopConditions(int i) {
	Position p = snakes[i].getHead();
	return p.x() < 0 || p.x() >= WIDTH || p.y() < 0 || p.y() >= HEIGHT
		|| board[p.x()][p.y()] != 0 && board[p.x()][p.y()] != 3;
    }

    public Player getSnake(int i) {
	return snakes[i];
    }

    public void setSnakesDirections(Direction[] directions) {
	for (int i = 0; i < directions.length; i++) {
	    if (!snakesDirections[i].opposite(directions[i])) {
		snakesDirections[i] = directions[i];
	    }
	}
    }

    public int[][] getBoard() {
	return board;
    }

    /**
     * @return the bonus
     */
    public Vector<Position> getBonuses() {
	return bonuses;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
    }

    public int getPlayerId() {
	return playerId;
    }

    /**
     * @return the myNextTurn
     */
    public Direction getMyNextTurn() {
	return myNextTurn;
    }

    /**
     * @param myNextTurn
     *            the myNextTurn to set
     */
    public void setMyNextTurn(Direction myNextTurn) {
	this.myNextTurn = myNextTurn;
    }

    public void setDirections(String[] array) {
	ArrayList<Direction> moves = new ArrayList<Direction>();
	for (int i = 0; i < array.length; i++) {
	    moves.add(Direction.fromString(array[i]));
	}
	setSnakesDirections(moves.toArray(new Direction[] {}));
	update();
    }

    public boolean isGameOver() {
	return gameOver;
    }

    public void setGameOver(boolean gameOver) {
	this.gameOver = gameOver;
    }

    /**
     * Randomly selects an empty field on the board and puts a bonus there.
     */
    private void generateBonus() {
	while (bonusesOnBoard < maxBonuses) {
	    int i = generator.nextInt(WIDTH - 1);
	    int j = generator.nextInt(HEIGHT - 1);
	    if (board[i][j] == 0) {
		board[i][j] = 3;
		bonuses.add(new Position(i, j));
		bonusesOnBoard++;
	    }
	}
    }
}
