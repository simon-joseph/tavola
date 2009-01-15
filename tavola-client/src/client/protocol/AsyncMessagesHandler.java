package client.protocol;

import client.application.Pipe;

/**
 * @author 186841
 * 
 */
public interface AsyncMessagesHandler {
  public void handleMessage(String s, Pipe pipe);
}
