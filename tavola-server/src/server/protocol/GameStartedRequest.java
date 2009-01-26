package server.protocol;

import data.network.LoggerHelper;
import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class GameStartedRequest extends Request<Boolean> {

  private final int seed;

  public GameStartedRequest(int seed) {
    this.seed = seed;
    LoggerHelper.get().info("seed = " + seed);
  }

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null;
  }

  @Override
  protected String getQueryString() {
    return "GAME_STARTED " + seed;
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length == 1;
    return answerStrings[0].equals("OK");
  }
}
