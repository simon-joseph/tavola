package tavola.lobby;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import client.application.Client;
import client.protocol.ChatMessageListener;
import client.protocol.ClientChatRequestsHandler;
import client.protocol.PostChatMessageRequest;
import data.network.ConnectionLostException;

/**
 * @author polchawa
 * 
 */
public class ChatPanel extends JPanel {
  public static final long serialVersionUID = 1;

  private class ChatMessagesListModel extends DefaultListModel {
    public static final long serialVersionUID = 1;

    public void addMessage(String nick, String message) {
      add(size(), nick + ": " + message);
    }
  }

  final ChatMessagesListModel model = new ChatMessagesListModel();
  final JList list;

  public ChatPanel(final Client client, final GUIDriver driver) {

    list = new JList(model);
    list.setFocusable(false);
    list.setSelectionBackground(list.getBackground());

    setLayout(new BorderLayout());

    final JScrollPane sp = new JScrollPane(list);
    add(sp, BorderLayout.CENTER);
    sp.setPreferredSize(new Dimension(100, 200));

    final JPanel sayPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = gbc.gridy = 0;
    sayPanel.add(new JLabel("Say: "), gbc);

    final JTextField messageString = new JTextField();

    final ActionListener sendActionListener = new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          try {
            new PostChatMessageRequest(messageString.getText()).send(client
                .getMessagesPipe());
            messageString.setText("");
            messageString.requestFocusInWindow();
          } catch (ConnectionLostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
          }

        } catch (data.network.RequestSendingException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    };

    messageString.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 10) {
          sendActionListener.actionPerformed(null);
        }
      }
    });
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.gridx = 1;
    sayPanel.add(messageString, gbc);

    final JButton sendBtn = new JButton("Send");
    sendBtn.addActionListener(sendActionListener);
    gbc.weightx = gbc.weighty = 0;
    gbc.fill = GridBagConstraints.NONE;
    gbc.gridx = 2;
    sayPanel.add(sendBtn, gbc);
    add(sayPanel, BorderLayout.SOUTH);
  }

  public ClientChatRequestsHandler createClientChatRequestsHandler() {
    ClientChatRequestsHandler clientChatRequestsHandler = new ClientChatRequestsHandler();
    clientChatRequestsHandler.addChatMessageListener(new ChatMessageListener() {
      public void chatMessageReceived(String author, String content) {
        model.addElement(author + ": " + content);
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            list.ensureIndexIsVisible(model.getSize() - 1);
          }
        });
      }
    });
    return clientChatRequestsHandler;
  }
}
