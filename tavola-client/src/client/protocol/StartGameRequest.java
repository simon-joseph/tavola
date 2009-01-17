package client.protocol;

import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class StartGameRequest extends Request<Boolean> {

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null;
  }

  @Override
  protected String getQueryString() {
    return "START_GAME";
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length == 1;
    return answerStrings[0].equals("GAME_STARTED");
  }

}
