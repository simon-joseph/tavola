package client.protocol;

import java.util.EventListener;

import data.game.Game;

/**
 * @author polchawa
 * 
 */
public interface GameListener extends EventListener {
  public void gameActionPerformed(Game game);
}
