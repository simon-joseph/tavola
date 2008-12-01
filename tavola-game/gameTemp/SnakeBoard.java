/**
 * 
 */
package game;

import interfaces.IBoard;

/**
 * @author sla
 * 
 */
public final class SnakeBoard implements IBoard {
	public final long SPEED = 10;
	public final int WIDTH = 60;
	public final int HEIGHT = 35;
	private boolean gameOver;
	private Snake snake;
	private int[][] board;

	public SnakeBoard() {
		this.initBoard();
		this.snake = new Snake(this.WIDTH, this.HEIGHT);
		this.gameOver = false;
	}

	private void initBoard() {
		this.board = new int[this.WIDTH][this.HEIGHT];
		for (int w = 0; w < this.WIDTH; w++)
			for (int h = 0; h < this.HEIGHT; h++)
				this.board[w][h] = 0;
	}

	public void run() {
		// TODO
	}

	public void update() {
		snake.move();
		for (int i = 0; i < snake.body.size(); i++)
			this.board[snake.body.get(i).vertical][snake.body.get(i).horizontal] = 1;
		this.board[snake.last.vertical][snake.last.horizontal] = 0;
		if (stopConditions())
			gameOver = true;
		else
			this.board[snake.head.vertical][snake.head.horizontal] = 2;
	}

	private boolean stopConditions() {
		Position p = snake.head;
		return p.vertical < 0 || p.vertical > this.WIDTH || p.horizontal < 0
				|| p.horizontal > this.HEIGHT
				|| this.board[p.vertical][p.horizontal] != 0;
	}
}
