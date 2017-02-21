package datastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ProfileTest {

    private Profile p1, p2, p3;
    private UUID uuid1, uuid2, uuid3;

    @Before
    public void setUp() throws Exception {
        //create schema
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Adresse", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Stadt");

        //create profiles and add them
        uuid1 = UUID.randomUUID();
        uuid2 = UUID.randomUUID();
        uuid3 = UUID.randomUUID();
        p1 = new Profile(uuid1);
        p1.profileData.put("Name", "Tim");
        p1.profileData.put("Adresse", "Bruchfeldweg 18");
        p1.profileData.put("Stadt", "MS");
        p2 = new Profile(uuid2);
        p2.profileData.put("Name", "Karl");
        p2.profileData.put("Adresse", "Buchenweg 3");
        p2.profileData.put("Stadt", "MS");
        p3 = new Profile(uuid3);
        p2.profileData.put("Name", "Susi");
        p2.profileData.put("Adresse", "Finkenweg 30");
        p2.profileData.put("Stadt", "Hamburg");
    }

    @Test
    public void testToString() {
        assertThat(p1.uuid, is(uuid1));
    }

}
