package datastructure;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
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

    @Ignore
    @Test
    public void deleteProfileInList() {
        Profile profile = profiles[0];
        assertThat(kdTree.findProfile(profile), is(true));
        kdTree.delete(profile);
        assertThat(kdTree.findProfile(profile), is(false));
    }

    @Ignore
    @Test
    public void deleteProfileInLeaf() {
        Profile profile = profiles[9];
        assertThat(kdTree.findProfile(profile), is(true));
        assertThat(kdTree.findNode(profile.getProfileData()), is(true));
        kdTree.delete(profile);
        assertThat(kdTree.findProfile(profile), is(false));
        assertThat(kdTree.findNode(profile.getProfileData()), is(false));
    }

    @Test
    public void deleteLastProfileInInternalNodeLeftSide() {
        kdTree.delete(profiles[1]);
        System.out.println(kdTree);
    }

    @Ignore
    @Test
    public void deleteLastProfileInInternalNodeRightSide() {
        Profile profile = profiles[3];
        assertThat(kdTree.findProfile(profile), is(true));
        assertThat(kdTree.findNode(profile.getProfileData()), is(true));
        kdTree.delete(profile);
        assertThat(kdTree.findProfile(profile), is(false));
        assertThat(kdTree.findNode(profile.getProfileData()), is(false));
    }

    @Ignore
    @Test
    public void updateProfile() {
        Profile profile = profiles[0];
        assertThat(kdTree.findProfile(profile), is(true));
        KdNode kdNode = kdTree.updateProfile(profile, createProfileData("100", "2"));
        assertThat(kdTree.findProfile(profile), is(true));
        assertThat(kdNode.equals(kdNodes[0]), is(false));
    }

    @Test
    public void findNode() {
        assertThat(kdTree.findNode(createProfileData("9", "9")).equals(kdNodes[5]), is(true));
    }

}
