package server.protocol;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
public class ServerLobbyRequestsHandler extends RequestsHandler {

  private final Player player;

  public ServerLobbyRequestsHandler(Player player) {
    this.player = player;
  }

  /*
   * (non-Javadoc)
   * 
   * @see data.network.TavolaProtocol#processInput(java.lang.String)
   */
  @Override
  public List<String> handleRequest(String input) {
    // LIST GAMES
    final LinkedList<String> result = new LinkedList<String>();
    final List<Game> games = Server.getGames();

    if (input.equals("LIST_GAMES")) {
      for (Game game : games) {
        result.addLast(game.toString());
      }

      result.addLast("END");

      // JOIN GAME
    } else if (input.matches("JOIN_GAME [a-zA-Z0-9]+$")) {

      String id = input.substring(10);

      Game gameToJoin = null;

      for (Game game : games) {
        if (game.getId().equals(id)) {
          gameToJoin = game;
          break;
        }
      }

      if (gameToJoin == null) {
        result.addLast("INCORRECT_GAME_ID");

      } else if (Server.addPlayer(gameToJoin, player)) {

        for (Player p : gameToJoin.getPlayers()) {
          result.addLast(p.getId() + " " + p.getName());
        }

        result.addLast("END");

        for (Player p : gameToJoin.getPlayers()) {
          if (p != player) {
            synchronized (p) {
              try {
                new PlayerJoinedRequest(player).send(p.getMessagesPipe());
              } catch (RequestSendingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              } catch (ConnectionLostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              }
            }
          }
        }

      } else {
        result.addLast("CANNOT_JOIN_GAME");
      }

      // CREATE GAME
    } else if (input.matches("CREATE_GAME [a-zA-Z0-9]+ [a-zA-Z0-9]+"
        + " [a-zA-Z0-9]+ [a-zA-Z0-9]+$")) {

      try {

        String id = Server.getGamesCounter().toString();

        String[] temp = input.split(" ");
        String levelId = temp[1];
        int maxPlayersCount = Integer.valueOf(temp[2]);
        int maxBonusesCount = Integer.valueOf(temp[3]);
        String creatorId = player.getId();

        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);

        Game newGame = new Game(id, players, levelId, maxPlayersCount,
            maxBonusesCount, creatorId, null, 3);

        if (Server.addGame(newGame)) {
          player.setGame(newGame);
          result.addLast("OK " + id);

        } else {
          result.addLast("GAMES_LIMIT_EXCEEDED");
        }
      } catch (NumberFormatException e) {
        result.addLast("UNKNOWN_COMMAND");
      }
    } else {
      result.addLast("UNKNOWN_COMMAND");
    }
    return result;
  }

}
