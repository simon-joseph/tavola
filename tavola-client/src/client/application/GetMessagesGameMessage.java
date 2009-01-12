package client.application;

import client.protocol.GameMessage;
import client.protocol.InvalidProtocolException;
import data.network.ChatMessages;

/**
 * @author polchawa
 * 
 */
public class GetMessagesGameMessage extends GameMessage<ChatMessages.Message[]> {

  /*
   * (non-Javadoc)
   * 
   * @see client.protocol.GameMessage#endOfAnswer(java.lang.String)
   */
  @Override
  protected boolean endOfAnswer(String s) {
    // TODO Auto-generated method stub
    return s != null && s.equals("END");
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.protocol.GameMessage#getMessageString()
   */
  @Override
  protected String getMessageString() {
    // TODO Auto-generated method stub
    return "GET_MSGS";
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.protocol.GameMessage#parseAnswerStrings(java.lang.String[])
   */
  @Override
  protected ChatMessages.Message[] parseAnswerStrings(String[] answerStrings)
      throws InvalidProtocolException {
    // TODO Auto-generated method stub
    ChatMessages.Message[] messages = new ChatMessages.Message[answerStrings.length - 1];
    for (int i = 0; i < answerStrings.length - 1; ++i) {
      String[] param = answerStrings[i].split(":", 2);
      messages[i] = new ChatMessages.Message();
      messages[i].setAuthor(param[0]);
      messages[i].setContent(param[1]);
    }
    return messages;
  }

}
