package client;

import java.util.logging.Logger;

public class TimeLogger {

    private static Logger logger;

    public TimeLogger(String fileName) {
        logger = Logger.getLogger(fileName);
        //load existing file
    }

    public void loadFile(String fileName) {

    }

    public void getProfileByUuid(long estimatedTime) {
        logger.info("getProfileByUuid " + estimatedTime);
    }

    public void getProfileByThirdPartyID(long estimatedTime) {
        logger.info("getProfileByThirdPartyID " + estimatedTime);
    }

    public void insertProfile(long estimatedTime) {
        logger.info("insertProfile " + estimatedTime);
    }

    public void updateProfile(long estimatedTime) {
        logger.info("updateProfile " + estimatedTime);
    }

    public void getSchema(long estimatedTime) {
        logger.info("getSchema " + estimatedTime);
    }

    public void changeSchema(long estimatedTime) {
        logger.info("changeSchema " + estimatedTime);
    }

}