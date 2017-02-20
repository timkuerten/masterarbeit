package client;

import datastructure.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WriterLogger {

    private static Logger logger;

    public WriterLogger(String fileName) {
        logger = Logger.getLogger(fileName);
        //load existing file
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
