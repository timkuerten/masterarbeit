package client;

import datastructure.DataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ClientWriter {

    private DataStructure ds;
    private TimeLogger timeLogger;
    private WriterLogger writerLogger;
    private boolean logInsertProfile, logUpdateProfile, logAddSchema, logChangeSchema;
    private TimeSaverManager timeSaverManager;

    public ClientWriter(DataStructure ds, TimeLogger timeLogger, boolean writerLoggerAppend,
            TimeSaverManager timeSaverManager) {
        this.ds = ds;
        this.timeLogger = timeLogger;
        if (timeLogger == null) {
            logInsertProfile = false;
            logUpdateProfile = false;
            logAddSchema = false;
            logChangeSchema = false;
        } else {
            logInsertProfile = true;
            logUpdateProfile = true;
            logAddSchema = true;
            logChangeSchema = true;
        }
        //writerLogger = new WriterLogger(WriterLogger.class.getName(), writerLoggerAppend);
        this.timeSaverManager = timeSaverManager;
    }

    public void setLogInsertProfile(boolean logInsertProfile) {
        this.logInsertProfile = logInsertProfile;
    }

    public void setLogUpdateProfile(boolean logUpdateProfile) {
        this.logUpdateProfile = logUpdateProfile;
    }

    public void setLogAddSchema(boolean lodAddSchema) {
        this.logAddSchema = lodAddSchema;
    }

    public void setLogChangeSchema(boolean logChangeSchema) {
        this.logChangeSchema = logChangeSchema;
    }

    public UUID insertProfile(Map<String, String> profileData) {
        long startTime = System.nanoTime();
        UUID returnValue = ds.insert(profileData);
        long estimatedTime = System.nanoTime() - startTime;
        if (logInsertProfile) {
            timeLogger.insertProfile(estimatedTime);
        }
        //writerLogger.insertProfile(returnValue, profileData);
        timeSaverManager.insertProfile(estimatedTime);
        return returnValue;
    }

    public boolean updateProfile(UUID uuid, Map<String, String> profileData) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.update(uuid, profileData);
        long estimatedTime = System.nanoTime() - startTime;
        if (logUpdateProfile) {
            timeLogger.updateProfile(estimatedTime);
        }
        //writerLogger.updateProfile(uuid, profileData);
        timeSaverManager.updateProfile(estimatedTime);
        return returnValue;
    }

    //schema
    public boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.addSchema(schema, thirdPartyIDs);
        long estimatedTime = System.nanoTime() - startTime;
        if (logAddSchema) {
            timeLogger.addSchema(estimatedTime);
        }
        //writerLogger.changeSchema(schema, thirdPartyIDs);
        timeSaverManager.addSchema(estimatedTime);
        return returnValue;
    }

}
