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

  public final long SPEED = 100;

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

  public int[][] getBoard() {
    return board;
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

  public void update() {
    snake.move();
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
      board[snake.head.horizontal][snake.head.vertical] = 2;
    }
  }

  private boolean stopConditions() {
    Position p = snake.head;
    // System.out.println("Pozycja glowy: " + p.horizontal + " " + p.vertical);
    return p.horizontal < 0 || p.horizontal >= WIDTH || p.vertical < 0
        || p.vertical >= HEIGHT || board[p.horizontal][p.vertical] != 0;
  }

  public boolean isGameOver() {
    return gameOver;
  }
}
