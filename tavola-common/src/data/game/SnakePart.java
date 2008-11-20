package data.game;

/**
 * @author Piotr Staszak
 * 
 */
public class SnakePart {

  private Field next;

  private Field prev;

  /**
   * @param next
   * @param prev
   */
  public SnakePart(Field next, Field prev) {
    this.next = next;
    this.prev = prev;
  }

  public SnakePart() {
    this(null, null);
  }

  /**
   * @return the next
   */
  public Field getNext() {
    return next;
  }

  /**
   * @param next
   *          the next to set
   */
  public void setNext(Field next) {
    this.next = next;
  }

  /**
   * @return the prev
   */
  public Field getPrev() {
    return prev;
  }

  /**
   * @param prev
   *          the prev to set
   */
  public void setPrev(Field prev) {
    this.prev = prev;
  }

  /**
   * return true if it is the last part of snake
   */
  public boolean isLastPart() {
    return next == null;
  }

  /**
   * return true if it is the first part of snake
   */
  public boolean isFirstPart() {
    return prev == null;
  }
}
