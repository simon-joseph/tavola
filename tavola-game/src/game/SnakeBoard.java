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
    initBoard();
    snake = new Snake(WIDTH, HEIGHT);
    gameOver = false;
  }

  private void initBoard() {
    board = new int[WIDTH][HEIGHT];
    for (int w = 0; w < WIDTH; w++) {
      for (int h = 0; h < HEIGHT; h++) {
        board[w][h] = 0;
      }
    }
  }

  public void run() {
    // TODO
  }

  public boolean getGameOver() {
    return gameOver;
  }

  public void update() {
    snake.move();
    for (int i = 0; i < snake.body.size(); i++) {
      board[snake.body.get(i).vertical][snake.body.get(i).horizontal] = 1;
    }
    board[snake.last.vertical][snake.last.horizontal] = 0;
    if (stopConditions()) {
      gameOver = true;
    } else {
      board[snake.head.vertical][snake.head.horizontal] = 2;
    }
  }

  private boolean stopConditions() {
    Position p = snake.head;
    return p.vertical < 0 || p.vertical > WIDTH || p.horizontal < 0
        || p.horizontal > HEIGHT || board[p.vertical][p.horizontal] != 0;
  }
}
