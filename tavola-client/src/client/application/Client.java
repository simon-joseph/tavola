package client.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

import client.protocol.ClientRequestsHandler;
import data.game.Player;
import data.network.ConnectionLostException;
import data.network.LoggerHelper;
import data.network.MessagesPipe;
import data.network.Pipe;

public class Client {

  public static final String DEFAULT_HOST = "localhost";// "snake.fm";
  public static final Integer DEFAULT_PORT = 4444;

  private MessagesPipe messagesPipe;
  private Socket socket;
  private Player player = null;
  private ClientRequestsHandler clientRequestsHandler = null;

  public Client(String host, int port) throws ConnectionLostException,
      UnknownHostException {
    try {
      socket = new Socket(host, port);
    } catch (IOException e) {
      throw new ConnectionLostException();
    }
    PrintWriter out = null;
    try {
      out = new PrintWriter(socket.getOutputStream(), true);
    } catch (IOException e) {
      try {
        socket.close();
      } catch (IOException e1) {
        // ignorujemy
      }
      throw new ConnectionLostException();
    }
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    } catch (IOException e) {
      out.close();
      try {
        socket.close();
      } catch (IOException e1) {
        // ignorujemy
      }
      throw new ConnectionLostException();
    }
    LoggerHelper.init("tavola", Level.ALL);
    Pipe pipe = new Pipe(in, out);
    pipe.readln(); // VERSION xxx
    messagesPipe = new MessagesPipe(pipe);
  }

  public MessagesPipe getMessagesPipe() {
    return messagesPipe;
  }

  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
      // ignorujemy
    }
  }

  public Player getPlayer() {
    return player;
  }

  public void setPlayer(Player player) {
    this.player = player;

    if (clientRequestsHandler != null) {
      clientRequestsHandler.setPlayer(player);
    }
  }

  public ClientRequestsHandler getClientRequestsHandler() {
    return clientRequestsHandler;
  }

  public void setClientRequestsHandler(
      ClientRequestsHandler clientRequestsHandler) {
    this.clientRequestsHandler = clientRequestsHandler;
    clientRequestsHandler.setPlayer(player);
  }
}
