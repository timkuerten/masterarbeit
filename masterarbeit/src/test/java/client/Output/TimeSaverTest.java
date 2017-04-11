package client.Output;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeSaverTest {

    private TimeSaver timeSaver;

    @Before
    public void setUp() {
        timeSaver = new TimeSaver();
        for (int i = 0; i < 101; i++) {
            timeSaver.addTime(i);
        }
    }

    @Test
    public void getMinimalTime() {
        assertThat(timeSaver.getMin(), is(0L));
    }

    @Test
    public void getMaximalTime() {
        assertThat(timeSaver.getMax(), is(100L));
    }

    @Test
    public void getAverageTime() {
        assertThat(timeSaver.getAverage(), is(50L));
    }

    @Test
    public void getSize() {
        assertThat(timeSaver.getNumberOfTimes(), is(101));
    }

    @Test
    public void getMinimalTimeAsString() {
        assertThat(timeSaver.getMinAsString(), is("0"));
    }

    @Test
    public void getMaximalTimeAsString() {
        assertThat(timeSaver.getMaxAsString(), is("100"));
    }

    @Test
    public void getAverageTimeAsString() {
        assertThat(timeSaver.getAverageAsString(), is("50"));
    }
}
