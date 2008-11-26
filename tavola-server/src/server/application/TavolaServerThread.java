package server.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.protocol.TavolaMiddleProtocol;
import data.game.Player;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaServerThread extends Thread {

  private final Socket socket;

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

      if (inputLine.matches("Hello .+")
          && (id = authentication(inputLine.substring(6))) != null) {

        Player player = new Player(id, socket);

        protocol = new TavolaMiddleProtocol(player);

        while (TavolaServer.isRunning() && (inputLine = in.readLine()) != null) {
          outputLine = protocol.processInput(inputLine);
          synchronized (player) {
            out.println(outputLine);
          }
          if (outputLine.equals("BYE")) {
            break;
          }
        }
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
    return "0";
  }

}
