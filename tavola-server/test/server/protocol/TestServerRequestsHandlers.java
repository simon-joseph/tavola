package server.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.UnknownHostException;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;
import server.application.Server;
import data.game.Game;
import data.game.Player;
import data.network.MessagesPipe;
import data.network.Pipe;

/**
 * @author rafal.paliwoda
 * 
 */
public class TestServerRequestsHandlers extends TestCase {

  @Override
  protected void setUp() {

    Server.clearGames();

    Game game = new Game("somegame1", null, "level2", 2, 3, "player1", null, 5);
    Game game2 = new Game("anothergame2", null, "level1", 4, 30, "player2",
        null, 99);

    Server.addGame(game);
    Server.addGame(game2);

  }

  private void setPlayerMessagesPipe(Player player) {
    player.setMessagesPipe(new MessagesPipe(new Pipe(new BufferedReader(
        new InputStreamReader(System.in)), new PrintWriter(System.out, true))));
  }

  public void testListGames() {

    Player player = new Player("player1", "player1");

    ServerRequestsHandler protocol = new ServerRequestsHandler();
    protocol.setPlayer(player);

    Assert.assertEquals("UNKNOWN_COMMAND", protocol.handleRequest("LIST GAME")
        .get(0));

    List<String> answer = protocol.handleRequest("LIST_GAMES");
    Assert.assertEquals("somegame1 level2 2 3 player1", answer.get(0));
    Assert.assertEquals("anothergame2 level1 4 30 player2", answer.get(1));
    Assert.assertEquals("END", answer.get(2));
  }

  public void testCreateGame() {

    Player player = new Player("player1", "player1");

    ServerRequestsHandler protocol = new ServerRequestsHandler();
    protocol.setPlayer(player);

    ServerRequestsHandler protocol2 = new ServerRequestsHandler();
    protocol2.setPlayer(player);

    Assert.assertEquals("UNKNOWN_COMMAND", protocol.handleRequest(
        "CREATE_GAME 25").get(0));

    Assert.assertEquals("UNKNOWN_COMMAND", protocol.handleRequest(
        "CREATE_GAME a5 c5").get(0));

    Assert.assertEquals("UNKNOWN_COMMAND", protocol.handleRequest(
        "CREATE_GAME 5 s5 d5").get(0));

    Assert.assertEquals("UNKNOWN_COMMAND", protocol.handleRequest(
        "CREATE_GAME 5 5 5 5 5").get(0));

    for (int i = 0; i < 4; i++) {
      ServerRequestsHandler serverRequestsHandler = new ServerRequestsHandler();
      serverRequestsHandler.setPlayer(new Player("asd", "asd"));
      Assert.assertEquals("OK " + (i + 2), serverRequestsHandler.handleRequest(
          "CREATE_GAME a5 5 5 h5").get(0));

    }

    Assert.assertEquals("OK 6", protocol2.handleRequest(
        "CREATE_GAME level6 6 6 player1").get(0));

    for (int i = 5; i < 8; i++) {
      ServerRequestsHandler serverRequestsHandler = new ServerRequestsHandler();
      serverRequestsHandler.setPlayer(new Player("asd", "asd"));
      Assert.assertEquals("OK " + (i + 2), serverRequestsHandler.handleRequest(
          "CREATE_GAME 5 5 5 5").get(0));

    }

    ServerRequestsHandler serverRequestsHandler = new ServerRequestsHandler();
    serverRequestsHandler.setPlayer(new Player("asd", "asd"));
    Assert.assertEquals("GAMES_LIMIT_EXCEEDED", serverRequestsHandler
        .handleRequest("CREATE_GAME 5 5 5 5").get(0));

    Assert.assertEquals("6 level6 6 6 player1", Server.getGames().get(6)
        .toString());
  }

  public void testJoinGame() throws UnknownHostException, IOException {

    final Player player = new Player("player1", "player1");
    setPlayerMessagesPipe(player);

    final Player player2 = new Player("player2", "player2");
    setPlayerMessagesPipe(player2);

    final Player player3 = new Player("player3", "player3");
    setPlayerMessagesPipe(player3);

    final Player player4 = new Player("player3", "player3");
    setPlayerMessagesPipe(player4);

    final ServerRequestsHandler protocol = new ServerRequestsHandler();
    protocol.setPlayer(player);

    final ServerRequestsHandler protocol2 = new ServerRequestsHandler();
    protocol2.setPlayer(player2);

    final ServerRequestsHandler protocol3 = new ServerRequestsHandler();
    protocol3.setPlayer(player3);

    final ServerRequestsHandler protocol4 = new ServerRequestsHandler();
    protocol4.setPlayer(player4);

    Assert.assertEquals("OK 2", protocol.handleRequest(
        "CREATE_GAME level6 6 6 player1").get(0));

    Assert.assertEquals("UNKNOWN_COMMAND", protocol.handleRequest(
        "JOIN_GAME game99").get(0));

    Assert.assertEquals("UNKNOWN_COMMAND", protocol
        .handleRequest("JOIN_GAME 2").get(0));

    List<String> answer = protocol2.handleRequest("JOIN_GAME 2");
    Assert.assertEquals("player1 player1", answer.get(0));
    Assert.assertEquals("player2 player2", answer.get(1));
    Assert.assertEquals("END", answer.get(2));

    Assert.assertEquals(protocol3.handleRequest("JOIN_GAME 3").get(0),
        "INCORRECT_GAME_ID");

    answer = protocol3.handleRequest("JOIN_GAME 2");
    Assert.assertEquals("player1 player1", answer.get(0));
    Assert.assertEquals("player2 player2", answer.get(1));
    Assert.assertEquals("player3 player3", answer.get(2));
    Assert.assertEquals("END", answer.get(3));

    Assert.assertEquals("CANNOT_JOIN_GAME", protocol4.handleRequest(
        "JOIN_GAME 2").get(0));
  }

  public void testLeaveGame() {

    final Player player = new Player("player1", "player1");
    setPlayerMessagesPipe(player);

    final Player player2 = new Player("player2", "player2");
    setPlayerMessagesPipe(player2);

    final ServerRequestsHandler protocol = new ServerRequestsHandler();
    protocol.setPlayer(player);

    final ServerRequestsHandler protocol2 = new ServerRequestsHandler();
    protocol2.setPlayer(player2);

    Assert.assertEquals("UNKNOWN_COMMAND", protocol.handleRequest("LEAVE_GAME")
        .get(0));

    Assert.assertEquals("OK 2", protocol.handleRequest(
        "CREATE_GAME level6 6 6 player1").get(0));

    List<String> answer = protocol2.handleRequest("JOIN_GAME 2");
    Assert.assertEquals("player1 player1", answer.get(0));
    Assert.assertEquals("player2 player2", answer.get(1));
    Assert.assertEquals("END", answer.get(2));

    Assert.assertEquals("OK", protocol2.handleRequest("LEAVE_GAME").get(0));

    answer = protocol2.handleRequest("JOIN_GAME 2");
    Assert.assertEquals("player1 player1", answer.get(0));
    Assert.assertEquals("player2 player2", answer.get(1));
    Assert.assertEquals("END", answer.get(2));

    Assert.assertEquals("OK", protocol2.handleRequest("LEAVE_GAME").get(0));

    Assert.assertEquals("UNKNOWN_COMMAND", protocol2
        .handleRequest("LEAVE_GAME").get(0));

    Assert.assertEquals("OK", protocol.handleRequest("LEAVE_GAME").get(0));
    Assert.assertEquals("INCORRECT_GAME_ID", protocol2.handleRequest(
        "JOIN_GAME 2").get(0));
  }

  public void testChat() {
    final Player player = new Player("player1", "player1");
    final ServerRequestsHandler protocol = new ServerRequestsHandler();
    protocol.setPlayer(player);

    final Player player2 = new Player("player2", "player2");
    final ServerRequestsHandler protocol2 = new ServerRequestsHandler();
    protocol2.setPlayer(player2);

    Assert.assertEquals("OK", protocol.handleRequest("CHAT MSG test").get(0));
  }

  public void testChatWhileGameStartAwaiting() {
    final Player player = new Player("player1", "player1");

    final ServerRequestsHandler protocol = new ServerRequestsHandler();
    protocol.setPlayer(player);

    final Player player2 = new Player("player2", "player2");
    final ServerRequestsHandler protocol2 = new ServerRequestsHandler();
    protocol2.setPlayer(player2);

    Assert.assertEquals("OK 2", protocol.handleRequest(
        "CREATE_GAME level6 6 6 player1").get(0));

    Assert.assertEquals("OK", protocol.handleRequest("CHAT MSG test2").get(0));
  }
}
