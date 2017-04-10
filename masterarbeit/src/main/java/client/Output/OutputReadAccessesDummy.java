package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Set;
import java.util.UUID;

public class OutputReadAccessesDummy implements OutputReadAccesses {

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

    public void close() {

    }

}
