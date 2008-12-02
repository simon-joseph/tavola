package client.application;

import client.protocol.GameMessageAnswer;
import client.protocol.InvalidProtocolException;

public class NextMoveGameMessageAnswer extends GameMessageAnswer<String> {

  private String moveId;

  private String getMove() {
    return "0"; // TODO
  }

  @Override
  protected boolean endOfMessage(String s) {
    return s != null;
  }

  @Override
  protected boolean isAnswer() {
    return true;
  }

  @Override
  protected String getAnswerString() {
    return "NEXT " + moveId + " " + getMove();
  }

  @Override
  protected String parseMessageStrings(String[] message)
      throws InvalidProtocolException {
    assert message.length == 1;

    if (!message[0].matches("^NEXT [0-9]+$")) {
      throw new InvalidProtocolException();
    }
    moveId = message[0].substring(5);
    return moveId;
  }

}
