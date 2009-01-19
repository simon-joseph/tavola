package tavola.lobby;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

import client.application.Client;
import client.protocol.ClientGameStartAwaitingRequestsHandler;
import client.protocol.GameListener;
import client.protocol.LeaveGameRequest;
import client.protocol.StartGameRequest;
import data.game.Game;
import data.game.Player;
import data.game.PlayerListener;
import data.network.ConnectionLostException;
import data.network.LoggerHelper;

/**
 * @author polchawa
 * 
 */
public class GameStartAwaitingPanel extends JPanel {
  public static final long serialVersionUID = 1;

  private final GUIDriver driver;
  private final Client client;
  private final Game game;
  private final DefaultListModel playersModel;
  private final JPanel btnsPanel;
  private JButton startGameBtn = null;
  private JButton leaveGameBtn = null;

  private void startGame() {
    game.setRunning(true);
    final GamePanel gamePanel = new GamePanel(client.getPlayer());
    client.getClientRequestsHandler().setClientInGameRequestsHandler(
        gamePanel.createClientInGameRequestsHandler());
    driver.startGame(gamePanel);

    new Thread(new Runnable() {
      public void run() {
        while (!gamePanel.isGameOver()) {
          try {
            Thread.sleep(1000);
          } catch (InterruptedException exception) {
            // ignore
          }
        }
        LoggerHelper.get().info("GameOver!");
        driver.stopGame(gamePanel);
      }
    }).start();
  }

  private void createStartGameButton() {
    startGameBtn = new JButton("Start game");
    startGameBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (new StartGameRequest().send(client.getMessagesPipe())) {
            startGame();
          } else {
            // server returned FAILED
          }
        } catch (data.network.RequestSendingException requestSendingException) {
          LoggerHelper.get().info(
              "RequestSenderException while starting the game: "
                  + requestSendingException);
          return;
        } catch (ConnectionLostException connectionLostException) {
          driver.connectionLost("game starting");
          return;
        }
      }
    });
  }

  private void createLeaveGameButton() {
    leaveGameBtn = new JButton("Leave game");
    leaveGameBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (new LeaveGameRequest().send(client.getMessagesPipe())) {
            driver.gameStartAwaitingCancelled();
          } else {
            // server returned FAILED (ie. game has just started)
          }
        } catch (data.network.RequestSendingException requestSendingException) {
          LoggerHelper.get().info(
              "RequestSenderException while leaving the game: "
                  + requestSendingException);
          return;
        } catch (ConnectionLostException connectionLostException) {
          driver.connectionLost("game leaving");
          return;
        }
      }
    });
  }

  public GameStartAwaitingPanel(final Client client, final GUIDriver driver) {

    this.client = client;
    this.driver = driver;
    game = client.getPlayer().getGame();

    playersModel = new DefaultListModel();
    for (Player player : game.getPlayers()) {
      playersModel.addElement(player);
    }

    JList players = new JList(playersModel);
    add(players, BorderLayout.CENTER);

    btnsPanel = new JPanel();

    if (game.getCreatorId() == client.getPlayer().getId()) {
      createStartGameButton();
      btnsPanel.add(startGameBtn);
    }

    createLeaveGameButton();
    btnsPanel.add(leaveGameBtn);

    add(btnsPanel, BorderLayout.SOUTH);
    validate();
  }

  public ClientGameStartAwaitingRequestsHandler createClientGameStartAwaitingRequestsHandler() {
    ClientGameStartAwaitingRequestsHandler handler = new ClientGameStartAwaitingRequestsHandler(
        client.getPlayer());

    handler.addPlayerJoinListener(new PlayerListener() {
      public void playerActionPerformed(Player player) {
        playersModel.addElement(player);
      }
    });

    handler.addPlayerLeaveListener(new PlayerListener() {
      public void playerActionPerformed(Player player) {
        playersModel.removeElement(player);
        if (game.getCreatorId() == client.getPlayer().getId()) {
          if (startGameBtn == null) {
            createStartGameButton();
            btnsPanel.setVisible(false);
            btnsPanel.remove(leaveGameBtn);
            btnsPanel.add(startGameBtn);
            btnsPanel.add(leaveGameBtn);
            btnsPanel.setVisible(true);
            btnsPanel.validate();
          }
        }
      }
    });

    handler.addGameStartListener(new GameListener() {
      public void gameActionPerformed(Game game) {
        startGame();
      }
    });
    return handler;
  }

}
