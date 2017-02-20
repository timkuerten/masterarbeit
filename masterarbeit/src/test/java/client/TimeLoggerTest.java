package client;

import org.junit.Before;
import org.junit.Test;

public class TimeLoggerTest {

    TimeLogger logger;

    @Before
    public void setUp() {
        logger = new TimeLogger("TimeLoggerTest");
    }

    @Test
    public void example() {
        long time = 1000;
        logger.getSchema(time);
    }

}
