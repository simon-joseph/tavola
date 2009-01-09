package applet;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author sla + agl
 * 
 */
@SuppressWarnings("serial")
public final class PauseButton extends JButton {
    public final static String cmdName = "PAUSE";

    public PauseButton(ActionListener al) {
	super();
	init(al);
    }

    private void init(ActionListener al) {
	setText("PAUSE");
	addActionListener(al);
	setActionCommand(PauseButton.cmdName);
	setPreferredSize(new Dimension(100, 50));
	setMinimumSize(new Dimension(100, 50));
	setMaximumSize(new Dimension(100, 50));
	setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
