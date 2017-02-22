package datastructure;

import exception.SchemaException;

import java.util.*;

/**
 * Class to save and manage profiles in a HashMap. Every profile (value) is mapped to a uuid (key).
 */
public class DSUnsorted implements DataStructure {

    private Schema schema;
    private Map<UUID, Profile> profiles = new HashMap<>();

    /**
     * Constructor. Give a schema and third-party-IDs to data structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws SchemaException throws exception if third-party-IDs are not contained in schema
     */
    public DSUnsorted(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaException {
        this.schema = new Schema(schema, thirdPartyIDs);
    }

    /**
     * Searching for a profile with given uuid. Returns the profile if it exists. Otherwise returns null.
     *
     * @param uuid uuid of profile
     * @return profile with given uuid
     */
    public Profile get(UUID uuid) {
        return profiles.get(uuid);
    }

    /**
     * Searching for a profile with given third-party-ID and its value. Returns a set of profiles which contain the given third-party-ID and value.
     *
     * @param ThirdPartyID third-party-ID
     * @param value        value of third-party-ID
     * @return profiles which contain the given third-party-ID and value
     */
    public Set<Profile> get(String ThirdPartyID, String value) {
        if (!this.schema.getThirdPartyIDs().contains(ThirdPartyID)) {
            return null;
        }
        Set<Profile> returnProfiles = new HashSet<>();
        this.profiles.forEach((k, v) -> {
            // search for profiles that given ThirdPartyID is mapped to given value and add them to return value
            String thirdPartyId = v.profileData.get(ThirdPartyID);
            if (thirdPartyId != null && thirdPartyId.equals(value)) {
                returnProfiles.add(v);
            }
        });
        return returnProfiles;
    }

    /**
     * Creates a new profile with given data, saves it in data structure and returns uuid.
     *
     * @param profileData data of new profile
     * @return uuid of new profile
     */
    public UUID insert(Map<String, String> profileData) {
        if (schema.getSchema().containsAll(profileData.keySet())) {
            Profile newProfile = new Profile(UUID.randomUUID(), new HashMap<>(profileData));
            // add new profile to profiles
            profiles.put(newProfile.uuid, newProfile);
            return newProfile.uuid;
        } else {
            return null;
        }
    }

    /**
     * Searching for profile with uuid and add data. If profile can not be found return false, otherwise true.
     *
     * @param uuid        uuid of profile
     * @param profileData data that should be added to profile
     * @return if profile with uuid can be found
     */
    public boolean update(UUID uuid, HashMap<String, String> profileData) {
        if (profiles.get(uuid) != null && this.schema.getSchema().containsAll(profileData.keySet())) {
            profiles.get(uuid).profileData.putAll(profileData);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns current schema.
     *
     * @return current schema.
     */
    public Schema getSchema() {
        return this.schema;
    }

    public boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        return this.schema.add(schema, thirdPartyIDs);
    }

    /**
     * Change current schema and third-party-IDs to given schema nd third-party-IDs if third-party-IDs contained in schema and returns true. Otherwise returns false.
     *
     * @param schema        new schema
     * @param thirdPartyIDs new third-party-IDs
     * @return if schema and third third-party-IDs are changed
     */
    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        if (this.schema.change(schema, thirdPartyIDs)) {
            // update profileData of every profile to given schema
            profiles.values().forEach(profile ->
                    profile.update(this.schema)
            );
            return true;
        } else {
            return false;
        }
    }

}
