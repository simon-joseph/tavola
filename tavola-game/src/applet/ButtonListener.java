package applet;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author agl + sla
 * 
 */
class ButtonListener implements ActionListener {

    private SnakeBoardApplet applet;

    public ButtonListener(SnakeBoardApplet applet) {
	super();
	this.applet = applet;
    }

    public void actionPerformed(ActionEvent ev) {

	if (ev.getSource() == applet.startButton) {
	    // SnakeBoardApplet applet = (SnakeBoardApplet) ((Component) ev
	    // .getSource()).getParent().getParent().getParent().getParent();
	    // System.out.print(appl.getClass().getName());
	    applet.startButton.setVisible(false);
	    // ((Component) ev.getSource()).setVisible(false);
	    applet.messageField.setVisible(true);
	    applet.board.setVisible(true);
	    applet.controlPanel.setVisible(true);
	    applet.clicked = true;
	}
	if (ev.getSource() == applet.exitButton) {
	    System.exit(0);
	}
	if (ev.getSource() == applet.pauseButton) {
	    applet.clicked = !applet.clicked;
	}
    }
}