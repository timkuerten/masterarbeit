package client;

import datastructure.DataStructure;
import datastructure.Profile;
import datastructure.Schema;

import java.util.Set;
import java.util.UUID;

public class ClientReader {

    private DataStructure ds;
    private TimeLogger timeLogger;
    private boolean logGetUuid, logGetThirdPartyID, logGetRange, logGetSchema;
    private TimeSaverManager timeSaverManager;

    public ClientReader(DataStructure ds, TimeLogger timeLogger, TimeSaverManager timeSaverManager) {
        this.ds = ds;
        this.timeLogger = timeLogger;
        if (timeLogger == null) {
            logGetUuid = false;
            logGetThirdPartyID = false;
            logGetSchema = false;
            logGetRange = false;
        } else {
            logGetUuid = true;
            logGetThirdPartyID = true;
            logGetSchema = true;
            logGetRange = true;
        }
        this.timeSaverManager = timeSaverManager;
    }

    public void setLogGetUuid(boolean logGetUuid) {
        this.logGetUuid = logGetUuid;
    }

    public void setLogGetThirdPartyID(boolean logGetThirdPartyID) {
        this.logGetThirdPartyID = logGetThirdPartyID;
    }

    public void setLogRange(boolean logGetRange) {
        this.logGetRange = logGetRange;
    }

    public void setLogGetSchema(boolean logGetSchema) {
        this.logGetSchema = logGetSchema;
    }

    public Profile get(UUID uuid) {
        long startTime = System.nanoTime();
        Profile returnValue = ds.get(uuid);
        long estimatedTime = System.nanoTime() - startTime;
        if (logGetUuid) {
            timeLogger.getProfileByUuid(estimatedTime);
        }
        timeSaverManager.getProfileByUuid(estimatedTime);
        return returnValue;
    }

    public Set<Profile> get(String thirdPartyID, String value) {

        long startTime = System.nanoTime();
        Set<Profile> returnValue = ds.get(thirdPartyID, value);
        long estimatedTime = System.nanoTime() - startTime;
        if (logGetThirdPartyID) {
            timeLogger.getProfileByThirdPartyID(estimatedTime);
        }
        timeSaverManager.getProfileByThirdPartyID(estimatedTime);
        return returnValue;

    }

    public Set<Profile> get(String thirdPartyID, String minValue, String maxValue) {

        long startTime = System.nanoTime();
        Set<Profile> returnValue = ds.get(thirdPartyID, minValue, maxValue);
        long estimatedTime = System.nanoTime() - startTime;
        if (logGetRange) {
            timeLogger.getProfileByRange(estimatedTime);
        }
        timeSaverManager.getProfileByRange(estimatedTime);
        return returnValue;
    }

    //schema
    public Schema getSchema() {

        long startTime = System.nanoTime();
        Schema returnValue = ds.getSchema();
        long estimatedTime = System.nanoTime() - startTime;
        if (logGetSchema) {
            timeLogger.getSchema(estimatedTime);
        }
        timeSaverManager.getSchema(estimatedTime);
        return returnValue;

    }

}
