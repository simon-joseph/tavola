package server.protocol;

import data.game.Player;
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
    if (input.matches("^MSG .*$") || input.equals("GET_MSGS")) {
      TavolaChatProtocol protocol = new TavolaChatProtocol(player);
      return protocol.processInput(input);
    } else if (isConnected == false) {
      TavolaLobbyProtocol protocol = new TavolaLobbyProtocol(player);
      String ret = protocol.processInput(input);
      isConnected = protocol.isConnected();
      return ret;
    } else {
      TavolaGameStartAwaitingProtocol protocol = new TavolaGameStartAwaitingProtocol(
          player, middleProtocol);
      String ret = protocol.processInput(input);
      isConnected = protocol.isConnected();
      return ret;
    }
  }
}
