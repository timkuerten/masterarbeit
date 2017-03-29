package datastructure;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class KdTreeTest {

    private KdTree kdTree;
    private Set<String> thirdPartyIDs;
    private ArrayList<Map<String, String>> profilesData = new ArrayList<>();
    private Profile[] profiles = new Profile[10];
    private KdNode[] kdNodes = new KdNode[10];

    @Before
    public void setUp() {
        setUpSchema();
        createExampleProfiles();
        kdTree = new KdTree(thirdPartyIDs);
        for (int i = 0; i < 10; i++) {
            kdNodes[i] = kdTree.insert(profiles[i]);
        }
    }

    private void setUpSchema() {
        thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.addAll(Arrays.asList("a", "b"));
    }

    private void createExampleProfiles() {
        profilesData.add(createProfileData("5", "5"));
        profilesData.add(createProfileData("3", "2"));
        profilesData.add(createProfileData("2", "3"));
        profilesData.add(createProfileData("7", "8"));
        profilesData.add(createProfileData("6", "7"));
        profilesData.add(createProfileData("9", "9"));
        profilesData.add(createProfileData("6", "9"));
        profilesData.add(createProfileData("5", "5"));
        profilesData.add(createProfileData("6", "9"));
        profilesData.add(createProfileData("4", "1"));
        for (int i = 0; i < 10; i++) {
            profiles[i] = createProfile(profilesData.get(i));
        }
    }

    private Map<String, String> createProfileData(String a, String b) {
        Map<String, String> profileData = new HashMap<>();
        profileData.put("a", a);
        profileData.put("b", b);
        return profileData;
    }

    private Profile createProfile(Map<String, String> profileData) {
        return new Profile(UUID.randomUUID(), new HashMap<>(profileData));
    }

    @Ignore
    @Test
    public void printKdTree() {
        System.out.println(kdTree);
    }

    @Test
    public void findMin() {
        assertThat(kdTree.findMin("a").equals(profiles[2].getProfileData().get("a")), is(true));
        assertThat(kdTree.findMin("b").equals(profiles[9].getProfileData().get("b")), is(true));
    }

    @Test
    public void deleteProfileInList() {
        Profile profile = profiles[0];
        assertThat(kdTree.contains(profile), is(true));
        kdTree.delete(profile);
        assertThat(kdTree.contains(profile), is(false));
    }

    @Test
    public void deleteProfileInLeaf() {
        Profile profile = profiles[9];
        assertThat(kdTree.contains(profile), is(true));
        kdTree.delete(profile);
        assertThat(kdTree.contains(profile), is(false));
    }

    @Test
    public void deleteLastProfileInInternalNodeLeftSide() {
        Profile profile = profiles[1];
        assertThat(kdTree.contains(profile), is(true));
        kdTree.delete(profile);
        assertThat(kdTree.contains(profile), is(false));
    }

    @Test
    public void deleteLastProfileInInternalNodeRightSide() {
        Profile profile = profiles[3];
        assertThat(kdTree.contains(profile), is(true));
        kdTree.delete(profile);
        assertThat(kdTree.contains(profile), is(false));
    }

    @Test
    public void updateProfile() {
        Profile profile = profiles[0];
        assertThat(kdTree.contains(profile), is(true));
        KdNode kdNode = kdTree.updateProfile(profile, createProfileData("100", "2"));
        assertThat(kdTree.contains(profile), is(true));
        assertThat(kdNode.equals(kdNodes[0]), is(false));
    }

    @Test
    public void findNode() {
        assertThat(kdTree.findNode(createProfileData("9", "9")).equals(kdNodes[5]), is(true));
    }

    @Test
    public void containsProfile() {
        Profile profile = profiles[3];
        assertThat(kdTree.contains(profile), is(true));
        kdTree.delete(profile);
        assertThat(kdTree.contains(profile), is(false));
    }

    @Test
    public void getProfileByUuid() {
        Profile profile = profiles[3];
        assertThat(kdTree.get(profile.getUuid()), is(profile));
        kdTree.delete(profile);
        assertThat(kdTree.get(profile.getUuid()), is(nullValue()));
    }

    @Test
    public void getProfileByUuid2() {
        Profile profile = profiles[3];
        assertThat(kdTree.get2(profile.getUuid()), is(profile));
        kdTree.delete(profile);
        assertThat(kdTree.get2(profile.getUuid()), is(nullValue()));
    }

    @Test
    public void getProfilesByThirdPartyId() {
        Set<Profile> ps = new HashSet<>(Arrays.asList(profiles[4], profiles[6]));
        assertThat(kdTree.get("a", "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByThirdPartyId2() {
        Set<Profile> ps = new HashSet<>(Arrays.asList(profiles[5], profiles[6], profiles[8]));
        assertThat(kdTree.get2("b", "9").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByRange() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles[0], profiles[1], profiles[4], profiles[6], profiles[7], profiles[8],
                        profiles[9]));
        assertThat(kdTree.get("a", "3", "6").containsAll(ps), is(true));
    }

}
