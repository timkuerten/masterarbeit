package client;

import datastructure.DataStructure;
import datastructure.Profile;
import datastructure.Schema;

import java.util.Set;
import java.util.UUID;

public class ClientReader {

    private DataStructure ds;
    private TimeLogger timeLogger;
    private boolean logGetUuid, logGetThirdPartyID, logGetSchema;

    public ClientReader(DataStructure ds, TimeLogger timeLogger) {
        this.ds = ds;
        this.timeLogger = timeLogger;
        logGetUuid = true;
        logGetThirdPartyID = true;
        logGetSchema = true;
    }

    public void setLogGetUuid(boolean logGetUuid) {
        this.logGetUuid = logGetUuid;
    }

    public void setLogGetThirdPartyID(boolean logGetThirdPartyID) {
        this.logGetThirdPartyID = logGetThirdPartyID;
    }

    public void setLogGetSchema(boolean logGetSchema) {
        this.logGetSchema = logGetSchema;
    }

    public Profile get(UUID uuid) {
        if (logGetUuid) {
            long startTime = System.nanoTime();
            Profile returnValue = ds.get(uuid);
            long estimatedTime = System.nanoTime() - startTime;
            timeLogger.getProfileByUuid(estimatedTime);
            return returnValue;
        } else {
            return ds.get(uuid);
        }
    }

    public Set<Profile> get(String thirdPartyID, String value) {
        if (logGetThirdPartyID) {
            long startTime = System.nanoTime();
            Set<Profile> returnValue = ds.get(thirdPartyID, value);
            long estimatedTime = System.nanoTime() - startTime;
            timeLogger.getProfileByThirdPartyID(estimatedTime);
            return returnValue;
        } else {
            return ds.get(thirdPartyID, value);
        }
    }

    public Set<Profile> get(String thirdPartyID, String minValue, String maxValue) {
        if (logGetThirdPartyID) {
            long startTime = System.nanoTime();
            Set<Profile> returnValue = ds.get(thirdPartyID, minValue, maxValue);
            long estimatedTime = System.nanoTime() - startTime;
            timeLogger.getProfileByThirdPartyID(estimatedTime);
            return returnValue;
        } else {
            return ds.get(thirdPartyID, minValue, maxValue);
        }
    }

    //schema
    public Schema getSchema() {
        if (logGetSchema) {
            long startTime = System.nanoTime();
            Schema returnValue = ds.getSchema();
            long estimatedTime = System.nanoTime() - startTime;
            timeLogger.getSchema(estimatedTime);
            return returnValue;
        } else {
            return ds.getSchema();
        }
    }

}
