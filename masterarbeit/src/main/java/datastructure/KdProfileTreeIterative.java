package datastructure;

import javafx.util.Pair;

import java.util.*;

public class KdProfileTreeIterative implements KdProfileTree {

    private String[] coordinates;
    private KdNode root;

    public KdProfileTreeIterative(Set<String> coordinates) {
        this.coordinates = new String[coordinates.size()];
        int i = 0;
        for (String coordinate : coordinates) {
            this.coordinates[i] = coordinate;
            i++;
        }
    }

    private int incrementCd(int cd) {
        return (cd + 1) % coordinates.length;
    }

    public KdNode insert(Profile profile) {
        if (root == null) {
            root = new KdNode(profile, coordinates, null);
            return root;
        }
        Set<KdNode> kdNodes = new HashSet<>();
        kdNodes.add(root);
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            Set<KdNode> tempKdNodes = new HashSet<>();
            for (KdNode kdNode : kdNodes) {
                if (sameData(profile, kdNode)) {
                    kdNode.profiles.add(profile);
                } else if (profile.getProfileData().get(coordinates[cd]).compareTo(kdNode.coordinateValues[cd]) < 0) {
                    if (kdNode.left != null) {
                        tempKdNodes.add(kdNode.left);
                    } else {
                        kdNode.left = new KdNode(profile, coordinates, kdNode);
                        return kdNode.left;
                    }
                } else {
                    if (kdNode.right != null) {
                        tempKdNodes.add(kdNode.right);
                    } else {
                        kdNode.right = new KdNode(profile, coordinates, kdNode);
                        return kdNode.right;
                    }
                }
            }
            cd = incrementCd(cd);
            kdNodes = tempKdNodes;
        }
        return null;
    }

    public void insert(Set<Profile> profiles) {
        // TODO:
        insert2(profiles);
    }

    private void insert2(Set<Profile> profiles) {
        for (Profile profile : profiles) {
            insert(profile);
        }
    }

    public boolean contains(Profile profile) {
        KdNode kdNode = findNode(profile.getProfileData());
        if (kdNode != null) {
            if (kdNode.profiles.contains(profile)) {
                return true;
            }
        }
        return false;
    }

    // old
    // TODO: rekursiv -> iterative
    public boolean contains2(Profile profile) {
        return contains(profile, root);
    }

    private boolean contains(Profile profile, KdNode t) {
        if (t == null) {
            return false;
        } else {
            return t.profiles.contains(profile) || (contains(profile, t.left) || contains(profile, t.right));
        }
    }

    public Profile get(UUID uuid) {
        if (root == null) {
            return null;
        }
        List<KdNode> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        KdNode kdNode;
        while (!kdNodes.isEmpty()) {
            kdNode = kdNodes.get(0);
            for (Profile profile : kdNode.profiles) {
                if (profile.getUuid() == uuid) {
                    return profile;
                }
            }
            if (kdNode.left != null) {
                kdNodes.add(kdNode.left);
            }
            if (kdNode.right != null) {
                kdNodes.add(kdNode.right);
            }
            kdNodes.remove(kdNode);
        }
        return null;
    }

    public Set<Profile> get(String thirdPartyID, String value) {
        if (root == null) {
            return Collections.emptySet();
        }
        int dim = giveDimension(thirdPartyID);
        if (dim < 0) {
            return Collections.emptySet();
        }

        List<KdNode> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<Profile> profiles = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNode> tempKdNodes = new ArrayList<>();
            for (KdNode kdNode : kdNodes) {
                if (cd == dim) {
                    int comparison = value.compareTo(kdNode.coordinateValues[dim]);
                    if (comparison < 0) {
                        if (kdNode.left != null) {
                            tempKdNodes.add(kdNode.left);
                        }
                    } else {
                        if (kdNode.right != null) {
                            tempKdNodes.add(kdNode.right);
                        }
                        if (comparison == 0) {
                            profiles.addAll(kdNode.profiles);
                        }
                    }
                } else {
                    if (value.compareTo(kdNode.coordinateValues[dim]) == 0) {
                        profiles.addAll(kdNode.profiles);
                    }
                    if (kdNode.left != null) {
                        tempKdNodes.add(kdNode.left);
                    }
                    if (kdNode.right != null) {
                        tempKdNodes.add(kdNode.right);
                    }
                }
            }
            cd = incrementCd(cd);
            kdNodes = tempKdNodes;
        }
        return profiles;
    }

    private int giveDimension(String thirdPartyID) {
        for (int i = 0; i < coordinates.length; i++) {
            if (coordinates[i].compareTo(thirdPartyID) == 0) {
                return i;
            }
        }
        return -1;
    }

    public Set<Profile> get(String thirdPartyID, String minValue, String maxValue) {
        if (root == null) {
            return Collections.emptySet();
        }
        int dim = giveDimension(thirdPartyID);
        if (dim < 0) {
            return Collections.emptySet();
        }
        List<KdNode> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<Profile> profiles = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNode> tempKdNodes = new ArrayList<>();
            for (KdNode kdNode : kdNodes) {
                if (cd == dim) {
                    int comparison = compareToRange(minValue, maxValue, kdNode.getValue(dim));
                    if (comparison < 0) {
                        if (kdNode.right != null) {
                            tempKdNodes.add(kdNode.right);
                        }
                    } else if (comparison > 0) {
                        if (kdNode.left != null) {
                            tempKdNodes.add(kdNode.left);
                        }
                    } else {
                        profiles.addAll(kdNode.profiles);
                        if (kdNode.left != null) {
                            tempKdNodes.add(kdNode.left);
                        }
                        if (kdNode.right != null) {
                            tempKdNodes.add(kdNode.right);
                        }
                    }
                } else {
                    if (compareToRange(minValue, maxValue, kdNode.getValue(dim)) == 0) {
                        profiles.addAll(kdNode.profiles);
                    }
                    if (kdNode.left != null) {
                        tempKdNodes.add(kdNode.left);
                    }
                    if (kdNode.right != null) {
                        tempKdNodes.add(kdNode.right);
                    }
                }
            }
            cd = incrementCd(cd);
            kdNodes = tempKdNodes;
        }
        return profiles;
    }

    public Set<Profile> get(Set<Triple<String, String, String>> searchValues2) {
        if (root == null || searchValues2 == null) {
            return Collections.emptySet();
        }
        Set<Triple<Integer, String, String>> searchValues = new HashSet<>();
        for (Triple<String, String, String> searchValue : searchValues2) {
            int dim = giveDimension(searchValue.getFirst());
            if (dim < 0) {
                return Collections.emptySet();
            }
            searchValues.add(new Triple<>(dim, searchValue.getSecond(), searchValue.getThird()));
        }

        List<KdNode> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<Profile> profiles = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNode> tempKdNodes = new ArrayList<>();
            for (KdNode kdNode : kdNodes) {
                Triple<Integer, String, String> cdTriple = null;
                for (Triple<Integer, String, String> searchValue : searchValues) {
                    if (searchValue.getFirst().equals(cd)) {
                        cdTriple = searchValue;
                        break;
                    }
                }

                // does a range search exist for dimension cd?
                if (cdTriple != null) {
                    int comparison = compareToRange(cdTriple.getSecond(), cdTriple.getThird(), kdNode.getValue(cd));
                    if (comparison < 0) {
                        if (kdNode.right != null) {
                            tempKdNodes.add(kdNode.right);
                        }
                    } else if (comparison > 0) {
                        if (kdNode.left != null) {
                            tempKdNodes.add(kdNode.left);
                        }
                    } else {
                        if (isInMultiRange(searchValues, kdNode)) {
                            profiles.addAll(kdNode.profiles);
                        }
                        if (kdNode.left != null) {
                            tempKdNodes.add(kdNode.left);
                        }
                        if (kdNode.right != null) {
                            tempKdNodes.add(kdNode.right);
                        }
                    }
                } else {
                    if (isInMultiRange(searchValues, kdNode)) {
                        profiles.addAll(kdNode.profiles);
                    }
                    if (kdNode.left != null) {
                        tempKdNodes.add(kdNode.left);
                    }
                    if (kdNode.right != null) {
                        tempKdNodes.add(kdNode.right);
                    }
                }
            }
            cd = incrementCd(cd);
            kdNodes = tempKdNodes;
        }
        return profiles;
    }

    private int compareToRange(String minValue, String maxValue, String input) {
        if (minValue != null && input.compareTo(minValue) < 0) {
            return -1;
        } else if (maxValue != null && input.compareTo(maxValue) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean isInMultiRange(Set<Triple<Integer, String, String>> searchValues, KdNode kdNode) {
        return searchValues.stream()
                .noneMatch(
                        t -> ((t.getSecond() != null && kdNode.getValue(t.getFirst()).compareTo(t.getSecond()) < 0) || (
                                t.getThird() != null && kdNode.getValue(t.getFirst()).compareTo(t.getThird()) > 0)));
    }

    // old
    private boolean isInMultiRange2(Set<Triple<Integer, String, String>> searchValues, KdNode kdNode) {
        boolean returnValue = true;
        for (Triple<Integer, String, String> t : searchValues) {
            if (t.getSecond() != null && kdNode.getValue(t.getFirst()).compareTo(t.getSecond()) < 0) {
                returnValue = false;
            } else if (t.getThird() != null && kdNode.getValue(t.getFirst()).compareTo(t.getThird()) > 0) {
                returnValue = false;
            }
        }

        return returnValue;
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
        if (profile == null) {
            return false;
        }
        KdNode kdNode = root;
        int cd = 0;
        while (true) {
            if (sameData(profile, kdNode)) {
                kdNode.profiles.remove(profile);
                if (kdNode.profiles.isEmpty()) {
                    deleteNode(kdNode, cd);
                }
                return true;
            } else if (profile.getProfileData().get(coordinates[cd]).compareTo(kdNode.coordinateValues[cd]) < 0) {
                if (kdNode.left == null) {
                    return false;
                } else {
                    kdNode = kdNode.left;
                }
            } else {
                if (kdNode.right == null) {
                    return false;
                } else {
                    kdNode = kdNode.right;
                }
            }
            cd = incrementCd(cd);
        }
    }

    // TODO: rekursiv -> iterative
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
            return findNode(profileData, t.left, incrementCd(cd));
        } else {
            return findNode(profileData, t.right, incrementCd(cd));
        }
    }

    // TODO: rekursiv -> iterative
    public String findMin(String thirdPartyID) {
        int dim = giveDimension(thirdPartyID);
        if (dim < 0) {
            return null;
        }
        return findMin(root, dim, 0).getKey().coordinateValues[dim];
    }

    // TODO: rekursiv -> iterative
    private Pair<KdNode, Integer> findMin(KdNode t, int dim, int cd) {
        if (t == null) {
            return null;
        } else if (cd == dim) {
            if (t.left == null) {
                return new Pair<>(t, cd);
            } else {
                return findMin(t.left, dim, incrementCd(cd));
            }
        } else {
            if (t.left == null) {
                if (t.right == null) {
                    return new Pair<>(t, cd);
                } else {
                    return min(findMin(t.parent, dim, incrementCd(cd)), t, dim, cd);
                }
            } else {
                if (t.right == null) {
                    return min(findMin(t.left, dim, incrementCd(cd)), t, dim, cd);
                } else {
                    return min(findMin(t.left, dim, incrementCd(cd)),
                            findMin(t.right, dim, incrementCd(cd)), t, dim, cd);
                }
            }
        }
    }

    private Pair<KdNode, Integer> min(Pair<KdNode, Integer> p1, Pair<KdNode, Integer> p2, KdNode t, int dim, int cd) {
        if (p1.getKey().coordinateValues[dim].compareTo(p2.getKey().coordinateValues[dim]) < 0) {
            if (p1.getKey().coordinateValues[dim].compareTo(t.getValue(dim)) < 0) {
                return p1;
            }
        } else if (p2.getKey().coordinateValues[dim].compareTo(t.getValue(dim)) < 0) {
            return p2;
        }
        return new Pair<>(t, cd);
    }

    private Pair<KdNode, Integer> min(Pair<KdNode, Integer> p1, KdNode t, int dim, int cd) {
        if (p1.getKey().coordinateValues[dim].compareTo(t.getValue(dim)) < 0) {
            return p1;
        }
        return new Pair<>(t, cd);
    }

    private boolean deleteNode(KdNode t, int cd) {
        if (t == null) {
            throw new NullPointerException("Node cannot be null");
        }
        KdNode kdNode = t;
        int newCd = cd;
        while (true) {
            if (kdNode.right != null) {
                // find
                Pair<KdNode, Integer> tempKdNode = findMin(kdNode.right, cd, incrementCd(newCd));
                // copy
                kdNode.copyData(tempKdNode.getKey());
                // delete
                kdNode = tempKdNode.getKey();
                cd = tempKdNode.getValue();
            } else if (kdNode.left != null) {
                // find
                Pair<KdNode, Integer> tempKdNode = findMin(kdNode.left, cd, incrementCd(newCd));
                // copy
                kdNode.copyData(tempKdNode.getKey());
                // delete
                kdNode = tempKdNode.getKey();
                cd = tempKdNode.getValue();
            } else {
                if (kdNode == root) {
                    root = null;
                } else {
                    if (kdNode.parent.left == kdNode) {
                        kdNode.parent.left = null;
                    } else {
                        kdNode.parent.right = null;
                    }
                }
                return true;
            }
        }

    }

    public KdNode updateProfile(Profile profile, Map<String, String> profileData) {
        delete(profile);
        profile.profileData = profileData;
        return insert(profile);
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


