package datastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract class to save and manage profiles.
 */
public interface DataStructure {

    //get, insertProfile, change

    /**
     * Searching for a profile with given uuid. Returns the profile if it exists. Otherwise returns null.
     *
     * @param uuid uuid of profile
     * @return profile with given uuid
     */
    Profile get(UUID uuid);

    /**
     * Searching for a profile with given third-party-ID and its value. Returns a set of profiles which correlate to the given third-party-ID and value.
     *
     * @param thirdPartyID third-party-ID
     * @param value        value of third-party-ID
     * @return profiles which contain the given third-party-ID and value
     */
    Set<Profile> get(String thirdPartyID, String value);

    /**
     * Searching for a profile with given third-party-ID and its range of value. Returns a set of profiles which contain the given third-party-ID and range of value.
     *
     * @param thirdPartyID third-party-ID
     * @param minValue minimal value of third-party-ID
     * @param maxValue maximal value of third-party-ID
     * @return profiles which contain the given third-party-ID and range of value
     */
    Set<Profile> get(String thirdPartyID, String minValue, String maxValue);

    /**
     * Creates a new profile with given key, saves it in key structure and returns uuid.
     *
     * @param profileData key of new profile
     * @return uuid of new profile
     */
    UUID insert(Map<String, String> profileData);

    /**
     * Searching for profile with uuid and add key to it. If profile can not be found return false, otherwise true.
     *
     * @param uuid        uuid of profile
     * @param profileData key of profile
     * @return if profile with uuid can be found
     */
    boolean update(UUID uuid, Map<String, String> profileData);

    //schema

    /**
     * Returns current schema.
     *
     * @return current schema.
     */
    Schema getSchema();

    boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs);

}
