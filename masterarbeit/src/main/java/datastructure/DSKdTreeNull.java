package datastructure;

import datastructure.Trees.KdTree;
import datastructure.Trees.KdTreeRecursive;
import exception.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class to save and manage profiles in a HashMap. Every profile (value) is mapped to a uuid (key).
 */
public class DSKdTreeNull implements DataStructure {

    private Schema schema;
    private Map<UUID, Profile> profiles = new HashMap<>();
    private KdTree<String, Profile> kdProfileTree;
    private List<String> thirdPartyIDs;

    /**
     * Constructor. Give a schema and third-party-IDs to key structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws SchemaNotAllowedException throws exception if third-party-IDs are not contained in schema
     */
    public DSKdTreeNull(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaNotAllowedException {
        createKdTree(schema, thirdPartyIDs);
    }

    private void createKdTree(Set<String> schema, Set<String> thirdPartyIDs) {
        this.schema = new Schema(schema, thirdPartyIDs);
        this.thirdPartyIDs = new ArrayList<>(thirdPartyIDs);
        if (thirdPartyIDs.size() > 0) {
            kdProfileTree = new KdTreeRecursive<>(thirdPartyIDs.size());
        }
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
        } else if (value == null) {
            throw new ValueNullPointerException();
        }

        // do the third-party-IDs in schema contain the given third-party-ID?
        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID) || this.thirdPartyIDs.indexOf(thirdPartyID) < 0) {
            return null;
        }

        return kdProfileTree.get(thirdPartyIDs.indexOf(thirdPartyID), value);
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
        } else if (minValue != null && maxValue != null && minValue.compareTo(maxValue) > 0) {
            throw new RangeValueException(minValue, maxValue);
        }

        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return Collections.emptySet();
        }

        return kdProfileTree.get(thirdPartyID.indexOf(thirdPartyID), minValue, maxValue);
    }

    public Set<Profile> get(Set<Triple<String, String, String>> searchValues) {
        if (searchValues.isEmpty()) {
            return Collections.emptySet();
        }

        List<Pair<String, String>> kDSearchValues = new ArrayList<>();
        List<Triple<String, String, String>> filteredSearchValues;
        for (String thirdPartyID : thirdPartyIDs) {

            filteredSearchValues = searchValues.stream()
                    .filter((triple) -> triple.getFirst().compareTo(thirdPartyID) == 0).collect(Collectors.toList());

            if (filteredSearchValues.size() == 1) {
                Triple<String, String, String> element = filteredSearchValues.get(0);
                kDSearchValues.add(new Pair<>(element.getSecond(), element.getThird()));
            } else {
                kDSearchValues.add(new Pair<>(null, null));
            }
        }

        return kdProfileTree.get(kDSearchValues);
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
            insertInKdTree(newProfile);
            return newProfile.uuid;
        } else {
            return null;
        }
    }

    private void insertInKdTree(Profile profile) {
        if (containsAnyThirdPartyId(profile)) {
            kdProfileTree.insert(createKey(profile), profile);
        }
    }

    private boolean containsAnyThirdPartyId(Profile profile) {
        return !Collections.disjoint(profile.getProfileData().keySet(), schema.getThirdPartyIDs());
    }

    private List<String> createKey(Profile profile) {
        if (profile == null) {
            return Collections.emptyList();
        }

        List<String> key = new ArrayList<>();

        for (String thirdPartyID : thirdPartyIDs) {
            if (profile.getProfileData().containsKey(thirdPartyID)) {
                key.add(profile.getProfileData().get(thirdPartyID));
            } else {
                key.add(null);
            }
        }
        return key;
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
        } else if (profileData == null) {
            throw new ProfileDataNullPointerException();
        }

        Profile profile = profiles.get(uuid);
        if (profile != null && this.schema.getSchema().containsAll(profileData.keySet())) {
            kdProfileTree.delete(createKey(profile), profile);
            profiles.get(uuid).profileData.putAll(profileData);
            kdProfileTree.insert(createKey(profile), profile);

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
        boolean returnValue = this.schema.add(schema, thirdPartyIDs);
        if (returnValue) {

            createKdTree(this.schema.getSchema(), this.schema.getThirdPartyIDs());
            for (Map.Entry<UUID, Profile> entry : profiles.entrySet()) {
                insertInKdTree(entry.getValue());
            }
        }

        return returnValue;
    }

}
