package client;

import datastructure.DataStructure;
import datastructure.Profile;
import datastructure.Schema;

import java.util.Set;
import java.util.UUID;

public class ClientReader {

    private DataStructure ds;

    public ClientReader(DataStructure ds) {
        this.ds = ds;
    }

    public Profile get(UUID uuid) {
        return ds.get(uuid);
    }

    public Set<Profile> get(String ThirdPartyID, String value) {
        return ds.get(ThirdPartyID, value);
    }

    //schema
    public Schema getSchema() {
        return ds.getSchema();
    }

}
