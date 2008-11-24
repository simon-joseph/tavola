package data.game;

/**
 * @author Piotr Staszak
 * 
 */
public class SnakePart {

  private Field next;

  private Field prev;

  public SnakePart(Field next, Field prev) {
    this.next = next;
    this.prev = prev;
  }

  public SnakePart() {
    this(null, null);
  }

  public Field getNext() {
    return next;
  }

  public void setNext(Field next) {
    this.next = next;
  }

  public Field getPrev() {
    return prev;
  }

  public void setPrev(Field prev) {
    this.prev = prev;
  }

  public boolean isLastPart() {
    return next == null;
  }

  public boolean isFirstPart() {
    return prev == null;
  }
}
