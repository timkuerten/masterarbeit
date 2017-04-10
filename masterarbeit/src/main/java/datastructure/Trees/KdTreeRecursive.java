package datastructure.Trees;

import datastructure.Pair;

import java.util.*;

public class KdTreeRecursive<T extends Comparable<? super T>, U> implements KdTree<T, U> {

    private KdNode<T, U> root;
    private int dimensions;

    public KdTreeRecursive(int dimensions) {
        if (dimensions < 1) {
            throw new RuntimeException("dimensions can not be less than 1");
        }
        this.dimensions = dimensions;
    }

    private int incrementCd(int cd) {
        return (cd + 1) % dimensions;
    }

    public KdNode<T, U> insert(List<T> key, U data) {
        if (root != null) {
            return insert(key, data, root, 0);
        } else {
            root = new KdNode<>(data, key, null);
            return root;
        }
    }

    private KdNode<T, U> insert(List<T> key, U data, KdNode<T, U> kdNode, int cd) {
        if (kdNode == null) {
            throw new NullPointerException("Node cannot be null");
        } else if (sameData(key, kdNode)) {
            kdNode.database.add(data);
            return kdNode;
        } else if (key.get(cd) != null && (kdNode.coordinateValues.get(cd) == null || key.get(cd).compareTo(kdNode.coordinateValues.get(cd)) < 0)) {
            if (kdNode.left != null) {
                return insert(key, data, kdNode.left, incrementCd(cd));
            } else {
                kdNode.left = new KdNode<>(data, key, kdNode);
                return kdNode.left;
            }
        } else {
            if (kdNode.right != null) {
                return insert(key, data, kdNode.right, incrementCd(cd));
            } else {
                kdNode.right = new KdNode<>(data, key, kdNode);
                return kdNode.right;
            }
        }
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

        return get(root, dim, value, 0);
    }

    private Set<U> get(KdNode<T, U> kdNode, int dim, T value, int cd) {
        if (kdNode == null) {
            return new HashSet<>();
        } else if (cd == dim) {
            int comparison = value.compareTo(kdNode.getValue(dim));
            if (comparison < 0) {
                return get(kdNode.left, dim, value, incrementCd(cd));
            } else {
                Set<U> returnValue = get(kdNode.right, dim, value, incrementCd(cd));
                if (comparison == 0) {
                    returnValue.addAll(kdNode.database);
                }

                return returnValue;
            }
        } else {
            Set<U> returnValue = get(kdNode.left, dim, value, incrementCd(cd));
            if (value.compareTo(kdNode.getValue(dim)) == 0) {
                returnValue.addAll(kdNode.database);
            }

            returnValue.addAll(get(kdNode.right, dim, value, incrementCd(cd)));
            return returnValue;
        }
    }

    public Set<U> get(int dim, T minValue, T maxValue) {
        if (root == null) {
            return Collections.emptySet();
        }

        if (dim > dimensions) {
            return Collections.emptySet();
        }

        return get(root, dim, minValue, maxValue, 0);
    }

    private Set<U> get(KdNode<T, U> kdNode, int dim, T minValue, T maxValue, int cd) {
        if (kdNode == null) {
            return new HashSet<>();
        } else if (cd == dim) {
            int comparison = compareToRange(minValue, maxValue, kdNode.getValue(dim));
            if (comparison < 0) {
                return get(kdNode.right, dim, minValue, maxValue, incrementCd(cd));
            } else if (comparison > 0) {
                return get(kdNode.left, dim, minValue, maxValue, incrementCd(cd));
            } else {
                Set<U> returnValue = get(kdNode.left, dim, minValue, maxValue, incrementCd(cd));
                returnValue.addAll(kdNode.database);
                returnValue.addAll(get(kdNode.right, dim, minValue, maxValue, incrementCd(cd)));
                return returnValue;
            }
        } else {
            Set<U> returnValue = get(kdNode.left, dim, minValue, maxValue, incrementCd(cd));
            if (compareToRange(minValue, maxValue, kdNode.getValue(dim)) == 0) {
                returnValue.addAll(kdNode.database);
            }

            returnValue.addAll(get(kdNode.right, dim, minValue, maxValue, incrementCd(cd)));
            return returnValue;
        }
    }

    public Set<U> get(List<Pair<T, T>> searchValues) {
        if (root == null || searchValues == null) {
            return Collections.emptySet();
        }

        return get(root, searchValues, 0);
    }

    private Set<U> get(KdNode<T, U> kdNode, List<Pair<T, T>> searchValues, int cd) {
        if (kdNode == null) {
            return new HashSet<>();
        }

        int comparison =
                compareToRange(searchValues.get(cd).getFirst(), searchValues.get(cd).getSecond(), kdNode.getValue(cd));
        if (comparison < 0) {
            if (kdNode.right != null) {
                return get(kdNode.right, searchValues, incrementCd(cd));
            } else {
                return new HashSet<>();
            }
        } else if (comparison > 0) {
            if (kdNode.left != null) {
                return get(kdNode.left, searchValues, incrementCd(cd));
            } else {
                return new HashSet<>();
            }
        } else {
            Set<U> returnValue = get(kdNode.left, searchValues, incrementCd(cd));
            if (isInMultiRange(searchValues, kdNode)) {
                returnValue.addAll(kdNode.database);
            }

            returnValue.addAll(get(kdNode.right, searchValues, incrementCd(cd)));
            return returnValue;
        }

    }

    private int compareToRange(T minValue, T maxValue, T input) {
        if (input == null) {
            if (minValue == null && maxValue == null) {
                return 0;
            } else {
                return -1;
            }
        }

        if (minValue != null && input.compareTo(minValue) < 0) {
            return -1;
        } else if (maxValue != null && input.compareTo(maxValue) > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    private boolean isInMultiRange(List<Pair<T, T>> searchValues, KdNode<T, U> kdNode) {
        for (int i = 0; i < searchValues.size(); i++) {
            if (kdNode.getValue(i) == null && (searchValues.get(i).getFirst() != null ||
                    searchValues.get(i).getSecond() != null)) {
                return false;

            }

            if ((searchValues.get(i).getFirst() != null
                    && kdNode.getValue(i).compareTo(searchValues.get(i).getFirst()) < 0)
                    || searchValues.get(i).getSecond() != null
                    && kdNode.getValue(i).compareTo(searchValues.get(i).getSecond()) > 0) {
                return false;
            }
        }

        return true;
    }

    private boolean sameData(List<T> key, KdNode<T, U> kdNode) {
        if (key == null || kdNode == null || kdNode.database == null) {
            return false;
        }

        return key.equals(kdNode.coordinateValues);
    }

    public boolean delete(List<T> key, U data) {
        if (key == null || data == null) {
            return false;
        }

        return deleteProfile(key, data, root, 0);
    }

    private boolean deleteProfile(List<T> key, U data, KdNode<T, U> kdNode, int cd) {
        if (kdNode == null) {
            return false;
        }

        if (sameData(key, kdNode)) {
            kdNode.database.remove(data);
            if (kdNode.database.isEmpty()) {
                deleteNode(kdNode, cd);
            }

            return true;
        } else if (key.get(cd).compareTo(kdNode.coordinateValues.get(cd)) < 0) {
            return deleteProfile(key, data, kdNode.left, incrementCd(cd));
        } else {
            return deleteProfile(key, data, kdNode.right, incrementCd(cd));
        }
    }

    private boolean deleteNode(KdNode<T, U> kdNode, int cd) {
        if (kdNode == null) {
            throw new NullPointerException("Node cannot be null");
        }

        int nextCd = incrementCd(cd);
        if (kdNode.right != null) {
            // find
            Pair<KdNode<T, U>, Integer> tempKdNode = findMin(kdNode.right, cd, incrementCd(cd));
            // copy
            kdNode.copyData(tempKdNode.getFirst());
            // delete
            return deleteNode(tempKdNode.getFirst(), tempKdNode.getSecond());
        } else if (kdNode.left != null) {
            // find
            Pair<KdNode<T, U>, Integer> tempKdNode = findMin(kdNode.left, cd, incrementCd(cd));
            // copy
            kdNode.copyData(tempKdNode.getFirst());
            // delete
            return deleteNode(tempKdNode.getFirst(), tempKdNode.getSecond());
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

    public KdNode<T, U> findNode(List<T> key) {
        if (key.size() != dimensions || root == null) {
            return null;
        }

        return findNode(key, root, 0);
    }

    private KdNode<T, U> findNode(List<T> key, KdNode<T, U> kdNode, int cd) {
        if (kdNode == null) {
            return null;
        } else if (sameData(key, kdNode)) {
            return kdNode;
        } else if (key.get(cd).compareTo(kdNode.coordinateValues.get(cd)) < 0) {
            return findNode(key, kdNode.left, incrementCd(cd));
        } else {
            return findNode(key, kdNode.right, incrementCd(cd));
        }
    }

    public T findMin(int dim) {
        if (dim > dimensions) {
            return null;
        }

        return findMin(root, dim, 0).getFirst().coordinateValues.get(dim);
    }

    private Pair<KdNode<T, U>, Integer> findMin(KdNode<T, U> kdNode, int dim, int cd) {
        if (kdNode == null) {
            return null;
        } else if (cd == dim) {
            if (kdNode.left == null) {
                return new Pair<>(kdNode, cd);
            } else {
                return findMin(kdNode.left, dim, incrementCd(cd));
            }
        } else {
            if (kdNode.left == null) {
                if (kdNode.right == null) {
                    return new Pair<>(kdNode, cd);
                } else {
                    return min(findMin(kdNode.right, dim, incrementCd(cd)), kdNode, dim, cd);
                }
            } else {
                if (kdNode.right == null) {
                    return min(findMin(kdNode.left, dim, incrementCd(cd)), kdNode, dim, cd);
                } else {
                    return min(findMin(kdNode.left, dim, incrementCd(cd)),
                            findMin(kdNode.right, dim, incrementCd(cd)), kdNode, dim, cd);
                }
            }
        }
    }

    private Pair<KdNode<T, U>, Integer> min(Pair<KdNode<T, U>, Integer> p1, Pair<KdNode<T, U>, Integer> p2, KdNode<T, U> t, int dim, int cd) {
        if (p1.getFirst().coordinateValues.get(dim).compareTo(p2.getFirst().coordinateValues.get(dim)) < 0) {
            if (p1.getFirst().coordinateValues.get(dim).compareTo(t.getValue(dim)) < 0) {
                return p1;
            }
        } else if (p2.getFirst().coordinateValues.get(dim).compareTo(t.getValue(dim)) < 0) {
            return p2;
        }

        return new Pair<>(t, cd);
    }

    private Pair<KdNode<T, U>, Integer> min(Pair<KdNode<T, U>, Integer> p1, KdNode<T, U> t, int dim, int cd) {
        if (p1.getFirst().coordinateValues.get(dim).compareTo(t.getValue(dim)) < 0) {
            return p1;
        }

        return new Pair<>(t, cd);
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


