package datastructure;

import exception.schemaException;

import java.util.*;

/**
 * Class to save and manage profiles in a HashMap. Every profile (value) is mapped to a uuid (key).
 */
public class DSUnsorted implements DataStructure {

    Schema schema;
    Map<UUID, Profile> profiles = new HashMap<>();

    public DSUnsorted(Set<String> schema, Set<String> thirdPartyIDs) throws schemaException {
        init(schema, thirdPartyIDs);
    }

    /**
     * Should be used after construction. Give a schema and third-party-IDs to data structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws Exception throws exception if third-party-IDs are not contained in schema
     */
    public void init(Set<String> schema, Set<String> thirdPartyIDs) throws schemaException {
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
        if (this.schema.getThirdPartyIDs().contains(ThirdPartyID) == false) {
            return null;
        }
        Set<Profile> retrunProfiles = new HashSet<>();
        this.profiles.forEach((k, v) -> {
            if (v.profileData.get(ThirdPartyID) == value) {
                retrunProfiles.add(v);
            }
        });
        return retrunProfiles;
    }

    /**
     * Creates a new profile with given data, saves it in data structure and returns uuid.
     *
     * @param profileData data of new profile
     * @return uuid of new profile
     */
    public UUID insert(Map<String, String> profileData) {
        Profile newProfile = new Profile(UUID.randomUUID(), new HashMap<>(profileData));
        if (newProfile.correspondToSchema(schema.getSchema())) {
            profiles.put(newProfile.uuid, newProfile);
            return newProfile.uuid;
        } else {
            return null;
        }
    }

    /**
     * Searching for profile with uuid and add data to it. If profile can not be found return false, otherwise true.
     *
     * @param uuid        uuid of profile
     * @param profileData data of profile
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

    /**
     * Change current schema and third-party-IDs to given schema nd third-party-IDs if third-party-IDs contained in schema and returns true. Otherwise returns false.
     *
     * @param schema        new schema
     * @param thirdPartyIDs new third-party-IDs
     * @return if schema and third third-party-IDs are changed
     */
    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        if (this.schema.update(schema, thirdPartyIDs)) {
            //update profileData of every profile that they correlate to new schema
            profiles.values().forEach(profile ->
                    profile.update(this.schema)
            );
            return true;
        } else {
            return false;
        }
    }

}
