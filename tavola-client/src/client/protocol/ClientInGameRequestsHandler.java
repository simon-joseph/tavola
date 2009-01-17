package client.protocol;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import data.game.Player;
import data.network.RequestsHandler;

/**
 * @author polchawa
 * 
 */
public class ClientInGameRequestsHandler extends RequestsHandler {

  private final static String NEXT = "NEXT";
  private final static String MOVES = "MOVES";

  private final Player player;

  private SnakesDriver snakesDriver;

  // private PlayerBoardApplet playerBoardApplet;

  public ClientInGameRequestsHandler(Player player) {
    this.player = player;
  }

  @Override
  public List<String> handleRequest(String input) {
    if (input.equals(ClientInGameRequestsHandler.NEXT)) {
      // zwroc kierunek ruchu weza
      return singleStringList(snakesDriver.getMyNextTurn());
    } else if (input.startsWith(ClientInGameRequestsHandler.MOVES + " ")) {
      String moves = input
          .substring(ClientInGameRequestsHandler.MOVES.length() + 1);
      // ustaw kierunki wezy
      Map<String, String> playerMoves = new HashMap<String, String>();
      for (String playerMove : moves.split(" ")) {
        String[] params = playerMove.split("=");
        if (params.length != 2) {
          // TODO ?!
          return null;
        }
        playerMoves.put(params[0], params[1]);
      }
      LinkedList<String> movesList = new LinkedList<String>();
      for (Player p : player.getGame().getPlayers()) {
        movesList.addLast(playerMoves.get(p.getId()));
      }
      snakesDriver.setDirections(movesList);
    }
    return null;
  }

  public SnakesDriver getSnakesDriver() {
    return snakesDriver;
  }

  public void setSnakesDriver(SnakesDriver snakesDriver) {
    this.snakesDriver = snakesDriver;
  }

}
