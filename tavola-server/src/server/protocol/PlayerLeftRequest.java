package server.protocol;

import data.game.Player;
import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class PlayerLeftRequest extends Request<Boolean> {

  private final Player player;

  public PlayerLeftRequest(Player player) {
    this.player = player;
  }

  @Override
  protected boolean endOfAnswer(String s) {
    return true;
  }

  @Override
  protected String getQueryString() {
    return "PLAYER_LEFT " + player.getId();
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    return true;
  }
}
