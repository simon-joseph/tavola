package client.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import client.protocol.TavolaClientProtocol;
import data.game.Game;

/**
 * @author Piotr Staszak
 * 
 */
public class TavolaClient {

  // private final static double VERSION = 0.1;

  private final static String HOST = "localhost";

  private final static int PORT = 4444;

  private static boolean connected = false;

  private static Game game;

  public static void main(String[] args) throws IOException {

    Socket socket = null;
    PrintWriter out = null;
    BufferedReader in = null;

    try {
      socket = new Socket(TavolaClient.HOST, TavolaClient.PORT);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      TavolaClient.connected = true;
    } catch (UnknownHostException e) {
      System.err.println("Unknown host " + TavolaClient.HOST);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to: "
          + TavolaClient.HOST);
      System.exit(1);
    }

    String fromServer;
    String fromUser;
    TavolaClientProtocol protocol = new TavolaClientProtocol();

    while (TavolaClient.isConnected() && (fromServer = in.readLine()) != null) {
      fromUser = protocol.processInput(fromServer);
      if (fromUser != null) {
        out.println(fromUser);
      }
    }

    TavolaClient.connected = false;
    out.close();
    in.close();
    socket.close();
  }

  public static boolean isConnected() {
    return TavolaClient.connected;
  }

  public static void setConnected(boolean connected) {
    TavolaClient.connected = connected;
  }

  public static Game getGame() {
    return TavolaClient.game;
  }

  public static void setGame(Game game) {
    TavolaClient.game = game;
  }

}
