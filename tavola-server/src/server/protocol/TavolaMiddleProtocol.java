package server.protocol;

import java.io.BufferedReader;
import java.io.PrintWriter;

import data.game.Game;
import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaMiddleProtocol implements TavolaProtocol {

  private final Player player;
  private final TavolaPreGameProtocol preGameProtocol;
  private final TavolaInGameProtocol inGameProtocol;
  private final BufferedReader in;

  public void startGame() {
    final Game game = player.getGame();
    final PrintWriter out = player.getPrintWriter();

    for (Player p : game.getPlayers()) {
      synchronized (p) {
        if (p != player) {
          p.getServerThread().suspend(); // TODO
        }
        p.getPrintWriter().println("START_GAME");
      }
    }

    // TODO
    for (Player p : game.getPlayers()) {
      synchronized (p) {
        if (p != player) {
          p.getServerThread().resume(); // TODO

        }
      }
    }
  }

  public TavolaMiddleProtocol(Player player, BufferedReader in) {
    this.player = player;
    this.in = in;
    preGameProtocol = new TavolaPreGameProtocol(this.player, this);
    inGameProtocol = new TavolaInGameProtocol(this.player, this, in);
  }

  @Override
  public String processInput(String input) {
    return preGameProtocol.processInput(input);
  }
}
