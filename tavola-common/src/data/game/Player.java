package data.game;

import java.net.Socket;

/**
 * @author rafal.paliwoda
 * 
 */
public class Player {

  private String name; // TODO

  private String id;

  private Socket socket;

  public Player(String id, Socket socket) {
    this.id = id;
    this.socket = socket;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

}