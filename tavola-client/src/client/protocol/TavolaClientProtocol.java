package client.protocol;

import data.network.TavolaProtocol;

/**
 * @author Piotr Staszak
 * 
 */
public class TavolaClientProtocol implements TavolaProtocol {

  private final TavolaPreGameProtocol preGameProtocol;

  private final TavolaInGameProtocol inGameProtocol;

  private String status;

  public TavolaClientProtocol() {
    preGameProtocol = new TavolaPreGameProtocol();
    inGameProtocol = new TavolaInGameProtocol();
    status = "pre";
  }

  @Override
  public String processInput(String input) {
    if (status.equals("pre")) {
      if (input.equals("START_GAME")) {
        status = "in";
      }
      return preGameProtocol.processInput(input);
    } else if (status.equals("in")) {
      return inGameProtocol.processInput(input);
    } else {
      return "ERROR";
    }
  }

}
