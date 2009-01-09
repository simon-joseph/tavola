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
public final class StartButton extends JButton {
    public final static String cmdName = "START";

    public StartButton(ActionListener al) {
	super();
	init(al);
    }

    private void init(ActionListener al) {
	setText("START");
	setActionCommand(StartButton.cmdName);
	addActionListener(al);
	setAlignmentX(Component.CENTER_ALIGNMENT);
	setMinimumSize(new Dimension(200, 100));
	setMaximumSize(new Dimension(200, 100));
	setPreferredSize(new Dimension(200, 100));
    }
}
