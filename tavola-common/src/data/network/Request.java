package data.network;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public abstract class Request<T> {
  protected abstract String getQueryString();

  protected abstract T parseAnswerStrings(String[] answerStrings);

  protected T parseAnswer(List<Message> answer) {
    String[] answerStrings = new String[answer.size()];
    ListIterator<Message> iterator = answer.listIterator();
    for (int i = 0; i < answerStrings.length; ++i) {
      answerStrings[i] = iterator.next().getContent();
    }
    return parseAnswerStrings(answerStrings);
  }

  private static long idSequence = 0;

  private long id;

  protected Request() {
    Request.idSequence++;
    id = Request.idSequence;
  }

  protected abstract boolean endOfAnswer(String s);

  private Message getMessage() {
    Message message = new Message(getQueryString());
    message.setIsAnswer(false);
    message.setRequestId(id);
    return message;
  }

  public final static String UNKNOWN_COMMAND = "UNKNOWN_COMMAND";

  public T send(MessagesPipe messagesPipe) throws RequestSendingException,
      ConnectionLostException {
    // todo: block returning non-answer messages from MessagesPipe ?
    // (CREATE_GAME/PLAYER_JOINED race condition problem)
    messagesPipe.writeMessage(getMessage());
    List<Message> answer = new LinkedList<Message>();
    Message message = null;
    while (!endOfAnswer(message == null ? null : message.getContent())) {
      message = messagesPipe.readMessage(id);
      if (message.equals(Request.UNKNOWN_COMMAND)) {
        return null;
      }
      assert message != null && message.isAnswer()
          && message.getRequestId() == id;
      answer.add(message);
    }
    return parseAnswer(answer);
  }

}
