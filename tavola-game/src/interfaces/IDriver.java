/**
 * Czyta wejście z klawiatury i dokonuje modyfikacji kierunku właściciela.
 */
package interfaces;

/**
 * @author sla
 * 
 */
public interface IDriver {

  /**
   * Uruchamia wczytywanie z klawiatury
   */
  public void start();

  /**
   * Zatrzymuje wczytywanie z klawiatury
   */
  public void stop();

  /**
   * Ustawia właściciela (Player);
   */
  public void setOwner(IPlayer player);
}
