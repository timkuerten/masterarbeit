package client;

import datastructure.DataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class ClientWriter {

    private DataStructure ds;

    public ClientWriter(DataStructure ds) {
        this.ds = ds;
    }

    public UUID insert(Map<String, String> profileData) {
        return ds.insert(profileData);
    }

    public boolean updateProfile(UUID uuid, HashMap<String, String> profileData) {
        return ds.update(uuid, profileData);
    }

    //schema
    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        return ds.changeSchema(schema, thirdPartyIDs);
    }

}
