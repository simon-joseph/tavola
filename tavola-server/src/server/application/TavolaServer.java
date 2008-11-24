package server.application;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import data.game.Game;
import data.game.Player;

/**
 * @author rafal.paliwoda
 * 
 */
public abstract class TavolaServer {

  private final static List<Game> games = new ArrayList<Game>();

  private final static int maxGamesCount = 10;

  private static Integer gamesCounter = 0;

  public final static int PORT = 4444;

  public final static double VERSION = 0.1;

  private static boolean listening;

  public static void main(String[] args) throws IOException {

    ServerSocket serverSocket = null;
    TavolaServer.listening = true;

    try {
      serverSocket = new ServerSocket(TavolaServer.PORT);
    } catch (final IOException e) {
      System.err.println("Could not listen on port: " + TavolaServer.PORT);
      System.exit(-1);
    }

    while (TavolaServer.listening) {
      new TavolaServerThread(serverSocket.accept()).start();
    }

    serverSocket.close();
  }

  public synchronized static void gamesStateChanged() {
  }

  public synchronized static List<Game> getGames() {
    return TavolaServer.games;
  }

  public static Integer getGamesCounter() {
    return TavolaServer.gamesCounter;
  }

  public synchronized static boolean addGame(Game game) {
    return ++TavolaServer.gamesCounter > 0
        && TavolaServer.games.size() < TavolaServer.maxGamesCount
        && TavolaServer.games.add(game);
  }

  public synchronized static boolean addPlayer(Game game, Player player) {
    return game.getPlayers().size() < game.getMaxPlayersCount()
        && game.getPlayers().add(player);
  }

  public static void clearGames() {
    TavolaServer.games.clear();
    TavolaServer.gamesCounter = 0;
  }

  public static void shutDown() {
    TavolaServer.listening = false;
  }

  public static boolean isRunning() {
    return TavolaServer.listening;
  }

  public static void startUp() throws IOException {
    TavolaServer.main(new String[] {});
  }
}
