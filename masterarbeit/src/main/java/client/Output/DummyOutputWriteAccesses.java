package client.Output;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class DummyOutputWriteAccesses implements OutputWriteAccesses {

    public void insert(Map<String, String> profileData, UUID returnValue) {

    }

    public void update(UUID uuid, Map<String, String> profileData, boolean returnValue) {

    }

    public void addSchema(Set<String> schema, Set<String> thirdPartyIDs, boolean returnValue) {

    }
}
