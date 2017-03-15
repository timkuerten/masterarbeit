package datastructure;

import exception.*;
import javafx.util.Pair;

import java.util.*;

/**
 * Class to save and manage profiles in two HashMaps. In HashMap profiles every profile (value) is mapped to a uuid (key).
 * The HashMap thirdPartyIDs exits to search faster for third-party-IDs. Therefore the keys of thirdPartiIDs are third-party-IDs
 * which are mapped to a HashMap. This HashMap contains every value of third-party-IDs as keys and map them to a Set of
 * profiles which have given third-party-ID and value.
 */
public class DSSortedArray implements DataStructure {

    private Schema schema;
    private Map<UUID, Profile> profiles = new HashMap<>();
    private Map<String, Map<String, Pair<String, Set<Profile>>>> thirdPartyIDsMap = new HashMap<>();
    private Map<String, ArrayList<Pair<String, Set<Profile>>>> thirdPartyIDsArray = new HashMap<>();

    /**
     * Constructor. Give a schema and third-party-IDs to data structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws SchemaNotAllowedException throws exception if third-party-IDs are not contained in schema
     */
    public DSSortedArray(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaNotAllowedException {
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
        } else if (value == null) {
            throw new ValueNullPointerException();
        }

        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return null;
        } else if ((thirdPartyIDsMap.get(thirdPartyID) != null)
                && thirdPartyIDsMap.get(thirdPartyID).get(value) != null) {
            Set<Profile> returnProfiles = new HashSet<>();
            // search for profiles that given ThirdPartyID is mapped to given value and add them to return value
            this.thirdPartyIDsMap.get(thirdPartyID).get(value).getValue().forEach(returnProfiles::add);
            return returnProfiles;
        } else {
            return Collections.emptySet();
        }
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

        ArrayList<Pair<String, Set<Profile>>> values = this.thirdPartyIDsArray.get(thirdPartyID);

        if (values == null || values.size() == 0) {
            return Collections.emptySet();
        }
        Set<Profile> returnProfiles = new HashSet<>();

        int arraySize = values.size();
        int left = 0;
        int right = arraySize - 1;

        if (minValue != null) {
            int lLeft = 0;
            int rLeft = arraySize - 1;
            while (rLeft - lLeft > 1) {
                left = (lLeft + rLeft) / 2;
                if (values.get(left).getKey().compareTo(minValue) > 0) {
                    rLeft = left;
                } else if (values.get(left).getKey().compareTo(minValue) < 0) {
                    lLeft = left;
                } else {
                    break;
                }
                if (values.get(lLeft).getKey().compareTo(minValue) < 0) {
                    left = rLeft;
                } else {
                    left = rLeft;
                }
            }
        }

        if (maxValue != null) {
            int lRight = left;
            int rRight = arraySize - 1;
            while (rRight - lRight > 1) {
                right = (lRight + rRight) / 2;
                if (values.get(right).getKey().compareTo(maxValue) > 0) {
                    rRight = right;
                } else if (values.get(right).getKey().compareTo(maxValue) < 0) {
                    lRight = right;
                } else {
                    break;
                }
            }
            if (values.get(rRight).getKey().compareTo(maxValue) > 0) {
                right = lRight;
            } else {
                right = rRight;
            }
        }


        for (int i = left; i <= right; i++) {
            returnProfiles.addAll(values.get(i).getValue());
            //values.get(i).getValue().forEach(p -> System.out.println(p.getProfileData().get("Name")));
        }

        return returnProfiles;
    }

    /**
     * Creates a new profile with given data, saves it in data structure and returns uuid.
     *
     * @param profileData data of new profile
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
            // add profile to thirdPartyIDs
            addProfileToThirdPartyIDsMap(newProfile);
            addProfileToThirdPartyIDsMap(newProfile);
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
    public boolean update(UUID uuid, Map<String, String> profileData) {
        // null checks
        if (uuid == null) {
            throw new UuidNullPointerException();
        } else if (profileData == null) {
            throw new ProfileDataNullPointerException();
        }

        if (profiles.get(uuid) != null && this.schema.getSchema().containsAll(profileData.keySet())) {
            profiles.get(uuid).profileData.putAll(profileData);
            addProfileToThirdPartyIDsMap(profiles.get(uuid));
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
        if (this.schema.change(schema, thirdPartyIDs)) {
            // change profileData of every profile that they correlate to new schema
            profiles.values().forEach(profile ->
                    profile.update(this.schema)
            );
            // change HashMap of thirdPartyIDs
            this.thirdPartyIDsMap.clear();
            profiles.forEach((u, p) ->
                    addProfileToThirdPartyIDsMap(p)
            );
            return true;
        } else {
            return false;
        }
    }

    public boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        return this.schema.add(schema, thirdPartyIDs);
    }

    private void addProfileToThirdPartyIDsMap(Profile p) {
        p.profileData.forEach((k, v) -> {
            if (schema.getThirdPartyIDs().contains(k)) {
                Map<String, Pair<String, Set<Profile>>> kList = this.thirdPartyIDsMap.get(k);
                if (kList == null) {
                    Set<Profile> s = new HashSet<>();
                    s.add(p);
                    Pair<String, Set<Profile>> pair = createPairAndAddItToSortedArray(k, v, s);
                    Map<String, Pair<String, Set<Profile>>> m = new HashMap<>();
                    m.put(v, pair);
                    this.thirdPartyIDsMap.put(k, m);
                } else {
                    Pair<String, Set<Profile>> sList = kList.get(v);
                    if (sList == null) {
                        //create Set uuids
                        Set<Profile> s = new HashSet<>();
                        s.add(p);
                        Pair<String, Set<Profile>> pair = createPairAndAddItToSortedArray(k, v, s);
                        kList.put(v, pair);
                    } else {
                        sList.getValue().add(p);
                    }
                }
            }
        });
    }

    private Pair<String, Set<Profile>> createPairAndAddItToSortedArray(String thirdPartyId, String value,
            Set<Profile> profiles) {
        Pair<String, Set<Profile>> pair = new Pair<>(value, profiles);
        ArrayList<Pair<String, Set<Profile>>> values =
                thirdPartyIDsArray.computeIfAbsent(thirdPartyId, k -> new ArrayList<>());

        // old
        //values.add(pair);
        //values.sort(Comparator.comparing(Pair::getKey));

        if (values.isEmpty()) {
            values.add(pair);
        } else {
            int left = 0;
            int right = values.size() - 1;
            int index;
            while (right - left > 1) {
                index = (left + right) / 2;
                if (values.get(index).getKey().compareTo(value) > 0) {
                    right = index;
                } else if (values.get(index).getKey().compareTo(value) < 0) {
                    left = index;
                }
            }
            if (values.get(left).getKey().compareTo(value) > 0) {
                index = left;
            } else {
                index = left + 1;
            }

            values.add(index, pair);
        }
        /*
        System.out.println("Sortieren: ");
        values.forEach(v -> {
            System.out.println(v.getKey());
        });
        */

        return pair;
    }

}
