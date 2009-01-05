package applet;

import game.SnakeBoard;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import commons.Direction;

/**
 * @author sla + agl
 */

@SuppressWarnings("serial")
public class SnakeBoardApplet extends JApplet implements Runnable {

    protected BoardPanel board;
    protected boolean clicked = false;
    protected JPanel controlPanel;
    protected JButton startButton;
    protected JButton exitButton;
    protected JButton pauseButton;
    protected JTextField messageField;
    private ButtonListener buttonListener;
    private SnakeBoard snakeBoard;

    public SnakeBoardApplet() {
	super();
	buttonListener = new ButtonListener(this);
	snakeBoard = new SnakeBoard();
	initGUI();
	startButton.setVisible(true);
	new Thread(this).start();
    }

    public static void main(String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeBoardApplet inst = new SnakeBoardApplet();
		frame.getContentPane().add(inst);
		((JComponent) frame.getContentPane()).setPreferredSize(inst
			.getSize());
		frame.pack();
		frame.setVisible(true);
		inst.startButton.setVisible(true);
	    }
	});

    }

    public void run() {
	snakeBoard.initialize(Direction.RIGHT);
	int i = 0;
	while (!snakeBoard.isGameOver()) {
	    if (clicked) {
		snakeBoard.update();
		requestFocus();
		board.repaint();
		i++;
	    }
	    try {
		Thread.sleep(snakeBoard.INITIAL_MOVE_TIME
			- snakeBoard.getSpeed());
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

	    board = new BoardPanel(snakeBoard);
	    board.setPreferredSize(new Dimension(10 * snakeBoard.WIDTH + 1,
		    10 * snakeBoard.HEIGHT + 1));
	    board.setMaximumSize(new Dimension(10 * snakeBoard.WIDTH + 1,
		    10 * snakeBoard.HEIGHT + 1));
	    board.setVisible(false);

	    controlPanel = new JPanel();
	    controlPanel.setLayout(new FlowLayout());

	    {
		pauseButton = new JButton();
		pauseButton.setText("PAUSE");
		pauseButton.setPreferredSize(new Dimension(100, 50));
		pauseButton.setMinimumSize(new Dimension(100, 50));
		pauseButton.setMaximumSize(new Dimension(100, 50));
		pauseButton.addActionListener(buttonListener);
		pauseButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton = new JButton();
		exitButton.setText("EXIT");
		exitButton.setPreferredSize(new Dimension(100, 50));
		exitButton.setMinimumSize(new Dimension(100, 50));
		exitButton.setMaximumSize(new Dimension(100, 50));
		exitButton.addActionListener(buttonListener);
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    }

	    controlPanel.add(pauseButton);
	    controlPanel.add(exitButton);
	    controlPanel.setVisible(false);
	    controlPanel.setFocusable(false);

	    setFocusable(true);
	    setFocusTraversalKeysEnabled(false);
	    addKeyListener(new ChangeDirectionListener(snakeBoard));

	    startButton = new JButton();
	    startButton.setText("START");
	    startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	    startButton.addActionListener(buttonListener);
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
