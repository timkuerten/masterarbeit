package client;

import datastructure.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ScenarioBenchmarks extends AbstractScenario {

    private TimeSaverManager timeSaverManager;

    /**
     * @param dataStructure what key structure is used? Needed to use key structure with this name.
     */
    public ScenarioBenchmarks(String dataStructure) {
        super();
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

        //String timeLoggerName = dataStructure + "." + TimeLogger.class.getName();
        //TimeLogger timeLogger = new TimeLogger(timeLoggerName, false);
        clientReader = new ClientReader(ds, null, timeSaverManager);
        clientWriter = new ClientWriter(ds, null, false, timeSaverManager);
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
        //getProfilesByThirdPartyID("Name", "Runfried Mühlberger", iterations);
        getProfilesByThirdPartyID("Alter", "20", iterations);
        //getProfilesByRange("Name", "Olaf John", "Stilla Gille", iterations);
        //System.out.println("--------------------------");
        //getProfilesByRange("Name", "Raa", "Rzz", iterations);
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
        //addSchema(schema, thirdPartyIDs, iterations);
        System.out.println(timeSaverManager.printOutTimeSavers());
    }

}
