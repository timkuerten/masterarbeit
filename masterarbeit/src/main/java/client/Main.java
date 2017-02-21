package client;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        startScenario();
    }

    private static void startScenario() {
        Scenario scenario = new Scenario("DSUnsorted");
        //Scenario scenario = new Scenario("DSHashMap");
        instructions(scenario);
    }

    public static void instructions(Scenario scenario) {
        scenario.addProfiles(999);
        UUID uuid = scenario.addOneProfile();
        scenario.getOneProfileByUuid(uuid);
        scenario.getProfilesByThirdPartyID("Alter", "50");
    }

}