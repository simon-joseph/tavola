package applet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Vector;

import javax.imageio.ImageIO;

import commons.BodyParts;
import commons.Direction;
import commons.Position;

/**
 * @author sla
 * 
 */
public final class LighterTheme extends DefaultTheme {

    private BufferedImage snakes;
    private BufferedImage[] bonusTx;
    final private int numOfBonuses = 2;
    private int bonusNum;
    private Position oldBonus;
    private int f = fieldSize;

    public LighterTheme() {
	super();
	bonusNum = 0;
	oldBonus = null;
	snakes = loadImage("/applet/textures/SNAKES.png");
	bonusTx = new BufferedImage[numOfBonuses];
	for (int i = 0; i < numOfBonuses; i++) {
	    bonusTx[i] = loadImage("/applet/textures/BONUS_" + i + ".png");
	}
    }

    private BufferedImage loadImage(String path) {
	URL url = BoardPanel.class.getResource(path);
	try {
	    return ImageIO.read(url);
	    // return ImageIO.read(new File(path));
	} catch (IOException e) {
	    System.out.print("nie wczytalo sie");
	    return null;
	}
    }

    @Override
    public void paintBodyPart(Graphics g, Position part, BodyParts bp, int id) {
	if (part != null) {
	    g.drawImage(snakes, part.x() * f, part.y() * f, (part.x() + 1) * f,
		    (part.y() + 1) * f, (bp.ordinal() / 2 + 4) * 80, id * 80,
		    (bp.ordinal() / 2 + 5) * 80, (id + 1) * 80, null);

	}
    }

    @Override
    public void paintHead(Graphics g, Position head, Direction dir, int id) {
	if (head != null) {
	    g.drawImage(snakes, head.x() * f, head.y() * f, (head.x() + 1) * f,
		    (head.y() + 1) * f, dir.ordinal() * 80, id * 80, (dir
			    .ordinal() + 1) * 80, (id + 1) * 80, null);
	}
    }

    @Override
    public void paintLast(Graphics g, Position last, Direction dir, int id) {
	if (last != null) {
	    g.drawImage(snakes, last.x() * f, last.y() * f, (last.x() + 1) * f,
		    (last.y() + 1) * f, dir.ordinal() * 80, id * 80, (dir
			    .ordinal() + 1) * 80, (id + 1) * 80, null);
	}
    }

    @Override
    public void paintBonus(Graphics g, Vector<Position> bonuses) {
	if (bonuses != null) {
	    // for(Position bonus : bonuses)
	    for (int i = 0; i < bonuses.size(); i++) {
		g.drawImage(bonusTx[bonusNum], bonuses.get(i).x() * f, bonuses
			.get(i).y()
			* f, f, f, null, null);
	    }
	}

    }

    private void changeBonus() {
	bonusNum++;
	if (bonusNum >= numOfBonuses) {
	    bonusNum = 0;
	}
    }

    private int fromBP(BodyParts bp) {
	if (bp.ordinal() < 4) {
	    return 0;
	} else {
	    return 1;
	}
    }
}
