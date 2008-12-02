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

  private Game game;

  public Player(String id, PrintWriter printWriter) {
    this.id = id;
    game = null;
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

}