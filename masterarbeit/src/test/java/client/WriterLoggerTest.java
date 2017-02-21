package client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class WriterLoggerTest {

    private WriterLogger logger;

    @Before
    public void setUp() {
        logger = new WriterLogger("WriterLoggerTest.log", true);
    }

    @Test
    public void insertProfile() {
        UUID uuid = UUID.randomUUID();
        Map<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Tim");
        profileData.put("Adresse", "Bruchfeldweg 18");
        profileData.put("Stadt", "MS");
        logger.insertProfile(uuid, profileData);
    }

    @Test
    public void updateProfile() {
        UUID uuid = UUID.randomUUID();
        Map<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Hans");
        profileData.put("Adresse", "Wilhelmstrasse 42");
        profileData.put("Stadt", "MS");
        logger.updateProfile(uuid, profileData);
    }

    @Test
    public void changeSchema() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Adresse", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Stadt");
        logger.changeSchema(schema, thirdPartyIDs);
    }

    @After
    public void closeLogger() {
        logger.close();
    }

}
