package data.game;

import java.util.List;

/**
 * @author rafal.paliwoda
 * 
 * game and players settings
 */
public class Game {

  private String id;

  final List<Player> players;

  private String levelId;

  private int maxPlayersCount;

  private int maxBonusesCount;

  private String creatorId;

  private GameState gameState;

  private int speed;

  private boolean isRunning;

  public Game(String id, List<Player> players, String levelId,
      int maxPlayersCount, int maxBonusesCount, String creatorId,
      GameState gameState, int speed) {
    this.id = id;
    this.players = players;
    this.levelId = levelId;
    this.maxPlayersCount = maxPlayersCount;
    this.maxBonusesCount = maxBonusesCount;
    this.creatorId = creatorId;
    this.gameState = gameState;
    this.speed = speed;
    isRunning = false;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLevelId() {
    return levelId;
  }

  public void setLevelId(String levelId) {
    this.levelId = levelId;
  }

  public int getMaxPlayersCount() {
    return maxPlayersCount;
  }

  public void setMaxPlayersCount(int maxPlayersCount) {
    this.maxPlayersCount = maxPlayersCount;
  }

  public int getMaxBonusesCount() {
    return maxBonusesCount;
  }

  public void setMaxBonusesCount(int maxBonusesCount) {
    this.maxBonusesCount = maxBonusesCount;
  }

  public String getCreatorId() {
    return creatorId;
  }

  public void setCreatorId(String creatorId) {
    this.creatorId = creatorId;
  }

  public GameState getGameState() {
    return gameState;
  }

  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public synchronized boolean isRunning() {
    return isRunning;
  }

  public synchronized void setRunning(boolean isRunning) {
    this.isRunning = isRunning;
  }

  @Override
  public String toString() {
    return id + " " + levelId + " " + maxPlayersCount + " " + maxBonusesCount
        + " " + creatorId;
  }

}
