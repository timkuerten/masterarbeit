package datastructure;

import java.util.*;

/**
 * Class to save and manage profiles.
 */
public class DSHashMap implements DataStructure {

    Schema schema;
    Map<UUID, Profile> profiles = new HashMap<>();
    Map<String, Map<String, Set<UUID>>> thirdPartiIDs = new HashMap<>();

    /**
     * Should be used after construction. Give a schema and third-party-IDs to data structure.
     * It throws an exception if third-party-IDs are not contained in schema.
     *
     * @param schema        schema for profiles
     * @param thirdPartyIDs third-party-IDs of schema
     * @throws Exception throws exception if third-party-IDs are not contained in schema
     */
    public void init(Set<String> schema, Set<String> thirdPartyIDs) throws Exception {
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
        } else if (thirdPartiIDs.get(ThirdPartyID).get(value) != null) {
            Set<Profile> lProfiles = new HashSet<>();
            this.thirdPartiIDs.get(ThirdPartyID).get(value).forEach(uuid -> {
                lProfiles.add(profiles.get(uuid));
            });
            return lProfiles;
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
        Profile p = new Profile(UUID.randomUUID());
        p.profileData.putAll(profileData);
        if (p.correspondToSchema(schema.getSchema())) {
            this.profiles.put(p.uuid, p);
            addProfileToThirtPartyIDs(p);
            return p.uuid;
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

    private void setSchema(Schema schema) {
        this.schema = schema;
        this.schema.getThirdPartyIDs().forEach(t -> {
            Map<String, Set<UUID>> m = new HashMap<String, Set<UUID>>();
            thirdPartiIDs.clear();
            thirdPartiIDs.put(t, m);
        });
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
            profiles.values().forEach(profile ->
                    profile.update(this.schema)
            );
            setSchema(this.schema);
            profiles.forEach((u, p) -> {
                addProfileToThirtPartyIDs(p);
            });
            return true;
        } else {
            return false;
        }
    }

    public void addProfileToThirtPartyIDs(Profile p) {
        p.profileData.forEach((k, v) -> {
            Map<String, Set<UUID>> kList = this.thirdPartiIDs.get(k);
            if (kList == null) {
                Set<UUID> s = new HashSet<>();
                Map<String, Set<UUID>> m = new HashMap<String, Set<UUID>>();
                s.add(p.uuid);
                m.put(v, s);
                this.thirdPartiIDs.put(k, m);
            } else {
                Set<UUID> sList = kList.get(v);
                if (sList == null) {
                    Set<UUID> s = new HashSet<>();
                    s.add(p.uuid);
                    kList.put(v, s);
                } else {
                    kList.get(v).add(p.uuid);
                }
            }
        });
    }

}
