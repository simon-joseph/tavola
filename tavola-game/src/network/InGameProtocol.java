package network;

import game.PlayerBoard;

import java.util.ArrayList;

import data.network.ConnectionLostException;
import data.network.Pipe;
import data.network.RequestSendingException;

/**
 * @author Piotr Staszak
 */

public class InGameProtocol implements Runnable {

    private Pipe pipe;
    private PlayerBoard board;
    private volatile boolean termination = false;

    public InGameProtocol(Pipe pipe, PlayerBoard board2) {
	this.pipe = pipe;
	board = board2;
    }

    public void run() {

	int moveId = 0;

	try {
	    while (!termination) {
		String s;
		if ((s = pipe.readln()) != null) {

		    if (s.matches("^NEXT [0-9]+$")
			    && Integer.parseInt(s.substring(5)) == moveId) {

			pipe.println("MOVE " + String.valueOf(moveId) + " "
				+ board.getMyNextTurn());

			s = pipe.readln();
			if (s == null
				|| !s.equals("MOVES " + String.valueOf(moveId))) {
			    throw new RequestSendingException();
			}

			ArrayList<String> moves = new ArrayList<String>();
			while ((s = pipe.readln()) != null
				&& s.matches("^[0123]?$")) {
			    moves.add(s);
			}

			if (s == null || !s.equals("END")) {
			    throw new RequestSendingException();
			}

			board.setDirections(moves.toArray(new String[] {}));
			moveId++;

		    } else {
			throw new RequestSendingException();
		    }

		}
	    }
	} catch (RequestSendingException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (NumberFormatException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ConnectionLostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public void kill() {
	termination = true;
    }

}
