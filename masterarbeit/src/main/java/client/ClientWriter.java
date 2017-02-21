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

    public ClientWriter(DataStructure ds) {
        this.ds = ds;
        timeLogger = new TimeLogger(TimeLogger.class.getName());
        writerLogger = new WriterLogger(WriterLogger.class.getName());
    }

    public UUID insert(Map<String, String> profileData) {
        long startTime = System.nanoTime();
        UUID returnValue = ds.insert(profileData);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.insertProfile(estimatedTime);
        writerLogger.insertProfile(returnValue, profileData);
        return returnValue;
    }

    public boolean updateProfile(UUID uuid, HashMap<String, String> profileData) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.update(uuid, profileData);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.updateProfile(estimatedTime);
        writerLogger.updateProfile(uuid, profileData);
        return returnValue;
    }

    //schema
    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.changeSchema(schema, thirdPartyIDs);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.changeSchema(estimatedTime);
        writerLogger.changeSchema(schema, thirdPartyIDs);
        return returnValue;
    }

}
