package datastructure;

import exception.*;

import java.util.*;

/**
 * Class to save and manage profiles in a HashMap. Every profile (value) is mapped to a uuid (key).
 */
public class DSUnsorted implements DataStructure {

    private Schema schema;
    private Map<UUID, Profile> profiles = new HashMap<>();

    /**
     * Constructor. Give a schema and third-party-IDs to key structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws SchemaNotAllowedException throws exception if third-party-IDs are not contained in schema
     */
    public DSUnsorted(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaNotAllowedException {
        this.schema = new Schema(schema, thirdPartyIDs);
    }

    /**
     * Searching for a profile with given uuid. Returns the profile if it exists. Otherwise returns null.
     *
     * @param uuid uuid of profile
     * @return profile with given uuid
     */
    public Profile get(UUID uuid) {
        // null check
        if (uuid == null) {
            throw new UuidNullPointerException();
        }

        return profiles.get(uuid);
    }

    /**
     * Searching for a profile with given third-party-ID and its value. Returns a set of profiles which correlate to the given third-party-ID and value.
     *
     * @param thirdPartyID third-party-ID
     * @param value        value of third-party-ID
     * @return profiles which contain the given third-party-ID and value
     */
    public Set<Profile> get(String thirdPartyID, String value) {
        // null checks
        if (thirdPartyID == null) {
            throw new ThirdPartyIDNullPointerException();
        }
        else if (value == null) {
            throw new ValueNullPointerException();
        }

        // do the third-party-IDs in schema contain the given third-party-ID?
        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return null;
        }
        Set<Profile> returnProfiles = new HashSet<>();
        this.profiles.forEach((k, v) -> {
            // search for profiles that given ThirdPartyID is mapped to given value and add them to return value
            String thirdPartyId = v.profileData.get(thirdPartyID);
            if (thirdPartyId != null && thirdPartyId.equals(value)) {
                returnProfiles.add(v);
            }
        });
        return returnProfiles;
    }

    /**
     * Searching for a profile with given third-party-ID and its range of value. Returns a set of profiles which contain the given third-party-ID and its range of value.
     *
     * @param thirdPartyID third-party-ID
     * @param minValue     minimal value of third-party-ID
     * @param maxValue     maximal value of third-party-ID
     * @return profiles which contain the given third-party-ID and range of value
     */
    public Set<Profile> get(String thirdPartyID, String minValue, String maxValue) {
        // null check
        if (thirdPartyID == null) {
            throw new ThirdPartyIDNullPointerException();
        }
        else if (minValue != null && maxValue != null && minValue.compareTo(maxValue) > 0) {
            throw new RangeValueException(minValue, maxValue);
        }

        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return Collections.emptySet();
        }
        Set<Profile> returnProfiles = new HashSet<>();
        this.profiles.forEach((k, v) -> {
            // search for profiles that given ThirdPartyID is mapped to given value and add them to return value
            String thirdPartyId = v.profileData.get(thirdPartyID);
            if (thirdPartyId != null) {
                if (minValue != null && maxValue != null) {
                    if (thirdPartyId.compareTo(minValue) >= 0 && thirdPartyId.compareTo(maxValue) <= 0) {
                        returnProfiles.add(v);
                    }
                } else if (minValue != null) {
                    if (thirdPartyId.compareTo(minValue) >= 0) {
                        returnProfiles.add(v);
                    }
                } else if (maxValue != null) {
                    if (thirdPartyId.compareTo(maxValue) <= 0) {
                        returnProfiles.add(v);
                    }
                } else {
                    returnProfiles.add(v);
                }
            }
        });
        return returnProfiles;
    }

    public Set<Profile> get(Set<Triple<String, String, String>> searchValues) {
        if (searchValues.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Profile> returnProfiles = null;
        Set<Profile> tempProfiles;

        for (Triple<String, String, String> searchValue : searchValues) {
            if (returnProfiles == null) {
                returnProfiles = get(searchValue);
            } else {
                tempProfiles = get(searchValue);
                returnProfiles.retainAll(tempProfiles);
            }
        }

        return returnProfiles;
    }

    private Set<Profile> get(Triple<String, String, String> searchValue) {
        return get(searchValue.getFirst(), searchValue.getSecond(), searchValue.getThird());
    }

    /**
     * Creates a new profile with given key, saves it in key structure and returns uuid.
     *
     * @param profileData key of new profile
     * @return uuid of new profile
     */
    public UUID insert(Map<String, String> profileData) {
        // null check
        if (profileData == null) {
            throw new ProfileDataNullPointerException();
        }

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
     * Searching for profile with uuid and add key. If profile can not be found return false, otherwise true.
     *
     * @param uuid        uuid of profile
     * @param profileData key that should be added to profile
     * @return if profile with uuid can be found
     */
    public boolean update(UUID uuid, Map<String, String> profileData) {
        // null checks
        if (uuid == null) {
            throw new UuidNullPointerException();
        }
        else if (profileData == null) {
            throw new ProfileDataNullPointerException();
        }

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
