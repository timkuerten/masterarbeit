package datastructure;

import javafx.util.Pair;

import java.util.*;

public class KdProfileTreeIterativeGeneric implements KdProfileTreeGeneric {

    private String[] coordinates;
    private KdNodeGeneric<String> root;

    public KdProfileTreeIterativeGeneric(Set<String> coordinates) {
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

    public KdNodeGeneric<String> insert(Profile profile) {
        if (root == null) {
            root = new KdNodeGeneric<>(profile, coordinates, null);
            return root;
        }

        Set<KdNodeGeneric<String>> kdNodes = new HashSet<>();
        kdNodes.add(root);
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            Set<KdNodeGeneric<String>> tempKdNodes = new HashSet<>();
            for (KdNodeGeneric<String> kdNode : kdNodes) {
                if (sameData(profile, kdNode)) {
                    kdNode.profiles.add(profile);
                } else if (profile.getProfileData().get(coordinates[cd]).compareTo(kdNode.coordinateValues[cd]) < 0) {
                    if (kdNode.left != null) {
                        tempKdNodes.add(kdNode.left);
                    } else {
                        kdNode.left = new KdNodeGeneric<>(profile, coordinates, kdNode);
                        return kdNode.left;
                    }
                } else {
                    if (kdNode.right != null) {
                        tempKdNodes.add(kdNode.right);
                    } else {
                        kdNode.right = new KdNodeGeneric<>(profile, coordinates, kdNode);
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
        KdNodeGeneric<String> kdNode = findNode(profile.getProfileData());
        return kdNode != null && kdNode.profiles.contains(profile);
    }

    public Profile get(UUID uuid) {
        if (root == null) {
            return null;
        }

        List<KdNodeGeneric<String>> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        KdNodeGeneric<String> kdNode;
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

        List<KdNodeGeneric<String>> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<Profile> profiles = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNodeGeneric<String>> tempKdNodes = new ArrayList<>();
            for (KdNodeGeneric<String> kdNode : kdNodes) {
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

        List<KdNodeGeneric<String>> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<Profile> profiles = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNodeGeneric<String>> tempKdNodes = new ArrayList<>();
            for (KdNodeGeneric<String> kdNode : kdNodes) {
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

        List<KdNodeGeneric<String>> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<Profile> profiles = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNodeGeneric<String>> tempKdNodes = new ArrayList<>();
            for (KdNodeGeneric<String> kdNode : kdNodes) {
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

    private boolean isInMultiRange(Set<Triple<Integer, String, String>> searchValues, KdNodeGeneric<String> kdNode) {
        return searchValues.stream()
                .noneMatch(
                        t -> ((t.getSecond() != null && kdNode.getValue(t.getFirst()).compareTo(t.getSecond()) < 0) || (
                                t.getThird() != null && kdNode.getValue(t.getFirst()).compareTo(t.getThird()) > 0)));
    }

    // old
    private boolean isInMultiRange2(Set<Triple<Integer, String, String>> searchValues, KdNodeGeneric<String> kdNode) {
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

    private boolean sameData(Profile profile, KdNodeGeneric<String> t) {
        return sameData(profile.getProfileData(), t);
    }

    private boolean sameData(Map<String, String> profileData, KdNodeGeneric<String> t) {
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

        KdNodeGeneric<String> kdNode = root;
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

    public KdNodeGeneric<String> findNode(Map<String, String> profileData) {
        if (profileData.size() != coordinates.length || root == null) {
            return null;
        }

        KdNodeGeneric<String> kdNode = root;
        int cd = 0;
        while (true) {
            if (sameData(profileData, kdNode)) {
                return kdNode;
            } else if (profileData.get(coordinates[cd]).compareTo(kdNode.coordinateValues[cd]) < 0) {
                if (kdNode.left != null) {
                    kdNode = kdNode.left;
                } else {
                    return null;
                }
            } else {
                if (kdNode.right != null) {
                    kdNode = kdNode.right;
                } else {
                    return null;
                }
            }

            cd = incrementCd(cd);
        }
    }

    public String findMin(String thirdPartyID) {
        int dim = giveDimension(thirdPartyID);
        if (dim < 0) {
            return null;
        }

        return findMin(root, dim, 0).getKey().coordinateValues[dim];
    }

    private Pair<KdNodeGeneric<String>, Integer> findMin(KdNodeGeneric<String> t, int dim, int cd) {
        if (t == null) {
            return null;
        }

        Pair<KdNodeGeneric<String>, Integer> minPair = new Pair<>(t, cd);
        Stack<Pair<KdNodeGeneric<String>, Integer>> pairStack = new Stack<>();
        pairStack.add(minPair);
        Pair<KdNodeGeneric<String>, Integer> pair;

        while (!pairStack.isEmpty()) {
            pair = pairStack.pop();

            if (pair.getValue() == dim) {
                if (pair.getKey().left == null) {
                    if (pair.getKey().getValue(dim).compareTo(minPair.getKey().getValue(dim)) < 0) {
                        minPair = new Pair<>(pair.getKey(), pair.getValue());
                    }
                } else {
                    pairStack.push(new Pair<>(pair.getKey().left, incrementCd(pair.getValue())));
                }
            } else {
                if (pair.getKey().left == null) {
                    if (pair.getKey().right == null) {
                        if (pair.getKey().getValue(dim).compareTo(minPair.getKey().getValue(dim)) < 0) {
                            minPair = new Pair<>(pair.getKey(), pair.getValue());
                        }
                    } else {
                        if (pair.getKey().getValue(dim).compareTo(minPair.getKey().getValue(dim)) < 0) {
                            minPair = new Pair<>(pair.getKey(), pair.getValue());
                        }

                        if (pair.getKey().parent != null) {
                            pairStack.push(new Pair<>(pair.getKey().right, incrementCd(cd)));
                        }
                    }
                } else {
                    if (pair.getKey().right == null) {
                        if (pair.getKey().getValue(dim).compareTo(minPair.getKey().getValue(dim)) < 0) {
                            minPair = new Pair<>(pair.getKey(), pair.getValue());
                        }

                        if (pair.getKey().left != null) {
                            pairStack.push(new Pair<>(pair.getKey().left, incrementCd(cd)));
                        }
                    } else {
                        if (pair.getKey().getValue(dim).compareTo(minPair.getKey().getValue(dim)) < 0) {
                            minPair = new Pair<>(pair.getKey(), pair.getValue());
                        }

                        if (pair.getKey().left != null) {
                            pairStack.push(new Pair<>(pair.getKey().left, incrementCd(cd)));
                        }
                        pairStack.push(new Pair<>(pair.getKey().right, incrementCd(cd)));
                    }
                }
            }
        }
        return minPair;
    }

    private boolean deleteNode(KdNodeGeneric<String> t, int cd) {
        if (t == null) {
            throw new NullPointerException("Node cannot be null");
        }

        KdNodeGeneric<String> kdNode = t;
        int newCd = cd;
        while (true) {
            if (kdNode.right != null) {
                // find
                Pair<KdNodeGeneric<String>, Integer> tempKdNode = findMin(kdNode.right, cd, incrementCd(newCd));
                // copy
                kdNode.copyData(tempKdNode.getKey());
                // delete
                kdNode = tempKdNode.getKey();
                cd = tempKdNode.getValue();
            } else if (kdNode.left != null) {
                // find
                Pair<KdNodeGeneric<String>, Integer> tempKdNode = findMin(kdNode.left, cd, incrementCd(newCd));
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

    public KdNodeGeneric<String> updateProfile(Profile profile, Map<String, String> profileData) {
        delete(profile);
        profile.profileData = profileData;
        return insert(profile);
    }

    // recursive
    @Override
    public String toString() {
        if (root == null) {
            return "()";
        } else {
            return "(" + root.toString() + ")";
        }
    }

}
