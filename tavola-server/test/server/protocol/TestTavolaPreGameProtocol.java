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
    Game game2 = new Game("anothergame2", "level1", 4, 30, "player2");

    TavolaServer.addGame(game);
    TavolaServer.addGame(game2);

  }

  public void testListGames() {

    Player player = new Player("player1", null);

    TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player, null);

    Assert.assertTrue(protocol.processInput("LIST GAME").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol.processInput("LIST_GAMES").equals(
        ", somegame1 level2 2 3 player1, anothergame2 level1 4 30 player2"));
  }

  public void testCreateGame() {

    Player player = new Player("player1", null);

    TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player, null);

    Assert.assertTrue(protocol.processInput("LIST_GAMES").equals(
        ", somegame1 level2 2 3 player1, anothergame2 level1 4 30 player2"));

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

    Assert.assertTrue(protocol.processInput("CREATE_GAME a5 5 5 h5").equals(
        "OK 2"));

    Assert.assertTrue(protocol.processInput("CREATE_GAME 5h 5 5 5G").equals(
        "OK 3"));
    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 5 5 5").equals(
        "OK 4"));

    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 5 5 5").equals(
        "OK 5"));

    Assert.assertTrue(protocol.processInput("CREATE_GAME level6 6 6 player1")
        .equals("OK 6"));
    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 5 5 5").equals(
        "OK 7"));
    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 5 5 5").equals(
        "OK 8"));
    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 5 5 5").equals(
        "OK 9"));
    Assert.assertTrue(protocol.processInput("CREATE_GAME 5 5 5 5").equals(
        "GAMES_LIMIT_EXCEEDED"));

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
        "INCORRECT_GAME_ID"));

    Assert.assertTrue(protocol.processInput("JOIN_GAME 2").equals(
        "CANNOT JOIN GAME"));

    Assert.assertTrue(protocol2.processInput("JOIN_GAME 2").equals(
        ", player1, player2"));

    Assert.assertTrue(protocol3.processInput("JOIN_GAME 2").equals(
        ", player1, player2, player3"));
  }
}
