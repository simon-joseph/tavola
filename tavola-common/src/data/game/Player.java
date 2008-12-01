package data.game;

import java.io.PrintWriter;

/**
 * @author rafal.paliwoda
 * 
 */
public class Player {

  private String name; // TODO

  private String id;

  private PrintWriter printWriter;

  public Player(String id, PrintWriter printWriter) {
    this.id = id;
    this.printWriter = printWriter;
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

  @Override
  public String toString() {
    return getId();
  }
}