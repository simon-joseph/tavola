package data.network;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author polchawa
 * 
 */
public final class MessagesPipe {
  private Pipe pipe;
  private LinkedList<Message> messages = new LinkedList<Message>();
  private ReadWriteLock rwLock = null;

  private synchronized void initRWLock() {
    if (rwLock == null) {
      rwLock = new ReentrantReadWriteLock();
    }
  }

  private Message findMessage(Long expectedAnswerId) {
    synchronized (messages) {
      ListIterator<Message> iterator = messages.listIterator();
      while (iterator.hasNext()) {
        Message message = iterator.next();
        if (message.isExpected(expectedAnswerId)) {
          iterator.remove();
          return message;
        }
      }
      return null;
    }
  }

  public Message readMessage(Long expectedAnswerId)
      throws ConnectionLostException {
    Message message = findMessage(expectedAnswerId);
    if (message != null) {
      return message;
    }

    initRWLock();

    while (true) {
      if (rwLock.writeLock().tryLock()) {
        message = findMessage(expectedAnswerId);
        if (message != null) {
          rwLock.writeLock().unlock();
          return message;
        }
        String s = pipe.readln();
        assert s != null;
        try {
          message = Message.createMessageFromString(s);
          if (message.isExpected(expectedAnswerId)) {
            rwLock.writeLock().unlock();
            return message;
          }
          synchronized (messages) {
            messages.addLast(message);
          }
        } catch (InvalidMessageStringException invalidMessageStringException) {
          // ignoring bad messages is OK
        }
        rwLock.writeLock().unlock();
      } else {
        message = findMessage(expectedAnswerId);
        if (message != null) {
          return message;
        }
        try {
          Thread.sleep(10);
        } catch (InterruptedException e) {
          // ignore
        }
      }
    }
  }

  public void writeMessage(Message message) throws ConnectionLostException {
    assert message != null;
    pipe.println(message.toString());
  }

  public MessagesPipe(Pipe pipe) {
    this.pipe = pipe;
  }

  synchronized public void close() {
    pipe.close();
  }
}
