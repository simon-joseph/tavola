package interfaces;

import commons.Direction;

/**
 * This is a simple player interface that can change directions.
 * 
 * @author sla
 * 
 */
public interface IPlayer {

    /**
     * Changes the direction of player movement.
     * 
     * @param direction
     */
    public void changeDirection(Direction direction);
}
