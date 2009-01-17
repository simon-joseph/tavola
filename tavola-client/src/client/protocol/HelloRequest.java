package client.protocol;

import data.network.Request;

public class HelloRequest extends Request<Boolean> {

  private String ticket;

  public HelloRequest(String ticket) {
    this.ticket = ticket;
  }

  @Override
  protected boolean endOfAnswer(String s) {
    return s != null;
  }

  @Override
  protected String getQueryString() {
    return "HELLO " + ticket;
  }

  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    assert answerStrings.length == 1;
    return answerStrings[0].equals("OK");
  }

}
