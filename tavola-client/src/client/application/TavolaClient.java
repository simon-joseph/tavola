package client.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TavolaClient {

  public final static String HOST = "localhost";
  public final static int PORT = 4444;
  public volatile static boolean inGame;
  private static Pipe pipe;

  public static void main(String[] args) {

    try {
      Socket socket = new Socket(TavolaClient.HOST, TavolaClient.PORT);
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket
          .getInputStream()));
      TavolaClient.pipe = new Pipe(in, out);
    } catch (UnknownHostException e) {
      System.err.println("Unknown host " + TavolaClient.HOST);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to: "
          + TavolaClient.HOST);
      System.exit(1);
    }

    // Po rozpoczęciu gry
    TavolaClient.inGame = true;
    TavolaInGameClient inGameClient = new TavolaInGameClient(TavolaClient.pipe);
    new Thread(inGameClient).start();
    // TavolaClient.inGame = false; - usypia go
    // inGameClient.kill(); - zabija
  }

  public static void nextMoves(String[] array) {
    // TODO Auto-generated method stub
    System.out.println("MOVES");
    for (String s : array) {
      System.out.println(s);
    }
  }

  public static String getLastMove() {
    // TODO Auto-generated method stub
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("??");
      return br.readLine();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return "0";
    }
  }

}
