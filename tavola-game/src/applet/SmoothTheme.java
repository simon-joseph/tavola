package applet;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;

import commons.Direction;
import commons.Position;

/**
 * @author sla
 * 
 */
public final class SmoothTheme extends DefaultTheme {
    private Image body_up;
    private Image body_down;
    private Image body_right;
    private Image body_left;
    private Image head_up;
    private Image head_down;
    private Image head_right;
    private Image head_left;

    public SmoothTheme() {
	super();
	body_up = loadImage("/applet/textures/BODY_UP.png");
	body_down = loadImage("/applet/textures/BODY_DOWN.png");
	body_right = loadImage("/applet/textures/BODY_RIGHT.png");
	body_left = loadImage("/applet/textures/BODY_LEFT.png");
	head_up = loadImage("/applet/textures/HEAD_UP.png");
	head_down = loadImage("/applet/textures/HEAD_DOWN.png");
	head_right = loadImage("/applet/textures/HEAD_RIGHT.png");
	head_left = loadImage("/applet/textures/HEAD_LEFT.png");
    }

    // "/applet/textures/head.png"
    // if (url == null) {
    // System.out.print("not working:");
    // }
    // Toolkit.getDefaultToolkit();
    // img = Toolkit.getDefaultToolkit().createImage(url);
    // g.drawImage(img, head.x() * 10, head.y() * 10, 14, 14, null,
    // null);

    private Image loadImage(String path) {
	URL url = BoardPanel.class.getResource(path);
	return Toolkit.getDefaultToolkit().createImage(url);
    }

    @Override
    public void paintBodyPart(Graphics g, Position toPaint,
	    Direction reverseDirection) {
	if (toPaint != null) {
	    switch (reverseDirection) {
	    case RIGHT:
		g.drawImage(body_right, toPaint.x() * fieldSize, toPaint.y()
			* fieldSize, fieldSize, fieldSize, null, null);
		break;
	    case LEFT:
		g.drawImage(body_left, toPaint.x() * fieldSize, toPaint.y()
			* fieldSize, fieldSize, fieldSize, null, null);
		break;
	    case UP:
		g.drawImage(body_up, toPaint.x() * fieldSize, toPaint.y()
			* fieldSize, fieldSize, fieldSize, null, null);
		break;
	    case DOWN:
		g.drawImage(body_down, toPaint.x() * fieldSize, toPaint.y()
			* fieldSize, fieldSize, fieldSize, null, null);
		break;
	    default:
		break;
	    }
	}
    }
}
