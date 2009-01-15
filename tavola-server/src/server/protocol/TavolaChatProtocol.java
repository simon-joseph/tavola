package server.protocol;

import data.game.Player;
import data.network.ChatMessages;
import data.network.TavolaProtocol;

/**
 * @author polchawa
 * 
 */
public class TavolaChatProtocol implements TavolaProtocol {
  private final Player player;

  public TavolaChatProtocol(Player player) {
    this.player = player;
  }

  /*
   * (non-Javadoc)
   * 
   * @see data.network.TavolaProtocol#processInput(java.lang.String)
   */
  public String processInput(String input) {
    StringBuffer result = new StringBuffer();
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
    }
    return result.toString();
  }

}
