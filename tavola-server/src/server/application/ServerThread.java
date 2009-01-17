package server.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.protocol.ServerRequestsHandler;

import com.danga.MemCached.SockIOPool;

import data.game.Player;
import data.game.PlayerListener;
import data.network.MessagesPipe;
import data.network.Pipe;
import data.network.RequestsAnswerer;

/**
 * @author rafal.paliwoda
 * 
 */
public class ServerThread extends Thread {

  public final Socket socket;

  public ServerThread(Socket socket) {
    this.socket = socket;
  }

  private MessagesPipe messagesPipe = null;

  public MessagesPipe getMessagesPipe() {
    return messagesPipe;
  }

  @Override
  public void run() {
    try {
      Pipe pipe = null;
      try {
        pipe = new Pipe(new BufferedReader(new InputStreamReader(socket
            .getInputStream())),
            new PrintWriter(socket.getOutputStream(), true));
      } catch (final IOException ioException) {
        return;
      }
      assert pipe != null;
      pipe.println("VERSION " + Server.VERSION);

      messagesPipe = new MessagesPipe(pipe);

      ChatMessages.addServerThread(this);
      try {
        ServerRequestsHandler serverRequestsHandler = new ServerRequestsHandler();
        serverRequestsHandler
            .addPlayerAuthenticationListener(new PlayerListener() {
              public void playerActionPerformed(Player player) {
                player.setMessagesPipe(messagesPipe);
              }
            });
        RequestsAnswerer requestsAnswerer = new RequestsAnswerer(
            serverRequestsHandler, messagesPipe);
        Thread thread = new Thread(requestsAnswerer);
        thread.start();
        try {
          thread.join();
        } catch (InterruptedException e) {
          // konczymy...
        }
      } finally {
        messagesPipe.close();
      }

    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        // ignorujemy
      }
    }

  }

  static {
    SockIOPool s = com.danga.MemCached.SockIOPool.getInstance();
    s.setServers(new String[] { "127.0.0.1:11211" });
    s.initialize();
  }

}
