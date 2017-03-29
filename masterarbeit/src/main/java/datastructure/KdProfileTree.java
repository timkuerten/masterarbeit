package datastructure;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface KdProfileTree {
    KdNode insert(Profile profile);

    void insert(Set<Profile> profiles);

    boolean contains(Profile profile);

    Profile get(UUID uuid);

    Set<Profile> get(String thirdPartyID, String value);

    Set<Profile> get(String thirdPartyID, String minValue, String maxValue);

    Set<Profile> get(Set<Triple<String, String, String>> searchValues);

    boolean delete(Profile profile);

    KdNode findNode(Map<String, String> profileData);

    String findMin(String thirdPartyID);

    KdNode updateProfile(Profile profile, Map<String, String> profileData);
}
