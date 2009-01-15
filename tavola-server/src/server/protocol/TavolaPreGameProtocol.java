package server.protocol;

import java.util.ArrayList;
import java.util.List;

import server.application.TavolaServer;
import data.game.Game;
import data.game.Player;
import data.network.ChatMessages;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 *         TODO kod wymaga rekaktoryzacji
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

    if (input.matches("^MSG .*$")) {
      ChatMessages.add(player.getId(), input.substring(4));
      result.append("OK");
    } else if (input.equals("GET_MSGS")) {
      for (ChatMessages.Message msg : ChatMessages.getMessages(player
          .getLastMessageId())) {
        result.append(msg.getAuthor() + ":" + msg.getContent() + "\n");
        player.setLastMessageId(msg.getId());
      }
      result.append("END");
    } else if (isConnected == false) {

      // LIST GAMES
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
                /*
                 * player.getPrintWriter().println( "PLAYER_JOINED " +
                 * this.player.getId());
                 */
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

      // isConnected == true
    } else {

      // LEAVE GAME
      if (input.equals("LEAVE_GAME")) {
        List<Player> players = player.getGame().getPlayers();

        if (TavolaServer.removePlayer(player.getGame(), player)) {
          isConnected = false;
          result.append("OK");

          for (Player p : players) {
            if (player != p) {
              synchronized (p) {
                p.getPrintWriter().println("PLAYER_LEFT " + player.getId());
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
                  p.getPrintWriter().println("PLAYER_BANNED " + player.getId());
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
    }
    return result.toString();
  }
}
