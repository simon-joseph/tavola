package server.protocol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import junit.framework.Assert;
import junit.framework.TestCase;
import server.application.TavolaServer;
import data.game.Game;
import data.game.Player;

/**
 * @author rafal.paliwoda
 * 
 */
public class TestTavolaPreGameProtocol extends TestCase {

  @Override
  protected void setUp() {

    TavolaServer.clearGames();

    Game game = new Game("somegame1", null, "level2", 2, 3, "player1", null, 5);
    Game game2 = new Game("anothergame2", null, "level1", 4, 30, "player2",
        null, 99);

    TavolaServer.addGame(game);
    TavolaServer.addGame(game2);

  }

  public void testListGames() {

    Player player = new Player("player1", null);

    TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player, null);

    Assert.assertTrue(protocol.processInput("LIST GAME").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol.processInput("LIST_GAMES").equals(
        "somegame1 level2 2 3 player1\nanothergame2 level1 4 30 player2\nEND"));
  }

  public void testCreateGame() {

    Player player = new Player("player1", null);

    TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player, null);

    TavolaPreGameProtocol protocol2 = new TavolaPreGameProtocol(player, null);

    Assert.assertTrue(protocol.processInput("LIST_GAMES").equals(
        "somegame1 level2 2 3 player1\nanothergame2 level1 4 30 player2\nEND"));

    Assert.assertTrue(protocol.processInput("CREATE_GAME 25").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol.processInput("CREATE_GAME a5 c5").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 s5 d5").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 5 5 5 5 ").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 5 5 5 5 5").equals(
        "UNKNOWN_COMMAND"));

    for (int i = 0; i < 4; i++) {
      Assert
          .assertTrue(new TavolaPreGameProtocol(new Player("asd", null), null)
              .processInput("CREATE_GAME a5 5 5 h5").equals("OK " + (i + 2)));

    }

    Assert.assertTrue(protocol2.processInput("CREATE_GAME level6 6 6 player1")
        .equals("OK 6"));

    for (int i = 5; i < 8; i++) {
      Assert
          .assertTrue(new TavolaPreGameProtocol(new Player("asd", null), null)
              .processInput("CREATE_GAME 5 5 5 5").equals("OK " + (i + 2)));

    }

    Assert.assertTrue(new TavolaPreGameProtocol(new Player("asd", null), null)
        .processInput("CREATE_GAME 5 5 5 5").equals("GAMES_LIMIT_EXCEEDED"));

    Assert.assertTrue(TavolaServer.getGames().get(6).toString().equals(
        "6 level6 6 6 player1"));
  }

  public void testJoinGame() throws UnknownHostException, IOException {

    final Player player = new Player("player1", new PrintWriter(System.out,
        true));

    final Player player2 = new Player("player2", new PrintWriter(System.out,
        true));

    final Player player3 = new Player("player3", new PrintWriter(System.out,
        true));

    final TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player,
        null);

    final TavolaPreGameProtocol protocol2 = new TavolaPreGameProtocol(player2,
        null);

    final TavolaPreGameProtocol protocol3 = new TavolaPreGameProtocol(player3,
        null);

    Assert.assertTrue(protocol.processInput("CREATE_GAME level6 6 6 player1")
        .equals("OK 2"));

    Assert.assertTrue(protocol.processInput("JOIN_GAME game99").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol.processInput("JOIN_GAME 2").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol2.processInput("JOIN_GAME 2").equals(
        "player1\nplayer2\nEND"));

    Assert.assertTrue(protocol3.processInput("JOIN_GAME 2").equals(
        "player1\nplayer2\nplayer3\nEND"));
  }

  public void testLeaveGame() {

    final Player player = new Player("player1", new PrintWriter(System.out,
        true));

    final Player player2 = new Player("player2", new PrintWriter(System.out,
        true));

    final TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player,
        null);

    final TavolaPreGameProtocol protocol2 = new TavolaPreGameProtocol(player2,
        null);

    Assert.assertTrue(protocol.processInput("CREATE_GAME level6 6 6 player1")
        .equals("OK 2"));

    Assert.assertTrue(protocol2.processInput("JOIN_GAME 2").equals(
        "player1\nplayer2\nEND"));

    Assert.assertTrue(protocol2.processInput("LEAVE_GAME").equals("OK"));
  }
}
