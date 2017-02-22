package client;

import datastructure.Profile;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
     * Adds count numbers of profiles to data structure
     */
    public void addProfiles(int count) {
        for (int i = 0; i < count; i++) {
            addOneProfile();
        }
    }

    /**
     * Add one profile to data structure
     *
     * @return uuid of new profile in data structure
     */
    public UUID addOneProfile() {
        return clientWriter.insertProfile(generator.generateNewProfileData());
    }

    /**
     * Searches for profile with given uuid in data structure
     *
     * @param uuid uuid of profile
     * @return profile with given uuid
     */
    public Profile getOneProfileByUuid(UUID uuid) {
        return clientReader.get(uuid);
    }

    /**
     * Searches for profiles with given third-party-ID and value
     *
     * @param thirdPartyId third-party-ID of profiles
     * @param value value which thirdPartyId is mapped to
     * @return profiles that have given third-party-ID and value
     */
    public Set<Profile> getProfilesByThirdPartyID(String thirdPartyId, String value) {
        return clientReader.get(thirdPartyId, value);
    }
}
