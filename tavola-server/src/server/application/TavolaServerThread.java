package server.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.protocol.TavolaMiddleProtocol;

import com.danga.MemCached.MemCachedClient;

import data.game.Player;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaServerThread extends Thread {

  public final Socket socket;

  public TavolaServerThread(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {

    try {
      final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      final BufferedReader in = new BufferedReader(new InputStreamReader(socket
          .getInputStream()));

      String inputLine, outputLine, id;
      final TavolaMiddleProtocol protocol;

      out.println("VERSION " + TavolaServer.VERSION);
      inputLine = in.readLine();

      if (inputLine.matches("HELLO .+")
          && (id = authentication(inputLine.substring(6))) != null) {

        out.println("OK");

        final Player player = new Player(id, out, this, in);

        protocol = new TavolaMiddleProtocol(player, in);
        try {
          while (TavolaServer.isRunning()
              && (inputLine = in.readLine()) != null) {
            outputLine = protocol.processInput(inputLine);
            synchronized (player) {
              out.println(outputLine);
            }
            if (outputLine.equals("BYE")) {
              break;
            }
          }
        } catch (final IOException e) {
          protocol.processInput("LEAVE_GAME");
          e.printStackTrace();
          throw e;
        }
      }

      else {
        out.println("BAD");
      }

      out.close();
      in.close();
      socket.close();
    } catch (final IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * @return player's id
   */
  private String authentication(String cookie) {
    // TODO Auto-generated method stub

    MemCachedClient cache = new MemCachedClient();
    String cacheKey = cache.get(cookie).toString();

    if (cacheKey == null) {
      return null;
    }

    return cacheKey.split(" ")[0];

  }

}
