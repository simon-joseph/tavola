package server.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import server.protocol.TavolaMiddleProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaServerThread extends Thread {

  private Socket socket = null;

  public TavolaServerThread(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {

    try {
      final PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      final BufferedReader in = new BufferedReader(new InputStreamReader(socket
          .getInputStream()));

      String inputLine, outputLine;
      final TavolaMiddleProtocol protocol = new TavolaMiddleProtocol();
      out.println("VERSION " + TavolaServer.VERSION);

      inputLine = in.readLine();

      if (inputLine.matches("^Hello .+") /* TODO regexp */
          && authentication(inputLine.substring(7)) /* TODO method */) {

        while ((inputLine = in.readLine()) != null) {
          outputLine = protocol.processInput(inputLine);
          out.println(outputLine);
          if (outputLine.equals("END")) {
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

  private boolean authentication(String cookie) {
    // TODO Auto-generated method stub
    return false;
  }

}
