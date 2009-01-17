package tavola.lobby;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import javax.swing.JApplet;
import javax.swing.JLabel;

import client.application.Client;
import client.protocol.ClientRequestsHandler;
import client.protocol.HelloRequest;
import data.game.Player;
import data.network.ConnectionLostException;
import data.network.RequestsAnswerer;

/**
 * @author polchawa
 * 
 */
public class LobbyApplet extends JApplet {
  public static final long serialVersionUID = 1;

  private Thread clientRequestsAnswererThread = null;
  private Client client = null;

  @Override
  public void init() {
    try {
      client = new Client(Client.DEFAULT_HOST, Client.DEFAULT_PORT);

      String ticket = getParameter("ticket");
      if (ticket == null) {
        // tymczasowo:
        try {
          System.out.print("login: ");
          System.out.flush();
          ticket = new BufferedReader(new InputStreamReader(System.in))
              .readLine();
        } catch (IOException e) {
          ticket = "anonymous";
        }
        // ---
      }

      if (!new HelloRequest(ticket).send(client.getMessagesPipe())) {
        add(new JLabel("Zaloguj się ponownie..."));
        return;
      }
      setLayout(new BorderLayout());

      client.setPlayer(new Player(ticket, ticket)); // fixme
      client.setClientRequestsHandler(new ClientRequestsHandler());

      GUIDriver driver = new GUIDriver();
      ChatPanel chatPanel = new ChatPanel(client, driver);
      driver.setChatPanel(chatPanel);
      driver.setJoinGamePanel(new JoinGamePanel(client, driver));
      driver.setCreateGamePanel(new CreateGamePanel(client, driver));
      driver.setApplet(this);

      driver.initGUI();

      ClientRequestsHandler clientRequestsHandler = client
          .getClientRequestsHandler();

      clientRequestsHandler.setClientChatRequestsHandler(chatPanel
          .createClientChatRequestsHandler());

      clientRequestsAnswererThread = new Thread(new RequestsAnswerer(
          clientRequestsHandler, client.getMessagesPipe()));
      clientRequestsAnswererThread.start();

    } catch (data.network.RequestSendingException e) {
      add(new JLabel("Opss."));
      return;
    } catch (UnknownHostException e) {
      add(new JLabel("Unknown game server host."));
      return;
    } catch (ConnectionLostException e) {
      add(new JLabel("Unable to connect to the game server."));
      return;
    }

  }

  @Override
  public void destroy() {
    super.destroy();
    if (client != null) {
      client.close();
      if (clientRequestsAnswererThread != null) {
        try {
          clientRequestsAnswererThread.join();
        } catch (InterruptedException e) {
          // ignore
        }
        clientRequestsAnswererThread = null;
      }
    }
  }
}
