package server.protocol;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import server.application.Server;
import data.game.Game;
import data.game.Player;
import data.network.ConnectionLostException;
import data.network.RequestSendingException;
import data.network.RequestsHandler;

/**
 * @author polchawa
 * 
 */
public class ServerGameStartAwaitingRequestsHandler extends RequestsHandler {

  private final Player player;

  public ServerGameStartAwaitingRequestsHandler(Player player) {
    this.player = player;
  }

  @Override
  public List<String> handleRequest(String input) {
    // LEAVE GAME
    final LinkedList<String> result = new LinkedList<String>();

    if (input.equals("LEAVE_GAME")) {
      List<Player> players = player.getGame().getPlayers();

      if (Server.removePlayer(player.getGame(), player)) {
        result.addLast("OK");

        for (Player p : players) {
          if (player != p) {
            synchronized (p) {
              try {
                new PlayerLeftRequest(player).send(p.getMessagesPipe());
              } catch (RequestSendingException e) {
                // TODO Auto-generated catch block
              } catch (ConnectionLostException e) {
                // TODO Auto-generated catch block
              }
            }
          }
        }
      } else {
        result.addLast("FAILED");
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
            && Server.removePlayer(player.getGame(), playerToBan)) {
          result.addLast("OK");

          for (Player p : players) {
            if (player != p) {
              synchronized (p) {
                try {
                  new PlayerLeftRequest(player).send(p.getMessagesPipe());
                } catch (RequestSendingException e) {
                  // TODO Auto-generated catch block
                } catch (ConnectionLostException e) {
                  // TODO Auto-generated catch block
                }
              }
            }
          }
        }
      } else {
        result.addLast("FAILED");
      }

      // START_GAME TODO
    } else if (input.equals("START_GAME")) {
      startGame(player.getGame());
      result.addLast("GAME_STARTED");
    } else {
      result.addLast("UNKNOWN_COMMAND");
    }
    return result;
  }

  private Random random = new Random(System.currentTimeMillis());

  private void startGame(Game game) {

    game.setRunning(true);

    int seed = random.nextInt();

    for (Player p : game.getPlayers()) {
      if (p != player) {
        synchronized (p) {
          try {
            new GameStartedRequest(seed).send(p.getMessagesPipe());
          } catch (RequestSendingException e) {
            // TODO Auto-generated catch block
          } catch (ConnectionLostException e) {
            // TODO Auto-generated catch block
          }
        }
      }
    }

    new ServerGameThread(game).start();
  }
}
