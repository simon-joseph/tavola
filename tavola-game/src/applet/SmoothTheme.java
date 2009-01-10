package applet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import commons.BodyParts;
import commons.Direction;
import commons.Position;

/**
 * @author sla
 * 
 */
public final class SmoothTheme extends DefaultTheme {

    private BufferedImage[] bodyTx;
    private BufferedImage[] headTx;
    private BufferedImage[] lastTx;
    private BufferedImage[] bonusTx;
    final private int numOfBonuses = 2;
    private int bonusNum;
    private Position oldBonus;

    public SmoothTheme() {
	super();
	bonusNum = 0;
	oldBonus = null;
	bodyTx = new BufferedImage[12];
	headTx = new BufferedImage[4];
	lastTx = new BufferedImage[4];
	bonusTx = new BufferedImage[numOfBonuses];
	for (BodyParts parts : BodyParts.values()) {
	    bodyTx[parts.ordinal()] = loadImage("/applet/textures/BODY_"
		    + parts.name() + ".png");

	}
	for (Direction dir : Direction.values()) {
	    headTx[dir.ordinal()] = loadImage("/applet/textures/HEAD_"
		    + dir.name() + ".png");
	}
	for (Direction dir : Direction.values()) {
	    lastTx[dir.ordinal()] = loadImage("/applet/textures/LAST_"
		    + dir.name() + ".png");
	}
	for (int i = 0; i < numOfBonuses; i++) {
	    bonusTx[i] = loadImage("/applet/textures/BONUS_" + i + ".png");
	}
    }

    private BufferedImage loadImage(String path) {
	URL url = BoardPanel.class.getResource(path);
	try {
	    return ImageIO.read(url);
	} catch (IOException e) {
	    return null;
	}
    }

    @Override
    public void paintBodyPart(Graphics g, Position part, BodyParts bp) {
	if (part != null) {
	    g.drawImage(bodyTx[bp.ordinal()], part.x() * fieldSize, part.y()
		    * fieldSize, fieldSize, fieldSize, null, null);
	}
    }

    @Override
    public void paintHead(Graphics g, Position head, Direction dir) {
	if (head != null) {
	    g.drawImage(headTx[dir.ordinal()], head.x() * fieldSize, head.y()
		    * fieldSize, fieldSize, fieldSize, null, null);
	}
    }

    @Override
    public void paintLast(Graphics g, Position last, Direction dir) {
	if (last != null) {
	    g.drawImage(lastTx[dir.ordinal()], last.x() * fieldSize, last.y()
		    * fieldSize, fieldSize, fieldSize, null, null);
	}
    }

    @Override
    public void paintBonus(Graphics g, Position bonus) {
	if (oldBonus != bonus) {
	    changeBonus();
	    oldBonus = bonus;
	}
	if (bonus != null) {
	    g.drawImage(bonusTx[bonusNum], bonus.x() * fieldSize - fieldSize
		    / 2, bonus.y() * fieldSize - fieldSize / 2, fieldSize * 2,
		    fieldSize * 2, null, null);
	}

    }

    private void changeBonus() {
	bonusNum++;
	if (bonusNum >= numOfBonuses) {
	    bonusNum = 0;
	}
    }
}
