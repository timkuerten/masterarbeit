package datastructure;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class DataStructureTest {

    DataStructure ds;
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
        Map<String, String> profileData1 = new HashMap<>();
        profileData1.put("Name", "Tim");
        profileData1.put("Adresse", "Bruchfeldweg 18");
        profileData1.put("Stadt", "MS");
        uuid1 = ds.insert(profileData1);
        Map<String, String> profileData2 = new HashMap<>();
        profileData2.put("Name", "Karl");
        profileData2.put("Adresse", "Buchenweg 3");
        profileData2.put("Stadt", "MS");
        uuid2 = ds.insert(profileData2);
        Map<String, String> profileData3 = new HashMap<>();
        profileData3.put("Name", "Susi");
        profileData3.put("Adresse", "Finkenweg 30");
        profileData3.put("Stadt", "Hamburg");
        uuid3 = ds.insert(profileData3);
    }

    @Test
    public void getProfileByID() {
        assertThat(ds.get(uuid1).getProfileData().get("Name"), is("Tim"));
    }

    @Test
    public void getProfileByThirdPartyID() {
        Set<UUID> lUuid = new HashSet<>();
        lUuid.add(uuid1);
        lUuid.add(uuid2);
        Set<UUID> lUuid2 = new HashSet<>();
        ds.get("Stadt", "MS").forEach(x -> lUuid2.add(x.getUuid()));
        assertThat(lUuid2.equals(lUuid), is(true));
    }

    @Test
    public void getProfileByNonThirdPartyID() {
        assertThat(ds.get("Name", "Tim"), is(nullValue(null)));
    }

    @Test
    public void insertProfile() {
        Map<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Max");
        profileData.put("Adresse", "Blubbweg 3");
        profileData.put("Stadt", "Düsseldorf");
        assertThat(ds.insert(profileData), is(notNullValue()));
    }

    @Test
    public void insertProfileWithWrongSchema() {
        Map<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Max");
        profileData.put("Adresse", "Blubbweg 3");
        profileData.put("Stadt", "Düsseldorf");
        profileData.put("Land", "Deutschland");
        assertThat(ds.insert(profileData), is(nullValue()));
    }

    @Test
    public void updateProfile() {
        HashMap<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Tom");
        ds.update(uuid1, profileData);
        assertThat(ds.get(uuid1).profileData.get("Name"), is("Tom"));
    }

    @Test
    public void changeSchema() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        ds.changeSchema(schema, thirdPartyIDs);
        Set<UUID> lUuid = new HashSet<>();
        ds.get("Name", "Tim").forEach(x -> lUuid.add(x.getUuid()));
        assertThat(lUuid, hasItem(uuid1));
    }

    @Test
    public void changeSchemaAndGet() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        ds.changeSchema(schema, thirdPartyIDs);
        Set<UUID> lUuid = new HashSet<>();
        ds.get("Name", "Tim").forEach(x -> lUuid.add(x.getUuid()));
        assertThat(lUuid, hasItem(uuid1));
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
