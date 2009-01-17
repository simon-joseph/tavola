package data.network;

import java.util.List;
import java.util.ListIterator;

/**
 * @author polchawa
 * 
 */
public final class RequestsAnswerer implements Runnable {
  private RequestsHandler requestsHandler;
  private MessagesPipe messagesPipe;
  private volatile boolean termination = false;
  private volatile boolean errorOccured = false;
  private volatile boolean terminated = false;

  public RequestsAnswerer(RequestsHandler requestsHandler,
      MessagesPipe messagesPipe) {
    this.messagesPipe = messagesPipe;
    this.requestsHandler = requestsHandler;
  }

  public void terminate() {
    termination = true;
  }

  public boolean isTerminated() {
    return terminated;
  }

  public boolean hasErrorOccured() {
    return errorOccured;
  }

  public void run() {
    while (!termination) {
      try {
        Message messageReceived = messagesPipe.readMessage(null);
        List<String> answer = requestsHandler.handleRequest(messageReceived
            .getContent());
        if (answer != null) {
          ListIterator<String> iterator = answer.listIterator();
          while (iterator.hasNext()) {
            String messageString = iterator.next();
            Message message = new Message(messageString);
            message.setRequestId(messageReceived.getRequestId());
            message.setIsAnswer(true);
            messagesPipe.writeMessage(message);
          }
        }
      } catch (ConnectionLostException connectionResetException) {
        LoggerHelper.get().info(
            "RequestAnswerer termination ("
                + requestsHandler.getClass().getName() + ")");
        errorOccured = true;
        terminated = true;
        return;
      }
    }
    terminated = true;
  }
}