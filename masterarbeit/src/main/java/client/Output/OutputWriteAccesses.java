package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface OutputWriteAccesses {

    void insert(Map<String, String> profileData, UUID returnValue);

    void update(UUID uuid, Map<String, String> profileData, boolean returnValue);

    void addSchema(Set<String> schema, Set<String> thirdPartyIDs, boolean returnValue);

}
