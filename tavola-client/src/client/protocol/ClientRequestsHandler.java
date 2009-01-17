package client.protocol;

import java.util.List;

import data.game.Player;
import data.network.RequestsHandler;

/**
 * @author polchawa
 * 
 */
public class ClientRequestsHandler extends RequestsHandler {

  private ClientChatRequestsHandler clientChatRequestsHandler = null;
  private ClientGameStartAwaitingRequestsHandler clientGameStartAwaitingRequestsHandler = null;
  private ClientInGameRequestsHandler clientInGameRequestsHandler = null;

  private Player player = null;

  @Override
  public List<String> handleRequest(String input) {
    if (player == null) {
      return null;
    }

    if (input.startsWith("CHAT ")) {
      if (clientChatRequestsHandler != null) {
        return clientChatRequestsHandler.handleRequest(input);
      }
    } else if (player.getGame() != null) {
      if (!player.getGame().isRunning()) {
        return clientGameStartAwaitingRequestsHandler.handleRequest(input);
      } else {
        return clientInGameRequestsHandler.handleRequest(input);
      }
    }
    return null;
  }

  public ClientChatRequestsHandler getClientChatRequestsHandler() {
    return clientChatRequestsHandler;
  }

  public void setClientChatRequestsHandler(
      ClientChatRequestsHandler clientChatRequestsHandler) {
    this.clientChatRequestsHandler = clientChatRequestsHandler;
  }

  public ClientGameStartAwaitingRequestsHandler getClientGameStartAwaitingRequestsHandler() {
    return clientGameStartAwaitingRequestsHandler;
  }

  public void setClientGameStartAwaitingRequestsHandler(
      ClientGameStartAwaitingRequestsHandler clientGameStartAwaitingRequestsHandler) {
    this.clientGameStartAwaitingRequestsHandler = clientGameStartAwaitingRequestsHandler;
  }

  public ClientInGameRequestsHandler getClientInGameRequestsHandler() {
    return clientInGameRequestsHandler;
  }

  public void setClientInGameRequestsHandler(
      ClientInGameRequestsHandler clientInGameRequestsHandler) {
    this.clientInGameRequestsHandler = clientInGameRequestsHandler;
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }

}
