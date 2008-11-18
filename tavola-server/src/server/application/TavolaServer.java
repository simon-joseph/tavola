package server.application;

import java.io.IOException;
import java.net.ServerSocket;

import data.game.GameState;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaServer {

  private static GameState gameState;

  private final static int PORT = 4444;

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
   * @return the gameState
   */
  public GameState getGameState() {
    return TavolaServer.gameState;
  }

  /**
   * @param gameState
   *          the gameState to set
   */
  public void setGameState(GameState gameState) {
    TavolaServer.gameState = gameState;
  }
}
