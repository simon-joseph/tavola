package client.application;

import client.protocol.GameMessage;

public class HelloGameMessage extends GameMessage<Boolean> {

  @Override
  protected boolean endOfAnswer(String s) {
    // TODO Auto-generated method stub
    return s != null;
  }

  @Override
  protected String getMessageString() {
    // TODO Auto-generated method stub
    return "HELLO " + ticket;
  }

  private String ticket;

  public HelloGameMessage(String ticket) {
    this.ticket = ticket;
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    // TODO Auto-generated method stub
    return answerStrings.length == 1 && answerStrings[0].equals("OK");
  }

}
