package server.protocol;

import java.util.TreeMap;

import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class ShowMovesRequest extends Request<Boolean> {

  private final TreeMap<String, String> moves;

  public ShowMovesRequest(TreeMap<String, String> moves) {
    this.moves = moves;
  }

  @Override
  protected boolean endOfAnswer(String s) {
    return true;
  }

  @Override
  protected String getQueryString() {
    // TODO Auto-generated method stub
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("MOVES");
    for (String playerId : moves.keySet()) {
      stringBuilder.append(" " + playerId + "=" + moves.get(playerId));
    }
    return stringBuilder.toString();
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    return true;
  }
}
