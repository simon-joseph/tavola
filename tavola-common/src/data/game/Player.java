package data.game;

import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * @author rafal.paliwoda
 * 
 */
public class Player {

  private String name; // TODO
  private String id;
  private PrintWriter printWriter;
  private Game game;
  private Thread serverThread;
  private BufferedReader in;

  public Player(String id, PrintWriter printWriter) {
    this.id = id;
    game = null;
    this.printWriter = printWriter;
  }

  public Player(String id, PrintWriter printWriter, Thread serverThread,
      BufferedReader in) {
    this.id = id;
    game = null;
    this.printWriter = printWriter;
    this.serverThread = serverThread;
    this.in = in;
  }

  public Thread getServerThread() {
    return serverThread;
  }

  public void setServerThread(Thread serverThread) {
    this.serverThread = serverThread;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public PrintWriter getPrintWriter() {
    return printWriter;
  }

  public void setPrintWriter(PrintWriter printWriter) {
    this.printWriter = printWriter;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  @Override
  public String toString() {
    return getId();
  }

  /**
   * @return the in
   */
  public BufferedReader getIn() {
    return in;
  }

  /**
   * @param in
   *            the in to set
   */
  public void setIn(BufferedReader in) {
    this.in = in;
  }

}