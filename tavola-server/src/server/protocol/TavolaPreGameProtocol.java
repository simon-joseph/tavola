package server.protocol;

import java.util.ArrayList;
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

  private final TavolaMiddleProtocol middleProtocol;

  public TavolaPreGameProtocol(Player player,
      TavolaMiddleProtocol middleProtocol) {
    this.player = player;
    this.middleProtocol = middleProtocol;
    isConnected = false;
  }

  public String processInput(String input) {

    final StringBuffer result = new StringBuffer();
    final List<Game> games = TavolaServer.getGames();

    if (isConnected == false) {

      // LIST GAMES
      if (input.equals("LIST_GAMES")) {
        for (Game game : games) {
          result.append(", " + game);
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
            result.append(", " + p);
          }

          for (Player player : gameToJoin.getPlayers()) {
            synchronized (player) {
              if (player != this.player) {
                player.getPrintWriter().println(
                    "PLAYER_JOINED " + this.player.getId());
              }
            }
          }

          isConnected = true;

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

          Game newGame = new Game(id, levelId, maxPlayersCount,
              maxBonusesCount, creatorId);

          ArrayList<Player> players = new ArrayList<Player>();
          players.add(player);

          newGame.setPlayers(players);

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

    } else {
      if (input.equals("LEAVE GAME")) {
        isConnected = false;

        result.append("OK");

      } else {
        if (input.equals("START_GAME")) {
          synchronized (player.getGame()) {
            middleProtocol.startGame();
          }
        }
      }

    }

    return result.toString();
  }
}
