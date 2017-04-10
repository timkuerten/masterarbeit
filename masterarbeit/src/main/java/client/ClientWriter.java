package client;

import client.Output.OutputWriteAccesses;
import datastructure.DataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ClientWriter {

    private DataStructure ds;
    private TimeSaverManager timeSaverManager;
    protected OutputWriteAccesses outputWriteAccesses;

    public ClientWriter(DataStructure ds, TimeSaverManager timeSaverManager, OutputWriteAccesses outputWriteAccesses) {
        this.ds = ds;
        this.timeSaverManager = timeSaverManager;
        this.outputWriteAccesses = outputWriteAccesses;
    }

    public UUID insertProfile(Map<String, String> profileData) {
        long startTime = System.nanoTime();
        UUID returnValue = ds.insert(profileData);
        long estimatedTime = System.nanoTime() - startTime;
        timeSaverManager.insertProfile(estimatedTime);
        outputWriteAccesses.insert(profileData, returnValue, estimatedTime);
        return returnValue;
    }

    public boolean updateProfile(UUID uuid, Map<String, String> profileData) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.update(uuid, profileData);
        long estimatedTime = System.nanoTime() - startTime;
        timeSaverManager.updateProfile(estimatedTime);
        outputWriteAccesses.update(uuid, profileData, returnValue, estimatedTime);
        return returnValue;
    }

    //schema
    public boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.addSchema(schema, thirdPartyIDs);
        long estimatedTime = System.nanoTime() - startTime;
        timeSaverManager.addSchema(estimatedTime);
        outputWriteAccesses.addSchema(schema, thirdPartyIDs, returnValue, estimatedTime);
        return returnValue;
    }

}
