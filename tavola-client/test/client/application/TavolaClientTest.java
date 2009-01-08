package client.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import junit.framework.Assert;
import junit.framework.TestCase;
import client.protocol.InvalidProtocolException;

public class TavolaClientTest extends TestCase {

  private Pipe pipe;

  private Socket socket = null;

  private PrintWriter out = null;

  private BufferedReader in = null;

  protected void testConnection() throws Exception {

    try {
      socket = new Socket(TavolaClient.HOST, TavolaClient.PORT);
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      // TavolaClient.setConnected(true);
    } catch (UnknownHostException e) {
      System.err.println("Unknown host " + TavolaClient.HOST);
      System.exit(1);
    } catch (IOException e) {
      System.err.println("Couldn't get I/O for the connection to: "
          + TavolaClient.HOST);
      System.exit(1);
    }

    pipe = new Pipe(in, out);
    pipe.readln(); // VERSION xxx
  }

  public void testHello() throws IOException, InvalidProtocolException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String ticket = br.readLine();
    Assert.assertTrue(new HelloGameMessage(ticket).send(pipe));// kopytko*/
  }

  public void testCreateGame() throws IOException, InvalidProtocolException {
    Assert.assertTrue(new CreateGameMessage("testLevel", 4, 5, "emptyTheme")
        .send(pipe) != null);
  }

  public void testJoinGame() throws IOException, InvalidProtocolException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    new JoinGameMessage(br.readLine()).send(pipe);
  }
}
