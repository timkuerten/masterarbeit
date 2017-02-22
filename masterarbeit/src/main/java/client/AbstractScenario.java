package client;

import datastructure.Profile;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public abstract class AbstractScenario {
    protected Set<String> schema;
    protected Set<String> thirdPartyIDs;
    protected ClientReader clientReader;
    protected ClientWriter clientWriter;
    private Generator generator;

    public AbstractScenario() {
        thirdPartyIDs = new HashSet<>();
        generator = new Generator(Long.MAX_VALUE);
        schema = new HashSet<>();
    }

    public abstract void run();

    public void addProfiles(int count) {
        for (int i = 0; i < count; i++) {
            addOneProfile();
        }
    }

    public UUID addOneProfile() {
        return clientWriter.insertProfile(generator.generateNewProfileData());
    }

    public Profile getOneProfileByUuid(UUID uuid) {
        return clientReader.get(uuid);
    }

    public Set<Profile> getProfilesByThirdPartyID(String thirdPartyId, String value) {
        return clientReader.get(thirdPartyId, value);
    }
}
