package server.protocol;

import java.util.List;

import javax.swing.event.EventListenerList;

import com.danga.MemCached.MemCachedClient;

import data.game.Player;
import data.game.PlayerListener;
import data.network.RequestsHandler;

/**
 * @author rafal.paliwoda
 * 
 *         TODO kod wymaga rekaktoryzacji
 */
public class ServerRequestsHandler extends RequestsHandler {

  private Player player = null;

  EventListenerList playerAuthenticated = new EventListenerList();

  public void addPlayerAuthenticationListener(PlayerListener listener) {
    playerAuthenticated.add(PlayerListener.class, listener);
  }

  public void removePlayerAuthenticationListener(PlayerListener listener) {
    playerAuthenticated.remove(PlayerListener.class, listener);
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

  @Override
  public List<String> handleRequest(String input) {

    if (player == null) {
      if (input.matches("HELLO .+")) {
        String[] params = authentication(input.substring(6));
        if (params != null && params.length == 2) {
          setPlayer(new Player(params[0], params[1]));
          for (PlayerListener playerListener : playerAuthenticated
              .getListeners(PlayerListener.class)) {
            playerListener.playerActionPerformed(player);
          }
          return singleStringList("OK");
        } else {
          return singleStringList("BAD");
        }
      } else {
        return singleStringList("UNKNOWN_COMMAND");
      }
    } else {
      if (input.startsWith("CHAT")) {
        ServerChatRequestsHandler handler = new ServerChatRequestsHandler(
            player);
        return handler.handleRequest(input);
      } else if (player.getGame() == null) {
        ServerLobbyRequestsHandler handler = new ServerLobbyRequestsHandler(
            player);
        return handler.handleRequest(input);
      } else if (!player.getGame().isRunning()) {
        ServerGameStartAwaitingRequestsHandler handler = new ServerGameStartAwaitingRequestsHandler(
            player);
        return handler.handleRequest(input);
      } else {
        ServerInGameRequestsHandler handler = new ServerInGameRequestsHandler();
        return handler.handleRequest(input);
      }
    }
  }

  /**
   * @return player's id
   */
  private String[] authentication(String cookie) {
    // TODO Auto-generated method stub

    MemCachedClient cache = new MemCachedClient();
    String cacheKey = (String) cache.get(cookie);

    if (cacheKey == null) {
      return new String[] { cookie, cookie }; // tymczasowo
    }

    return cacheKey.split(" ", 2);

  }
}
