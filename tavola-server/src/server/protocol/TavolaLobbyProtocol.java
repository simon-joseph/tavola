package server.protocol;

import java.util.ArrayList;
import java.util.List;

import server.application.TavolaServer;
import data.game.Game;
import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author polchawa
 * 
 */
public class TavolaLobbyProtocol implements TavolaProtocol {

  private boolean isConnected;
  private final Player player;

  public TavolaLobbyProtocol(Player player) {
    this.player = player;
    isConnected = false;
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
    // LIST GAMES
    final StringBuffer result = new StringBuffer();
    final List<Game> games = TavolaServer.getGames();
    if (input.equals("LIST_GAMES")) {
      for (Game game : games) {
        result.append(game + "\n");
      }

      result.append("END");

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
        result.append("INCORRECT_GAME_ID");

      } else if (TavolaServer.addPlayer(gameToJoin, player)) {

        for (Player p : gameToJoin.getPlayers()) {
          result.append(p + "\n");
        }

        result.append("END");

        for (Player player : gameToJoin.getPlayers()) {
          if (player != this.player) {
            synchronized (player) {
              player.getPrintWriter().println(
                  "ASYNC PLAYER_JOINED " + this.player.getId());
            }
          }
        }

        isConnected = true;
        player.setGame(gameToJoin);

      } else {
        result.append("CANNOT JOIN GAME");
      }

      // CREATE GAME
    } else if (input.matches("CREATE_GAME [a-zA-Z0-9]+ [a-zA-Z0-9]+"
        + " [a-zA-Z0-9]+ [a-zA-Z0-9]+$")) {

      try {

        String id = TavolaServer.getGamesCounter().toString();

        String[] temp = input.split(" ");
        String levelId = temp[1];
        int maxPlayersCount = Integer.valueOf(temp[2]);
        int maxBonusesCount = Integer.valueOf(temp[3]);
        String creatorId = temp[4];

        ArrayList<Player> players = new ArrayList<Player>();
        players.add(player);

        Game newGame = new Game(id, players, levelId, maxPlayersCount,
            maxBonusesCount, creatorId, null, 3);

        if (TavolaServer.addGame(newGame)) {

          isConnected = true;
          player.setGame(newGame);
          result.append("OK " + id);

        } else {
          result.append("GAMES_LIMIT_EXCEEDED");
        }
      } catch (NumberFormatException e) {
        result.append("UNKNOWN_COMMAND");
      }
    } else {
      result.append("UNKNOWN_COMMAND");
    }
    return result.toString();
  }

}
