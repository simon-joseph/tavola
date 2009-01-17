package applet;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;

/**
 * @author sla + agl
 * 
 */
public final class ExitButton extends JButton {
    public static final long serialVersionUID = 1;

    public final static String cmdName = "EXIT";

    public ExitButton(ActionListener al) {
	super();
	init(al);
    }

    private void init(ActionListener al) {
	setText("EXIT");
	setActionCommand(ExitButton.cmdName);
	addActionListener(al);
	setPreferredSize(new Dimension(100, 50));
	setMinimumSize(new Dimension(100, 50));
	setMaximumSize(new Dimension(100, 50));
	setAlignmentX(Component.CENTER_ALIGNMENT);
    }
}
