package datastructure;

import exception.SchemaException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * Abstract class to save and manage profiles.
 */
public interface DataStructure {

    /**
     * Should be used after construction. Give a schema and third-party-IDs to data structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws SchemaException throws exception if third-party-IDs are not contained in schema
     */
    void init(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaException;

    //get, insertProfile, change

    /**
     * Searching for a profile with given uuid. Returns the profile if it exists. Otherwise returns null.
     *
     * @param uuid uuid of profile
     * @return profile with given uuid
     */
    Profile get(UUID uuid);

    /**
     * Searching for a profile with given third-party-ID and its value. Returns a set of profiles which contain the given third-party-ID and value.
     *
     * @param ThirdPartyID third-party-ID
     * @param value        value of third-party-ID
     * @return profiles which contain the given third-party-ID and value
     */
    Set<Profile> get(String ThirdPartyID, String value);

    /**
     * Creates a new profile with given data, saves it in data structure and returns uuid.
     *
     * @param profileData data of new profile
     * @return uuid of new profile
     */
    UUID insert(Map<String, String> profileData);

    /**
     * Searching for profile with uuid and add data to it. If profile can not be found return false, otherwise true.
     *
     * @param uuid        uuid of profile
     * @param profileData data of profile
     * @return if profile with uuid can be found
     */
    boolean update(UUID uuid, HashMap<String, String> profileData);

    //schema

    /**
     * Returns current schema.
     *
     * @return current schema.
     */
    Schema getSchema();

    /**
     * Change current schema and third-party-IDs to given schema and third-party-IDs if third-party-IDs contained in schema and returns true. Otherwise returns false.
     *
     * @param schema        new schema
     * @param thirdPartyIDs new third-party-IDs
     * @return if schema and third third-party-IDs are changed
     */
    boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs);

    boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs);

}
