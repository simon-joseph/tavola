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

  final static String HOST = "localhost";

  final static int PORT = 4444;

  static boolean connected = false;
  
  private static Game game;
  

  public static void main(String[] args) throws IOException {
     
    //String fromServer;
    //String fromUser;
    //TavolaClientProtocol protocol = new TavolaClientProtocol();

    /* jak wy to chcieliscie zrobic? przeciez klient nie zachowuje sie deterministycznie, i to co oraz kiedy wysle zalezy od uzytkownika
     * while (TavolaClient.isConnected() && (fromServer = in.readLine()) != null) {
      System.out.println("pakiet " + fromServer);
      fromUser = protocol.processInput(fromServer);
      System.out.println("odp:" + fromUser);
      if (fromUser != null) {
        out.println(fromUser);
      }
    }*/

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
