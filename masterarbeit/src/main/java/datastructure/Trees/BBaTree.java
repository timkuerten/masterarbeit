package datastructure.Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BBaTree<T extends Comparable<? super T>> {

    protected BBaNode<T> root;
    private double alpha;
    private double epsilon;

    public BBaTree() {
        alpha = 2.0 / 7.0;
        epsilon = 1.0 / 98.0;
    }

    public BBaTree(double alpha) {
        if (alpha >= 1.0 - 1.0 / Math.sqrt(2.0)) {
            throw new RuntimeException("alpha must be < 1 - (1/sqrt(2))");
        }

        this.alpha = alpha;
        epsilon = alpha * alpha - 2.0 * alpha + 0.5;
    }

    public BBaTree(double alpha, double epsilon) {
        if (alpha >= 1.0 - 1.0 / Math.sqrt(2.0) || alpha <= 0.0) {
            throw new RuntimeException("alpha must be > 0 && < 1 - (1/sqrt(2))");
        } else if (epsilon > alpha * alpha - 2.0 * alpha + 0.5 || epsilon <= 0.0) {
            throw new RuntimeException("epsilon must be > 0 && <= alpha * alpha - 2.0 * alpha + 0.5");
        }

        this.alpha = alpha;
        this.epsilon = epsilon;
    }

    public void insert(T key) {
        if (root == null) {
            root = new BBaNode<>(key);
        } else {
            insert(root, key);
        }
    }

    private void insert(BBaNode<T> node, T key) {
        if (node == null) {
            throw new NullPointerException("node cannot be null");
        }

        Stack<BBaNode<T>> bBaNodeStack = new Stack<>();
        BBaNode<T> tmpNode = node;
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
            throw new RuntimeException("Inserted key '" + key + "' already exists.");
        }

        // key is distinct, now perform the insert
        if (tmpNode.key.compareTo(key) < 0) {
            tmpNode.addLeft(new BBaNode<>(tmpNode.key));
            tmpNode.addRight(new BBaNode<>(key));
            tmpNode.key = key;
        } else {
            tmpNode.addLeft(new BBaNode<>(key));
            tmpNode.addRight(new BBaNode<>(tmpNode.key));
        }

        //tmpNode.weight = 2;
        updateWeight(tmpNode);
        rebalance(bBaNodeStack);
    }

    public void insertKeys(List<T> keys) {
        for (T key : keys) {
            insert(key);
        }
    }

    private void rebalance(Stack<BBaNode<T>> bBaNodeStack) {
        BBaNode<T> tmpNode;
        /* rebalance */
        while (!bBaNodeStack.isEmpty()) {
            tmpNode = bBaNodeStack.pop();

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

    private BBaNode<T> rightRotation(BBaNode<T> node) {
        //System.out.println("rightRotation: " + node.key);
        BBaNode<T> oldNode = node;
        node = oldNode.left;
        if (oldNode.parent == null) {
            root = node;
            node.parent = null;
            oldNode.addLeft(node.right);
            node.addRight(oldNode);
        } else {
            BBaNode<T> parent = oldNode.parent;
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

    private BBaNode<T> leftRotation(BBaNode<T> node) {
        //System.out.println("leftRotation: " + node.key);
        BBaNode<T> oldNode = node;
        node = oldNode.right;
        if (oldNode.parent == null) {
            root = node;
            node.parent = null;
            oldNode.addRight(node.left);
            node.addLeft(oldNode);
        } else {
            BBaNode<T> parent = oldNode.parent;
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
        BBaNode<T> node = root;
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

    public BBaNode<T> getLeaf(T key) {
        BBaNode<T> node = root;
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

    public boolean delete(T key) {
        if (root == null) {
            return false;
        }

        Stack<BBaNode<T>> bBaNodeStack = new Stack<>();
        BBaNode<T> tmpNode = root;
        while (!tmpNode.isLeaf()) {
            bBaNodeStack.push(tmpNode);
            if (key.compareTo(tmpNode.key) < 0) {
                tmpNode = tmpNode.left;
            } else {
                tmpNode = tmpNode.right;
            }
        }

        // found the candidate leaf. Test whether key is same
        if (tmpNode.key.compareTo(key) != 0) {
            return false;
        }

        // key is same, now perform the deletion
        if (tmpNode == root) {
            // root && leaf -> only this key in tree
            root = null;
            return true;

        } else {
            BBaNode<T> parent = tmpNode.parent;
            if (parent.left == tmpNode) {
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

    private void updateWeight(BBaNode<T> node) {
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

    private void updateKey(BBaNode<T> node) {
        if (node.isLeaf()) {
            return;
        }

        while (node != null) {
            node.key = (node.left.key.compareTo(node.right.key) > 0) ? node.left.key : node.right.key;
            //System.out.println("new :" + node);
            node = node.parent;
        }
    }

    public List<BBaNode<T>> inOrder() {
        return inOrder(root);
    }

    private List<BBaNode<T>> inOrder(BBaNode<T> node) {
        Stack<BBaNode<T>> nodeStack = new Stack<>();
        List<BBaNode<T>> nodes = new ArrayList<>();
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
