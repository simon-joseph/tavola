package client.application;

import client.protocol.GameMessage;
import client.protocol.InvalidProtocolException;
import data.game.Game;

/**
 * @author polchawa
 * 
 */
public class ListGameMessage extends GameMessage<Game[]> {

  /*
   * (non-Javadoc)
   * 
   * @see client.protocol.GameMessage#endOfAnswer(java.lang.String)
   */
  @Override
  protected boolean endOfAnswer(String s) {
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
    return "LIST_GAMES";
  }

  /*
   * (non-Javadoc)
   * 
   * @see client.protocol.GameMessage#parseAnswerStrings(java.lang.String[])
   */
  @Override
  protected Game[] parseAnswerStrings(String[] answerStrings)
      throws InvalidProtocolException {
    // TODO Auto-generated method stub
    Game[] games = new Game[answerStrings.length - 1];
    for (int i = 0; i < answerStrings.length - 1; ++i) {
      String[] args = answerStrings[i].split(" ");

      Integer maxPlayersCount = null, maxBonusesCount = null;
      try {
        maxPlayersCount = Integer.parseInt(args[2]);
        maxBonusesCount = Integer.parseInt(args[3]);
      } catch (NumberFormatException e) {
        games[i] = null;
        continue;
      }

      final String gameId = args[0];
      final String levelId = args[1];
      final String creatorId = args[4];

      games[i] = new Game(gameId, null, levelId, maxPlayersCount,
          maxBonusesCount, creatorId, null, 0);
    }
    return games;
  }

}
