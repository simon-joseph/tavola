package server.protocol;

import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaMiddleProtocol implements TavolaProtocol {

  private final Player player;

  final TavolaPreGameProtocol preGameProtocol;

  final TavolaInGameProtocol inGameProtocol;

  public TavolaMiddleProtocol(Player player) {
    this.player = player;
    preGameProtocol = new TavolaPreGameProtocol(this.player);
    inGameProtocol = new TavolaInGameProtocol(this.player);
  }

  @Override
  public String processInput(String input) { // TODO
    return preGameProtocol.processInput(input);
  }
}