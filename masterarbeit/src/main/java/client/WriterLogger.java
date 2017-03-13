package client;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Class to log every writing access in data structure.
 */
public class WriterLogger extends AbstractLogger {

    /**
     * Constructor. It creates a logger with handler.
     *
     * @param fileName name of logfile; it will get a ".log" suffix
     * @param append   if logger should use appending file or create new
     */
    public WriterLogger(String fileName, boolean append) {
        super(fileName, append);
    }

    /**
     * Creates an entry for insertProfile with given values of uuid and profileData
     *
     * @param uuid uuid of new profile
     * @param profileData profile data of new profile
     */
    public void insertProfile(UUID uuid, Map<String, String> profileData) {
        logger.info("insertProfile(" + uuid + ", " + profileData + ")");
    }

    /**
     * Creates an entry for updateProfile with given values of uuid and profileData
     *
     * @param uuid uuid of existent profile that should get additional profile data
     * @param profileData profile data that should be add to profile with uuid
     */
    public void updateProfile(UUID uuid, Map<String, String> profileData) {
        logger.info("updateProfile(" + uuid + ", " + profileData + ")");
    }

    /**
     * Creates an entry for changeSchema with given values of new schema and thirdPartyIDs
     *
     * @param schema new schema
     * @param thirdPartyIDs new third-party-ID
     */
    public void changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        logger.info("changeSchema(" + schema + ", " + thirdPartyIDs + ")");
    }

}
