package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class OutputBenchmarksInTerminal implements Output {

    protected TimeSaverManager timeSaverManager =  new TimeSaverManager();

    public void start() {

    }

    public void get(UUID uuid, Profile returnValue, long estimatedTime) {
        timeSaverManager.getProfileByUuid(estimatedTime);
    }

    public void get(String thirdPartyID, String value, Set<Profile> returnValue, long estimatedTime) {
        timeSaverManager.getProfileByThirdPartyID(estimatedTime);
    }

    public void get(String thirdPartyID, String minValue, String maxValue, Set<Profile> returnValue, long estimatedTime) {
        timeSaverManager.getProfileByRange(estimatedTime);
    }

    public void get(Set<Triple<String, String, String>> searchValues, Set<Profile> returnValue, long estimatedTime) {
        timeSaverManager.getProfileByMultiRange(estimatedTime);
    }

    public void getSchema(Schema returnValue, long estimatedTime) {
        timeSaverManager.getSchema(estimatedTime);
    }

    public void insert(Map<String, String> profileData, UUID returnValue, long estimatedTime) {
        timeSaverManager.insertProfile(estimatedTime);
    }

    public void update(UUID uuid, Map<String, String> profileData, boolean returnValue, long estimatedTime) {
        timeSaverManager.updateProfile(estimatedTime);
    }

    public void addSchema(Set<String> schema, Set<String> thirdPartyIDs, boolean returnValue, long estimatedTime) {
        timeSaverManager.addSchema(estimatedTime);
    }

    public void close() {
        System.out.println(timeSaverManager.printOutTimeSavers());
    }

}
