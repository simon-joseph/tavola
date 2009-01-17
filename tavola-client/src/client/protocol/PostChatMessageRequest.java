package client.protocol;

import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class PostChatMessageRequest extends Request<Boolean> {

  private String message;

  public PostChatMessageRequest(String message) {
    this.message = message;
  }

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null;
  }

  @Override
  protected String getQueryString() {
    return "CHAT MSG " + message;
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length == 1;
    return answerStrings[0].equals("OK");
  }

}
