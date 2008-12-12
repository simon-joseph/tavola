package client.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Pipe {
  private BufferedReader br;
  private PrintWriter pw;

  public Pipe(BufferedReader br, PrintWriter pw) {
    this.br = br;
    this.pw = pw;
  }

  synchronized public void println(String s) {
    System.out.println("sent to pipe: " + s);
    pw.println(s);
  }

  synchronized public String readln() throws IOException {
    String ans = br.readLine();
    System.out.println("pipe received: " + ans);
    return ans;
  }
}
