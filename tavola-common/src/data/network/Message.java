package data.network;

/**
 * @author polchawa
 * 
 */
public final class Message {
  private String content;
  private long requestId = 0;
  private boolean answer = false;

  private static String ANSWER_PREFIX = "ANSWER ";

  public Message(String content) {
    assert content != null;
    this.content = content;
  }

  public static Message createMessageFromString(String fromString)
      throws InvalidMessageStringException {
    assert fromString != null;
    boolean isAnswer = fromString.startsWith(Message.ANSWER_PREFIX);
    if (isAnswer) {
      fromString = fromString.substring(Message.ANSWER_PREFIX.length());
    }
    int position = fromString.indexOf(" ");
    if (position == -1) {
      throw new InvalidMessageStringException();
    }
    Message message = new Message(fromString.substring(position + 1));
    message.setIsAnswer(isAnswer);
    try {
      message.setRequestId(Long.parseLong(fromString.substring(0, position)));
    } catch (NumberFormatException numberFormatException) {
      throw new InvalidMessageStringException();
    }
    return message;
  }

  public String getContent() {
    return content;
  }

  public long getRequestId() {
    return requestId;
  }

  public void setRequestId(long requestId) {
    this.requestId = requestId;
  }

  public boolean isAnswer() {
    return answer;
  }

  public void setIsAnswer(boolean isAnswer) {
    answer = isAnswer;
  }

  @Override
  public String toString() {
    return (answer ? Message.ANSWER_PREFIX : "") + requestId + " " + content;
  }

  public boolean isExpected(Long expectedAnswerId) {
    if (expectedAnswerId == null) {
      return !answer;
    } else {
      return answer && requestId == expectedAnswerId;
    }
  }
}
