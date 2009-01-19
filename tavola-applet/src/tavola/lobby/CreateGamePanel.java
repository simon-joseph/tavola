package tavola.lobby;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.application.Client;
import client.protocol.CreateGameRequest;
import data.game.Game;
import data.game.Player;
import data.network.ConnectionLostException;
import data.network.LoggerHelper;

/**
 * @author polchawa
 * 
 */
public class CreateGamePanel extends JPanel {
  public static final long serialVersionUID = 1;

  public CreateGamePanel(final Client client, final GUIDriver driver) {
    setLayout(new BorderLayout());
    final JPanel panel = new JPanel(new GridLayout(5, 2));
    panel.add(new JLabel("Game name: "));
    final JTextField gameName = new JTextField(20);
    panel.add(gameName);
    panel.add(new JLabel("Max players: "));
    final JComboBox maxPlayers = new JComboBox(new Integer[] { 1, 2, 3, 4 });
    panel.add(maxPlayers);
    panel.add(new JLabel("Max bonuses: "));
    final JComboBox maxBonuses = new JComboBox(new Integer[] { 1, 2, 4, 8, 16 });
    panel.add(maxBonuses);
    panel.add(new JLabel("Game type: "));
    final JComboBox gameType = new JComboBox(new String[] { "tron", "snake" });
    panel.add(gameType);
    /*
     * panel.add(new JLabel("Map: ")); final JComboBox levelId = new
     * JComboBox(new String[] { "Damnit", "Nukethemall", "Littleplanet", "Dogz"
     * }); panel.add(levelId);
     */

    add(panel, BorderLayout.CENTER);

    final JPanel btnsPanel = new JPanel();

    final JButton createGameBtn = new JButton("Create game");
    createGameBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          String gameNameString = gameName.getText();
          Integer maxPlayersInteger = (Integer) maxPlayers.getSelectedItem();
          Integer maxBonusesInteger = (Integer) maxBonuses.getSelectedItem();
          String gameTypeString = (String) gameType.getSelectedItem();
          String levelIdString = "todo";// (String) levelId.getSelectedItem();

          String gameId = new CreateGameRequest(gameNameString,
              maxPlayersInteger, maxBonusesInteger, levelIdString,
              gameTypeString).send(client.getMessagesPipe());
          if (gameId == null) {
            LoggerHelper.get().info("Creating game failed.");
            return;
          }

          LinkedList<Player> players = new LinkedList<Player>();
          players.addLast(client.getPlayer());

          Game game = new Game(gameId, players, levelIdString,
              maxPlayersInteger, maxBonusesInteger, client.getPlayer().getId(),
              null, 0, gameTypeString);
          client.getPlayer().setGame(game);

          GameStartAwaitingPanel gameStartAwaitingPanel = new GameStartAwaitingPanel(
              client, driver);
          client.getClientRequestsHandler()
              .setClientGameStartAwaitingRequestsHandler(
                  gameStartAwaitingPanel
                      .createClientGameStartAwaitingRequestsHandler());

          driver.gameCreated(gameStartAwaitingPanel);
        } catch (data.network.RequestSendingException requestSendingException) {
          // TODO Auto-generated catch block
          requestSendingException.printStackTrace();
          return;
        } catch (ConnectionLostException connectionLostException) {
          driver.connectionLost("game creating");
        }
      }
    });
    btnsPanel.add(createGameBtn);

    add(btnsPanel, BorderLayout.SOUTH);
  }
}
