

import junit.framework.TestCase;

import commons.Position;

/**
 * @author sla
 * 
 */
public class TPosition extends TestCase {

    Position position;

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	position = new Position(1, 2);
    }

    /**
     * Test method for {@link commons.Position#add(commons.Position)}.
     */
    public void testAdd() {
	position.add(new Position(5, 7));
	assert position.x() == 6 && position.y() == 9;
    }

    /**
     * Test method for {@link commons.Position#clone()}.
     */
    public void testClone() {
	Position clone = position.clone();
	assert position.x() == clone.x() && position.y() == clone.y();
	clone.add(new Position(1, 2));
	assert position.x() != clone.x() && position.y() != clone.y();
    }

}
