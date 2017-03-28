package datastructure;

import javafx.util.Pair;

import java.util.Map;
import java.util.Set;

public class KdTree {

    private String[] coordinates;
    private KdNode root;

    public KdTree(Set<String> coordinates) {
        this.coordinates = new String[coordinates.size()];
        int i = 0;
        for (String coordinate : coordinates) {
            this.coordinates[i] = coordinate;
            i++;
        }
    }

    /**
     * Insert one profile.
     *
     * @param profile inserts this profile
     */
    public KdNode insert(Profile profile) {
        if (root != null) {
            return insert(profile, root, 0);
        } else {
            root = new KdNode(profile, coordinates, null);
            return root;
        }
    }

    /**
     * Insert a set of profiles.
     *
     * @param profiles inserts this profiles
     */
    public void insert(Set<Profile> profiles) {
        // TODO:
    }

    private KdNode insert(Profile profile, KdNode t, int cd) {
        if (t == null) {
            throw new NullPointerException("Node cannot be null");
        } else if (sameData(profile, t)) {
            t.profiles.add(profile);
            return t;
        } else if (profile.getProfileData().get(coordinates[cd]).compareTo(t.coordinateValues[cd]) < 0) {
            if (t.left != null) {
                return insert(profile, t.left, (cd + 1) % coordinates.length);
            } else {
                t.left = new KdNode(profile, coordinates, t);
                return t.left;
            }
        } else {
            if (t.right != null) {
                return insert(profile, t.right, (cd + 1) % coordinates.length);
            } else {
                t.right = new KdNode(profile, coordinates, t);
                return t.right;
            }
        }
    }

    private boolean sameData(Profile profile, KdNode t) {
        return sameData(profile.getProfileData(), t);
    }

    private boolean sameData(Map<String, String> profileData, KdNode t) {
        for (int i = 0; i < coordinates.length; i++) {
            if (!profileData.get(coordinates[i]).equals(t.coordinateValues[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean delete(Profile profile) {
        return deleteProfile(profile, root, 0);
    }

    private boolean delete(Profile profile, KdNode t, int cd) {
        if (t == null) {
            return false;
        } else if (sameData(profile, t)) {
            t.profiles.remove(profile);
            return true;
        } else if (profile.getProfileData().get(coordinates[cd]).compareTo(t.coordinateValues[cd]) < 0) {
            return delete(profile, t.left, (cd + 1) % coordinates.length);
        } else {
            return delete(profile, t.right, (cd + 1) % coordinates.length);
        }
    }

    public boolean findProfile(Profile profile) {
        // TODO:
        return false;
    }

    public KdNode findNode(Map<String, String> profileData) {
        if (profileData.size() != coordinates.length || root == null) {
            return null;
        }
        return findNode(profileData, root, 0);
    }

    private KdNode findNode(Map<String, String> profileData, KdNode t, int cd) {
        if (t == null) {
            return null;
        } else if (sameData(profileData, t)) {
            return t;
        } else if (profileData.get(coordinates[cd]).compareTo(t.coordinateValues[cd]) < 0) {
            return findNode(profileData, t.left, (cd + 1) % coordinates.length);
        } else {
            return findNode(profileData, t.right, (cd + 1) % coordinates.length);
        }
    }

    public String findMin(String string) {
        int dim = -1;
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i].compareTo(string) == 0) {
                dim = i;
            }
        }
        if (dim < 0) {
            return null;
        }
        return findMin(root, dim, 0).getKey().coordinateValues[dim];
    }

    private Pair<KdNode, Integer> findMin(KdNode t, int dim, int cd) {
        if (t == null) {
            return null;
        } else if (cd == dim) {
            if (t.left == null) {
                return new Pair<>(t, cd);
            } else {
                return findMin(t.left, dim, (cd + 1) % coordinates.length);
            }
        } else {
            if (t.left == null) {
                if (t.right == null) {
                    return new Pair<>(t, cd);
                } else {
                    return min(findMin(t.parent, dim, (cd + 1) % coordinates.length), t, dim, cd);
                }
            } else {
                if (t.right == null) {
                    return min(findMin(t.left, dim, (cd + 1) % coordinates.length), t, dim, cd);
                } else {
                    return min(findMin(t.left, dim, (cd + 1) % coordinates.length),
                            findMin(t.right, dim, (cd + 1) % coordinates.length), t, dim, cd);
                }
            }
        }
    }

    private Pair<KdNode, Integer> min(Pair<KdNode, Integer> p1, Pair<KdNode, Integer> p2, KdNode t, int dim, int cd) {
        if (p1.getKey().coordinateValues[dim].compareTo(p2.getKey().coordinateValues[dim]) < 0) {
            if (p1.getKey().coordinateValues[dim].compareTo(t.coordinateValues[dim]) < 0) {
                return p1;
            }
        } else if (p2.getKey().coordinateValues[dim].compareTo(t.coordinateValues[dim]) < 0) {
            return p2;
        }
        return new Pair<>(t, cd);
    }

    private Pair<KdNode, Integer> min(Pair<KdNode, Integer> p1, KdNode t, int dim, int cd) {
        if (p1.getKey().coordinateValues[dim].compareTo(t.coordinateValues[dim]) < 0) {
            return p1;
        }
        return new Pair<>(t, cd);
    }

    private boolean deleteProfile(Profile profile, KdNode t, int cd) {
        if (t == null) {
            return false;
        }
        if (sameData(profile, t)) {
            t.profiles.remove(profile);
            if (t.profiles.isEmpty()) {
                deleteNode(t, cd);
            }
            return true;
        } else if (profile.getProfileData().get(coordinates[cd]).compareTo(t.coordinateValues[cd]) < 0) {
            return deleteProfile(profile, t.left, (cd + 1) % coordinates.length);
        } else {
            return deleteProfile(profile, t.right, (cd + 1) % coordinates.length);
        }
    }

    private boolean deleteNode(KdNode t, int cd) {
        if (t == null) {
            throw new NullPointerException("Node cannot be null");
        }

        int nextCd = (cd + 1) % coordinates.length;
        if (t.right != null) {
            // find
            Pair<KdNode, Integer> kdNode = findMin(t.right, cd, nextCd);
            // copy
            t.copyData(kdNode.getKey());
            // delete
            return deleteNode(kdNode.getKey(), kdNode.getValue());
        } else if (t.left != null) {
            // find
            Pair<KdNode, Integer> kdNode = findMin(t.left, cd, nextCd);
            // copy
            t.copyData(kdNode.getKey());
            // delete
            return deleteNode(kdNode.getKey(), kdNode.getValue());
        } else {
            if (t == root) {
                root = null;
            } else {
                if (t.parent.left == t) {
                    t.parent.left = null;
                } else {
                    t.parent.right = null;
                }
            }
            return true;
        }
    }

    public KdNode updateProfile(Profile profile, Map<String, String> profileData) {
        delete(profile);
        profile.profileData = profileData;
        return insert(profile, root, 0);
    }

    @Override
    public String toString() {
        if (root == null) {
            return "()";
        } else {
            return "(" + root.toString() + ")";
        }
    }

}


