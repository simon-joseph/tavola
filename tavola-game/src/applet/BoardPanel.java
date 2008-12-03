package applet;

import game.SnakeBoard;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * @author agl + sla
 * 
 */
@SuppressWarnings("serial")
class BoardPanel extends JPanel {

  SnakeBoard sb;

  public BoardPanel(SnakeBoard s) {
    sb = s;
  }

  public void paintBoard(Graphics g) {
    int[][] board = sb.getBoard();
    for (int i = 0; i < sb.WIDTH; i++) {
      for (int j = 0; j < sb.HEIGHT; j++) {
        if (board[i][j] > 0) {
          g.setColor(Color.pink);
          if (board[i][j] == 2) {
            g.setColor(Color.yellow);
          }
          if (board[i][j] == 3) {
            g.setColor(Color.orange);
          }
        } else {
          g.setColor(new Color(100, 100, 150));
        }
        g.fillOval(i * 10, j * 10, 10, 10);
      }
    }
    g.setColor(Color.red);
    g.drawString("Speed: " + sb.getSpeed(), 10, 10);
    g.drawString("Level: " + sb.getLevel(), 10, 30);
  }

  @Override
  public void paintComponent(Graphics g) {
    setBackground(new Color(100, 100, 150));
    super.paintComponent(g);
    paintBoard(g);
  }
}
