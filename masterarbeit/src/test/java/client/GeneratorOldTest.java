package client;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GeneratorOldTest {

    private GeneratorOld generatorOld;

    @Before
    public void setUp() throws IOException {
        generatorOld = new GeneratorOld(Long.MAX_VALUE);
    }

    @Test
    public void createOneProfile() {
        Map<String, String> profile = generatorOld.generateNewProfileData();
        assertThat(profile.containsKey("Name"), is(true));
    }

    @Test
    public void createOneProfile2() {
        //<Profile d1fc27e0-418c-4826-8bc4-046e7089c6bb, profileData: {Alter=50, Stadt=31073 Delligsen, Geschlecht=männlich, Straße=Rothofleite, Hausnummer=955, Name=Runfried Mühlberger}>
        //System.out.println(generatorOld.generateNewProfileData());
        assertThat(generatorOld.generateNewProfileData().toString(),
                is("{Alter=50, Stadt=31073 Delligsen, Geschlecht=männlich, Straße=Rothofleite, Hausnummer=955, Name=Runfried Mühlberger}"));
    }

    @Ignore
    @Test
    public void test() {
        /* Total amount of free memory available to the JVM */
        System.out.println("Free memory (bytes):     " +
                Runtime.getRuntime().freeMemory());
        /* This will return Long.MAX_VALUE if there is no preset limit */
        long maxMemory = Runtime.getRuntime().maxMemory();
        /* Maximum amount of memory the JVM will attempt to use */
        System.out.println("Maximum memory (bytes): " +
                (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));
        /* Total memory currently in use by the JVM */
        System.out.println("Total memory (bytes):    " +
                Runtime.getRuntime().totalMemory());
    }

}
