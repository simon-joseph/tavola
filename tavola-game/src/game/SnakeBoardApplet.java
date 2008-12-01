package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class SnakeBoardApplet extends JApplet implements Runnable {

  private class BoardPanel extends JPanel {

    SnakeBoard sb;

    public BoardPanel(SnakeBoard s) {
      sb = s;
    }

    public void paintBoard(Graphics g) {
      int[][] board = sb.getBoard();
      for (int i = 0; i < sb.WIDTH; i++) {
        for (int j = 0; j < sb.HEIGHT; j++) {
          if (board[i][j] > 0) {
            g.setColor(Color.red);
            if (board[i][j] == 2) {
              g.setColor(Color.pink);
              // System.out.println("glowa weza");
            }
            g.fillRect(i * 10, j * 10, 10, 10);
          } else {
            g.setColor(Color.white);
            g.fillRect(i * 10, j * 10, 10, 10);
          }
        }
      }
    }

    @Override
    public void paintComponent(Graphics g) {
      setBackground(new Color(20, 10, 80));
      super.paintComponent(g);
      paintBoard(g);
    }
  }

  class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent ev) {
      startButton.setVisible(false);
      board.setVisible(true);
      clicked = true;
    }
  }

  private ButtonListener bl = new ButtonListener();

  private BoardPanel board;

  private boolean clicked = false;

  private JButton startButton;

  private SnakeBoard sb = new SnakeBoard();

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SnakeBoardApplet inst = new SnakeBoardApplet();
        frame.getContentPane().add(inst);
        ((JComponent) frame.getContentPane()).setPreferredSize(inst.getSize());
        frame.pack();
        frame.setVisible(true);
        inst.startButton.setVisible(true);

      }
    });

  }

  public SnakeBoardApplet() {
    super();
    initGUI();
    startButton.setVisible(true);
    new Thread(this).start();

  }

  public void run() {
    int i = 0;
    while (!sb.isGameOver()) {
      if (clicked) {
        sb.update();
        board.repaint();
        i++;
        // System.out.println("narysowalem " + i);
      }
      try {
        Thread.sleep(sb.SPEED);
      } catch (InterruptedException e) {
        break;
      }
    }
  }

  private void initGUI() {
    try {
      setSize(new Dimension(800, 600));
      getContentPane().setLayout(new FlowLayout());
      board = new BoardPanel(sb);
      board.setPreferredSize(new Dimension(10 * sb.WIDTH + 1,
          10 * sb.HEIGHT + 1));
      board.setVisible(false);
      startButton = new JButton();
      startButton.setText("START");
      startButton.addActionListener(bl);
      startButton.setPreferredSize(new Dimension(200, 100));
      startButton.setVisible(false);
      getContentPane().add(board);
      getContentPane().add(startButton);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
