package client;

import client.Output.OutputWriteAccesses;
import client.Output.TimeSaverManager;
import datastructure.DataStructure;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ClientWriter {

    private DataStructure ds;
    private OutputWriteAccesses outputWriteAccesses;

    public ClientWriter(DataStructure ds, OutputWriteAccesses outputWriteAccesses) {
        this.ds = ds;
        this.outputWriteAccesses = outputWriteAccesses;
    }

    public UUID insertProfile(Map<String, String> profileData) {
        long startTime = System.nanoTime();
        UUID returnValue = ds.insert(profileData);
        long estimatedTime = System.nanoTime() - startTime;
        outputWriteAccesses.insert(profileData, returnValue, estimatedTime);
        return returnValue;
    }

    public boolean updateProfile(UUID uuid, Map<String, String> profileData) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.update(uuid, profileData);
        long estimatedTime = System.nanoTime() - startTime;
        outputWriteAccesses.update(uuid, profileData, returnValue, estimatedTime);
        return returnValue;
    }

    //schema
    public boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        long startTime = System.nanoTime();
        boolean returnValue = ds.addSchema(schema, thirdPartyIDs);
        long estimatedTime = System.nanoTime() - startTime;
        outputWriteAccesses.addSchema(schema, thirdPartyIDs, returnValue, estimatedTime);
        return returnValue;
    }

}
