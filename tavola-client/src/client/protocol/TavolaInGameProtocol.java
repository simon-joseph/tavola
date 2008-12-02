package client.protocol;

import data.network.TavolaProtocol;

public class TavolaInGameProtocol implements TavolaProtocol {

  // private int moveNumber = 0;

  public String processInput(String input) {

    if (input.matches("NEXT")) {
      return "MOVE " + getLastMove();

    } else if (input.matches("MOVES [0-3]? [0-3]? [0-3]? [0-3]?")) {
      doMoves(input);
      return null;

    } else {
      return "UNKNOWN_COMMAND";
    }

  }

  private void doMoves(String input) { // TODO
  }

  private String getLastMove() { // TODO
    return "0";
  }

}
