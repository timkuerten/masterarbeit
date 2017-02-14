package datastructure;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface Datastructure {

    void init(Set<String> schema, Set<String> thirdPartyIDs) throws Exception;

    //get, insert, update
    Profile get(UUID uuid);

    Set<Profile> get(String ThirdPartyID, String value);

    UUID insert(Map<String, String> profileData);

    boolean update(UUID uuid, HashMap<String, String> profileData);

    //schema
    Schema getSchema();

    boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs);

}
