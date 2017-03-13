package datastructure;

import exception.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

abstract public class AbstractDataStructureTest {
    protected DataStructure ds;
    private UUID uuid1;
    private UUID uuid2;
    private UUID uuid3;
    protected Set<String> schema;
    protected Set<String> thirdPartyIDs;
    private Map<String, String> profileData;

    @Before
    public void setUpSchema() {
        schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Adresse", "Stadt", "Alter"));
        thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Stadt");
    }

    protected void createExampleProfilesAndAddThem() {
        Map<String, String> profileData1 = new HashMap<>();
        profileData1.put("Name", "Tim");
        profileData1.put("Adresse", "Bruchfeldweg 18");
        profileData1.put("Stadt", "MS");
        Map<String, String> profileData2 = new HashMap<>();
        profileData2.put("Name", "Karl");
        profileData2.put("Adresse", "Buchenweg 3");
        profileData2.put("Stadt", "MS");
        Map<String, String> profileData3 = new HashMap<>();
        profileData3.put("Name", "Susi");
        profileData3.put("Adresse", "Finkenweg 30");
        uuid1 = ds.insert(profileData1);
        uuid2 = ds.insert(profileData2);
        uuid3 = ds.insert(profileData3);
    }

    @Before
    public void setUpProfileData() {
        profileData = new HashMap<>();
        profileData.put("Name", "Tim");
        profileData.put("Adresse", "Bruchfeldweg 18");
        profileData.put("Stadt", "MS");
    }

    @Test
    public void getProfileByID() {
        //System.out.println("Test");
        assertThat(ds.get(uuid1).getProfileData().get("Name"), is("Tim"));
    }

    @Test
    public void getProfileByThirdPartyID() {
        Set<UUID> uuids1 = new HashSet<>();
        uuids1.add(uuid1);
        uuids1.add(uuid2);
        Set<UUID> uuids2 = new HashSet<>();
        ds.get("Stadt", "MS").forEach(x -> uuids2.add(x.getUuid()));
        assertThat(uuids2.equals(uuids1), is(true));
    }

    @Test
    public void getProfilesByRange() {
        Set<UUID> uuidSet1 = new HashSet<>();
        uuidSet1.add(uuid1);
        uuidSet1.add(uuid2);
        Set<UUID> uuidSet2 = new HashSet<>();
        ds.get("Stadt", "Aachen", "Werl").forEach(x -> uuidSet2.add(x.getUuid()));
        assertThat(uuidSet2.equals(uuidSet1), is(true));
    }

    @Test
    public void getProfilesByRangeMaxValueLargerThanMinValue() {
        thrown.expect(MinMaxValueException.class);
        ds.get("Stadt", "Werl", "Aachen");
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
    public void addSchema() {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("a", "b", "c"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        ds.addSchema(schema, thirdPartyIDs);
        assertThat(ds.getSchema().getSchema().containsAll(Arrays.asList("b", "c", "a")), is(true));
        assertThat(ds.getSchema().getThirdPartyIDs().contains("Name"), is(true));
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

    // manipulation tests
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void manipulateGetSchema() {
        Schema s1 = ds.getSchema();
        thrown.expect(UnsupportedOperationException.class);
        s1.getThirdPartyIDs().add("Name");
    }

    @Test
    public void manipulateChangeSchema() {
        schema = new HashSet<>();
        schema.addAll(Arrays.asList("a", "b"));
        thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("a");
        ds.changeSchema(schema, thirdPartyIDs);
        schema.add("c");
        thirdPartyIDs.add("b");
        Assert.assertThat(ds.getSchema().getSchema(), containsInAnyOrder("a", "b"));
        Assert.assertThat(ds.getSchema().getThirdPartyIDs(), contains("a"));
    }

    @Test
    public void manipulateGetProfileByID() {
        UUID uuid = ds.insert(profileData);
        Profile profile = ds.get(uuid);
        thrown.expect(UnsupportedOperationException.class);
        profile.getProfileData().put("a", "b");
    }

    @Test
    public void manipulateGetProfileByThirdPartyID() {
        ds.insert(profileData);
        Set<Profile> profiles = ds.get("Stadt", "MS");
        profiles.forEach(x -> {
            thrown.expect(UnsupportedOperationException.class);
            x.getProfileData().put("a", "b");
        });
    }

    @Test
    public void manipulateInsertProfile() {
        UUID uuid = ds.insert(profileData);
        //manipulate
        profileData.put("Name", "Hans");
        Assert.assertThat(ds.get(uuid).getProfileData().get("Name"), is("Tim"));
    }

    @Test
    public void manipulateSchemaAfterCreateDs() {
        schema.add("a");
        thirdPartyIDs.add("a");
        Assert.assertThat(ds.getSchema().getSchema().contains("a"), is(false));
        Assert.assertThat(ds.getSchema().getThirdPartyIDs().contains("a"), is(false));
    }

    // null variables

    @Test
    public void getProfileByIDWithNullUUID() {
        thrown.expect(UuidNullPointerException.class);
        ds.get(null);
    }

    @Test
    public void getProfileByThirdPartyIDWithNullThirdPartyID() {
        thrown.expect(ThirdPartyIDNullPointerException.class);
        ds.get(null, "MS");
    }

    @Test
    public void getProfileByThirdPartyIDWithNullValue() {
        thrown.expect(ValueNullPointerException.class);
        ds.get("Stadt", null);
    }

    @Test
    public void getProfilesByRangeWithNullThirdPartyID() {
        thrown.expect(ThirdPartyIDNullPointerException.class);
        ds.get(null, "Aachen", "Werl");
    }

    @Test
    public void getProfilesByRangeWithNullMinValue() {
        //thrown.expect(ValueNullPointerException.class);
        ds.get("Stadt", null, "Werl");
        // TODO: assertThat()
    }

    @Test
    public void getProfilesByRangeWithNullMaxValue() {
        //thrown.expect(ValueNullPointerException.class);
        ds.get("Stadt", "Aachen", null);
        // TODO: assertThat()
    }


    @Test
    public void insertProfileWithNullProfileData() {
        thrown.expect(ProfileDataNullPointerException.class);
        ds.insert(null);
    }

    @Test
    public void updateProfileWithNullUuid() {
        HashMap<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Tom");
        thrown.expect(UuidNullPointerException.class);
        ds.update(null, profileData);
    }

    @Test
    public void updateProfileWithNullProfileData() {
        thrown.expect(ProfileDataNullPointerException.class);
        ds.update(uuid1, null);
    }

    @Test
    public void addSchemaWithNullSchema() {
        Set<String> oldSchema = ds.getSchema().getSchema();
        Set<String> oldThirdPartyIDs = ds.getSchema().getThirdPartyIDs();
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        ds.addSchema(null, thirdPartyIDs);
        // add old third-party-IDs to added third-party-IDs
        thirdPartyIDs.addAll(oldThirdPartyIDs);
        assertThat(ds.getSchema().getSchema(), is(oldSchema));
        assertThat(ds.getSchema().getThirdPartyIDs(), is(thirdPartyIDs));
    }

    @Test
    public void addSchemaWithNullThirdPartyIDs() {
        Set<String> oldSchema = ds.getSchema().getSchema();
        Set<String> oldThirdPartyIDs = ds.getSchema().getThirdPartyIDs();
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("a", "b", "c"));
        ds.addSchema(schema, null);
        // add old schema to added schema
        schema.addAll(oldSchema);
        assertThat(ds.getSchema().getSchema(), is(schema));
        assertThat(ds.getSchema().getThirdPartyIDs(), is(oldThirdPartyIDs));
    }

    @Test
    public void addSchemaWithNullSchemaAndThirdPartyIDs() {
        thrown.expect(SchemaNullPointerException.class);
        ds.addSchema(null, null);
    }

    @Test
    public void changeSchemaWithNullSchema() {
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Name");
        thrown.expect(SchemaNotAllowedException.class);
        ds.changeSchema(null, thirdPartyIDs);
    }

    @Test
    public void changeSchemaWithNullThirdPartyIDs() {
        Set<String> schema = new HashSet<>();
        schema.add("Name");
        ds.changeSchema(schema, null);
        assertThat(ds.getSchema().getSchema(), is(schema));
        assertThat(ds.getSchema().getThirdPartyIDs(), is(Collections.emptySet()));
    }

    @Test
    public void changeSchemaWithNullSchemaAndThirdPartyIDs() {
        ds.changeSchema(null, null);
        assertThat(ds.getSchema().getSchema(), is(Collections.emptySet()));
        assertThat(ds.getSchema().getThirdPartyIDs(), is(Collections.emptySet()));
    }

}
