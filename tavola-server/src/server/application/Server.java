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
public final class Server {

  private final static List<Game> games = new ArrayList<Game>();
  private final static int maxGamesCount = 10;
  private static Integer gamesCounter = 0;
  public final static int PORT = 4444;
  public final static double VERSION = 0.1;
  private static volatile boolean listening;

  private Server() {

  }

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = null;
    Server.listening = true;
    try {
      serverSocket = new ServerSocket(Server.PORT);
    } catch (final IOException e) {
      System.err.println("Could not listen on port: " + Server.PORT);
      System.exit(-1);
    }
    // LoggerHelper.init("tavola", Level.ALL);
    assert serverSocket != null;
    try {
      while (Server.listening) {
        new ServerThread(serverSocket.accept()).start();
      }
    } finally {
      serverSocket.close();
    }
  }

  public synchronized static List<Game> getGames() {
    return Server.games;
  }

  public static Integer getGamesCounter() {
    return Server.gamesCounter;
  }

  public synchronized static boolean addGame(Game game) {
    return ++Server.gamesCounter > 0
        && Server.games.size() < Server.maxGamesCount && Server.games.add(game);
  }

  /**
   * 
   */
  private static List<String> getPlayersIds(Game game) {
    ArrayList<String> ids = new ArrayList<String>();

    for (Player player : game.getPlayers()) {
      ids.add(player.getId());
    }
    return ids;
  }

  public synchronized static boolean addPlayer(Game game, Player player) {
    boolean ok = game.getPlayers().size() < game.getMaxPlayersCount()
        && !Server.getPlayersIds(game).contains(player.getId())
        && game.getPlayers().add(player) && !game.isRunning();
    if (ok) {
      player.setGame(game);
    }
    return ok;
  }

  public synchronized static boolean removePlayer(Game game, Player player) {
    if (game.isRunning() == false) {
      game.getPlayers().remove(player);
      player.setGame(null);
      if (game.getPlayers().isEmpty()) {
        Server.removeGame(game);
      }
      return true;
    } else {
      return false;
    }
  }

  private synchronized static boolean removeGame(Game game) {
    return game.getPlayers().isEmpty() && Server.games.remove(game);
  }

  public static void clearGames() {
    Server.games.clear();
    Server.gamesCounter = 0;
  }

  public static void shutDown() {
    Server.listening = false;
  }

  public static boolean isRunning() {
    return Server.listening;
  }
}
