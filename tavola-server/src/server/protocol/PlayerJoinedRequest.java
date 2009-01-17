package server.protocol;

import data.game.Player;
import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class PlayerJoinedRequest extends Request<Boolean> {
  private final Player player;

  public PlayerJoinedRequest(Player player) {
    this.player = player;
  }

  @Override
  protected boolean endOfAnswer(String s) {
    return true;
  }

  @Override
  protected String getQueryString() {
    return "PLAYER_JOINED " + player.getId();
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    return true;
  }

}
