package client.protocol;

import java.util.EventListener;

/**
 * @author polchawa
 * 
 */
public interface ChatMessageListener extends EventListener {
  public void chatMessageReceived(String author, String content);
}
