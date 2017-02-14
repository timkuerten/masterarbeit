package client;

import datastructure.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

public class DataStructureManipulationTest {

    DataStructure ds;
    Profile p;
    Set<String> schema;
    Set<String> thirdPartyIDs;
    Map<String, String> profileData;

    @Before
    public void setUp() throws Exception {
        //schema
        schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Adresse", "Stadt", "Alter"));
        thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Stadt");
        //ds
        ds = new DSUnsorted();
        ds.init(schema, thirdPartyIDs);
        //one profile
        profileData = new HashMap<>();
        profileData.put("Name", "Tim");
        profileData.put("Adresse", "Bruchfeldweg 18");
        profileData.put("Stadt", "MS");
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getSchema() {
        Schema s1 = ds.getSchema();
        thrown.expect(UnsupportedOperationException.class);
        s1.getThirdPartyIDs().add("Name");
    }

    @Test
    public void changeSchema() {
        schema = new HashSet<>();
        schema.addAll(Arrays.asList("a", "b"));
        thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("a");
        ds.changeSchema(schema, thirdPartyIDs);
        schema.add("c");
        thirdPartyIDs.add("b");
        assertThat(ds.getSchema().getSchema(), containsInAnyOrder("a", "b"));
        assertThat(ds.getSchema().getThirdPartyIDs(), contains("a"));
    }

    @Test
    public void initDsUnsorted() {
        //schema
        HashSet<String> newSchema = new HashSet<>();
        newSchema.addAll(Arrays.asList("a", "b"));
        HashSet<String> newThirdPartyIDs = new HashSet<>();
        newThirdPartyIDs.add("a");
        //ds
        DataStructure newDs = new DSUnsorted();
        try {
            newDs.init(newSchema, newThirdPartyIDs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //add items to local schema and third-party-ID
        newSchema.add("c");
        newThirdPartyIDs.add("b");
        assertThat(newDs.getSchema().getSchema(), containsInAnyOrder("a", "b"));
        assertThat(newDs.getSchema().getThirdPartyIDs(), contains("a"));
    }

    @Test
    public void initDsHashMap() {
        //schema
        HashSet<String> newSchema = new HashSet<>();
        newSchema.addAll(Arrays.asList("a", "b"));
        HashSet<String> newThirdPartyIDs = new HashSet<>();
        newThirdPartyIDs.add("a");
        //ds
        DataStructure newDs = new DSHashMap();
        try {
            newDs.init(newSchema, newThirdPartyIDs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //add items to local schema and third-party-ID
        newSchema.add("c");
        newThirdPartyIDs.add("b");
        assertThat(newDs.getSchema().getSchema(), containsInAnyOrder("a", "b"));
        assertThat(newDs.getSchema().getThirdPartyIDs(), contains("a"));
    }

    @Test
    public void getProfileByID() {
        UUID uuid = ds.insert(profileData);
        Profile profile = ds.get(uuid);
        thrown.expect(UnsupportedOperationException.class);
        profile.getProfileData().put("a", "b");
    }

    @Test
    public void getProfileByThirdPartyID() {
        ds.insert(profileData);
        Set<Profile> profiles = ds.get("Stadt", "MS");
        profiles.forEach(x -> {
            thrown.expect(UnsupportedOperationException.class);
            x.getProfileData().put("a", "b");
        });
    }

    @Test
    public void insertProfile() {
        //one profile
        Map<String, String> profileData = new HashMap<>();
        profileData.put("Name", "Tim");
        profileData.put("Adresse", "Bruchfeldweg 18");
        profileData.put("Stadt", "MS");
        UUID uuid = ds.insert(profileData);
        //manipulate
        profileData.put("Name", "Hans");
        assertThat(ds.get(uuid).getProfileData().get("Name"), is("Tim"));
    }

}
