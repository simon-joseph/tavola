package server.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import data.game.Game;
import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaInGameProtocol implements TavolaProtocol {

  private Player player;
  private BufferedReader in;
  private final TavolaMiddleProtocol middleProtocol;

  public TavolaInGameProtocol(Player player,
      TavolaMiddleProtocol middleProtocol, BufferedReader in) {
    this.in = in;
    this.player = player;
    this.middleProtocol = middleProtocol;
  }

  public String processInput(String input) {
    // TODO Auto-generated method stub
    return null;
  }

  public void startGame(Game game) throws InterruptedException, IOException,
      InvalidInGameProtocolException {

    game.setRunning(true);
    int moveId = 0;

    ArrayList<String> moves = new ArrayList<String>();

    while (true) {

      for (Player p : game.getPlayers()) {
        synchronized (p) {
          // System.out.println("NEXT " + moveId);
          p.getPrintWriter().println("NEXT " + moveId);
        }
      }
      moves.clear();
      for (Player p : game.getPlayers()) {
        synchronized (p) {
          /*
           * String move = new BufferedReader(new InputStreamReader(
           * ((TavolaServerThread) p.getServerThread()).socket
           * .getInputStream())).readLine();
           */
          String move = p.getIn().readLine();
          if (move == null) {
            // ?! TODO
          }
          // System.out.println(move);

          final String[] moveSplited = move.split(" ");

          if (moveSplited[0].equals("MOVE") /* && move[1].equals(moveId) */) {
            moves.add(moveSplited[2]);
          } else {
            throw new InvalidInGameProtocolException();
          }
        }
      }
      for (Player p : game.getPlayers()) {
        synchronized (p) {
          p.getPrintWriter().println("MOVES " + moveId);
          // System.out.println("MOVES " + moveId);

          for (int i = 0; i < game.getPlayers().size(); i++) {
            p.getPrintWriter().println(moves.get(i));
            // System.out.println("pp " + moves.get(i));
            // moves.get(i));
          }
          p.getPrintWriter().println("END");
        }
      }

      Thread.sleep(50);
      moveId++;
    }
  }
}
