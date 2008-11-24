package server.protocol;

import java.util.List;

import server.application.TavolaServer;
import data.game.Game;
import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaPreGameProtocol implements TavolaProtocol {

  private boolean isConnected;

  private final Player player;

  public TavolaPreGameProtocol(Player player) {
    this.player = player;
    isConnected = false;
  }

  public String processInput(String input) {

    final StringBuffer result = new StringBuffer();
    final List<Game> games = TavolaServer.getGames();

    if (isConnected == false) {

      // LIST GAMES
      if (input.equals("LIST_GAMES")) {
        for (Game game : games) {
          result.append(", " + game.getId());
        }

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
            if (player != p) {
              result.append(", " + p);
            }
          }

          /*
           * for (Player player : gameToJoin.getPlayers()) { synchronized
           * (player) { try { new
           * PrintWriter(player.getSocket().getOutputStream(), true)
           * .println("PLAYER_JOINED " + this.player.getId()); } catch
           * (IOException e) { // TODO Auto-generated catch block
           * e.printStackTrace(); } } }
           */

        } else {
          result.append("MAXIMUM_PLAYERS_NUMBER_REACHED");
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

          Game newGame = new Game(id, levelId, maxPlayersCount,
              maxBonusesCount, creatorId);

          if (TavolaServer.addGame(newGame)) {
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

    } else {
      // TODO

    }

    return result.toString();
  }
}
