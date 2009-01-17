package client.protocol;

import java.util.LinkedList;
import java.util.List;

import data.game.Game;
import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class ListGamesRequest extends Request<List<Game>> {

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null && s.equals("END");
  }

  @Override
  protected String getQueryString() {
    return "LIST_GAMES";
  }

  @Override
  protected List<Game> parseAnswerStrings(String[] answerStrings) {
    LinkedList<Game> games = new LinkedList<Game>();
    for (int i = 0; i < answerStrings.length - 1; ++i) {
      String[] args = answerStrings[i].split(" ");

      Integer maxPlayersCount = null, maxBonusesCount = null;
      try {
        maxPlayersCount = Integer.parseInt(args[2]);
        maxBonusesCount = Integer.parseInt(args[3]);
      } catch (NumberFormatException e) {
        continue;
      }

      final String gameId = args[0];
      final String levelId = args[1];
      final String creatorId = args[4];

      games.addLast(new Game(gameId, null, levelId, maxPlayersCount,
          maxBonusesCount, creatorId, null, 0));
    }
    return games;
  }
}
