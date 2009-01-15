package client.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TavolaClient {

  public static final String DEFAULT_HOST = "snake.fm";
  public static final Integer DEFAULT_PORT = 4444;

  private Pipe pipe;

  public TavolaClient(String host, int port) throws IOException {
    Socket socket = new Socket(host, port);
    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
    BufferedReader in = new BufferedReader(new InputStreamReader(socket
        .getInputStream()));
    pipe = new Pipe(in, out);
    pipe.readln(); // VERSION xxx
  }

  public Pipe getPipe() {
    return pipe;
  }
}
