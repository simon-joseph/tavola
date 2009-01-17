package client.protocol;

import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class BanPlayerRequest extends Request<Boolean> {

  private String playerId;

  public BanPlayerRequest(String playerId) {
    this.playerId = playerId;
  }

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null;
  }

  @Override
  protected String getQueryString() {
    return "BAN " + playerId;
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length == 1;
    return answerStrings[0].equals("OK");
  }

}
