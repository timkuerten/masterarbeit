package datastructure;

import javafx.util.Pair;

import java.util.*;

public class KdProfileTreeRecursive implements KdProfileTree {

    private String[] coordinates;
    private KdNode root;

    public KdProfileTreeRecursive(Set<String> coordinates) {
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
        if (root != null) {
            return insert(profile, root, 0);
        } else {
            root = new KdNode(profile, coordinates, null);
            return root;
        }
    }

    private KdNode insert(Profile profile, KdNode t, int cd) {
        if (t == null) {
            throw new NullPointerException("Node cannot be null");
        } else if (sameData(profile, t)) {
            t.profiles.add(profile);
            return t;
        } else if (profile.getProfileData().get(coordinates[cd]).compareTo(t.coordinateValues[cd]) < 0) {
            if (t.left != null) {
                return insert(profile, t.left, incrementCd(cd));
            } else {
                t.left = new KdNode(profile, coordinates, t);
                return t.left;
            }
        } else {
            if (t.right != null) {
                return insert(profile, t.right, incrementCd(cd));
            } else {
                t.right = new KdNode(profile, coordinates, t);
                return t.right;
            }
        }
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
    public boolean contains2(Profile profile) {
        return contains(profile, root);
    }

    // old
    private boolean contains(Profile profile, KdNode t) {
        if (t == null) {
            return false;
        } else {
            return t.profiles.contains(profile) || (contains(profile, t.left) || contains(profile, t.right));
        }
    }

    public Profile get(UUID uuid) {
        return get(uuid, root);
    }

    private Profile get(UUID uuid, KdNode t) {
        if (t == null) {
            return null;
        } else {
            // is any profile with uuid in t.profiles?
            for (Profile profile : t.profiles) {
                if (profile.getUuid() == uuid) {
                    return profile;
                }
            }
            Profile left = get(uuid, t.left);
            if (left != null) {
                return left;
            } else {
                return get(uuid, t.right);
            }
        }
    }

    public Set<Profile> get(String thirdPartyID, String value) {
        if (root == null) {
            return Collections.emptySet();
        }
        int dim = giveDimension(thirdPartyID);
        if (dim < 0) {
            return Collections.emptySet();
        }
        return get(root, dim, value, 0);
    }

    private Set<Profile> get(KdNode t, int dim, String value, int cd) {
        if (t == null) {
            return new HashSet<>();
        } else if (cd == dim) {
            int comparison = value.compareTo(t.getValue(dim));
            if (comparison < 0) {
                return get(t.left, dim, value, incrementCd(cd));
            } else {
                Set<Profile> returnValue = get(t.right, dim, value, incrementCd(cd));
                if (comparison == 0) {
                    returnValue.addAll(t.profiles);
                }
                return returnValue;
            }
        } else {
            Set<Profile> returnValue = get(t.left, dim, value, incrementCd(cd));
            if (value.compareTo(t.getValue(dim)) == 0) {
                returnValue.addAll(t.profiles);
            }
            returnValue.addAll(get(t.right, dim, value, incrementCd(cd)));
            return returnValue;
        }
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
        return get(root, dim, minValue, maxValue, 0);
    }

    private Set<Profile> get(KdNode t, int dim, String minValue, String maxValue, int cd) {
        if (t == null) {
            return new HashSet<>();
        } else if (cd == dim) {
            int comparison = compareToRange(minValue, maxValue, t.getValue(dim));
            if (comparison < 0) {
                return get(t.right, dim, minValue, maxValue, incrementCd(cd));
            } else if (comparison > 0) {
                return get(t.left, dim, minValue, maxValue, incrementCd(cd));
            } else {
                Set<Profile> returnValue = get(t.left, dim, minValue, maxValue, incrementCd(cd));
                returnValue.addAll(t.profiles);
                returnValue.addAll(get(t.right, dim, minValue, maxValue, incrementCd(cd)));
                return returnValue;
            }
        } else {
            Set<Profile> returnValue = get(t.left, dim, minValue, maxValue, incrementCd(cd));
            if (compareToRange(minValue, maxValue, t.getValue(dim)) == 0) {
                returnValue.addAll(t.profiles);
            }
            returnValue.addAll(get(t.right, dim, minValue, maxValue, incrementCd(cd)));
            return returnValue;
        }
    }

    public Set<Profile> get(Set<Triple<String, String, String>> searchValues) {
        if (root == null || searchValues == null) {
            return Collections.emptySet();
        }
        Set<Triple<Integer, String, String>> searchValues2 = new HashSet<>();
        for (Triple<String, String, String> searchValue : searchValues) {
            int dim = giveDimension(searchValue.getFirst());
            if (dim < 0) {
                return Collections.emptySet();
            }
            searchValues2.add(new Triple<>(dim, searchValue.getSecond(), searchValue.getThird()));
        }
        return get(root, searchValues2, 0);
    }

    private Set<Profile> get(KdNode t, Set<Triple<Integer, String, String>> searchValues, int cd) {
        if (t == null) {
            return new HashSet<>();
        } else {
            Triple<Integer, String, String> cdTriple = null;
            for (Triple<Integer, String, String> searchValue : searchValues) {
                if (searchValue.getFirst().equals(cd)) {
                    cdTriple = searchValue;
                    break;
                }
            }

            // does a range search exist for dimension cd?
            if (cdTriple != null) {
                int comparison = compareToRange(cdTriple.getSecond(), cdTriple.getThird(), t.getValue(cd));
                if (comparison < 0) {
                    return get(t.right, searchValues, incrementCd(cd));
                } else if (comparison > 0) {
                    return get(t.left, searchValues, incrementCd(cd));
                } else {
                    Set<Profile> returnValue = get(t.left, searchValues, incrementCd(cd));
                    if (isInMultiRange(searchValues, t)) {
                        returnValue.addAll(t.profiles);
                    }
                    returnValue.addAll(get(t.right, searchValues, incrementCd(cd)));
                    return returnValue;
                }
            } else {
                Set<Profile> returnValue = get(t.left, searchValues, incrementCd(cd));
                if (isInMultiRange(searchValues, t)) {
                    returnValue.addAll(t.profiles);
                }
                returnValue.addAll(get(t.right, searchValues, incrementCd(cd)));
                return returnValue;
            }
        }
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
        return deleteProfile(profile, root, 0);
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
            return deleteProfile(profile, t.left, incrementCd(cd));
        } else {
            return deleteProfile(profile, t.right, incrementCd(cd));
        }
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
            return findNode(profileData, t.left, incrementCd(cd));
        } else {
            return findNode(profileData, t.right, incrementCd(cd));
        }
    }

    public String findMin(String thirdPartyID) {
        int dim = giveDimension(thirdPartyID);
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

        int nextCd = incrementCd(cd);
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


