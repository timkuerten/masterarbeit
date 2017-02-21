package client;

public class TimeLogger extends AbstractLogger {

    public TimeLogger(String fileName, boolean append) {
        super(fileName, append);
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

    public void addSchema(long estimatedTime) {
        logger.info("addSchema " + estimatedTime);
    }

    public void changeSchema(long estimatedTime) {
        logger.info("changeSchema " + estimatedTime);
    }

}
