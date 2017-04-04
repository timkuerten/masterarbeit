package datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BBaTree {

    BBaNode root;
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

    public void insert(int key) {
        if (root == null) {
            root = new BBaNode(key);
        } else {
            insert(root, key);
        }
    }

    private void insert(BBaNode node, int key) {
        if (node == null) {
            throw new NullPointerException("node cannot be null");
        }

        Stack<BBaNode> bBaNodeStack = new Stack<>();
        BBaNode tmpNode = node;
        while (!tmpNode.isLeaf()) {
            bBaNodeStack.push(tmpNode);
            if (key < tmpNode.key) {
                tmpNode = tmpNode.left;
            } else {
                tmpNode = tmpNode.right;
            }
        }
        // found the candidate leaf. Test whether key distinct

        if (tmpNode.key == key) {
            throw new RuntimeException("Inserted key '" + key + "' already exists.");
        }

        // key is distinct, now perform the insert
        if (tmpNode.key < key) {
            tmpNode.addLeft(new BBaNode(tmpNode.key));
            tmpNode.addRight(new BBaNode(key));
            tmpNode.key = key;
        } else {
            tmpNode.addLeft(new BBaNode(key));
            tmpNode.addRight(new BBaNode(tmpNode.key));
        }
        //tmpNode.weight = 2;
        updateWeight(tmpNode);
        //System.out.println("set new tmpNode key: " + tmpNode.key + " , l: " + tmpNode.left.key + ", r:" + tmpNode.right.key);
        rebalance(bBaNodeStack);

    }

    public void insertKeys(List<Integer> keys) {
        for (Integer key : keys) {
            insert(key);
        }
    }

    private void rebalance(Stack<BBaNode> bBaNodeStack) {
        BBaNode tmpNode;
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

    private BBaNode rightRotation(BBaNode node) {
        //System.out.println("rightRotation: " + node.key);
        BBaNode oldNode = node;
        node = oldNode.left;
        if (oldNode.parent == null) {
            root = node;
            node.parent = null;
            oldNode.addLeft(node.right);
            node.addRight(oldNode);
        } else {
            BBaNode parent = oldNode.parent;
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

    private BBaNode leftRotation(BBaNode node) {
        //System.out.println("leftRotation: " + node.key);
        BBaNode oldNode = node;
        node = oldNode.right;
        if (oldNode.parent == null) {
            root = node;
            node.parent = null;
            oldNode.addRight(node.left);
            node.addLeft(oldNode);
        } else {
            BBaNode parent = oldNode.parent;
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

    public boolean find(int key) {
        BBaNode node = root;
        while (node != null) {
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                return true;
            }
        }
        return false;
    }

    public BBaNode getLeaf(int key) {
        BBaNode node = root;
        while (!node.isLeaf()) {
            if (key < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        if (node.key == key) {
            return node;
        } else {
            return null;
        }
    }

    public boolean delete(int key) {
        if (root == null) {
            return false;
        }

        Stack<BBaNode> bBaNodeStack = new Stack<>();
        BBaNode tmpNode = root;
        while (!tmpNode.isLeaf()) {
            bBaNodeStack.push(tmpNode);
            if (key < tmpNode.key) {
                tmpNode = tmpNode.left;
                System.out.println("go left");
            } else {
                tmpNode = tmpNode.right;

                System.out.println("go right");
            }
        }
        // found the candidate leaf. Test whether key is same

        System.out.println(tmpNode);

        if (tmpNode.key != key) {
            return false;
        }

        // key is same, now perform the deletion
        if (tmpNode == root) {
            // root && leaf -> only this key in tree
            root = null;
            return true;

        } else {
            BBaNode parent = tmpNode.parent;
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

    private void updateWeight(BBaNode node) {
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

    private void updateKey(BBaNode node) {
        if (node.isLeaf()) {
            return;
        }
        while (node != null) {
            node.key = Math.max(node.left.key, node.right.key);
            //System.out.println("new :" + node);
            node = node.parent;
        }
    }

    public List<BBaNode> inOrder() {
        return inOrder(root);
    }

    private List<BBaNode> inOrder(BBaNode node) {
        Stack<BBaNode> integerStack = new Stack<>();
        List<BBaNode> nodes = new ArrayList<>();
        while (!integerStack.isEmpty() || node != null) {
            if (node != null) {
                integerStack.push(node);
                node = node.left;
            } else {
                node = integerStack.pop();
                nodes.add(node);
                node = node.right;
            }
        }
        return nodes;
    }

}
