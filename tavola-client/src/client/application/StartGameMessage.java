package client.application;

import client.protocol.GameMessage;
import client.protocol.InvalidProtocolException;

/**
 * @author polchawa
 * 
 */
public class StartGameMessage extends GameMessage<Boolean> {

  /*
   * (non-Javadoc)
   * 
   * @see client.protocol.GameMessage#endOfAnswer(java.lang.String)
   */
  @Override
  protected boolean endOfAnswer(String s) {
    // TODO Auto-generated method stub
    return s != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.protocol.GameMessage#getMessageString()
   */
  @Override
  protected String getMessageString() {
    // TODO Auto-generated method stub
    return "START_GAME";
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.protocol.GameMessage#parseAnswerStrings(java.lang.String[])
   */
  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings)
      throws InvalidProtocolException {
    // TODO Auto-generated method stub
    return answerStrings[0].equals("START_GAME");
  }

}
