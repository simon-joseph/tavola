package server.protocol;

import data.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaMiddleProtocol implements TavolaProtocol {

  final TavolaPreGameProtocol preGameProtocol = new TavolaPreGameProtocol();

  final TavolaInGameProtocol inGameProtocol = new TavolaInGameProtocol();

  /*
   * (non-Javadoc)
   * 
   * @see server.protocol.Protocol#processInput(java.lang.Object)
   */
  @Override
  public String processInput(String string) {
    return string; // preGameProtocol.processInput(object);
  }

}
