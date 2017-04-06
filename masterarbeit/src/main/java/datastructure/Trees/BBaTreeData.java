package datastructure.Trees;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BBaTreeData<T extends Comparable<? super T>, U extends Comparable<? super U>> {

    public BBaNodeData<T, U> root;
    private double alpha;
    private double epsilon;

    public BBaTreeData() {
        alpha = 2.0 / 7.0;
        epsilon = 1.0 / 98.0;
    }

    public BBaTreeData(double alpha) {
        if (alpha >= 1.0 - 1.0 / Math.sqrt(2.0)) {
            throw new RuntimeException("alpha must be < 1 - (1/sqrt(2))");
        }
        this.alpha = alpha;
        epsilon = alpha * alpha - 2.0 * alpha + 0.5;
    }

    public BBaTreeData(double alpha, double epsilon) {
        if (alpha >= 1.0 - 1.0 / Math.sqrt(2.0) || alpha <= 0.0) {
            throw new RuntimeException("alpha must be > 0 && < 1 - (1/sqrt(2))");
        } else if (epsilon > alpha * alpha - 2.0 * alpha + 0.5 || epsilon <= 0.0) {
            throw new RuntimeException("epsilon must be > 0 && <= alpha * alpha - 2.0 * alpha + 0.5");
        }
        this.alpha = alpha;
        this.epsilon = epsilon;
    }

    public void insert(T key, U data) {
        if (root == null) {
            root = new BBaNodeData<>(key, data);
        } else {
            insert(root, key, data);
        }
    }

    private void insert(BBaNodeData<T, U> node, T key, U data) {
        if (node == null) {
            throw new NullPointerException("node cannot be null");
        }

        Stack<BBaNodeData<T, U>> bBaNodeStack = new Stack<>();
        BBaNodeData<T, U> tmpNode = node;
        while (!tmpNode.isLeaf()) {
            bBaNodeStack.push(tmpNode);
            if (key.compareTo(tmpNode.key) < 0) {
                tmpNode = tmpNode.left;
            } else {
                tmpNode = tmpNode.right;
            }
        }
        // found the candidate leaf. Test whether key distinct

        if (tmpNode.key.compareTo(key) == 0) {
            tmpNode.insertData(data);
            return;
        }


        // key is distinct, now perform the insert
        if (tmpNode.key.compareTo(key) < 0) {
            tmpNode.addLeft(new BBaNodeData<>(tmpNode.key, tmpNode.database));
            tmpNode.addRight(new BBaNodeData<>(key, data));
            tmpNode.key = key;
        } else {
            tmpNode.addLeft(new BBaNodeData<>(key, data));
            tmpNode.addRight(new BBaNodeData<>(tmpNode.key, tmpNode.database));
        }
        tmpNode.database.clear();
        //tmpNode.weight = 2;
        updateWeight(tmpNode);
        //System.out.println("set new tmpNode key: " + tmpNode.key + " , l: " + tmpNode.left.key + ", r:" + tmpNode.right.key);
        rebalance(bBaNodeStack);

    }

    public void insert(List<Pair<T, U>> pairList) {
        for (Pair<T, U> pair : pairList) {
            insert(pair.getKey(), pair.getValue());
        }
    }

    private void rebalance(Stack<BBaNodeData<T, U>> bBaNodeStack) {
        BBaNodeData<T, U> tmpNode;
        /* rebalance */
        while (!bBaNodeStack.isEmpty()) {
            tmpNode = bBaNodeStack.pop();

            //System.out.println("tmpNode: " + tmpNode.key);

            // set new weight
            tmpNode.weight = tmpNode.left.weight + tmpNode.right.weight;
            // check alpha
            if (tmpNode.right.weight < alpha * tmpNode.weight) {
                if (tmpNode.left.left.weight > (alpha + epsilon) * tmpNode.weight) {
                    tmpNode = rightRotation(tmpNode);
                    updateWeight(tmpNode.right);
                } else {
                    tmpNode = leftRotation(tmpNode.left);
                    tmpNode = rightRotation(tmpNode);
                    tmpNode.right.weight = tmpNode.right.left.weight + tmpNode.right.right.weight;
                    updateWeight(tmpNode.left);
                }
            } else if (tmpNode.left.weight < alpha * tmpNode.weight) {
                if (tmpNode.right.right.weight > (alpha + epsilon) * tmpNode.weight) {
                    tmpNode = leftRotation(tmpNode);
                    updateWeight(tmpNode.left);
                } else {
                    tmpNode = rightRotation(tmpNode.right);
                    tmpNode = leftRotation(tmpNode);
                    tmpNode.right.weight = tmpNode.right.left.weight + tmpNode.right.right.weight;
                    updateWeight(tmpNode.left);
                }
            }
        }
        /* end rebalance */
    }

    private BBaNodeData<T, U> rightRotation(BBaNodeData<T, U> node) {
        //System.out.println("rightRotation: " + node.key);
        BBaNodeData<T, U> oldNode = node;
        node = oldNode.left;
        if (oldNode.parent == null) {
            root = node;
            node.parent = null;
            oldNode.addLeft(node.right);
            node.addRight(oldNode);
        } else {
            BBaNodeData<T, U> parent = oldNode.parent;
            if (parent.left == oldNode) {
                parent.addLeft(node);
                oldNode.addLeft(node.right);
                node.addRight(oldNode);
            } else {
                parent.addRight(node);
                oldNode.addLeft(node.right);
                node.addRight(oldNode);
            }
        }
        return node;
    }

    private BBaNodeData<T, U> leftRotation(BBaNodeData<T, U> node) {
        //System.out.println("leftRotation: " + node.key);
        BBaNodeData<T, U> oldNode = node;
        node = oldNode.right;
        if (oldNode.parent == null) {
            root = node;
            node.parent = null;
            oldNode.addRight(node.left);
            node.addLeft(oldNode);
        } else {
            BBaNodeData<T, U> parent = oldNode.parent;
            if (parent.left == oldNode) {
                parent.addLeft(node);
                oldNode.addRight(node.left);
                node.addLeft(oldNode);
            } else {
                parent.addRight(node);
                oldNode.addRight(node.left);
                node.addLeft(oldNode);
            }
        }
        return node;
    }

    public boolean find(T key) {
        BBaNodeData<T, U> node = root;
        while (node != null) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else if (key.compareTo(node.key) > 0) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public BBaNodeData<T, U> getLeaf(T key) {
        BBaNodeData<T, U> node = root;
        while (!node.isLeaf()) {
            if (key.compareTo(node.key) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        if (node.key.compareTo(key) == 0) {
            return node;
        } else {
            return null;
        }
    }

    public boolean delete(T key, U data) {
        if (root == null || key == null || data == null) {
            return false;
        }

        Stack<BBaNodeData<T, U>> bBaNodeStack = new Stack<>();
        BBaNodeData<T, U> tmpNode = root;
        while (!tmpNode.isLeaf()) {
            bBaNodeStack.push(tmpNode);
            if (key.compareTo(tmpNode.key) < 0) {
                tmpNode = tmpNode.left;
            } else {
                tmpNode = tmpNode.right;
            }
        }
        // found the candidate leaf. Test whether key is same
        if (tmpNode.key.compareTo(key) != 0 || tmpNode.hasEmptyDatabase()) {
            return false;
        }

        // remove data
        boolean returnValue = tmpNode.removeData(data);
        if (tmpNode.hasEmptyDatabase()) {
            // delete node
            delete(tmpNode, bBaNodeStack);
        }

        return returnValue;
    }

    private boolean delete(BBaNodeData<T, U> node, Stack<BBaNodeData<T, U>> bBaNodeStack) {
        if (node == root) {
            // root && leaf -> only this key in tree
            root = null;
            return true;

        } else {
            BBaNodeData<T, U> parent = node.parent;
            if (parent.left == node) {
                if (parent.right.isLeaf()) {
                    parent.left = null;
                    parent.right = null;
                } else {
                    parent.addLeft(parent.right.left);
                    parent.addRight(parent.right.right);
                }
            } else {
                if (parent.left.isLeaf()) {
                    parent.left = null;
                    parent.right = null;
                } else {
                    parent.addLeft(parent.left.left);
                    parent.addRight(parent.left.right);
                }
            }
            updateWeight(parent);
            updateKey(parent);
        }
        bBaNodeStack.pop();
        rebalance(bBaNodeStack);
        return true;
    }

    private void updateWeight(BBaNodeData<T, U> node) {
        if (node.isLeaf()) {
            //System.out.println("Leaf new :" + node);
            node.weight = 1;
            node = node.parent;
        }
        while (node != null) {
            node.weight = node.left.weight + node.right.weight;
            //System.out.println("new :" + node);
            node = node.parent;
        }
    }

    private void updateKey(BBaNodeData<T, U> node) {
        if (node.isLeaf()) {
            return;
        }
        while (node != null) {
            node.key = (node.left.key.compareTo(node.right.key) > 0) ? node.left.key : node.right.key;
            //System.out.println("new :" + node);
            node = node.parent;
        }
    }

    public List<BBaNodeData<T, U>> inOrder() {
        return inOrder(root);
    }

    private List<BBaNodeData<T, U>> inOrder(BBaNodeData<T, U> node) {
        Stack<BBaNodeData<T, U>> nodeStack = new Stack<>();
        List<BBaNodeData<T, U>> nodes = new ArrayList<>();
        while (!nodeStack.isEmpty() || node != null) {
            if (node != null) {
                nodeStack.push(node);
                node = node.left;
            } else {
                node = nodeStack.pop();
                nodes.add(node);
                node = node.right;
            }
        }
        return nodes;
    }

}
