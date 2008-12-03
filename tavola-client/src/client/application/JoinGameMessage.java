package client.application;

import client.protocol.GameMessage;
import client.protocol.InvalidProtocolException;
import data.gamePawel.Player;

public class JoinGameMessage extends GameMessage<Player[]> {

  private String id;

  @Override
  protected boolean endOfAnswer(String s) {
    // TODO Auto-generated method stub
    return s.equals("END");
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
    Player[] players = new Player[answerStrings.length - 1];
    for (int i = 0; i < players.length; ++i) {
      if (answerStrings[i].equals("END")) {
        if (i < players.length - 1) {
          throw new InvalidProtocolException();
        } else {
          break;
        }
      }
      String[] a = answerStrings[i].split(" ");
      players[i] = new Player(a[0], a[1]);
    }
    return players;
  }

}
