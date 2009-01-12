package network;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Piotr Staszak
 */

public class InGameProtocol implements Runnable {

    private Pipe pipe;
    private BoardPanel board;
    private volatile boolean termination = false;

    public InGameProtocol(Pipe pipe, BoardPanel board) {
	this.pipe = pipe;
	this.board = board;
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
				+ board.getDirection());

			s = pipe.readln();
			if (s == null
				|| !s.equals("MOVES " + String.valueOf(moveId))) {
			    throw new InvalidProtocolException();
			}

			ArrayList<String> moves = new ArrayList<String>();
			while ((s = pipe.readln()) != null
				&& s.matches("^[a-zA-Z0-9]+ [0,1,2,3]?$")) {
			    moves.add(s);
			}

			if (s == null || !s.equals("END")) {
			    throw new InvalidProtocolException();
			}

			board.setDirections(moves.toArray(new String[] {}));
			moveId++;

		    } else {
			throw new InvalidProtocolException();
		    }

		}
	    }
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InvalidProtocolException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public void kill() {
	termination = true;
    }

}
