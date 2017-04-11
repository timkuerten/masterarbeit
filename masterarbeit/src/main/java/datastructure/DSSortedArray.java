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
     * Constructor. Give a schema and third-party-IDs to key structure.
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
            left = indexOf(values, minValue);
            if (values.get(left).getKey().compareTo(minValue) < 0) {
                left = left + 1;
            }
        }
        if (maxValue != null) {
            right = indexOf(values, maxValue, left, values.size() - 1);
        }

        for (int i = left; i <= right; i++) {
            returnProfiles.addAll(values.get(i).getValue());
            //values.get(i).getValue().forEach(p -> System.out.println(p.getProfileData().get("Name")));
        }

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
            // add profile to thirdPartyIDs
            addProfileToThirdPartyIDsMap(newProfile);
            addProfileToThirdPartyIDsMap(newProfile);
            return newProfile.uuid;
        } else {
            return null;
        }
    }

    /**
     * Searching for profile with uuid and add key to it. If profile can not be found return false, otherwise true.
     *
     * @param uuid        uuid of profile
     * @param profileData key of profile
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
            int index = indexOf(values, value);
            if (values.get(index).getKey().compareTo(value) < 0) {
                index = index + 1;
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

    private static int indexOf(ArrayList<Pair<String, Set<Profile>>> a, String key) {
        return indexOf(a, key, 0, a.size() - 1);
    }

    private static int indexOf(ArrayList<Pair<String, Set<Profile>>> a, String key, int left, int right) {
        int lo = left;
        int hi = right;
        int mid = 0;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            mid = lo + (hi - lo) / 2;
            if (key.compareTo(a.get(mid).getKey()) < 0) {
                hi = mid - 1;
            } else if (key.compareTo(a.get(mid).getKey()) > 0) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return mid;
    }

}
