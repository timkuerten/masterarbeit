package client;

import datastructure.Profile;
import datastructure.Schema;

import java.util.*;

/**
 * Abstract class to create a scenario which used data structure
 */
public abstract class AbstractScenario {
    protected Set<String> schema;
    protected Set<String> thirdPartyIDs;
    protected ClientReader clientReader;
    protected ClientWriter clientWriter;
    private Generator generator;

    /**
     * Creates thirdPartyIDs, generator and schema
     */
    public AbstractScenario() {
        thirdPartyIDs = new HashSet<>();
        generator = new Generator(Long.MAX_VALUE);
        schema = new HashSet<>();
    }

    /**
     * Do something with data structure
     */
    public abstract void run();

    /**
     * Do something with data structure
     */
    public abstract void run(int profiles, int iterations);

    /**
     * Searches for profile with given uuid in data structure
     *
     * @param uuid uuid of profile
     * @return profile with given uuid
     */
    public Profile getProfileByUuid(UUID uuid) {
        return clientReader.get(uuid);
    }

    public Set<Profile> getProfileByUuid(List<UUID> uuids, int counter) {
        Random random = new Random(Long.MAX_VALUE);
        Set<Profile> profiles = new HashSet<>();
        for (int i = 0; i < counter; i++) {
            profiles.add(getProfileByUuid(uuids.get(Math.abs(random.nextInt() % uuids.size()))));
        }
        return profiles;
    }

    /**
     * Searches for profiles with given third-party-ID and value
     *
     * @param thirdPartyId third-party-ID of profiles
     * @param value        value which thirdPartyId is mapped to
     * @return profiles that have given third-party-ID and value
     */
    public Set<Profile> getProfilesByThirdPartyID(String thirdPartyId, String value) {
        return clientReader.get(thirdPartyId, value);
    }

    public Set<Profile> getProfilesByThirdPartyID(String thirdPartyId, String value, int counter) {
        Set<Profile> returnValue = new HashSet<>();
        for (int i = 0; i < counter; i++) {
            returnValue = clientReader.get(thirdPartyId, value);
        }
        return returnValue;
    }

    /**
     * Searches for profiles with given third-party-ID and range
     *
     * @param thirdPartyId third-party-ID of profiles
     * @param minValue     minimal value of thirdPartyId
     * @param maxValue     maximal value of thirdPartyId
     * @return profiles that have given third-party-ID and value
     */
    public Set<Profile> getProfilesByRange(String thirdPartyId, String minValue, String maxValue) {
        return clientReader.get(thirdPartyId, minValue, maxValue);
    }

    public Set<Profile> getProfilesByRange(String thirdPartyId, String minValue, String maxValue, int counter) {
        Set<Profile> returnValue = new HashSet<>();
        for (int i = 0; i < counter; i++) {
            returnValue = clientReader.get(thirdPartyId, minValue, maxValue);
        }
        return returnValue;
    }

    /**
     * Add one profile to data structure
     *
     * @return uuid of new profile in data structure
     */
    public UUID insertProfile() {
        return clientWriter.insertProfile(generator.generateNewProfileData());
    }

    /**
     * Adds count numbers of profiles to data structure
     */
    public Set<UUID> insertProfiles(int counter) {
        Set<UUID> uuids = new HashSet<>();
        for (int i = 0; i < counter; i++) {
            uuids.add(insertProfile());
        }
        return uuids;
    }

    public boolean updateProfile(UUID uuid, Map<String, String> profileData) {
        return clientWriter.updateProfile(uuid, profileData);
    }

    public boolean updateProfile(UUID uuid, Map<String, String> profileData, int counter) {
        boolean returnValue = false;
        for (int i = 0; i < counter; i++) {
            returnValue = updateProfile(uuid, profileData);
        }
        return returnValue;
    }

    public Schema getSchema() {
        return clientReader.getSchema();
    }

    public Schema getSchema(int counter) {
        Schema schema = new Schema(null, null);
        for (int i = 0; i < counter; i++) {
            schema = getSchema();
        }
        return schema;
    }

    public boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        return clientWriter.addSchema(schema, thirdPartyIDs);
    }

    public boolean addSchema(Set<String> schema, Set<String> thirdPartyIDs, int counter) {
        boolean returnValue = false;
        for (int i = 0; i < counter; i++) {
            returnValue = addSchema(schema, thirdPartyIDs);
        }
        return returnValue;
    }

    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs) {
        return clientWriter.changeSchema(schema, thirdPartyIDs);
    }

    public boolean changeSchema(Set<String> schema, Set<String> thirdPartyIDs, int counter) {
        boolean returnValue = false;
        for (int i = 0; i < counter; i++) {
            returnValue = changeSchema(schema, thirdPartyIDs);
        }
        return returnValue;
    }

}
