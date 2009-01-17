package data.game;

import java.util.EventListener;


/**
 * @author polchawa
 * 
 */
public interface PlayerListener extends EventListener {
  public void playerActionPerformed(Player player);
}
