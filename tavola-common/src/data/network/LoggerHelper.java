package data.network;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author polchawa
 * 
 */
public final class LoggerHelper {
  public static void init(String loggerName, Level level) {
    Logger logger = Logger.getLogger(loggerName);
    logger.setLevel(level);
    for (Handler handler : logger.getParent().getHandlers()) {
      handler.setLevel(level);
    }
  }

  public static Logger get() {
    return Logger.getLogger("tavola");
  }
}
