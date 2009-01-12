package tavola.lobby;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import applet.SnakeBoardApplet;
import client.application.CreateGameMessage;
import client.application.HelloGameMessage;
import client.application.JoinGameMessage;
import client.application.LeaveGameMessage;
import client.application.ListGameMessage;
import client.application.StartGameMessage;
import client.application.TavolaClient;
import client.application.TavolaInGameClient;
import client.protocol.InvalidProtocolException;
import data.game.Game;

/**
 * @author polchawa
 * 
 */
public class LobbyApplet extends JApplet {

  private class ChatMessagesListModel extends DefaultListModel {

    public void addMessage(String nick, String message) {
      add(size(), nick + ": " + message);
    }
  }

  private JPanel createChatPanel() {
    final JPanel chatPanel = new JPanel(new BorderLayout());
    chatPanel.add(new JScrollPane(new JList(new ChatMessagesListModel())),
        BorderLayout.CENTER);

    final JPanel sayPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gbc.gridy = 0;
    sayPanel.add(new JLabel("Say: "), gbc);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.gridx = 1;
    sayPanel.add(new JTextField(), gbc);
    chatPanel.add(sayPanel, BorderLayout.SOUTH);
    return chatPanel;
  }

  private void createGame(String gameName, Integer maxPlayers,
      Integer maxBonuses, String levelId) throws IOException,
      InvalidProtocolException {
    new CreateGameMessage(gameName, maxPlayers, maxBonuses, levelId)
        .send(tavolaClient.getPipe());
  }

  JTabbedPane gamesPane;
  JPanel gamePanel;

  private JPanel createCreateGamePanel() {
    final JPanel formPanel = new JPanel(new BorderLayout());
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
    panel.add(new JLabel("Map: "));
    final JComboBox levelId = new JComboBox(new String[] { "Damnit",
        "Nukethemall", "Littleplanet", "Dogz" });
    panel.add(levelId);
    formPanel.add(panel, BorderLayout.CENTER);

    final JPanel btnsPanel = new JPanel();

    final JButton createGameBtn = new JButton("Create game");
    createGameBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          createGame(gameName.getText(),
              (Integer) maxPlayers.getSelectedItem(), (Integer) maxBonuses
                  .getSelectedItem(), (String) levelId.getSelectedItem());
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          return;
        } catch (InvalidProtocolException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          return;
        }

        gamesPane.setVisible(false);
        gamePanel.setVisible(true);
      }
    });
    btnsPanel.add(createGameBtn);

    formPanel.add(btnsPanel, BorderLayout.SOUTH);
    return formPanel;
  }

  private TavolaClient tavolaClient = null;

  private void initTavolaClient() throws IOException {
    tavolaClient = new TavolaClient(TavolaClient.DEFAULT_HOST,
        TavolaClient.DEFAULT_PORT);

  }

  private class GamesListModel extends DefaultListModel {
    private Game[] games = null;

    @Override
    public Object getElementAt(int index) {
      return games == null ? null : games[index];
    }

    @Override
    public int getSize() {
      return games == null ? 0 : games.length;
    }

    public GamesListModel() {
      games = null;
      try {
        games = new ListGameMessage().send(tavolaClient.getPipe());
      } catch (IOException e) {
        return;
      } catch (InvalidProtocolException e) {
        return;
      }
      assert games != null;
    }
  }

  private JPanel createJoinGamePanel() {
    final JPanel formPanel = new JPanel(new BorderLayout());
    final JPanel panel = new JPanel();

    final JList gamesList = new JList(new GamesListModel());
    panel.add(new JScrollPane(gamesList));
    formPanel.add(panel, BorderLayout.CENTER);

    final JPanel btnsPanel = new JPanel();

    final JButton joinBtn = new JButton("Join game");
    btnsPanel.add(joinBtn);

    joinBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Game game = (Game) gamesList.getSelectedValue();
        if (game != null) {
          try {
            new JoinGameMessage(game.getId()).send(tavolaClient.getPipe());
          } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
          } catch (InvalidProtocolException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
          }
          gamesPane.setVisible(false);
          gamePanel.setVisible(true);
        }
      }
    });

    final JButton refreshBtn = new JButton("Refresh");
    btnsPanel.add(refreshBtn);

    refreshBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        gamesList.setModel(new GamesListModel());
      }
    });

    formPanel.add(btnsPanel, BorderLayout.SOUTH);

    return formPanel;
  }

  public LobbyApplet() {
    super();
    try {
      initTavolaClient();
      if (!new HelloGameMessage("aaaa"/* getParameter("ticket") */)
          .send(tavolaClient.getPipe())) {
        add(new JLabel("Zaloguj się ponownie..."));
        return;
      }
    } catch (IOException e) {
      add(new JLabel("Przepraszamy, problemy techniczne."));
      return;
    } catch (InvalidProtocolException e) {
      add(new JLabel("Przepraszamy, problemy techniczne."));
      return;
    }
    setSize(500, 500);
    setLayout(new BorderLayout());

    gamesPane = new JTabbedPane();
    gamesPane.addTab("GAMES LIST", createJoinGamePanel());
    final JPanel formsPanel = new JPanel();
    formsPanel.add(createCreateGamePanel(), BorderLayout.NORTH);
    gamesPane.add("CREATE NEW GAME", formsPanel);
    add(gamesPane, BorderLayout.CENTER);
    add(createChatPanel(), BorderLayout.SOUTH);

    gamePanel = new JPanel(new BorderLayout());
    gamePanel.setVisible(false);
    add(gamePanel, BorderLayout.NORTH);

    JPanel players = new JPanel(new GridLayout(4, 2));
    for (int i = 0; i < 4; ++i) {
      players.add(new JLabel("Player" + (i + 1)));
      players.add(new JComboBox(new String[] { "Player" + (i + 1), "Ban" }));
    }
    gamePanel.add(players, BorderLayout.CENTER);
    JPanel btnsPanel = new JPanel();

    JButton startGameBtn = new JButton("Start game");
    startGameBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          new StartGameMessage().send(tavolaClient.getPipe());
          tavolaClient.getPipe().println("GAME_STARTED");
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          return;
        } catch (InvalidProtocolException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          return;
        }
        // ???
        TavolaInGameClient inGameClient = new TavolaInGameClient(tavolaClient
            .getPipe());
        Thread t = new Thread(inGameClient);
        t.start();

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SnakeBoardApplet inst = new SnakeBoardApplet();
        frame.getContentPane().add(inst);
        ((JComponent) frame.getContentPane()).setPreferredSize(inst.getSize());
        frame.pack();
        frame.setVisible(true);
      }
    });
    btnsPanel.add(startGameBtn);
    JButton cancelGameBtn = new JButton("Cancel");
    btnsPanel.add(cancelGameBtn);
    cancelGameBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          new LeaveGameMessage().send(tavolaClient.getPipe());
        } catch (IOException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          return;
        } catch (InvalidProtocolException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
          return;
        }
        gamePanel.setVisible(false);
        gamesPane.setVisible(true);
      }
    });
    gamePanel.add(btnsPanel, BorderLayout.SOUTH);
  }
}
