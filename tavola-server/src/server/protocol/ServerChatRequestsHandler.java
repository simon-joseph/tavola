package server.protocol;

import java.util.List;

import server.application.ChatMessages;
import data.game.Player;
import data.network.RequestsHandler;

/**
 * @author polchawa
 * 
 */
public class ServerChatRequestsHandler extends RequestsHandler {
  private final Player player;

  public ServerChatRequestsHandler(Player player) {
    this.player = player;
  }

  /*
   * (non-Javadoc)
   * 
   * @see data.network.TavolaProtocol#processInput(java.lang.String)
   */
  @Override
  public List<String> handleRequest(String input) {
    if (input.matches("^CHAT MSG .*$")) {
      ChatMessages.addMessage(player.getId(), input.substring("CHAT MSG "
          .length()));
      return singleStringList("OK");
    }
    return singleStringList("UNKNOWN_COMMAND");
  }

}
