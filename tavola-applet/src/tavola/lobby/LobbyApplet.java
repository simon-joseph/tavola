package tavola.lobby;

import javax.swing.JComponent;
import javax.swing.JFrame;

import applet.SnakeBoardApplet;

/**
 * @author polchawa
 *
 */
public class LobbyApplet extends JApplet {
	public LobbyApplet() {
		super();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LobbyApplet applet = new LobbyApplet();
        frame.getContentPane().add(applet);
        ((JComponent) frame.getContentPane()).setPreferredSize(applet.getSize());
        frame.pack();
        frame.setVisible(true);
        applet.startButton.setVisible(true);
	}
}
