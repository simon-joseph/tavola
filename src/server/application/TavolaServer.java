package server.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import server.protocol.MiddleProtocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class TavolaServer {

  final static int PORT = 4444;

  public static void main(String[] args) throws IOException {

    ServerSocket serverSocket = null;
    try {
      serverSocket = new ServerSocket(TavolaServer.PORT);
    } catch (final IOException e) {
      System.err
          .println("Could not listen on port: " + TavolaServer.PORT + ".");
      System.exit(1);
    }

    Socket clientSocket = null;
    try {
      clientSocket = serverSocket.accept();
    } catch (final IOException e) {
      System.err.println("Accept failed.");
      System.exit(1);
    }

    final PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),
        true);
    final BufferedReader in = new BufferedReader(new InputStreamReader(
        clientSocket.getInputStream()));
    String inputLine, outputLine;

    final MiddleProtocol prot = new MiddleProtocol();

    outputLine = prot.processInput(null);
    out.println(outputLine);

    while ((inputLine = in.readLine()) != null) {
      outputLine = prot.processInput(inputLine);
      out.println(outputLine);
      if (outputLine.equals("END.")) {
        break;
      }
    }
    out.close();
    in.close();
    clientSocket.close();
    serverSocket.close();
  }

}
