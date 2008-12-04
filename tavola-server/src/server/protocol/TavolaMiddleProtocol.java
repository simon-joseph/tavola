package server.protocol;

import java.io.BufferedReader;

import data.game.Player;
import data.network.TavolaProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaMiddleProtocol implements TavolaProtocol {

  private final Player player;
  private final TavolaPreGameProtocol preGameProtocol;
  private final TavolaInGameProtocol inGameProtocol;
  private final BufferedReader in;
  private boolean inGame;

  public void startGame() {
    inGame = true;
  }

  public void stopGame() {
    inGame = false;
  }

  public TavolaMiddleProtocol(Player player, BufferedReader in) {
    this.player = player;
    this.in = in;
    preGameProtocol = new TavolaPreGameProtocol(this.player, this);
    inGameProtocol = new TavolaInGameProtocol(this.player, this, in);
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