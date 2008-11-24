package server.protocol;

import java.io.IOException;
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

    Game game = new Game("somegame1", null, null, 2, 3, null, null, 5);
    Game game2 = new Game("anothergame2", null, null, 2, 3, null, null, 5);

    TavolaServer.addGame(game);
    TavolaServer.addGame(game2);

  }

  public void testListGames() {

    Player player = new Player("player1", null);

    TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player);

    Assert.assertTrue(protocol.processInput("LIST GAME").equals(
        "UNKNOWN_COMMAND"));

    Assert.assertTrue(protocol.processInput("LIST_GAMES").equals(
        ", somegame1, anothergame2"));
  }

  public void testCreateGame() {

    Player player = new Player("player1", null);

    TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player);

    Assert.assertTrue(protocol.processInput("LIST_GAMES").equals(
        ", somegame1, anothergame2"));

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

    // TavolaServer.startUp();

    Player player = new Player("player1", null);// new Socket("localhost",
    // 4444));

    TavolaPreGameProtocol protocol = new TavolaPreGameProtocol(player);

    Assert.assertTrue(protocol.processInput("CREATE_GAME level6 6 6 player1")
        .equals("OK 2"));

    Assert.assertTrue(protocol.processInput("JOIN_GAME game99").equals(
        "INCORRECT_GAME_ID"));

    System.out.println(protocol.processInput("JOIN_GAME 2"));

    Assert.assertTrue(protocol.processInput("JOIN_GAME 2").equals(""));

    // TavolaServer.shutDown();

  }
}
