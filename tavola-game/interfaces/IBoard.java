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
	 * Wykonuje krok algorytmu uaktualniając stan gry.
	 */
	public void update();

	/**
	 * Uruchamia grę, tworzy gracza (Player), w określonych odstępach czasu
	 * wywołuje metodę update() oraz draw(this). Po osiągnięciu warunku stopu
	 * zatrzymuje grę.
	 */
	public void run();
}
