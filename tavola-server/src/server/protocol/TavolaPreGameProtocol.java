package server.protocol;

import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaPreGameProtocol implements TavolaProtocol {

  private boolean isConnected = false;

  // TODO -- check regexes
  public String processInput(String input) {

    String result = null;

    if (isConnected == false) {
      if (input.equals("LIST_GAMES")) { // list games
      } else if (input.matches("^JOIN_GAME [a-zA-Z0-9]+$")) { // joining game
      } else if (input.matches("^CREATE_GAME [a-zA-Z0-9]+ [a-zA-Z0-9]+"
          + " [a-zA-Z0-9]+ [a-zA-Z0-9]+$")) { // game
        // creation
      }

    } else {
      // TODO

    }

    return result;
  }
}
