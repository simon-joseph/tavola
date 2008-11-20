package data.game;

import java.util.List;

/**
 * @author rafal.paliwoda
 * 
 *         game and players settings
 */
public class Game {

  private int id;

  List<Player> players;

  private int levelId; // TODO enums

  private int maxPlayersCount;

  private int maxBonusesCount;

  private int creatorId;

  private GameState gameState;

  private int speed;

  // ...

}
