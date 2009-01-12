package data.network;

import java.util.ArrayList;
import java.util.List;

/**
 * @author polchawa
 * 
 */
public class ChatMessages {

  /**
   * @author polchawa
   * 
   */
  public static class Message {

    private String content;
    private String author;
    private Integer id;

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getAuthor() {
      return author;
    }

    public void setAuthor(String author) {
      this.author = author;
    }

    public Integer getId() {
      return id;
    }

    public void setId(Integer id) {
      this.id = id;
    }
  }

  static List<Message> messages = null;

  /**
   * @param substring
   */
  public static void add(String author, String content) {
    // TODO Auto-generated method stub
    if (ChatMessages.messages == null) {
      ChatMessages.messages = new ArrayList<Message>();
    }

    Message msg = new Message();
    msg.setAuthor(author);
    msg.setContent(content);
    msg.setId(ChatMessages.messages.size());
    ChatMessages.messages.add(msg);
  }

  /**
   * @param lastMessageId
   * @return
   */
  public static Message[] getMessages(Integer lastMessageId) {
    // TODO Auto-generated method stub
    if (ChatMessages.messages == null) {
      ChatMessages.messages = new ArrayList<Message>();
    }
    if (ChatMessages.messages.size() <= lastMessageId) {
      return new Message[0];
    }
    Message[] ret = new Message[ChatMessages.messages.size() - lastMessageId];
    for (int i = 0; i < ChatMessages.messages.size() - lastMessageId; ++i) {
      ret[i] = ChatMessages.messages.get(i + lastMessageId);
    }
    return ret;
  }

}
