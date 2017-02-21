package client;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class WriterLogger extends AbstractLogger {

    public WriterLogger(String fileName, boolean append) {
        super(fileName, append);
    }

    public void loadFile(String fileName) {

    }

    public void insertProfile(UUID uuid, Map<String, String> profileData) {
        logger.info("insertProfile(" + uuid + ", " + profileData + ")");
    }

    public void updateProfile(UUID uuid, Map<String, String> profileData) {
        logger.info("updateProfile(" + uuid + ", " + profileData + ")");
    }

    public void changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        logger.info("changeSchema(" + schema + ", " + thirdPartyIDs + ")");
    }

}
