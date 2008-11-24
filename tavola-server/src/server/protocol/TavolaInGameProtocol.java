package server.protocol;

import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaInGameProtocol implements TavolaProtocol {

  private Player player;

  public TavolaInGameProtocol(Player player) {
    this.player = player;
  }

  @Override
  public String processInput(String input) {
    // TODO Auto-generated method stub
    return null;
  }

}
