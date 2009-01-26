package tavola.lobby;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import client.application.Client;
import client.protocol.JoinGameRequest;
import client.protocol.ListGamesRequest;
import data.game.Game;
import data.game.Player;
import data.network.ConnectionLostException;
import data.network.LoggerHelper;
import data.network.RequestSendingException;

/**
 * @author polchawa
 * 
 */
public class JoinGamePanel extends JPanel {
  public static final long serialVersionUID = 1;

  private class GamesTableModel extends DefaultTableModel {
    private static final long serialVersionUID = 1L;

    public GamesTableModel() {
      final String[] columnNames = new String[] { "name", "max players",
          "max bonuses", "type", "" };
      for (String columnName : columnNames) {
        addColumn(columnName);
      }
    }
  }

  private final TreeSet<String> games = new TreeSet<String>();
  private final GamesTableModel gamesTableModel;
  private final Client client;
  private final GUIDriver driver;

  private void addGame(Game game) {
    games.add(game.getId());

    gamesTableModel.addRow(new Object[] { game.getLevelId(),
        game.getMaxPlayersCount(), game.getMaxBonusesCount(),
        game.getGameType(), game });

  }

  private void removeGame(String gameId) {
    games.remove(gameId);
    for (int i = 0; i < gamesTableModel.getRowCount(); ++i) {
      if (gamesTableModel.getValueAt(i, 0).equals(gameId)) {
        gamesTableModel.removeRow(i);
        break;
      }
    }
  }

  public JoinGamePanel(final Client client, final GUIDriver driver)
      throws ConnectionLostException, RequestSendingException {
    this.client = client;
    this.driver = driver;

    gamesTableModel = new GamesTableModel();

    List<Game> gamesList = new ListGamesRequest()
        .send(client.getMessagesPipe());
    if (gamesList == null) {
      LoggerHelper.get().info("Listing games failed.");
      add(new JLabel("Server error."));
      return;
    }

    final JTable gamesTable = new JTable(gamesTableModel);
    for (Game game : gamesList) {
      addGame(game);
    }

    gamesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    add(new JScrollPane(gamesTable), BorderLayout.CENTER);
    // gamesTable.setFillsViewportHeight(true);

    final JPanel btnsPanel = new JPanel();

    final JButton joinBtn = new JButton("Join game");
    btnsPanel.add(joinBtn);

    joinBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        int selectedIndex = gamesTable.getSelectedRow();
        if (selectedIndex == -1) {
          return;
        }
        Game game = (Game) gamesTable.getModel().getValueAt(selectedIndex, 4);
        if (game != null) {
          List<Player> players = null;
          try {
            players = new JoinGameRequest(game.getId()).send(client
                .getMessagesPipe());
          } catch (RequestSendingException e1) {
            LoggerHelper.get().info("Error occured while joining game");
          } catch (ConnectionLostException e1) {
            driver.connectionLost("joining game");
            return;
          }
          if (players != null) {
            ListIterator<Player> listIterator = players.listIterator();
            while (listIterator.hasNext()) {
              Player p = listIterator.next();
              if (p.getId().equals(client.getPlayer().getId())) {
                listIterator.set(client.getPlayer());
                client.getPlayer().setGame(game);
              } else {
                p.setGame(game);
              }
            }
            game.setPlayers(players);

            GameStartAwaitingPanel gameStartAwaitingPanel = new GameStartAwaitingPanel(
                client, driver);
            client.getClientRequestsHandler()
                .setClientGameStartAwaitingRequestsHandler(
                    gameStartAwaitingPanel
                        .createClientGameStartAwaitingRequestsHandler());

            driver.gameJoined(gameStartAwaitingPanel);
          } else {
            LoggerHelper.get().info("Joining game failed.");
          }
        }
      }
    });

    add(btnsPanel, BorderLayout.SOUTH);
  }

  private Thread refreshingThread = null;

  synchronized public void startRefreshing() {
    if (refreshingThread != null) {
      return;
    }
    refreshingThread = new Thread(new Runnable() {
      private void refreshGamesTable(Client client)
          throws ConnectionLostException, RequestSendingException {

        List<Game> newGamesList = new ListGamesRequest().send(client
            .getMessagesPipe());
        if (newGamesList == null) {
          // UNKNOWN_COMMAND (bo np. już dołączyliśmy do gry)
          return;
        }
        Set<String> newGames = new HashSet<String>();
        for (Game game : newGamesList) {
          newGames.add(game.getId());
        }
        for (String gameId : games) {
          if (!newGames.contains(gameId)) {
            removeGame(gameId);
          }
        }
        for (Game game : newGamesList) {
          if (!games.contains(game.getId())) {
            addGame(game);
          }
        }
      }

      public void run() {
        while (true) {
          try {
            refreshGamesTable(client);
          } catch (RequestSendingException e1) {
            Logger.getLogger("tavola").warning("Unable to refresh games list.");
          } catch (ConnectionLostException e1) {
            // TODO: !!!
            driver.connectionLost("refreshing games list");
            return;
          }
          try {
            Thread.sleep(1000);
          } catch (InterruptedException interruptedException) {
            return;
          }
        }
      }
    });
    refreshingThread.start();
  }

  synchronized public void stopRefreshing() {
    if (refreshingThread == null) {
      return;
    }
    refreshingThread.interrupt();
    try {
      refreshingThread.join();
    } catch (InterruptedException e) {
      // nothing wrong
    } finally {
      refreshingThread = null;
    }
  }
}
