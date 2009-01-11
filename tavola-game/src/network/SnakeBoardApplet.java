package network;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import applet.StartButton;

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
	connect();
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
	setSize(new Dimension(600, 350));
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

	    pipe.println("START_GAME");
	    try {
		pipe.readln();
	    } catch (IOException e1) {
		e1.printStackTrace();
	    }
	    pipe.println("GAME_STARTED");

	    new Thread(new InGameProtocol(pipe, board)).start();

	}
    }

    Pipe pipe;

    private void connect() {

	final String HOST = "localhost";
	final int PORT = 4444;
	try {
	    Socket socket = new Socket(HOST, PORT);
	    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket
		    .getInputStream()));
	    pipe = new Pipe(in, out);
	    pipe.readln(); // VERSION xxx
	    new HelloGameMessage("1").send(pipe);
	    pipe.println("CREATE_GAME a 2 2 W");
	    pipe.readln();
	    System.out.println("connected");

	} catch (UnknownHostException e) {
	    System.err.println("Unknown host " + HOST);
	    System.exit(1);
	} catch (IOException e) {
	    System.err.println("Couldn't get I/O for the connection to: "
		    + HOST);
	    System.exit(1);
	} catch (InvalidProtocolException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
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
