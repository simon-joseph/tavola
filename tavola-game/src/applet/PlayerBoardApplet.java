package applet;

import game.PlayerBoard;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JApplet;

import commons.Direction;

/**
 * @author sla + agl
 */

@SuppressWarnings("serial")
public class PlayerBoardApplet extends JApplet implements Runnable,
	ActionListener {

    protected PlayBoardPanel board;
    static boolean clicked;
    private StartButton startButton;
    private PlayerBoard playerBoard;
    private long speed;

    public String getMyNextTurn() {
	return playerBoard.getMyNextTurn().toString();
    }

    public void setDirections(List<String> directions) {
	playerBoard.setDirections(directions.toArray(new String[] {}));
    }

    public PlayerBoardApplet(int all, int me) {
	super();
	SnakeBoardApplet.clicked = false;
	// serwerze! ja chce grac!
	// TODO pobierz informacje od serwera
	playerBoard = new PlayerBoard(all, me);
	speed = 80;
	initGUI();
    }

    public void ruszSie() {
	// TODO server.ready(board.getMyNextTurn());
    }

    // TODO mam watpliwosc czy to tu, czy cos wywolywane z run, czy jak...
    public void updateState(long speed, Direction[] directions) {
	this.speed = speed;
	playerBoard.setSnakesDirections(directions);
    }

    public void run() {
	while (true) {// !server.isGameOver()) { //TODO to serwer stwierdza,
	    // ze jest juz gameover
	    if (SnakeBoardApplet.clicked) {
		// TODO pobierz ruchy od serwera i wywolaj setSnakesDiractions,
		// setBonus
		// playerBoard.update();
		// requestFocus();
		board.repaint();
	    }
	    try {
		Thread.sleep(100);
		// TODO: predkosc tez serwer ustala
		Thread.sleep(playerBoard.INITIAL_MOVE_TIME - speed);
	    } catch (InterruptedException e) {
		break;
	    }
	}
    }

    private void initGUI() {
	setSize(new Dimension(800, 600));
	getContentPane().setLayout(
		new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);

	board = new PlayBoardPanel(playerBoard);
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

    public boolean isGameOver() {
	return playerBoard.isGameOver();
    }

}
