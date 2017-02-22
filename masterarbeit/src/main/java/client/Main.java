package client;

public class Main {

    public static void main(String[] args) {
        startScenario();
    }

    private static void startScenario() {
        ScenarioTest scenario = new ScenarioTest("DSUnsorted");
        //ScenarioTest scenario = new ScenarioTest("DSHashMap");
        scenario.run();
    }

}