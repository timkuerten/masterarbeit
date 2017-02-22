package client;

import datastructure.DSHashMap;
import datastructure.DSUnsorted;
import datastructure.DataStructure;

import java.util.*;

public class ScenarioTest extends AbstractScenario {

    /**
     *
     *
     * @param dataStructure what data structure is used? Needed to use data structure with this name.
     */
    public ScenarioTest(String dataStructure) {
        super();
        //create schema
        schema.addAll(Arrays.asList("Name", "Geschlecht", "Stadt", "Straße", "Hausnummer", "Alter"));
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
    }

    /**
     * Add 100 profiles to data structure, get a profile by its UUID and get profiles by third-party-ID
     */
    @Override
    public void run() {
        addProfiles(999);
        UUID uuid = addOneProfile();
        getOneProfileByUuid(uuid);
        getProfilesByThirdPartyID("Alter", "50");
    }

}
