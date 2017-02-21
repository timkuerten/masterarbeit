package client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TimeLoggerTest {

    private String fileName;
    private TimeLogger logger;

    @Before
    public void setUp() {
        fileName = "TimeLoggerTest.log";
        boolean append = true;
        logger = new TimeLogger(fileName, append);
    }

    @Test
    public void exampleGetSchema() {
        long time = 1000;
        logger.getSchema(time);
    }

    @Test
    public void exampleGetProfileByUuid() {
        long time = 1000;
        logger.getProfileByUuid(time);
    }

    @After
    public void closeLogger() {
        logger.close();
    }

}
