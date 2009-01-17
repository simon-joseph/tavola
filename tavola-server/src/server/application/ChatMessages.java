package server.application;

import java.util.LinkedList;

import server.protocol.ShowChatMessageRequest;
import data.network.ConnectionLostException;
import data.network.RequestSendingException;

/**
 * @author polchawa
 * 
 */
public class ChatMessages {

  private static void initThreads() {
    if (ChatMessages.threads == null) {
      ChatMessages.threads = new LinkedList<ServerThread>();
    }
  }

  /**
   * @param substring
   */
  public static void addMessage(String author, String content) {
    ChatMessages.initThreads();
    // TODO Auto-generated method stub
    ShowChatMessageRequest request = new ShowChatMessageRequest(author, content);
    for (ServerThread thread : ChatMessages.threads) {
      try {
        request.send(thread.getMessagesPipe());
      } catch (RequestSendingException e) {
        // ignore?
      } catch (ConnectionLostException e) {
        // ignore
      }
    }
  }

  public static void addServerThread(ServerThread thread) {
    assert thread != null;
    ChatMessages.initThreads();
    ChatMessages.threads.addLast(thread);
  }

  public static boolean removeServerThread(ServerThread thread) {
    assert thread != null;
    ChatMessages.initThreads();
    return ChatMessages.threads.remove(thread);
  }

  private static LinkedList<ServerThread> threads = null;
}
