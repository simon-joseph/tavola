package client.application;

import java.io.IOException;
import java.util.ArrayList;

import client.protocol.InvalidProtocolException;

/**
 * @author Piotr Staszak
 */

public class TavolaInGameClient implements Runnable {

  private Pipe pipe;
  private volatile boolean termination = false;

  public TavolaInGameClient(Pipe pipe) {
    this.pipe = pipe;
  }

  @Override
  public void run() {

    int moveId = 0;

    while (!termination) {
      try {
        String s;
        if (TavolaClient.inGame && (s = pipe.readln()) != null) {

          if (s.matches("^NEXT [0-9]+$")
              && Integer.parseInt(s.substring(5)) == ++moveId) {

            pipe.println("MOVE " + String.valueOf(moveId) + " "
                + TavolaClient.getLastMove());

            s = pipe.readln();
            if (s == null || !s.equals("MOVES " + String.valueOf(moveId))) {
              throw new InvalidProtocolException();
            }

            ArrayList<String> moves = new ArrayList<String>();
            while ((s = pipe.readln()) != null
                && s.matches("^[a-zA-Z0-9]+ [0-9]+$")) {
              moves.add(s);
            }

            if (s == null || !s.equals("END")) {
              throw new InvalidProtocolException();
            }

            TavolaClient.nextMoves(moves.toArray(new String[] {}));

          } else {
            throw new InvalidProtocolException();
          }

        } else {
          try {
            Thread.currentThread();
            Thread.sleep(100);
          } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

  }

  public void kill() {
    termination = true;
  }

}
