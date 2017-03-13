package client;

public class Main {

    public static void main(String[] args) {
        startScenario();
    }

    private static void startScenario() {
        int i = 1;
        ScenarioTest scenario;

        switch (i) {
            case 0:
                scenario = new ScenarioTest("DSUnsorted");
                break;
            case 1:
                scenario = new ScenarioTest("DSHashMap");
                break;
            case 2:
                scenario = new ScenarioTest("DSSortedArray");
                break;
            default:
                scenario = new ScenarioTest("DSUnsorted");
                break;
        }
        scenario.run(100000, 100);
    }

}