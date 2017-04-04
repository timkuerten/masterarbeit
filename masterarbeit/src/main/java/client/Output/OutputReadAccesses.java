package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface OutputReadAccesses {

    void get(UUID uuid, Profile returnValue);

    void get(String thirdPartyID, String value, Set<Profile> returnValue);

    void get(String thirdPartyID, String minValue, String maxValue, Set<Profile> returnValue);

    void get(Set<Triple<Integer, String, String>> searchValues, Set<Profile> returnValue);

    void getSchema(Schema returnValue);

}
