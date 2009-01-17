package data.game;

import data.network.MessagesPipe;

/**
 * @author rafal.paliwoda
 * 
 */
public class Player {

  private String name;
  private String id;

  private Game game = null;
  private MessagesPipe messagesPipe = null;

  public Player(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    if (game == null && this.game.getCreatorId().equals(id)) {
      this.game.selectNewCreator();
    }
    this.game = game;
  }

  @Override
  public String toString() {
    return getName();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public MessagesPipe getMessagesPipe() {
    return messagesPipe;
  }

  public void setMessagesPipe(MessagesPipe messagesPipe) {
    this.messagesPipe = messagesPipe;
  }
}