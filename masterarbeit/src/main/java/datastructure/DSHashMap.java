package datastructure;

import exception.*;

import java.util.*;

/**
 * Class to save and manage profiles in two HashMaps. In HashMap profiles every profile (value) is mapped to a uuid (key).
 * The HashMap thirdPartyIDs exits to search faster for third-party-IDs. Therefore the keys of thirdPartiIDs are third-party-IDs
 * which are mapped to a HashMap. This HashMap contains every value of third-party-IDs as keys and map them to a Set of
 * profiles which have given third-party-ID and value.
 */
public class DSHashMap implements DataStructure {

    private Schema schema;
    private Map<UUID, Profile> profiles = new HashMap<>();
    private Map<String, Map<String, Set<Profile>>> thirdPartyIDs = new HashMap<>();

    /**
     * Constructor. Give a schema and third-party-IDs to key structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws SchemaNotAllowedException throws exception if third-party-IDs are not contained in schema
     */
    public DSHashMap(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaNotAllowedException {
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

        if (value == null) {
            throw new ValueNullPointerException();
        }

        // do the third-party-IDs in schema contain the given third-party-ID?
        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return null;
        }

        if ((thirdPartyIDs.get(thirdPartyID) != null) && thirdPartyIDs.get(thirdPartyID).get(value) != null) {
            Set<Profile> returnProfiles = new HashSet<>();
            // search for profiles that given ThirdPartyID is mapped to given value and add them to return value
            this.thirdPartyIDs.get(thirdPartyID).get(value).forEach(returnProfiles::add);
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
        }

        if (minValue != null && maxValue != null && minValue.compareTo(maxValue) > 0) {
            throw new RangeValueException(minValue, maxValue);
        }

        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID) || thirdPartyIDs.get(thirdPartyID) == null) {
            return Collections.emptySet();
        }

        Set<Profile> returnProfiles = new HashSet<>();
        if (minValue != null && maxValue != null) {
            if (minValue.compareTo(maxValue) == 0) {
                return get(thirdPartyID, minValue);
            } else {
                this.thirdPartyIDs.get(thirdPartyID).forEach((k, v) -> {
                    if (k.compareTo(minValue) >= 0 && k.compareTo(maxValue) <= 0) {
                        returnProfiles.addAll(v);
                    }
                });
            }
        } else if (minValue != null) {
            this.thirdPartyIDs.get(thirdPartyID).forEach((k, v) -> {
                if (k.compareTo(minValue) >= 0) {
                    returnProfiles.addAll(v);
                }
            });
        } else if (maxValue != null) {
            this.thirdPartyIDs.get(thirdPartyID).forEach((k, v) -> {
                if (k.compareTo(maxValue) <= 0) {
                    returnProfiles.addAll(v);
                }
            });
        } else {
            this.thirdPartyIDs.get(thirdPartyID).forEach((k, v) -> returnProfiles.addAll(v));
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
            addProfileToThirdPartyIDs(newProfile);
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
        }

        if (profileData == null) {
            throw new ProfileDataNullPointerException();
        }

        if (profiles.get(uuid) == null || !schema.getSchema().containsAll(profileData.keySet())) {
            return false;
        }

        profiles.get(uuid).profileData.putAll(profileData);
        addProfileToThirdPartyIDs(profiles.get(uuid));
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
        return this.schema.add(schema, thirdPartyIDs);
    }

    /**
     * Adds profile to thirdPartyIDs. Therefore every third-party-ID in profileData of profile will be add to thirdPartyIDs,
     * and gets a HashMap, that map value of third-party-ID to a Set of profiles, as value
     *
     * @param p profile p which should be add to thirdPartyIDs
     */
    private void addProfileToThirdPartyIDs(Profile p) {
        p.profileData.forEach((k, v) -> {
            if (schema.getThirdPartyIDs().contains(k)) {
                Map<String, Set<Profile>> kList = this.thirdPartyIDs.get(k);
                //does the Map for third-party-id k exists in thirdPartyIDs?
                if (kList == null) {
                    //create  Map of Sets (valueOfThirdPartyID->uuids), add
                    Set<Profile> s = new HashSet<>();
                    Map<String, Set<Profile>> m = new HashMap<>();
                    s.add(p);
                    m.put(v, s);
                    this.thirdPartyIDs.put(k, m);
                } else {
                    Set<Profile> sList = kList.get(v);
                    //does an entry for value v of third-party-id k exists?
                    if (sList == null) {
                        //create Set uuids
                        Set<Profile> s = new HashSet<>();
                        s.add(p);
                        kList.put(v, s);
                    } else {
                        //add uuid of profile to third-party-IDs(k)-> value(v)-> uuids
                        sList.add(p);
                    }
                }
            }
        });
    }

}
