package client;

/**
 * Class to log estimated time by using interface mathods of data structure.
 */
public class TimeLogger extends AbstractLogger {

    /**
     * Constructor. It creates a logger with handler.
     *
     * @param fileName name of logfile; it will get a ".log" suffix
     * @param append   if logger should use appending file or create new
     */
    public TimeLogger(String fileName, boolean append) {
        super(fileName, append);
    }

    /**
     * Creates a entry for getProfileByUuid with its estimatedTime
     *
     * @param estimatedTime estimated time of getProfileByUuid
     */
    public void getProfileByUuid(long estimatedTime) {
        logger.info("getProfileByUuid " + estimatedTime);
    }

    /**
     * Creates an entry for getProfilesByThirdPartyID with its estimatedTime
     *
     * @param estimatedTime estimated time of getProfilesByThirdPartyID
     */
    public void getProfileByThirdPartyID(long estimatedTime) {
        logger.info("getProfilesByThirdPartyID " + estimatedTime);
    }

    /**
     * Creates an entry for getProfilesByRange with its estimatedTime
     *
     * @param estimatedTime estimated time of getProfilesByRange
     */
    public void getProfileByRange(long estimatedTime) {
        logger.info("getProfilesByRange " + estimatedTime);
    }

    /**
     * Creates an entry for insertProfile with its estimatedTime
     *
     * @param estimatedTime estimated time of insertProfile
     */
    public void insertProfile(long estimatedTime) {
        logger.info("insertProfile " + estimatedTime);
    }

    /**
     * Creates an entry for updateProfile with its estimatedTime
     *
     * @param estimatedTime estimated time of updateProfile
     */
    public void updateProfile(long estimatedTime) {
        logger.info("updateProfile " + estimatedTime);
    }

    /**
     * Creates an entry for getSchema with its estimatedTime
     *
     * @param estimatedTime estimated time of getSchema
     */
    public void getSchema(long estimatedTime) {
        logger.info("getSchema " + estimatedTime);
    }

    /**
     * Creates an entry for addSchema with its estimatedTime
     *
     * @param estimatedTime estimated time of addSchema
     */
    public void addSchema(long estimatedTime) {
        logger.info("addSchema " + estimatedTime);
    }

    /**
     * Creates an entry for changeSchema with its estimatedTime
     *
     * @param estimatedTime estimated time of changeSchema
     */
    public void changeSchema(long estimatedTime) {
        logger.info("changeSchema " + estimatedTime);
    }

}
