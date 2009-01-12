package client.application;

import client.protocol.GameMessage;
import client.protocol.InvalidProtocolException;
import data.game.Player;

public class JoinGameMessage extends GameMessage<Player[]> {

  private String id;
  private boolean failed = false;

  @Override
  protected boolean endOfAnswer(String s) {
    // TODO Auto-generated method stub
    return s != null
        && (s.equals("END") || s.equals("CANNOT JOIN GAME") && (failed = true));
  }

  public JoinGameMessage(String id) {
    this.id = id;
  }

  @Override
  protected String getMessageString() {
    // TODO Auto-generated method stub
    return "JOIN_GAME " + id;
  }

  @Override
  protected Player[] parseAnswerStrings(String[] answerStrings)
      throws InvalidProtocolException {
    // TODO Auto-generated method stub
    if (answerStrings.length < 1) {
      throw new InvalidProtocolException();
    }
    if (failed) {
      return new Player[0];
    }
    Player[] players = new Player[answerStrings.length];
    for (int i = 0; i < players.length - 1; ++i) {
      String[] a = answerStrings[i].split(" ");
      players[i] = new Player(a[0], null/* a[1] */);
    }
    return players;
  }

}
