package client.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Queue;

import client.protocol.AsyncMessagesHandler;

public class Pipe {
  private BufferedReader br;
  private PrintWriter pw;

  public Pipe(BufferedReader br, PrintWriter pw) {
    this.br = br;
    this.pw = pw;
  }

  synchronized public void println(String s) {
    // System.out.println("sent to pipe: " + s);
    pw.println(s);
  }

  synchronized public boolean readyToRead() throws IOException {
    return br.ready();

  }

  synchronized public String readln() throws IOException {
    String ans = br.readLine();
    // System.out.println("pipe received: " + ans);
    return ans;
  }

  Queue<String> bucket;
  private AsyncMessagesHandler asyncMessagesHandler;

  private static boolean isAsyncString(String s) {
    return s != null && s.startsWith("ASYNC ");
  }

  public void handleAsync(String s) {
    assert Pipe.isAsyncString(s);
    asyncMessagesHandler.handleMessage(s.substring("ASYNC ".length()), this);
  }

  synchronized public String readlnSync() throws IOException {
    while (true) {
      synchronized (bucket) {
        if (!bucket.isEmpty()) {
          return bucket.poll();
        }
      }
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  Thread asyncMessagesReaderThread = null;
  AsyncMessagesReader asyncMessagesReader = null;

  synchronized public void startAsyncMessagesReader(
      AsyncMessagesHandler asyncMessagesHandler) {
    assert this.asyncMessagesHandler == null;
    this.asyncMessagesHandler = asyncMessagesHandler;
    asyncMessagesReader = new AsyncMessagesReader();
    asyncMessagesReaderThread = new Thread(asyncMessagesReader);
    asyncMessagesReaderThread.start();
  }

  synchronized public void stopAsyncMessagesReader() {
    asyncMessagesReader.terminate = true;
    try {
      asyncMessagesReaderThread.join();
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    asyncMessagesHandler = null;
  }

  private class AsyncMessagesReader implements Runnable {
    public volatile boolean terminate = false;

    public void run() {
      while (!terminate) {
        try {
          synchronized (bucket) {
            String s = br.readLine();
            if (Pipe.isAsyncString(s)) {
              handleAsync(s);
            } else {
              bucket.add(s);
            }
          }
        } catch (IOException e) {
          // TODO Auto-generated catch block
          // ???
          e.printStackTrace();
        }
      }
    }
  }

}
