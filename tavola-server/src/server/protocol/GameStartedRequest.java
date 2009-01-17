package server.protocol;

import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class GameStartedRequest extends Request<Boolean> {

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null;
  }

  @Override
  protected String getQueryString() {
    return "GAME_STARTED";
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length == 1;
    return answerStrings[0].equals("OK");
  }
}
