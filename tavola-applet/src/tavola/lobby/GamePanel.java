package tavola.lobby;

import java.awt.BorderLayout;
import java.util.LinkedList;

import javax.swing.JPanel;

import applet.PlayerBoardApplet;
import client.protocol.ClientInGameRequestsHandler;
import client.protocol.SnakesDriver;
import data.game.Game;
import data.game.Player;

/**
 * @author polchawa
 * 
 */
public class GamePanel extends JPanel {

  private static final long serialVersionUID = 1L;

  private final Player player;
  private final PlayerBoardApplet playerBoardApplet;

  public GamePanel(Player player) {
    this.player = player;
    setLayout(new BorderLayout());

    final Game game = player.getGame();
    int playerPosition = 0;
    for (Player p : game.getPlayers()) {
      if (p == player) {
        break;
      }
      playerPosition++;
    }
    int allPlayersCount = game.getPlayers().size();
    if (playerPosition >= allPlayersCount) {
      // TODO: pomyslec co tutaj
      throw new RuntimeException("todo");
    }
    int maxBonusesCount = 0;
    if (game.getGameType().equals("snake")) {
      maxBonusesCount = game.getMaxBonusesCount();
    }
    playerBoardApplet = new PlayerBoardApplet(allPlayersCount, playerPosition,
        game.getSeed(), maxBonusesCount);
    add(playerBoardApplet, BorderLayout.CENTER);
    validate();
  }

  public ClientInGameRequestsHandler createClientInGameRequestsHandler() {
    ClientInGameRequestsHandler clientInGameRequestsHandler = new ClientInGameRequestsHandler(
        player);
    clientInGameRequestsHandler.setSnakesDriver(new SnakesDriver() {
      public String getMyNextTurn() {
        return playerBoardApplet.getMyNextTurn();
      }

      public void setDirections(LinkedList<String> directions) {
        playerBoardApplet.setDirections(directions);
      }
    });
    return clientInGameRequestsHandler;
  }

  public boolean isGameOver() {
    return playerBoardApplet.isGameOver();
  }
}
