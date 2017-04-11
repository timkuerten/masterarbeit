package client;

import client.Input.Generator;
import client.Output.OutputBenchmarksInLog;
import datastructure.*;

import java.util.*;

import static client.Constants.DS;

public class ScenarioBenchmarks extends AbstractScenario {

    public ScenarioBenchmarks() {
        super(new Generator(), new OutputBenchmarksInLog());

        //create schema
        schema.addAll(Arrays.asList("Name", "Geschlecht", "Stadt", "Straße", "Hausnummer", "Alter"));
        thirdPartyIDs.add("Alter");
        DataStructure ds;
        switch (DS) {
            case "DSUnsorted":
                ds = new DSUnsorted(schema, thirdPartyIDs);
                break;
            case "DSHashMap":
                ds = new DSHashMap(schema, thirdPartyIDs);
                break;
            case "DSSortedArray":
                ds = new DSSortedArray(schema, thirdPartyIDs);
                break;
            case "DSKdTree":
                ds = new DSKdTree(schema, thirdPartyIDs);
                break;
            default:
                throw new RuntimeException("datastructure '" + DS + "' unknown");
        }

        clientReader = new ClientReader(ds, outputReadAccesses);
        clientWriter = new ClientWriter(ds, outputWriteAccesses);
    }

    /**
     * Add 10000 profiles to key structure, get a profile by its UUID and get profiles by third-party-ID
     */
    @Override
    public void run() {
        insertProfiles(1000);
        UUID uuid = insertProfile();
        getProfileByUuid(uuid);
        getProfilesByThirdPartyID("Name", "Runfried Mühlberger");
        getProfilesByRange("Name", "Ralf", "Roland");

    }

    /**
     *
     */
    @Override
    public void run(int profiles, int iterations) {
        List<UUID> uuids = new ArrayList<>(insertProfiles(profiles));
        getProfileByUuid(uuids, iterations);
        getProfilesByThirdPartyID("Alter", "20", iterations);
        getProfilesByRange("Alter", "20", "22", iterations);
        Map<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Ralf Schmidt");
        profileData.put("Straße", "Bruchfeldweg");
        profileData.put("Stadt", "48161 Münster");
        updateProfile(uuids.get(0), profileData, iterations);
        getSchema(iterations);
        schema = new HashSet<>();
        schema.addAll(Arrays.asList("a", "b", "c", "d"));
        thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("b");
        addSchema(schema, thirdPartyIDs, iterations);
    }

}
