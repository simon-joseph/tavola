package data.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class Pipe {
  private BufferedReader bufferedReader;
  private PrintWriter printWriter;

  public Pipe(BufferedReader br, PrintWriter pw) {
    bufferedReader = br;
    printWriter = pw;
  }

  public void println(String s) {
    synchronized (printWriter) {
      Logger.getLogger("tavola").finest("Sent to pipe: " + s);
      printWriter.println(s);
    }
  }

  public boolean readyToRead() throws ConnectionLostException {
    try {
      synchronized (bufferedReader) {
        return bufferedReader.ready();
      }
    } catch (IOException e) {
      throw new ConnectionLostException();
    }
  }

  public String readln() throws ConnectionLostException {
    synchronized (bufferedReader) {
      String ans;
      try {
        ans = bufferedReader.readLine();
        if (ans == null) {
          throw new ConnectionLostException();
        }
      } catch (IOException e) {
        throw new ConnectionLostException();
      }
      Logger.getLogger("tavola").finest("Received from pipe: " + ans);
      return ans;
    }
  }

  synchronized public void close() {
    try {
      synchronized (bufferedReader) {
        bufferedReader.close();
      }
    } catch (IOException e) {
      Logger.getLogger("tavola").warning("Pipe: bufferedReader closing failed");
    }
    synchronized (printWriter) {
      printWriter.close();
    }
  }

}
