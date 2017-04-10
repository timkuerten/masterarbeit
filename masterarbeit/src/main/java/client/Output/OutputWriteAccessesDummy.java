package client.Output;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class OutputWriteAccessesDummy implements OutputWriteAccesses {

    public void start() {

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
