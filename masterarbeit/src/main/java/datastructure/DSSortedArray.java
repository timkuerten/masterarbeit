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
    private Map<String, Map<String, Set<Profile>>> thirdPartyIDs = new HashMap<>();
    private Map<String, List<Pair<String, Set<Profile>>>> thirdPartyIDs2 = new HashMap<>();

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
        }
        else if (value == null) {
            throw new ValueNullPointerException();
        }

        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return null;
        } else if ((thirdPartyIDs.get(thirdPartyID) != null) && thirdPartyIDs.get(thirdPartyID).get(value) != null) {
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
        else if (minValue != null && maxValue != null && minValue.compareTo(maxValue) > 0) {
            throw new MinMaxValueException(minValue, maxValue);
        }

        if (!this.schema.getThirdPartyIDs().contains(thirdPartyID)) {
            return Collections.emptySet();
        } else if (thirdPartyIDs.get(thirdPartyID) != null) {
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
        } else {
            return new HashSet<>();
        }
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
            addProfileToThirtPartyIDs(newProfile);
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
        // null checks
        if (uuid == null) {
            throw new UuidNullPointerException();
        }
        else if (profileData == null) {
            throw new ProfileDataNullPointerException();
        }

        if (profiles.get(uuid) != null && this.schema.getSchema().containsAll(profileData.keySet())) {
            profiles.get(uuid).profileData.putAll(profileData);
            addProfileToThirtPartyIDs(profiles.get(uuid));
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
            this.thirdPartyIDs.clear();
            profiles.forEach((u, p) ->
                    addProfileToThirtPartyIDs(p)
            );
            return true;
        } else {
            return false;
        }
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
    private void addProfileToThirtPartyIDs(Profile p) {
        //
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
                        kList.get(v).add(p);
                    }
                }
            }
        });
    }

}
