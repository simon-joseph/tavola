package server.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

    int moveId = 0;

    Map<String, String> moves = new HashMap<String, String>();

    while (true) {
      for (Player p : game.getPlayers()) {
        synchronized (p) {
          System.out.println("NEXT " + moveId);
          p.getPrintWriter().println("NEXT " + moveId);
        }
      }
      for (Player p : game.getPlayers()) {
        synchronized (p) {
          String move = p.getIn().readLine();
          System.out.println(move);

          final String[] moveSplited = move.split(" ");

          if (moveSplited[0].equals("MOVE") /* && move[1].equals(moveId) */) {
            moves.put(p.getId(), moveSplited[2]);
          } else {
            throw new InvalidInGameProtocolException();
          }
        }
      }
      for (Player p : game.getPlayers()) {
        synchronized (p) {
          p.getPrintWriter().println("MOVES " + moveId);
          System.out.println("MOVES " + moveId);

          for (Player p2 : game.getPlayers()) {
            p.getPrintWriter()
                .println(p2.getId() + " " + moves.get(p2.getId()));
            System.out.println(p2.getId() + " " + moves.get(p2.getId()));
          }
          p.getPrintWriter().println("END");
        }
      }

      Thread.sleep(100);
      moveId++;
    }
  }
}
