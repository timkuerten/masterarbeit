package datastructure;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

abstract public class AbstractKdProfileTreeTest {

    protected KdProfileTree kdProfileTree;
    protected Set<String> thirdPartyIDs;
    private ArrayList<Map<String, String>> profilesData = new ArrayList<>();
    private Profile[] profiles = new Profile[10];
    private KdNode[] kdNodes = new KdNode[10];

    @Before
    public void setUpSchema() {
        thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.addAll(Arrays.asList("a", "b"));
    }

    @Before
    public void createExampleProfiles() {
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

    protected void fillKdTree() {
        for (int i = 0; i < 10; i++) {
            kdNodes[i] = kdProfileTree.insert(profiles[i]);
        }
    }

    @Ignore
    @Test
    public void printKdTree() {
        System.out.println(kdProfileTree);
    }

    @Test
    public void findMin() {
        assertThat(kdProfileTree.findMin("a").equals(profiles[2].getProfileData().get("a")), is(true));
        assertThat(kdProfileTree.findMin("b").equals(profiles[9].getProfileData().get("b")), is(true));
    }

    @Test
    public void deleteProfileInList() {
        Profile profile = profiles[0];
        assertThat(kdProfileTree.contains(profile), is(true));
        kdProfileTree.delete(profile);
        assertThat(kdProfileTree.contains(profile), is(false));
    }

    @Test
    public void deleteProfileInLeaf() {
        Profile profile = profiles[9];
        assertThat(kdProfileTree.contains(profile), is(true));
        kdProfileTree.delete(profile);
        assertThat(kdProfileTree.contains(profile), is(false));
    }

    @Test
    public void deleteLastProfileInInternalNodeLeftSide() {
        Profile profile = profiles[1];
        assertThat(kdProfileTree.contains(profile), is(true));
        kdProfileTree.delete(profile);
        assertThat(kdProfileTree.contains(profile), is(false));
    }

    @Test
    public void deleteLastProfileInInternalNodeRightSide() {
        Profile profile = profiles[3];
        assertThat(kdProfileTree.contains(profile), is(true));
        kdProfileTree.delete(profile);
        assertThat(kdProfileTree.contains(profile), is(false));
    }

    @Test
    public void updateProfile() {
        Profile profile = profiles[0];
        assertThat(kdProfileTree.contains(profile), is(true));
        KdNode kdNode = kdProfileTree.updateProfile(profile, createProfileData("100", "2"));
        assertThat(kdProfileTree.contains(profile), is(true));
        assertThat(kdNode.equals(kdNodes[0]), is(false));
    }

    @Test
    public void findNode() {
        assertThat(kdProfileTree.findNode(createProfileData("9", "9")).equals(kdNodes[5]), is(true));
    }

    @Test
    public void containsProfile() {
        Profile profile = profiles[3];
        assertThat(kdProfileTree.contains(profile), is(true));
        kdProfileTree.delete(profile);
        assertThat(kdProfileTree.contains(profile), is(false));
    }

    @Test
    public void getProfileByUuid() {
        Profile profile = profiles[3];
        assertThat(kdProfileTree.get(profile.getUuid()), is(profile));
        kdProfileTree.delete(profile);
        assertThat(kdProfileTree.get(profile.getUuid()), is(nullValue()));
    }

    @Test
    public void getProfilesByThirdPartyId() {
        Set<Profile> ps = new HashSet<>(Arrays.asList(profiles[4], profiles[6]));
        assertThat(kdProfileTree.get("a", "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByRangeA() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles[0], profiles[1], profiles[4], profiles[6], profiles[7], profiles[8],
                        profiles[9]));
        assertThat(kdProfileTree.get("a", "3", "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByRangeB() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles[0], profiles[2], profiles[7]));
        assertThat(kdProfileTree.get("b", "3", "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByRangeOneSideOpen() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles[0], profiles[1], profiles[2], profiles[4], profiles[6], profiles[7], profiles[8],
                        profiles[9]));
        assertThat(kdProfileTree.get("a", null, "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByMultiRange() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles[3], profiles[4], profiles[6]));
        Set<Triple<String, String, String>> searchValues = new HashSet<>();
        searchValues.add(new Triple<>("a", "3", "7"));
        searchValues.add(new Triple<>("b", "6", null));
        assertThat(kdProfileTree.get(searchValues).containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByMultiRangeWithoutFullMultiRange() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles[0], profiles[1], profiles[4], profiles[6], profiles[7], profiles[8],
                        profiles[9]));
        Set<Triple<String, String, String>> searchValues = new HashSet<>();
        searchValues.add(new Triple<>("a", "3", "6"));
        assertThat(kdProfileTree.get(searchValues).containsAll(ps), is(true));
    }
}
