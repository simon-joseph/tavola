package server.protocol;

import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class NextMoveRequest extends Request<String> {

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null;
  }

  @Override
  protected String getQueryString() {
    return "NEXT";
  }

  @Override
  protected String parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length == 1;
    return answerStrings[0];
  }

}
