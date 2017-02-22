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

    public ClientWriter(DataStructure ds, TimeLogger timeLogger, boolean writerLoggerAppend) {
        this.ds = ds;
        this.timeLogger = timeLogger;
        //writerLogger = new WriterLogger(WriterLogger.class.getName(), writerLoggerAppend);
    }

    public void setLogInsertProfile(boolean logInsertProfile) {
        this.logInsertProfile = logInsertProfile;
    }

    public void setLogUpdateProfile(boolean logUpdateProfile) {
        this.logUpdateProfile = logUpdateProfile;
    }

    public void setLodAddSchema(boolean lodAddSchema) {
        this.logAddSchema = lodAddSchema;
    }

    public void setLogChangeSchema(boolean logChangeSchema) {
        this.logChangeSchema = logChangeSchema;
    }

    public UUID insertProfile(Map<String, String> profileData) {
        long startTime = System.nanoTime();
        UUID returnValue = ds.insert(profileData);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.insertProfile(estimatedTime);
        //writerLogger.insertProfile(returnValue, profileData);
        return returnValue;
    }

    public boolean updateProfile(UUID uuid, HashMap<String, String> profileData) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.update(uuid, profileData);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.updateProfile(estimatedTime);
        //writerLogger.updateProfile(uuid, profileData);
        return returnValue;
    }

    //schema
    public boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.addSchema(schema, thirdPartyIDs);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.addSchema(estimatedTime);
        //writerLogger.changeSchema(schema, thirdPartyIDs);
        return returnValue;
    }

    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.changeSchema(schema, thirdPartyIDs);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.changeSchema(estimatedTime);
        //writerLogger.changeSchema(schema, thirdPartyIDs);
        return returnValue;
    }

}
