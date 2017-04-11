package client.Output;

import client.SimpleLogger;

import static client.Constants.DS;

public class OutputBenchmarksInLog extends OutputBenchmarksInTerminal {

    private SimpleLogger simpleLogger;

    @Override
    public void start() {
        simpleLogger = new SimpleLogger(DS, false);
    }

    @Override
    public void close() {
        simpleLogger.log(timeSaverManager.printOutTimeSavers());
        simpleLogger.close();
    }

}
