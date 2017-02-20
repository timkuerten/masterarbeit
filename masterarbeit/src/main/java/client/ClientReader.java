package client;

import datastructure.DataStructure;
import datastructure.Profile;
import datastructure.Schema;

import java.util.Set;
import java.util.UUID;

public class ClientReader{

    private DataStructure ds;
    TimeLogger timeLogger;

    public ClientReader(DataStructure ds) {
        this.ds = ds;
        timeLogger = new TimeLogger(TimeLogger.class.getName());
    }

    public Profile get(UUID uuid) {
        long startTime = System.nanoTime();
        Profile returnValue = ds.get(uuid);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.getProfileByUuid(estimatedTime);
        return returnValue;
    }

    public Set<Profile> get(String ThirdPartyID, String value) {
        long startTime = System.nanoTime();
        Set<Profile> returnValue = ds.get(ThirdPartyID, value);
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.getProfileByUuid(estimatedTime);
        return returnValue;
    }

    //schema
    public Schema getSchema() {
        long startTime = System.nanoTime();
        Schema returnValue = ds.getSchema();
        long estimatedTime = System.nanoTime() - startTime;
        timeLogger.getSchema(estimatedTime);
        return returnValue;
    }

}
