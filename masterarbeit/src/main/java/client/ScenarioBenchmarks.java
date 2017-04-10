package client;

import client.Input.Generator;
import client.Output.OutputTerminal;
import datastructure.*;

import java.util.*;

public class ScenarioBenchmarks extends AbstractScenario {

    private TimeSaverManager timeSaverManager;

    /**
     * @param dataStructure what key structure is used? Needed to use key structure with this name.
     */
    public ScenarioBenchmarks(String dataStructure) {
        super(new Generator(), new OutputTerminal());

        if (dataStructure == null) {
            throw new NullPointerException("dataStructure can not be null");
        }

        //create schema
        schema.addAll(Arrays.asList("Name", "Geschlecht", "Stadt", "Straße", "Hausnummer", "Alter"));
        thirdPartyIDs.add("Alter");
        DataStructure ds;
        switch (dataStructure) {
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
                ds = new DSUnsorted(schema, thirdPartyIDs);
                break;
        }

        timeSaverManager = new TimeSaverManager();

        clientReader = new ClientReader(ds, timeSaverManager, outputReadAccesses);
        clientWriter = new ClientWriter(ds, timeSaverManager, outputWriteAccesses);
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
        System.out.println(timeSaverManager.printOutTimeSavers());
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
        //System.out.println(timeSaverManager.printOutTimeSavers());
    }

}
