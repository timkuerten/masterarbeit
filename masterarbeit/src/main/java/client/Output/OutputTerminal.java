package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class OutputTerminal implements Output {

    public void start() {
        System.out.println("Start OutputTerminal");
    }

    public void get(UUID uuid, Profile returnValue, long estimatedTime) {
        System.out.println("get(" + uuid + ") -> " + returnValue.toString() + " : " + estimatedTime);
    }

    public void get(String thirdPartyID, String value, Set<Profile> returnValue, long estimatedTime) {
        System.out.println("get(" + thirdPartyID + "," + value + ") -> " + returnValue.toString() + " : " + estimatedTime);
    }

    public void get(String thirdPartyID, String minValue, String maxValue, Set<Profile> returnValue, long estimatedTime) {
        System.out.println("get(" + thirdPartyID + "," + minValue + "," + maxValue + ") -> " + returnValue.toString() + " : " + estimatedTime);
    }

    public void get(Set<Triple<String, String, String>> searchValues, Set<Profile> returnValue, long estimatedTime) {
        System.out.println("get(" + searchValues + ") -> " + returnValue.toString() + " : " + estimatedTime);
    }

    public void getSchema(Schema returnValue, long estimatedTime) {
        System.out.println("getSchema() -> " + returnValue.toString() + " : " + estimatedTime);
    }

    public void insert(Map<String, String> profileData, UUID returnValue, long estimatedTime) {
        System.out.println("insert(" + profileData + ") -> " + returnValue.toString() + " : " + estimatedTime);
    }

    public void update(UUID uuid, Map<String, String> profileData, boolean returnValue, long estimatedTime) {
        System.out.println("update(" + uuid + "," + profileData + ") -> " + returnValue + " : " + estimatedTime);
    }

    public void addSchema(Set<String> schema, Set<String> thirdPartyIDs, boolean returnValue, long estimatedTime) {
        System.out.println("addSchema(" + schema + "," + thirdPartyIDs + ") -> " + returnValue + " : " + estimatedTime);
    }

    public void close() {
        System.out.println("Close OutputTerminal");
    }

}
