package client;

public class Main {

    public static void main(String[] args) {
        startScenario();
    }

    private static void startScenario() {
        ScenarioBenchmarks scenario = new ScenarioBenchmarks();
        scenario.run(100000, 100);
        scenario.close();
    }

}