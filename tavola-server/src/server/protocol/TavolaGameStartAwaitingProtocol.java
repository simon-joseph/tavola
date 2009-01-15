package server.protocol;

import java.util.List;

import server.application.TavolaServer;
import data.game.Game;
import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author polchawa
 * 
 */
public class TavolaGameStartAwaitingProtocol implements TavolaProtocol {

  private boolean isConnected;
  private final Player player;
  private TavolaMiddleProtocol middleProtocol;

  public TavolaGameStartAwaitingProtocol(Player player,
      TavolaMiddleProtocol middleProtocol) {
    this.player = player;
    this.middleProtocol = middleProtocol;
    isConnected = true;
  }

  public boolean isConnected() {
    return isConnected;
  }

  /*
   * (non-Javadoc)
   * 
   * @see data.network.TavolaProtocol#processInput(java.lang.String)
   */
  public String processInput(String input) {
    // LEAVE GAME
    final StringBuffer result = new StringBuffer();
    final List<Game> games = TavolaServer.getGames();

    if (input.equals("LEAVE_GAME")) {
      List<Player> players = player.getGame().getPlayers();

      if (TavolaServer.removePlayer(player.getGame(), player)) {
        isConnected = false;
        result.append("OK");

        for (Player p : players) {
          if (player != p) {
            synchronized (p) {
              p.getPrintWriter().println("ASYNC PLAYER_LEFT " + player.getId());
            }
          }
        }
      } else {
        result.append("FAILED");
      }

      // BAN (na razie tylko kick)
    } else if (input.matches("BAN [a-zA-Z0-9]+")) {
      String id = input.substring(5);
      if (player.getGame().getCreatorId().equals(player.getId())) {
        Player playerToBan = null;

        List<Player> players = player.getGame().getPlayers();

        for (Player p : player.getGame().getPlayers()) {
          if (p.getId().equals(id)) {
            playerToBan = p;
            break;
          }
        }

        if (playerToBan != null
            && TavolaServer.removePlayer(player.getGame(), playerToBan)) {
          isConnected = false;
          result.append("OK");

          for (Player p : players) {
            if (player != p) {
              synchronized (p) {
                p.getPrintWriter().println(
                    "ASYNC PLAYER_BANNED " + player.getId());
              }
            }
          }
        } else {
          result.append("FAILED");
        }
      } else {
        result.append("FAILED");
      }

      // START_GAME TODO
    } else if (input.equals("START_GAME")) {
      if (TavolaServer.startGame(player.getGame(), middleProtocol) == false) {
        result.append("FAILED");
      } else {
        result.append("GAME_OVER");
        for (Player p : player.getGame().getPlayers()) {
          if (player != p) {
            synchronized (p) {
              p.getPrintWriter().println("GAME_OVER");
            }
          }
        }
      }

    } else if (input.equals("GAME_STARTED")) {
      System.out.println("Game started " + player.getId());
      while (player.getGame() != null && player.getGame().isRunning()) {
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    } else {
      result.append("UNKNOWN_COMMAND");
    }
    return result.toString();
  }

}
