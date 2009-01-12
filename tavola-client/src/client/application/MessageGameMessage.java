package client.application;

import client.protocol.GameMessage;
import client.protocol.InvalidProtocolException;

/**
 * @author polchawa
 * 
 */
public class MessageGameMessage extends GameMessage<Boolean> {

  private String message;

  public MessageGameMessage(String message) {
    super();
    this.message = message;
  }

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
    return "MSG " + message;
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
    return answerStrings[0].equals("OK");
  }

}
