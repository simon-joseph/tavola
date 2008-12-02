package server.protocol;

import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaInGameProtocol implements TavolaProtocol {

  private Player player;

  private final TavolaMiddleProtocol middleProtocol;

  public TavolaInGameProtocol(Player player, TavolaMiddleProtocol middleProtocol) {
    super();
    this.player = player;
    this.middleProtocol = middleProtocol;
  }

  @Override
  public String processInput(String input) {
    // TODO Auto-generated method stub
    return null;
  }

}
