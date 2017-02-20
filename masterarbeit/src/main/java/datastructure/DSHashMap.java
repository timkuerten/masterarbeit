package datastructure;

import exception.SchemaException;

import java.util.*;

/**
 * Class to save and manage profiles in two HashMaps. In HashMap profiles every profile (value) is mapped to a uuid (key).
 * The HashMap thirdPartiIDs exits to search faster for third-party-IDs. Therefore the keys of thirdPartiIDs are third-party-IDs
 * which are mapped to a Hashmap. This HashMap contains every value of third-party-IDs as keys and map them to a Set of
 * profiles which have given third-party-ID and value.
 */
public class DSHashMap implements DataStructure {

    Schema schema;
    Map<UUID, Profile> profiles = new HashMap<>();
    Map<String, Map<String, Set<Profile>>> thirdPartyIDs = new HashMap<>();

    public DSHashMap(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaException {
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
    public void init(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaException {
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
        } else if ((thirdPartyIDs.get(ThirdPartyID) != null) && thirdPartyIDs.get(ThirdPartyID).get(value) != null) {
            Set<Profile> returnProfiles = new HashSet<>();
            //search for profiles
            this.thirdPartyIDs.get(ThirdPartyID).get(value).forEach(profile -> {
                returnProfiles.add(profile);
            });
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
        Profile newProfile = new Profile(UUID.randomUUID(), new HashMap<>(profileData));
        if (newProfile.correspondToSchema(schema.getSchema())) {
            profiles.put(newProfile.uuid, newProfile);
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
        if (this.schema.update(schema, thirdPartyIDs)) {
            //update profileData of every profile that they correlate to new schema
            profiles.values().forEach(profile ->
                    profile.update(this.schema)
            );
            //update HashMap thirdPartyIDs
            this.thirdPartyIDs.clear();
            profiles.forEach((u, p) ->
                    addProfileToThirtPartyIDs(p)
            );
            return true;
        } else {
            return false;
        }
    }

    /**
     * Adds profile to thirdPartyIDs. Therefore every third-party-ID in profileData of profile will be add to thirdPartyIDs,
     * and gets a HashMap, that map value of third-party-ID to a Set of profiles, as value
     *
     * @param p profile p which should be add to thirdPartyIDs
     */
    public void addProfileToThirtPartyIDs(Profile p) {
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
