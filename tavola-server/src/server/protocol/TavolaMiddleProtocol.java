package server.protocol;

import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaMiddleProtocol implements TavolaProtocol {

  final TavolaPreGameProtocol preGameProtocol = new TavolaPreGameProtocol();

  final TavolaInGameProtocol inGameProtocol = new TavolaInGameProtocol();

  @Override
  public String processInput(String input) {
    return input; // preGameProtocol.processInput(object);
  }

}
