package client;

import datastructure.DSUnsorted;
import datastructure.DataStructure;
import datastructure.Profile;
import datastructure.Schema;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Client {

    private DataStructure ds;

    public Client() {
        ds = new DSUnsorted();
    }

    public Profile get(UUID uuid) {
        return ds.get(uuid);
    }

    public Set<Profile> get(String ThirdPartyID, String value) {
        return ds.get(ThirdPartyID, value);
    }

    public UUID insert(Map<String, String> profileData) {
        return ds.insert(profileData);
    }

    public boolean updateProfile(UUID uuid, HashMap<String, String> profileData) {
        return ds.update(uuid, profileData);
    }

    //schema
    public Schema getSchema() {
        return ds.getSchema();
    }

    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        return ds.changeSchema(schema, thirdPartyIDs);
    }

}
