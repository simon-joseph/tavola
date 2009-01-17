package client.protocol;

import java.util.List;

import javax.swing.event.EventListenerList;

import data.network.RequestsHandler;

/**
 * @author polchawa
 * 
 */
public class ClientChatRequestsHandler extends RequestsHandler {

  EventListenerList eventListenersList = new EventListenerList();

  public void addChatMessageListener(ChatMessageListener chatMessageListener) {
    eventListenersList.add(ChatMessageListener.class, chatMessageListener);
  }

  public void removeChatMessageListener(ChatMessageListener chatMessageListener) {
    eventListenersList.remove(ChatMessageListener.class, chatMessageListener);
  }

  @Override
  public List<String> handleRequest(String input) {
    if (input.startsWith("CHAT MSG ")) {
      input = input.substring("CHAT MSG ".length());
      String[] params = input.split(":", 2);
      if (params.length == 2) {
        for (ChatMessageListener eventListener : eventListenersList
            .getListeners(ChatMessageListener.class)) {
          eventListener.chatMessageReceived(params[0], params[1]);
        }
      }
    }
    return null;
  }

}
