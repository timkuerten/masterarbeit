package client;

import client.Output.OutputReadAccesses;
import datastructure.DataStructure;
import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ClientReader {

    private DataStructure ds;
    private TimeSaverManager timeSaverManager;
    protected OutputReadAccesses outputReadAccesses;

    public ClientReader(DataStructure ds, TimeSaverManager timeSaverManager, OutputReadAccesses outputReadAccesses) {
        this.ds = ds;
        this.timeSaverManager = timeSaverManager;
        this.outputReadAccesses = outputReadAccesses;
    }

    public Profile get(UUID uuid) {
        long startTime = System.nanoTime();
        Profile returnValue = ds.get(uuid);
        long estimatedTime = System.nanoTime() - startTime;
        timeSaverManager.getProfileByUuid(estimatedTime);
        outputReadAccesses.get(uuid, returnValue, estimatedTime);
        return returnValue;
    }

    public Set<Profile> get(String thirdPartyID, String value) {
        long startTime = System.nanoTime();
        Set<Profile> returnValue = ds.get(thirdPartyID, value);
        long estimatedTime = System.nanoTime() - startTime;
        timeSaverManager.getProfileByThirdPartyID(estimatedTime);
        outputReadAccesses.get(thirdPartyID, value, returnValue, estimatedTime);
        return returnValue;

    }

    public Set<Profile> get(String thirdPartyID, String minValue, String maxValue) {
        long startTime = System.nanoTime();
        Set<Profile> returnValue = ds.get(thirdPartyID, minValue, maxValue);
        long estimatedTime = System.nanoTime() - startTime;
        timeSaverManager.getProfileByRange(estimatedTime);
        outputReadAccesses.get(thirdPartyID, minValue, maxValue, returnValue, estimatedTime);
        return returnValue;
    }

    public Set<Profile> get(Set<Triple<String, String, String>> searchValues) {
        long startTime = System.nanoTime();
        Set<Profile> returnValue = ds.get(searchValues);
        long estimatedTime = System.nanoTime() - startTime;
        timeSaverManager.getProfileByMultiRange(estimatedTime);
        outputReadAccesses.get(searchValues, returnValue, estimatedTime);
        return returnValue;
    }

    //schema
    public Schema getSchema() {
        long startTime = System.nanoTime();
        Schema returnValue = ds.getSchema();
        long estimatedTime = System.nanoTime() - startTime;
        timeSaverManager.getSchema(estimatedTime);
        outputReadAccesses.getSchema(returnValue, estimatedTime);
        return returnValue;

    }

}
