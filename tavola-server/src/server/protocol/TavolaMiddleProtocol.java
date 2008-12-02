package server.protocol;

import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaMiddleProtocol implements TavolaProtocol {

  private final Player player;

  final TavolaPreGameProtocol preGameProtocol;

  final TavolaInGameProtocol inGameProtocol;

  private boolean inGame;

  public void startGame() {
    inGame = true;
  }

  public void stopGame() {
    inGame = false;
  }

  public TavolaMiddleProtocol(Player player) {
    this.player = player;
    preGameProtocol = new TavolaPreGameProtocol(this.player, this);
    inGameProtocol = new TavolaInGameProtocol(this.player, this);
    inGame = false;
  }

  @Override
  public String processInput(String input) {
    if (inGame == false) {
      return preGameProtocol.processInput(input);
    } else {
      return inGameProtocol.processInput(input);
    }
  }
}