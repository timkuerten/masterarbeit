package client;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

abstract public class AbstractLogger {

    protected static Logger logger;
    private FileHandler fh;

    public AbstractLogger(String fileName, boolean append) {

        logger = Logger.getLogger(fileName);
        logger.setUseParentHandlers(false);
        try {
            // configure the logger with handler and formatter
            String logFileName = fileName + ".log";
            fh = new FileHandler(logFileName, append);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

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
