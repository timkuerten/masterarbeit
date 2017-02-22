package client;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Abstract class to create a logger with handler.
 */
abstract public class AbstractLogger {

    protected static Logger logger;
    private FileHandler fh;

    /**
     * Constructor. It creates a logger with handler.
     *
     * @param fileName name of logfile; it will get a ".log" suffix
     * @param append   if logger should use appending file or create new
     */
    public AbstractLogger(String fileName, boolean append) {
        // create logger
        logger = Logger.getLogger(fileName);
        logger.setUseParentHandlers(false);
        try {
            // configure the logger with handler and formatter
            String logFileName = fileName + ".log";
            fh = new FileHandler(logFileName, append);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            // first message
            logger.info("start");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        fh.close();
        logger.removeHandler(fh);
    }

}
