package game;

import interfaces.Direction;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * @author sla + agl
 */

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
            g.setColor(Color.pink);
            if (board[i][j] == 2)
              g.setColor(Color.yellow);
            if (board[i][j] == 3)
              g.setColor(Color.orange);
          } else
            g.setColor(new Color(100, 100, 150));
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

  class ButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent ev) {
      if (ev.getSource() == startButton) {
        startButton.setVisible(false);
        messageField.setVisible(true);
        board.setVisible(true);
        controlPanel.setVisible(true);
        clicked = true;
      }
      if (ev.getSource() == exitButton) {
        System.exit(0);
      }
      if (ev.getSource() == pauseButton) {
        clicked = !clicked;
      }
    }
  }

  private ButtonListener bl = new ButtonListener();

  private BoardPanel board;

  private boolean clicked = false;

  private JPanel controlPanel;

  private JButton startButton;

  private JButton exitButton;

  private JButton pauseButton;

  private JTextField messageField;

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

  private class ChangeDirectionListener implements KeyListener {

    public void keyPressed(KeyEvent e) {
      if (true) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_UP:
            sb.setSnakeDirection(Direction.UP);
            break;
          case KeyEvent.VK_DOWN:
            sb.setSnakeDirection(Direction.DOWN);
            break;
          case KeyEvent.VK_LEFT:
            sb.setSnakeDirection(Direction.LEFT);
            break;
          case KeyEvent.VK_RIGHT:
            sb.setSnakeDirection(Direction.RIGHT);
            break;
          default:
            break;
          // sb.getSnake().changeDirection(Direction.UP);
        }
      } else {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
          sb.getSnake().changeDirection(Direction.UP);
        }
      }
    }

    public void keyReleased(KeyEvent arg0) {
      // TODO Auto-generated method stub

    }

    public void keyTyped(KeyEvent arg0) {
      // TODO Auto-generated method stub

    }

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
        requestFocus();
        board.repaint();
        i++;
      }
      try {
        Thread.sleep(sb.INITIAL_MOVE_TIME - sb.getSpeed());
      } catch (InterruptedException e) {
        break;
      }
    }
    messageField.setText("GAME OVER");
  }

  private void initGUI() {
    try {
      setSize(new Dimension(800, 600));
      getContentPane().setLayout(
          new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

      messageField = new JTextField();
      messageField.setVisible(false);
      messageField.setMinimumSize(new Dimension(200, 100));
      messageField.setPreferredSize(new Dimension(200, 100));
      messageField.setMaximumSize(new Dimension(200, 100));
      messageField.setHorizontalAlignment(SwingConstants.CENTER);

      board = new BoardPanel(sb);
      board.setPreferredSize(new Dimension(10 * sb.WIDTH + 1,
          10 * sb.HEIGHT + 1));
      board
          .setMaximumSize(new Dimension(10 * sb.WIDTH + 1, 10 * sb.HEIGHT + 1));
      board.setVisible(false);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      {
        pauseButton = new JButton();
        pauseButton.setText("PAUSE");
        pauseButton.setPreferredSize(new Dimension(100, 50));
        pauseButton.setMinimumSize(new Dimension(100, 50));
        pauseButton.setMaximumSize(new Dimension(100, 50));
        pauseButton.addActionListener(bl);
        pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton = new JButton();
        exitButton.setText("EXIT");
        exitButton.setPreferredSize(new Dimension(100, 50));
        exitButton.setMinimumSize(new Dimension(100, 50));
        exitButton.setMaximumSize(new Dimension(100, 50));
        exitButton.addActionListener(bl);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      }
      
      controlPanel.add(pauseButton);
      controlPanel.add(exitButton);
      controlPanel.setVisible(false);
      controlPanel.setFocusable(false);

      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      addKeyListener(new ChangeDirectionListener());

      startButton = new JButton();
      startButton.setText("START");
      startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      startButton.addActionListener(bl);
      startButton.setMinimumSize(new Dimension(200, 100));
      startButton.setMaximumSize(new Dimension(200, 100));
      startButton.setPreferredSize(new Dimension(200, 100));
      startButton.setVisible(false);
      getContentPane().add(messageField);
      getContentPane().add(board);
      getContentPane().add(controlPanel);
      getContentPane().add(startButton);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
