package client;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Class to log every writing access in key structure.
 */
public class SimpleLogger extends AbstractLogger {

    /**
     * Constructor. It creates a logger with handler.
     *
     * @param fileName name of logfile; it will get a ".log" suffix
     * @param append   if logger should use appending file or create new
     */
    public SimpleLogger(String fileName, boolean append) {
        super(fileName, append);
    }

    public void log(String string) {
        logger.info(string);
    }

}
