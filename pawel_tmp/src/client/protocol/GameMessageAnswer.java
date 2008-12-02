package client.protocol;

import java.io.IOException;
import java.util.ArrayList;

import client.application.Pipe;

public abstract class GameMessageAnswer<T> {

  protected abstract T parseMessageStrings(String[] message)
      throws InvalidProtocolException;

  protected abstract boolean endOfMessage(String s);

  protected abstract String getAnswerString();

  protected abstract boolean isAnswer();

  public T answer(Pipe pipe) throws IOException, InvalidProtocolException {
    ArrayList<String> message = new ArrayList<String>();
    String s;
    do {
      s = pipe.readln();
      if (s == null) {
        throw new InvalidProtocolException();
      }
      message.add(s);
    } while (!endOfMessage(s));
    T result = parseMessageStrings(message.toArray(new String[] {}));

    if (isAnswer()) {
      pipe.println(getAnswerString());
    }
    return result;
  }

}
