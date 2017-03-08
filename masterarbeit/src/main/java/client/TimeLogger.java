package client;

/**
 * Class to log estimated time by using interface mathods of data structure.
 */
public class TimeLogger extends AbstractLogger {

    /**
     * Constructor. It creates a LOGGER with handler.
     *
     * @param fileName name of logfile; it will get a ".log" suffix
     * @param append   if LOGGER should use appending file or create new
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
        LOGGER.info("getProfileByUuid " + estimatedTime);
    }

    /**
     * Creates an entry for getProfileByThirdPartyID with its estimatedTime
     *
     * @param estimatedTime estimated time of getProfileByThirdPartyID
     */
    public void getProfileByThirdPartyID(long estimatedTime) {
        LOGGER.info("getProfileByThirdPartyID " + estimatedTime);
    }

    /**
     * Creates an entry for getProfileByRange with its estimatedTime
     *
     * @param estimatedTime estimated time of getProfileByRange
     */
    public void getProfileByRange(long estimatedTime) {
        LOGGER.info("getProfileByRange " + estimatedTime);
    }

    /**
     * Creates an entry for insertProfile with its estimatedTime
     *
     * @param estimatedTime estimated time of insertProfile
     */
    public void insertProfile(long estimatedTime) {
        LOGGER.info("insertProfile " + estimatedTime);
    }

    /**
     * Creates an entry for updateProfile with its estimatedTime
     *
     * @param estimatedTime estimated time of updateProfile
     */
    public void updateProfile(long estimatedTime) {
        LOGGER.info("updateProfile " + estimatedTime);
    }

    /**
     * Creates an entry for getSchema with its estimatedTime
     *
     * @param estimatedTime estimated time of getSchema
     */
    public void getSchema(long estimatedTime) {
        LOGGER.info("getSchema " + estimatedTime);
    }

    /**
     * Creates an entry for addSchema with its estimatedTime
     *
     * @param estimatedTime estimated time of addSchema
     */
    public void addSchema(long estimatedTime) {
        LOGGER.info("addSchema " + estimatedTime);
    }

    /**
     * Creates an entry for changeSchema with its estimatedTime
     *
     * @param estimatedTime estimated time of changeSchema
     */
    public void changeSchema(long estimatedTime) {
        LOGGER.info("changeSchema " + estimatedTime);
    }

}
