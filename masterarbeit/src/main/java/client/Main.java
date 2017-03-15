package client;

public class Main {

    public static void main(String[] args) {
        startScenario();
    }

    private static void startScenario() {
        int i = 2;
        ScenarioBenchmarks scenario;

        switch (i) {
            case 0:
                scenario = new ScenarioBenchmarks("DSUnsorted");
                break;
            case 1:
                scenario = new ScenarioBenchmarks("DSHashMap");
                break;
            case 2:
                scenario = new ScenarioBenchmarks("DSSortedArray");
                break;
            default:
                scenario = new ScenarioBenchmarks("DSUnsorted");
                break;
        }
        scenario.run(1000, 100);
    }

}