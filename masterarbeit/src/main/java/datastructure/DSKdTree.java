package datastructure;

import datastructure.Trees.KdTree;
import datastructure.Trees.KdTreeRecursive;
import exception.*;

import java.util.*;

/**
 * Class to save and manage profiles in a HashMap. Every profile (value) is mapped to a uuid (key).
 */
public class DSKdTree implements DataStructure {

    private Schema schema;
    private Map<UUID, Profile> profiles = new HashMap<>();
    private Map<List<String>, KdTree<String, Profile>> kdProfileTrees = new HashMap<>();

    /**
     * Constructor. Give a schema and third-party-IDs to key structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws SchemaNotAllowedException throws exception if third-party-IDs are not contained in schema
     */
    public DSKdTree(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaNotAllowedException {
        this.schema = new Schema(schema, thirdPartyIDs);
    }

    private void createNewKdTree(List<String> coordinates) {
        if (coordinates.size() > 0) {
            KdTreeRecursive<String, Profile> newKdProfileTree = new KdTreeRecursive<>(coordinates.size());
            kdProfileTrees.put(coordinates, newKdProfileTree);
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
        }

        if (value == null) {
            throw new ValueNullPointerException();
        }

        // do the third-party-IDs in schema contain the given third-party-ID?
        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return null;
        }

        Set<Profile> returnProfiles = new HashSet<>();
        kdProfileTrees.forEach((coordinates, tree) -> {
            // search for profiles that given ThirdPartyID is mapped to given value and add them to return value
            if (coordinates.contains(thirdPartyID)) {
                returnProfiles.addAll(tree.get(coordinates.indexOf(thirdPartyID), value));
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

        if (minValue != null && maxValue != null && minValue.compareTo(maxValue) > 0) {
            throw new RangeValueException(minValue, maxValue);
        }

        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return Collections.emptySet();
        }

        Set<Profile> returnProfiles = new HashSet<>();
        kdProfileTrees.forEach((coordinates, tree) -> {
            // search for profiles that given ThirdPartyID is mapped to given value and add them to return value
            if (coordinates.contains(thirdPartyID)) {
                returnProfiles.addAll(tree.get(coordinates.indexOf(thirdPartyID), minValue, maxValue));
            }
        });

        return returnProfiles;
    }

    public Set<Profile> get(Set<Triple<String, String, String>> searchValues) {
        if (searchValues.isEmpty()) {
            return Collections.emptySet();
        }

        Set<Profile> returnProfiles = new HashSet<>();
        List<Pair<String, String>> kdSearchValues = new ArrayList<>();
        Triple<String, String, String> values;
        for (Map.Entry<List<String>, KdTree<String, Profile>> entry : kdProfileTrees.entrySet()) {
            for (String thirdPartyID : entry.getKey()) {
                values =
                        searchValues.stream().filter(t -> t.getFirst().contains(thirdPartyID)).findFirst().orElse(null);
                if (values != null) {
                    kdSearchValues.add(new Pair<>(values.getSecond(), values.getThird()));
                } else {
                    kdSearchValues.add(new Pair<>(null, null));
                }
            }

            returnProfiles.addAll(entry.getValue().get(kdSearchValues));
            kdSearchValues.clear();
        }

        return returnProfiles;
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

        if (!schema.getSchema().containsAll(profileData.keySet())) {
            return null;
        }

        Profile newProfile = new Profile(UUID.randomUUID(), new HashMap<>(profileData));
        // add new profile to profiles
        profiles.put(newProfile.uuid, newProfile);
        insertInKdTree(newProfile);
        return newProfile.uuid;
    }

    private void insertInKdTree(Profile profile) {
        List<String> treeKey = createTreeKey(profile);
        if (treeKey.size() == 0) {
            return;
        }

        List<String> insertKey = createInsertKey(treeKey, profile);
        if (!kdProfileTrees.containsKey(treeKey)) {
            kdProfileTrees.put(treeKey, new KdTreeRecursive<>(treeKey.size()));
        }

        kdProfileTrees.get(treeKey).insert(insertKey, profile);
    }

    private List<String> createInsertKey(List<String> treeKey, Profile profile) {
        List<String> insertKey = new ArrayList<>();
        for (String k : treeKey) {
            insertKey.add(profile.getProfileData().get(k));
        }

        return insertKey;
    }

    private List<String> createTreeKey(Profile profile) {
        List<String> key = new ArrayList<>(thirdPartyIDsOfProfile(profile));
        Collections.sort(key);
        return key;

    }

    private Set<String> thirdPartyIDsOfProfile(Profile profile) {
        Set<String> returnValue = new HashSet<>();
        for (String data : profile.getProfileData().keySet()) {
            if (schema.getThirdPartyIDs().contains(data)) {
                returnValue.add(data);
            }
        }

        return returnValue;
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

        if (profileData == null) {
            throw new ProfileDataNullPointerException();
        }

        Profile profile = profiles.get(uuid);
        if (profile == null || !this.schema.getSchema().containsAll(profileData.keySet())) {
            return false;
        }

        List<String> treeKey = createTreeKey(profile);
        if (!kdProfileTrees.containsKey(treeKey) || kdProfileTrees.get(treeKey) == null) {
            return false;
        }

        List<String> insertKey = createInsertKey(treeKey, profile);
        kdProfileTrees.get(treeKey).delete(insertKey, profile);
        profile.profileData.putAll(profileData);
        treeKey = createTreeKey(profile);
        insertKey = createInsertKey(treeKey, profile);
        kdProfileTrees.get(treeKey).insert(insertKey, profile);
        return true;
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
            kdProfileTrees.clear();
            for (Map.Entry<UUID, Profile> entry : profiles.entrySet()) {
                insertInKdTree(entry.getValue());
            }
        }

        return returnValue;
    }

}
