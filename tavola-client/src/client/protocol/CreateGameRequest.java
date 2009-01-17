package client.protocol;

import data.network.Request;

public class CreateGameRequest extends Request<String> {
  private String levelId;
  private int maxPlayersCount;
  private int bonusesCount;
  private String theme;

  public final static String GAMES_LIMIT_EXCEEDED = "GAMES_LIMIT_EXCEEDED";

  public CreateGameRequest(String levelId, int maxPlayersCount,
      int bonusesCount, String theme) {
    this.levelId = levelId;
    this.maxPlayersCount = maxPlayersCount;
    this.bonusesCount = bonusesCount;
    this.theme = theme;
  }

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null;
  }

  @Override
  protected String getQueryString() {
    return "CREATE_GAME " + levelId + " " + maxPlayersCount + " "
        + bonusesCount + " " + theme;
  }

  @Override
  protected String parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length == 1;
    if (answerStrings[0].matches("^OK [0-9]+$")) {
      return answerStrings[0].substring(3);
    } else if (answerStrings[0].equals(CreateGameRequest.GAMES_LIMIT_EXCEEDED)) {
      return CreateGameRequest.GAMES_LIMIT_EXCEEDED;
    } else {
      return null;
    }
  }

}
