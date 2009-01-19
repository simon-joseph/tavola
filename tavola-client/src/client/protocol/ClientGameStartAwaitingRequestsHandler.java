package client.protocol;

import java.util.List;
import java.util.ListIterator;

import javax.swing.event.EventListenerList;

import data.game.Player;
import data.game.PlayerListener;
import data.network.RequestsHandler;

/**
 * @author polchawa
 * 
 */
public class ClientGameStartAwaitingRequestsHandler extends RequestsHandler {
  private static final String PLAYER_JOINED = "PLAYER_JOINED";
  private static final String PLAYER_LEFT = "PLAYER_LEFT";
  private static final String GAME_STARTED = "GAME_STARTED";

  private final Player player;

  private final EventListenerList gameStarted = new EventListenerList();
  private final EventListenerList playerJoined = new EventListenerList();
  private final EventListenerList playerLeft = new EventListenerList();

  public ClientGameStartAwaitingRequestsHandler(Player player) {
    this.player = player;
  }

  public void addGameStartListener(GameListener gameListener) {
    gameStarted.add(GameListener.class, gameListener);
  }

  public void removeGameStartListener(GameListener gameListener) {
    gameStarted.remove(GameListener.class, gameListener);
  }

  public void addPlayerJoinListener(PlayerListener playerListener) {
    playerJoined.add(PlayerListener.class, playerListener);
  }

  public void removePlayerJoinListener(PlayerListener playerListener) {
    playerJoined.remove(PlayerListener.class, playerListener);
  }

  public void addPlayerLeaveListener(PlayerListener playerListener) {
    playerLeft.add(PlayerListener.class, playerListener);
  }

  public void removePlayerLeaveListener(PlayerListener playerListener) {
    playerLeft.remove(PlayerListener.class, playerListener);
  }

  @Override
  public List<String> handleRequest(String input) {
    if (input.startsWith(ClientGameStartAwaitingRequestsHandler.PLAYER_JOINED
        + " ")) {
      String playerId = input
          .substring(ClientGameStartAwaitingRequestsHandler.PLAYER_JOINED
              .length() + 1);
      String playerName = playerId; // fix
      Player newPlayer = new Player(playerId, playerName);
      player.getGame().getPlayers().add(newPlayer);
      newPlayer.setGame(player.getGame());
      for (PlayerListener playerListener : playerJoined
          .getListeners(PlayerListener.class)) {
        playerListener.playerActionPerformed(newPlayer);
      }

    } else if (input
        .startsWith(ClientGameStartAwaitingRequestsHandler.PLAYER_LEFT + " ")) {
      String playerId = input
          .substring(ClientGameStartAwaitingRequestsHandler.PLAYER_LEFT
              .length() + 1);
      ListIterator<Player> listIterator = player.getGame().getPlayers()
          .listIterator();
      while (listIterator.hasNext()) {
        Player p = listIterator.next();
        if (p.getId().equals(playerId)) {
          listIterator.remove();
          p.setGame(null);
          for (PlayerListener playerListener : playerLeft
              .getListeners(PlayerListener.class)) {
            playerListener.playerActionPerformed(p);
          }
          break;
        }
      }
    } else if (input
        .startsWith(ClientGameStartAwaitingRequestsHandler.GAME_STARTED + " ")) {
      int seed = 0;
      try {
        seed = Integer.parseInt(input
            .substring(ClientGameStartAwaitingRequestsHandler.GAME_STARTED
                .length() + 1));
      } catch (NumberFormatException e) {
        // ignorujemy polecenie od serwera bo jest syntax error
        return null;
      }
      player.getGame().setSeed(seed);
      for (GameListener listener : gameStarted.getListeners(GameListener.class)) {
        listener.gameActionPerformed(player.getGame());
      }
      return singleStringList("OK");
    }
    return null;
  }
}
