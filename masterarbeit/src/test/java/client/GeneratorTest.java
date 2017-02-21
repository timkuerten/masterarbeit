package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import datastructure.Profile;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class GeneratorTest {

    private Generator generator;

    @Before
    public void setUp() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("database.yaml").getFile());
        try {
            Database database = mapper.readValue(file, Database.class);
            generator = new Generator(database, Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void createOneProfile() {
        Profile profile = generator.generateNewProfile();
        assertThat(profile.getProfileData().containsKey("Name"), is(true));
    }

    @Test
    public void createOneProfile2() {
        //<Profile d1fc27e0-418c-4826-8bc4-046e7089c6bb, profileData: {Alter=50, Stadt=31073 Delligsen, Geschlecht=männlich, Straße=Rothofleite, Hausnummer=955, Name=Runfried Mühlberger}>
        //System.out.println(generator.generateNewProfile());
        assertThat(generator.generateNewProfile().getProfileData().toString(),
                is("{Alter=50, Stadt=31073 Delligsen, Geschlecht=männlich, Straße=Rothofleite, Hausnummer=955, Name=Runfried Mühlberger}"));
    }

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