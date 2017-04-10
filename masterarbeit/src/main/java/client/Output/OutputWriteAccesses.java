package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface OutputWriteAccesses {

    void start();

    void insert(Map<String, String> profileData, UUID returnValue, long estimatedTime);

    void update(UUID uuid, Map<String, String> profileData, boolean returnValue, long estimatedTime);

    void addSchema(Set<String> schema, Set<String> thirdPartyIDs, boolean returnValue, long estimatedTime);

    void close();

}
