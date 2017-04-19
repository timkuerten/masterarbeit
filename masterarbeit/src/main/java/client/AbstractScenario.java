package client;

import client.Input.Input;
import client.Output.Output;
import client.Output.OutputReadAccesses;
import client.Output.OutputWriteAccesses;
import datastructure.Profile;
import datastructure.Schema;

import java.util.*;

/**
 * Abstract class to create a scenario which used key structure
 */
public abstract class AbstractScenario {
    protected Set<String> schema;
    protected Set<String> thirdPartyIDs;
    private Set<UUID> uuids;
    protected ClientReader clientReader;
    protected ClientWriter clientWriter;
    private Input input;
    protected OutputReadAccesses outputReadAccesses;
    protected OutputWriteAccesses outputWriteAccesses;

    public AbstractScenario(Input input, Output output) {
        if (input == null) {
            throw new NullPointerException("Input can not be null");
        }

        if (output == null) {
            throw new NullPointerException("Output can not be null");
        }

        thirdPartyIDs = new HashSet<>();
        schema = new HashSet<>();
        uuids = new HashSet<>();
        this.input = input;
        this.outputReadAccesses = output;
        this.outputWriteAccesses = output;
        start();
    }

    private void start() {
        outputReadAccesses.start();
        if (outputReadAccesses != outputWriteAccesses) {
            outputWriteAccesses.start();
        }
    }

    /**
     * Creates thirdPartyIDs, generatorOld and schema
     */
    public AbstractScenario(Input input, OutputReadAccesses outputReadAccesses, OutputWriteAccesses outputWriteAccesses) {
        if (input == null) {
            throw new NullPointerException("Input can not be null");
        }

        if (outputReadAccesses == null) {
            throw new NullPointerException("OutputReadAccesses can not be null");
        }

        if (outputWriteAccesses == null) {
            throw new NullPointerException("OutputWriteAccesses can not be null");
        }

        thirdPartyIDs = new HashSet<>();
        schema = new HashSet<>();
        uuids = new HashSet<>();
        this.input = input;
        this.outputReadAccesses = outputReadAccesses;
        this.outputWriteAccesses = outputWriteAccesses;
        start();
    }

    /**
     * Do something with key structure
     */
    public abstract void run();

    /**
     * Do something with key structure
     */
    public abstract void run(int profiles, int iterations);

    /**
     * Searches for profile with given uuid in key structure
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
     * Add one profile to key structure
     *
     * @return uuid of new profile in key structure
     */
    public UUID insertProfile() {
        return clientWriter.insertProfile(input.getNewValuesToSchema(schema));
    }

    /**
     * Adds count numbers of profiles to key structure
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

    public void close() {
        outputReadAccesses.close();
        if (outputReadAccesses != outputWriteAccesses) {
            outputWriteAccesses.close();
        }
    }

}
