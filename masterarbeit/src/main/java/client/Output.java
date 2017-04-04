package client;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface Output {


    void get(UUID uuid, Profile returnValue);

    void get(String thirdPartyID, String value, Set<Profile> returnValue);

    void get(String thirdPartyID, String minValue, String maxValue, Set<Profile> returnValue);

    void get(Set<Triple<Integer, String, String>> searchValues, Set<Profile> returnValue);

    void insert(Map<String, String> profileData, UUID returnValue);

    void update(UUID uuid, Map<String, String> profileData, boolean returnValue);

    void getSchema(Schema returnValue);

    void changeSchema(Set<String> schema, Set<String> thirdPartyIDs, boolean returnValue);

    void addSchema(Set<String> schema, Set<String> thirdPartyIDs, boolean returnValue);

}
