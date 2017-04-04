package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Set;
import java.util.UUID;

public class DummyOutputReadAccesses implements OutputReadAccesses {

    public void get(UUID uuid, Profile returnValue) {

    }

    public void get(String thirdPartyID, String value, Set<Profile> returnValue) {

    }

    public void get(String thirdPartyID, String minValue, String maxValue, Set<Profile> returnValue) {

    }

    public void get(Set<Triple<Integer, String, String>> searchValues, Set<Profile> returnValue) {

    }

    public void getSchema(Schema returnValue) {

    }

}
