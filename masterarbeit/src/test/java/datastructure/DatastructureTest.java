package datastructure;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class DatastructureTest {

    Datastructure ds;
    Profile p1, p2, p3;
    UUID uuid1, uuid2, uuid3;

    @Before
    public void setUp() throws Exception {
        //create schema
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Adresse", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Stadt");

        //create ds
        ds = new DSUnsorted(); //DSHashMap();
        ds.init(schema, thirdPartyIDs);

        //create profiles and add them
        uuid1 = UUID.randomUUID();
        uuid2 = UUID.randomUUID();
        uuid3 = UUID.randomUUID();
        p1 = new Profile(uuid1);
        p1.profileData.put("Name", "Tim");
        p1.profileData.put("Adresse", "Bruchfeldweg 18");
        p1.profileData.put("Stadt", "MS");
        ds.insert(p1);
        p2 = new Profile(uuid2);
        p2.profileData.put("Name", "Karl");
        p2.profileData.put("Adresse", "Buchenweg 3");
        p2.profileData.put("Stadt", "MS");
        ds.insert(p2);
        p3 = new Profile(uuid3);
        p3.profileData.put("Name", "Susi");
        p3.profileData.put("Adresse", "Finkenweg 30");
        p3.profileData.put("Stadt", "Hamburg");
        ds.insert(p3);
    }

    @Test
    public void getProfileByID() {
        assertThat(ds.get(uuid1), is(p1));
    }

    @Test
    public void getProfileByThirdPartyID() {
        Set<Profile> lProfiles = new HashSet<>();
        lProfiles.add(p1);
        lProfiles.add(p2);
        assertThat(ds.get("Stadt", "MS").equals(lProfiles), is(true));
    }

    @Test
    public void getProfileByNonThirdPartyID() {
        assertThat(ds.get("Name", "Tim"), is(nullValue(null)));
    }

    @Test
    public void insertProfile() {
        UUID uuid = UUID.randomUUID();
        Profile p = new Profile(uuid);
        p.profileData.put("Name", "Max");
        p.profileData.put("Adresse", "Blubbweg 3");
        p.profileData.put("Stadt", "Düsseldorf");
        assertThat(ds.insert(p), is(uuid));
    }

    @Test
    public void insertProfileWithWrongSchema() {

        UUID uuid = UUID.randomUUID();
        Profile p = new Profile(uuid);
        p.profileData.put("Name", "Max");
        p.profileData.put("Adresse", "Blubbweg 3");
        p.profileData.put("Stadt", "Düsseldorf");
        p.profileData.put("Land", "Deutschland");
        assertThat(ds.insert(p), is(nullValue()));
    }

    @Test
    public void updateProfile() {
        HashMap<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Tom");
        ds.update(p1.uuid, profileData);
        assertThat(ds.get(uuid1).profileData.get("Name"), is("Tom"));
    }

    @Test
    public void changeSchema() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        ds.changeSchema(schema, thirdPartyIDs);
        assertThat(ds.get("Name", "Tim"), hasItem(p1));
    }

    @Test
    public void changeSchemaAndGet() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        ds.changeSchema(schema, thirdPartyIDs);
        assertThat(ds.get("Name", "Tim"), hasItem(p1));
    }

    @Test
    public void changeToNotAllowedSchema() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Adresse", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Stadt");
        assertThat(ds.changeSchema(schema, thirdPartyIDs), is(false));
    }

    @Test
    public void changeSchemaAndUseOldThirdPartyID() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        ds.changeSchema(schema, thirdPartyIDs);
        assertThat(ds.get("Stadt", "MS"), is(nullValue(null)));
    }

    @Test
    public void updateProfileAndChangeSchema() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        ds.changeSchema(schema, thirdPartyIDs);
        assertThat(ds.get("Stadt", "MS"), is(nullValue(null)));
    }

}
