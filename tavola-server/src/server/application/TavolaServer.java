package server.application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import data.game.Game;

/**
 * @author rafal.paliwoda
 * 
 */
public abstract class TavolaServer {

  private static List<Game> games = new ArrayList<Game>();

  public final static int PORT = 4444;

  public final static double VERSION = 0.1;

  public static void main(String[] args) throws IOException {

    ServerSocket serverSocket = null;
    boolean listening = true;

    try {
      serverSocket = new ServerSocket(TavolaServer.PORT);
    } catch (final IOException e) {
      System.err
          .println("Could not listen on port: " + TavolaServer.PORT + ".");
      System.exit(-1);
    }

    while (listening) {
      new TavolaServerThread(serverSocket.accept()).start();
    }

    serverSocket.close();
  }

  /**
   * TODO something like that
   */

  public synchronized static void gamesStateChanged() {
  }

  public static List<Game> getGames() {
    return TavolaServer.games;
  }

}
