package client.Output;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimeSaverManagerTest {

    private TimeSaverManager timeSaverManager;

    @Before
    public void setUp() {
        timeSaverManager = new TimeSaverManager();
        for (int i = 0; i < 101; i++) {
            timeSaverManager.getProfileByThirdPartyID(i);
        }
    }

    @Test
    public void checkGetProfileByThirdPartyID() {
        assertThat(timeSaverManager.getProfileByThirdPartyIDTimeSaver.getMinAsString(), is("0"));
        assertThat(timeSaverManager.getProfileByThirdPartyIDTimeSaver.getMaxAsString(), is("100"));
        assertThat(timeSaverManager.getProfileByThirdPartyIDTimeSaver.getAverageAsString(), is("50"));
    }

}
