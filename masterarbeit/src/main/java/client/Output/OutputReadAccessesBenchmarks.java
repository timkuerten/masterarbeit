package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class OutputReadAccessesBenchmarks implements Output {

    public void start() {

    }

    public void get(UUID uuid, Profile returnValue, long estimatedTime) {

    }

    public void get(String thirdPartyID, String value, Set<Profile> returnValue, long estimatedTime) {

    }

    public void get(String thirdPartyID, String minValue, String maxValue, Set<Profile> returnValue, long estimatedTime) {

    }

    public void get(Set<Triple<String, String, String>> searchValues, Set<Profile> returnValue, long estimatedTime) {

    }

    public void getSchema(Schema returnValue, long estimatedTime) {

    }

    public void insert(Map<String, String> profileData, UUID returnValue, long estimatedTime) {

    }

    public void update(UUID uuid, Map<String, String> profileData, boolean returnValue, long estimatedTime) {

    }

    public void addSchema(Set<String> schema, Set<String> thirdPartyIDs, boolean returnValue, long estimatedTime) {

    }

    public void close() {

    }

}
