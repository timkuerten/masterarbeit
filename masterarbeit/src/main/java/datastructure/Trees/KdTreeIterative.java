package datastructure.Trees;

import datastructure.Pair;

import java.util.*;

public class KdTreeIterative<T extends Comparable<? super T>, U> implements KdTree<T, U> {

    private KdNodeGeneric<T, U> root;
    private int dimensions;

    public KdTreeIterative(int dimensions) {
        if (dimensions < 1) {
            throw new RuntimeException("dimensions can not be less than 1");
        }
        this.dimensions = dimensions;
    }

    private int incrementCd(int cd) {
        return (cd + 1) % dimensions;
    }

    public KdNodeGeneric<T, U> insert(List<T> key, U data) {
        if (root == null) {
            root = new KdNodeGeneric<>(data, key, null);
            return root;
        }

        Set<KdNodeGeneric<T, U>> kdNodes = new HashSet<>();
        kdNodes.add(root);
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            Set<KdNodeGeneric<T, U>> tempKdNodes = new HashSet<>();
            for (KdNodeGeneric<T, U> kdNode : kdNodes) {
                if (sameData(key, kdNode)) {
                    kdNode.database.add(data);
                } else if (key.get(cd).compareTo(kdNode.coordinateValues.get(cd)) < 0) {
                    if (kdNode.left != null) {
                        tempKdNodes.add(kdNode.left);
                    } else {
                        kdNode.left = new KdNodeGeneric<>(data, key, kdNode);
                        return kdNode.left;
                    }
                } else {
                    if (kdNode.right != null) {
                        tempKdNodes.add(kdNode.right);
                    } else {
                        kdNode.right = new KdNodeGeneric<>(data, key, kdNode);
                        return kdNode.right;
                    }
                }
            }

            cd = incrementCd(cd);
            kdNodes = tempKdNodes;
        }
        return null;
    }

    public boolean contains(List<T> key, U data) {
        return findNode(key) != null && findNode(key).database.contains(data);
    }

    public Set<U> get(int dim, T value) {
        if (root == null) {
            return Collections.emptySet();
        }

        if (dim > dimensions) {
            return Collections.emptySet();
        }

        List<KdNodeGeneric<T, U>> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<U> database = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNodeGeneric<T, U>> tempKdNodes = new ArrayList<>();
            for (KdNodeGeneric<T, U> kdNode : kdNodes) {
                if (cd == dim) {
                    int comparison = value.compareTo(kdNode.coordinateValues.get(dim));
                    if (comparison < 0) {
                        if (kdNode.left != null) {
                            tempKdNodes.add(kdNode.left);
                        }
                    } else {
                        if (kdNode.right != null) {
                            tempKdNodes.add(kdNode.right);
                        }

                        if (comparison == 0) {
                            database.addAll(kdNode.database);
                        }
                    }
                } else {
                    if (value.compareTo(kdNode.coordinateValues.get(dim)) == 0) {
                        database.addAll(kdNode.database);
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
        return database;
    }

    public Set<U> get(int dim, T minValue, T maxValue) {
        if (root == null) {
            return Collections.emptySet();
        }

        if (dim > dimensions) {
            return Collections.emptySet();
        }

        List<KdNodeGeneric<T, U>> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<U> database = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNodeGeneric<T, U>> tempKdNodes = new ArrayList<>();
            for (KdNodeGeneric<T, U> kdNode : kdNodes) {
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
                        database.addAll(kdNode.database);
                        if (kdNode.left != null) {
                            tempKdNodes.add(kdNode.left);
                        }

                        if (kdNode.right != null) {
                            tempKdNodes.add(kdNode.right);
                        }
                    }
                } else {
                    if (compareToRange(minValue, maxValue, kdNode.getValue(dim)) == 0) {
                        database.addAll(kdNode.database);
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
        return database;
    }

    public Set<U> get(List<Pair<T, T>> searchValues) {
        if (root == null || searchValues == null) {
            return Collections.emptySet();
        }

        List<KdNodeGeneric<T, U>> kdNodes = new ArrayList<>();
        kdNodes.add(root);
        Set<U> database = new HashSet<>();
        int cd = 0;
        while (!kdNodes.isEmpty()) {
            List<KdNodeGeneric<T, U>> tempKdNodes = new ArrayList<>();
            for (KdNodeGeneric<T, U> kdNode : kdNodes) {
                for (Pair<T, T> searchValue : searchValues) {
                    int comparison = compareToRange(searchValue.getFirst(), searchValue.getSecond(), kdNode.getValue(cd));
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
                            database.addAll(kdNode.database);
                        }

                        if (kdNode.left != null) {
                            tempKdNodes.add(kdNode.left);
                        }

                        if (kdNode.right != null) {
                            tempKdNodes.add(kdNode.right);
                        }
                    }
                }

            }

            cd = incrementCd(cd);
            kdNodes = tempKdNodes;
        }
        return database;
    }

    private int compareToRange(T minValue, T maxValue, T input) {
        if (minValue != null && input.compareTo(minValue) < 0) {
            return -1;
        } else if (maxValue != null && input.compareTo(maxValue) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean isInMultiRange(List<Pair<T, T>> searchValues, KdNodeGeneric<T, U> kdNode) {
        for (int i = 0; i < searchValues.size(); i++) {
            if ((searchValues.get(i).getFirst() != null && kdNode.getValue(i).compareTo(searchValues.get(i).getFirst()) < 0) || searchValues.get(i).getSecond() != null && kdNode.getValue(i).compareTo(searchValues.get(i).getSecond()) > 0) {
                return false;
            }
        }

        return true;
    }

    private boolean sameData(List<T> key, KdNodeGeneric<T, U> kdNode) {
        if (key == null || kdNode == null || kdNode.database == null) {
            return false;
        }

        return key.equals(kdNode.coordinateValues);
    }

    public boolean delete(List<T> key, U data) {
        if (data == null) {
            return false;
        }

        KdNodeGeneric<T, U> kdNode = root;
        int cd = 0;
        while (true) {
            if (sameData(key, kdNode)) {
                kdNode.database.remove(data);
                if (kdNode.database.isEmpty()) {
                    deleteNode(kdNode, cd);
                }
                return true;
            } else if (key.get(cd).compareTo(kdNode.coordinateValues.get(cd)) < 0) {
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

    public KdNodeGeneric<T, U> findNode(List<T> key) {
        if (key.size() != dimensions || root == null) {
            return null;
        }

        KdNodeGeneric<T, U> kdNode = root;
        int cd = 0;
        while (true) {
            if (sameData(key, kdNode)) {
                return kdNode;
            } else if (key.get(cd).compareTo(kdNode.coordinateValues.get(cd)) < 0) {
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

    public T findMin(int dim) {
        if (dim > dimensions) {
            return null;
        }

        return findMin(root, dim, 0).getFirst().coordinateValues.get(dim);
    }

    private Pair<KdNodeGeneric<T, U>, Integer> findMin(KdNodeGeneric<T, U> t, int dim, int cd) {
        if (t == null) {
            return null;
        }

        Pair<KdNodeGeneric<T, U>, Integer> minPair = new Pair<>(t, cd);
        Stack<Pair<KdNodeGeneric<T, U>, Integer>> pairStack = new Stack<>();
        pairStack.add(minPair);
        Pair<KdNodeGeneric<T, U>, Integer> pair;

        while (!pairStack.isEmpty()) {
            pair = pairStack.pop();

            if (pair.getSecond() == dim) {
                if (pair.getFirst().left == null) {
                    if (pair.getFirst().getValue(dim).compareTo(minPair.getFirst().getValue(dim)) < 0) {
                        minPair = new Pair<>(pair.getFirst(), pair.getSecond());
                    }
                } else {
                    pairStack.push(new Pair<>(pair.getFirst().left, incrementCd(pair.getSecond())));
                }
            } else {
                if (pair.getFirst().left == null) {
                    if (pair.getFirst().right == null) {
                        if (pair.getFirst().getValue(dim).compareTo(minPair.getFirst().getValue(dim)) < 0) {
                            minPair = new Pair<>(pair.getFirst(), pair.getSecond());
                        }
                    } else {
                        if (pair.getFirst().getValue(dim).compareTo(minPair.getFirst().getValue(dim)) < 0) {
                            minPair = new Pair<>(pair.getFirst(), pair.getSecond());
                        }

                        if (pair.getFirst().parent != null) {
                            pairStack.push(new Pair<>(pair.getFirst().right, incrementCd(cd)));
                        }
                    }
                } else {
                    if (pair.getFirst().right == null) {
                        if (pair.getFirst().getValue(dim).compareTo(minPair.getFirst().getValue(dim)) < 0) {
                            minPair = new Pair<>(pair.getFirst(), pair.getSecond());
                        }

                        if (pair.getFirst().left != null) {
                            pairStack.push(new Pair<>(pair.getFirst().left, incrementCd(cd)));
                        }
                    } else {
                        if (pair.getFirst().getValue(dim).compareTo(minPair.getFirst().getValue(dim)) < 0) {
                            minPair = new Pair<>(pair.getFirst(), pair.getSecond());
                        }

                        if (pair.getFirst().left != null) {
                            pairStack.push(new Pair<>(pair.getFirst().left, incrementCd(cd)));
                        }
                        pairStack.push(new Pair<>(pair.getFirst().right, incrementCd(cd)));
                    }
                }
            }
        }
        return minPair;
    }

    private boolean deleteNode(KdNodeGeneric<T, U> t, int cd) {
        if (t == null) {
            throw new NullPointerException("Node cannot be null");
        }

        KdNodeGeneric<T, U> kdNode = t;
        int newCd = cd;
        while (true) {
            if (kdNode.right != null) {
                // find
                Pair<KdNodeGeneric<T, U>, Integer> tempKdNode = findMin(kdNode.right, cd, incrementCd(newCd));
                // copy
                kdNode.copyData(tempKdNode.getFirst());
                // delete
                kdNode = tempKdNode.getFirst();
                cd = tempKdNode.getSecond();
            } else if (kdNode.left != null) {
                // find
                Pair<KdNodeGeneric<T, U>, Integer> tempKdNode = findMin(kdNode.left, cd, incrementCd(newCd));
                // copy
                kdNode.copyData(tempKdNode.getFirst());
                // delete
                kdNode = tempKdNode.getFirst();
                cd = tempKdNode.getSecond();
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
