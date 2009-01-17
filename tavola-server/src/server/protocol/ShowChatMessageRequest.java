package server.protocol;

import data.network.Request;

/**
 * @author polchawa
 * 
 */
public class ShowChatMessageRequest extends Request<Boolean> {

  private String author;
  private String content;

  /**
   * @param author
   * @param content
   */
  public ShowChatMessageRequest(String author, String content) {
    this.author = author;
    this.content = content;
  }

  /*
   * (non-Javadoc)
   * 
   * @see data.network.Request#endOfAnswer(java.lang.String)
   */
  @Override
  protected boolean endOfAnswer(String s) {
    // TODO Auto-generated method stub
    return true;
  }

  /*
   * (non-Javadoc)
   * 
   * @see data.network.Request#getQueryString()
   */
  @Override
  protected String getQueryString() {
    // TODO Auto-generated method stub
    return "CHAT MSG " + author + ":" + content;
  }

  /*
   * (non-Javadoc)
   * 
   * @see data.network.Request#parseAnswerStrings(java.lang.String[])
   */
  @Override
  protected Boolean parseAnswerStrings(String[] answerStrings) {
    // TODO Auto-generated method stub
    return true;
  }

}
