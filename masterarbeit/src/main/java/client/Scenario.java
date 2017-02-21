package client;

import datastructure.DSHashMap;
import datastructure.DSUnsorted;
import datastructure.DataStructure;
import datastructure.Profile;

import java.util.*;

public class Scenario {

    private Set<String> schema;
    private Set<String> thirdPartyIDs;
    private ClientReader clientReader;
    private ClientWriter clientWriter;
    private Generator generator;

    public Scenario(String dataStructure) {
        //create schema
        schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Geschlecht", "Stadt", "Straße", "Hausnummer", "Alter"));
        thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Alter");
        DataStructure ds;
        if (dataStructure.equals("DSUnsorted")) {
            ds = new DSUnsorted(schema, thirdPartyIDs);
        } else {
            ds = new DSHashMap(schema, thirdPartyIDs);
        }
        String timeLoggerName = dataStructure + "." + TimeLogger.class.getName();
        TimeLogger timeLogger = new TimeLogger(timeLoggerName, false);
        clientReader = new ClientReader(ds, timeLogger);
        clientWriter = new ClientWriter(ds, timeLogger, false);
        generator = new Generator(Long.MAX_VALUE);
    }

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
