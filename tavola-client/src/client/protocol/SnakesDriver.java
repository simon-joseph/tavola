package client.protocol;

import java.util.LinkedList;

/**
 * @author polchawa
 * 
 */
public interface SnakesDriver {
  public String getMyNextTurn();

  public void setDirections(LinkedList<String> directions);
}
