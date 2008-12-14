import junit.framework.Assert;
import junit.framework.TestCase;

import commons.Direction;
import commons.Position;

/**
 * @author sla
 * 
 */
public class TDirection extends TestCase {

    Direction direction;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Override
    protected void setUp() throws Exception {
	super.setUp();
    }

    /**
     * Test method for {@link commons.Direction#opposite(commons.Direction)}.
     */
    public void testOpposite() {
	Direction[] dirs = { Direction.RIGHT, Direction.LEFT, Direction.UP,
		Direction.DOWN };
	Direction[] oppDirs = { Direction.LEFT, Direction.RIGHT,
		Direction.DOWN, Direction.UP };
	for (int d = 0; d < 4; d++) {
	    for (int o = 0; o < 4; o++) {
		if (d == o) {
		    assert dirs[d].opposite(oppDirs[o]);
		} else {
		    assert !dirs[d].opposite(oppDirs[o]);
		}
	    }
	}
    }

    /**
     * Test method for {@link commons.Direction#toPosition()}.
     */
    public void testToPosition() {
	Assert.assertEquals(Direction.RIGHT.toPosition(), new Position(1, 0));
	Assert.assertEquals(Direction.LEFT.toPosition(), new Position(-1, 0));
	Assert.assertEquals(Direction.UP.toPosition(), new Position(0, -1));
	Assert.assertEquals(Direction.DOWN.toPosition(), new Position(0, 1));
    }
}
