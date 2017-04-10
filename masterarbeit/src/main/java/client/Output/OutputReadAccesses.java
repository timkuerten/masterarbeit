package client.Output;

import datastructure.Profile;
import datastructure.Schema;
import datastructure.Triple;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface OutputReadAccesses {

    void start();

    void get(UUID uuid, Profile returnValue, long estimatedTime);

    void get(String thirdPartyID, String value, Set<Profile> returnValue, long estimatedTime);

    void get(String thirdPartyID, String minValue, String maxValue, Set<Profile> returnValue, long estimatedTime);

    void get(Set<Triple<String, String, String>> searchValues, Set<Profile> returnValue, long estimatedTime);

    void getSchema(Schema returnValue, long estimatedTime);

    void close();

}
