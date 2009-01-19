package tavola.lobby;

import java.awt.BorderLayout;

import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import data.network.LoggerHelper;

/**
 * @author polchawa
 * 
 */
public class GUIDriver {

  private JoinGamePanel joinGamePanel = null;
  private CreateGamePanel createGamePanel = null;
  private ChatPanel chatPanel = null;
  private JTabbedPane gamesPane = null;
  private JPanel gameStartAwaitingPanel = null;
  private JApplet applet = null;

  public JoinGamePanel getJoinGamePanel() {
    return joinGamePanel;
  }

  public void setJoinGamePanel(JoinGamePanel joinGamePanel) {
    this.joinGamePanel = joinGamePanel;
  }

  public CreateGamePanel getCreateGamePanel() {
    return createGamePanel;
  }

  public void setCreateGamePanel(CreateGamePanel createGamePanel) {
    this.createGamePanel = createGamePanel;
  }

  public ChatPanel getChatPanel() {
    return chatPanel;
  }

  public void setChatPanel(ChatPanel chatPanel) {
    this.chatPanel = chatPanel;
  }

  public JApplet getApplet() {
    return applet;
  }

  public void setApplet(JApplet applet) {
    this.applet = applet;
  }

  public void initGUI() {
    gamesPane = new JTabbedPane();
    gamesPane.addTab("GAMES LIST", joinGamePanel);
    final JPanel formsPanel = new JPanel();
    formsPanel.add(createGamePanel, BorderLayout.NORTH);
    gamesPane.add("CREATE NEW GAME", formsPanel);

    applet.add(gamesPane, BorderLayout.CENTER);
    applet.add(chatPanel, BorderLayout.SOUTH);
    applet.validate();

    joinGamePanel.startRefreshing();
  }

  public void gameStartAwaitingCancelled() {
    applet.remove(gameStartAwaitingPanel);
    applet.validate();
    gameStartAwaitingPanel = null;

    gamesPane.setVisible(true);
    joinGamePanel.startRefreshing();
  }

  private void enterGameStartAwaitingPanel(
      GameStartAwaitingPanel gameStartAwaitingPanel) {
    joinGamePanel.stopRefreshing();
    gamesPane.setVisible(false);
    this.gameStartAwaitingPanel = gameStartAwaitingPanel;
    applet.add(gameStartAwaitingPanel);
    applet.validate();
  }

  public void gameCreated(GameStartAwaitingPanel gameStartAwaitingPanel) {
    enterGameStartAwaitingPanel(gameStartAwaitingPanel);
  }

  public void gameJoined(GameStartAwaitingPanel gameStartAwaitingPanel) {
    enterGameStartAwaitingPanel(gameStartAwaitingPanel);
  }

  public void connectionLost(String info) {
    applet.removeAll();
    applet.add(new JLabel("Connection to server lost."));
    LoggerHelper.get().info("Connection to server lost during: " + info);
    applet.validate();
  }

  public void startGame(GamePanel gamePanel) {
    applet.setVisible(false);
    applet.remove(gamesPane);
    applet.remove(chatPanel);
    applet.remove(gameStartAwaitingPanel);
    applet.add(gamePanel);
    applet.setVisible(true);
    applet.validate();
  }

  public void stopGame(GamePanel gamePanel) {
    applet.setVisible(false);
    applet.remove(gamePanel);
    applet.add(chatPanel);
    applet.add(gamesPane);
    applet.setVisible(true);
    applet.validate();
  }

}
