package applet;

import game.SnakeBoard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @author sla + agl
 */

@SuppressWarnings("serial")
public class SnakeBoardApplet extends JApplet implements Runnable,
	ActionListener {

    protected BoardPanel board;
    static boolean clicked;
    private StartButton startButton;
    private SnakeBoard snakeBoard;

    public SnakeBoardApplet() {
	super();
	SnakeBoardApplet.clicked = false;
	snakeBoard = new SnakeBoard();
	initGUI();
    }

    public void run() {
	while (!snakeBoard.isGameOver()) {
	    if (SnakeBoardApplet.clicked) {
		snakeBoard.update();
		requestFocus();
		board.repaint();
	    }
	    try {
		Thread.sleep(snakeBoard.INITIAL_MOVE_TIME
			- snakeBoard.getSpeed());
	    } catch (InterruptedException e) {
		break;
	    }
	}
    }

    private void initGUI() {
	setSize(new Dimension(60, 35));
	getContentPane().setLayout(
		new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);

	board = new BoardPanel(snakeBoard);
	addKeyListener(board);

	startButton = new StartButton(this);

	getContentPane().add(board);
	getContentPane().add(startButton);

	startButton.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
	if (StartButton.cmdName.equals(e.getActionCommand())) {
	    startButton.setVisible(false);
	    board.setVisible(true);
	    SnakeBoardApplet.clicked = true;
	    new Thread(this).start();
	}
    }

    /*
     * Ta metoda jest uzywana tylko do uruchomienia gry jako aplikacj. Applet
     * nie potrzebuje tej metody do poprawnej pracy.
     */
    @Deprecated
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
}
