package client;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Abstract class to create a LOGGER with handler.
 */
abstract public class AbstractLogger {

    protected static Logger LOGGER;
    private FileHandler fh;

    /**
     * Constructor. It creates a LOGGER with handler.
     *
     * @param fileName name of logfile; it will get a ".log" suffix
     * @param append   if LOGGER should use appending file or create new
     */
    public AbstractLogger(String fileName, boolean append) {
        // create LOGGER
        LOGGER = Logger.getLogger(fileName);
        LOGGER.setUseParentHandlers(false);
        try {
            // configure the LOGGER with handler and formatter
            String logFileName = fileName + ".log";
            fh = new FileHandler(logFileName, append);
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            // first message
            LOGGER.info("start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        fh.close();
        LOGGER.removeHandler(fh);
    }

}
