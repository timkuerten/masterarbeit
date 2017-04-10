package datastructure.Trees;

import datastructure.Pair;
import datastructure.Profile;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

abstract public class AbstractKdTreeTest {

    protected KdTree<String, Profile> kdProfileTree;
    protected Set<String> thirdPartyIDs;
    private List<Map<String, String>> profilesData = new ArrayList<>();
    private List<Profile> profiles = new ArrayList<>();
    private List<KdNodeGeneric<String, Profile>> kdNodes = new ArrayList<>();

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
            profiles.add(createProfile(profilesData.get(i)));
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
        Profile profile;
        for (int i = 0; i < 10; i++) {
            profile = profiles.get(i);
            kdNodes.add(i, kdProfileTree.insert(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile));
        }
    }

    private List<String> createInputKey(Profile profile) {
        ArrayList<String> coordinateValues = new ArrayList<>();
        for (String coordinate : thirdPartyIDs) {
            coordinateValues.add(profile.getProfileData().get(coordinate));
        }
        return coordinateValues;
    }

    @Ignore
    @Test
    public void printKdTree() {
        System.out.println(kdProfileTree);
    }

    @Test
    public void findMin() {
        assertThat(kdProfileTree.findMin(0).equals(profiles.get(2).getProfileData().get("a")), is(true));
        assertThat(kdProfileTree.findMin(1).equals(profiles.get(9).getProfileData().get("b")), is(true));
    }

    @Test
    public void findMinSame() {
        profilesData.add(createProfileData("2", "4"));
        profilesData.add(createProfileData("2", "1"));
        Profile profile10 = createProfile(profilesData.get(10));
        Profile profile11 = createProfile(profilesData.get(11));
        kdProfileTree.insert(new ArrayList<>(Arrays.asList(profile10.getProfileData().get("a"), profile10.getProfileData().get("b"))), profile10);
        kdProfileTree.insert(new ArrayList<>(Arrays.asList(profile11.getProfileData().get("a"), profile11.getProfileData().get("b"))), profile11);

        assertThat(kdProfileTree.findMin(0).equals(profiles.get(2).getProfileData().get("a")), is(true));
    }

    @Test
    public void deleteProfileInList() {
        Profile profile = profiles.get(0);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(true));
        kdProfileTree.delete(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(false));
    }

    @Test
    public void deleteProfileInLeaf() {
        Profile profile = profiles.get(9);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(true));
        kdProfileTree.delete(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(false));
    }

    @Test
    public void deleteLastProfileInInternalNodeLeftSide() {
        Profile profile = profiles.get(1);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(true));
        kdProfileTree.delete(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(false));
    }

    @Test
    public void deleteLastProfileInInternalNodeRightSide() {
        Profile profile = profiles.get(3);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(true));
        kdProfileTree.delete(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(false));
    }

    /*
    @Test
    public void findNode() {
        assertThat(kdProfileTree.findNode(createProfileData("9", "9")).equals(kdNodes.get(5)), is(true));
    }*/

    @Test
    public void containsProfile() {
        Profile profile = profiles.get(3);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(true));
        kdProfileTree.delete(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile);
        assertThat(kdProfileTree.contains(new ArrayList<>(Arrays.asList(profile.getProfileData().get("a"), profile.getProfileData().get("b"))), profile), is(false));
    }

    @Test
    public void getProfilesByThirdPartyId() {
        Set<Profile> ps = new HashSet<>(Arrays.asList(profiles.get(4), profiles.get(6)));
        assertThat(kdProfileTree.get(0, "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByRangeA() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles.get(0), profiles.get(1), profiles.get(4), profiles.get(6), profiles.get(7), profiles.get(8),
                        profiles.get(9)));
        assertThat(kdProfileTree.get(0, "3", "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByRangeB() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles.get(0), profiles.get(2), profiles.get(7)));
        assertThat(kdProfileTree.get(1, "3", "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByRangeOneSideOpen() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles.get(0), profiles.get(1), profiles.get(2), profiles.get(4), profiles.get(6), profiles.get(7), profiles.get(8),
                        profiles.get(9)));
        assertThat(kdProfileTree.get(0, null, "6").containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByMultiRange() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles.get(3), profiles.get(4), profiles.get(6)));
        List<Pair<String, String>> searchValues = new ArrayList<>();
        searchValues.add(new Pair<>("3", "7"));
        searchValues.add(new Pair<>("6", null));
        assertThat(kdProfileTree.get(searchValues).containsAll(ps), is(true));
    }

    @Test
    public void getProfilesByMultiRangeWithoutFullMultiRange() {
        Set<Profile> ps = new HashSet<>(
                Arrays.asList(profiles.get(0), profiles.get(1), profiles.get(4), profiles.get(6), profiles.get(7), profiles.get(8),
                        profiles.get(9)));
        List<Pair<String, String>> searchValues = new ArrayList<>();
        searchValues.add(new Pair<>("3", "6"));
        searchValues.add(new Pair<>(null, null));
        assertThat(kdProfileTree.get(searchValues).containsAll(ps), is(true));
    }
}
