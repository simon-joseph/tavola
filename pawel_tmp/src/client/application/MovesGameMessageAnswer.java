package client.application;

import java.util.ArrayList;

import client.protocol.GameMessageAnswer;
import client.protocol.InvalidProtocolException;

public class MovesGameMessageAnswer extends GameMessageAnswer<String[]> {

  private String moveId;

  public void setMoveId(String moveId) {
    this.moveId = moveId;
  }

  @Override
  protected boolean endOfMessage(String s) {
    return s.equals("END");
  }

  @Override
  protected String getAnswerString() {
    return null;
  }

  @Override
  protected boolean isAnswer() {
    return false;
  }

  @Override
  protected String[] parseMessageStrings(String[] message)
      throws InvalidProtocolException {
    if (message.length < 3 || !message[0].matches("^MOVES [0-9]+$")) {
      throw new InvalidProtocolException();
    }
    if (!message[0].substring(6).equals(moveId)) {
      throw new InvalidProtocolException();
    }
    ArrayList<String> moves = new ArrayList<String>();

    for (int i = 1; i < message.length - 1; ++i) {
      if (!message[i].matches("^[a-zA-Z0-9]+ [0-9]+$")) {
        throw new InvalidProtocolException();
      } else {
        moves.add(message[i]);
      }
    }
    return moves.toArray(new String[] {});
  }
}
