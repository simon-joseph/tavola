/**
 * 
 */
package interfaces;

/**
 * @author sla
 * 
 */
public interface IBoard {

    /**
     * Actualize game state.
     */
    public void update();

    /**
     * Uruchamia grę, tworzy gracza (Player), w określonych odstępach czasu
     * wywołuje metodę update() oraz draw(this). Po osiągnięciu warunku
     * stopu zatrzymuje grę.
     */
    @Deprecated
    public void run();
}
