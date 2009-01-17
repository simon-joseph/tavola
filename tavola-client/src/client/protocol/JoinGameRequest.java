package client.protocol;

import java.util.LinkedList;
import java.util.List;

import data.game.Player;
import data.network.Request;

public class JoinGameRequest extends Request<List<Player>> {

  private String gameId;
  private boolean failed = false;

  private final static String CANNOT_JOIN_GAME = "CANNOT_JOIN_GAME";
  private final static String INCORRECT_GAME_ID = "INCORRECT_GAME_ID";

  public JoinGameRequest(String id) {
    gameId = id;
  }

  @Override
  protected boolean endOfAnswer(String s) {
    if (s == null) {
      return false;
    }
    if (s.equals(JoinGameRequest.CANNOT_JOIN_GAME)
        || s.equals(JoinGameRequest.INCORRECT_GAME_ID)) {
      failed = true;
      return true;
    }
    return s.equals("END");
  }

  @Override
  protected String getQueryString() {
    return "JOIN_GAME " + gameId;
  }

  @Override
  protected List<Player> parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length > 0;
    if (failed) {
      return null;
    }
    LinkedList<Player> players = new LinkedList<Player>();
    for (int i = 0; i < answerStrings.length - 1; ++i) {
      String[] a = answerStrings[i].split(" ", 2);
      if (a.length != 2) {
        return null;
      }
      players.addLast(new Player(a[0], a[1]));
    }
    return players;
  }

}
