package client;

import datastructure.DSHashMap;
import datastructure.DSSortedArray;
import datastructure.DSUnsorted;
import datastructure.DataStructure;

import java.util.*;

public class ScenarioTest extends AbstractScenario {

    /**
     * @param dataStructure what data structure is used? Needed to use data structure with this name.
     */
    public ScenarioTest(String dataStructure) {
        super();
        //create schema
        schema.addAll(Arrays.asList("Name", "Geschlecht", "Stadt", "Straße", "Hausnummer", "Alter"));
        thirdPartyIDs.add("Name");
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
            default:
                ds = new DSUnsorted(schema, thirdPartyIDs);
                break;
        }

        String timeLoggerName = dataStructure + "." + TimeLogger.class.getName();
        TimeLogger timeLogger = new TimeLogger(timeLoggerName, false);
        clientReader = new ClientReader(ds, timeLogger);
        clientWriter = new ClientWriter(ds, timeLogger, false);
    }

    /**
     * Add 10000 profiles to data structure, get a profile by its UUID and get profiles by third-party-ID
     */
    @Override
    public void run() {
        addProfiles(9999);
        UUID uuid = addOneProfile();
        getOneProfileByUuid(uuid);
        getProfilesByThirdPartyID("Name", "Tim");
        getProfilesByRange("Name", "Ralf", "Roland");
    }

}
