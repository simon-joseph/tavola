package server.protocol;

/**
 * @author rafal.paliwoda
 * 
 */
public class MiddleProtocol implements Protocol {

  final PreGameProtocol preGameProtocol = new PreGameProtocol();

  final InGameProtocol inGameProtocol = new InGameProtocol();

  /*
   * (non-Javadoc)
   * 
   * @see server.protocol.Protocol#processInput(java.lang.Object)
   */
  @Override
  public String processInput(Object object) {
    return preGameProtocol.processInput(object);
  }

}
