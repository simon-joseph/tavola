package data.network;

import java.util.LinkedList;
import java.util.List;

/**
 * @author rafal.paliwoda
 * 
 */
public abstract class RequestsHandler {

  public abstract List<String> handleRequest(String input);

  protected List<String> singleStringList(String s) {
    LinkedList<String> list = new LinkedList<String>();
    list.addLast(s);
    return list;
  }
}
