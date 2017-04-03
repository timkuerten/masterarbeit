package datastructure;

import javafx.util.Pair;

public class BinaryNodeInteger {

    public BinaryNodeInteger left;
    public BinaryNodeInteger right;
    public BinaryNodeInteger parent = null;
    public int key;

    public BinaryNodeInteger(int key) {
        this.key = key;
    }

    public BinaryNodeInteger addLeftChild(BinaryNodeInteger child) {
        if (child == null) {
            throw new NullPointerException("Child cannot be null");
        }
        if (left != null) {
            throw new RuntimeException("Left child cannot add because it exists already");
        }
        child.parent = this;
        left = child;
        return child;
    }

    public BinaryNodeInteger addRightChild(BinaryNodeInteger child) {
        if (child == null) {
            throw new NullPointerException("Child cannot be null");
        }
        if (right != null) {
            throw new RuntimeException("Right child cannot add because it exists already");
        }
        child.parent = this;
        right = child;
        return child;
    }

    public boolean isRoot() {
        return (parent == null);
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    @Override
    public String toString() {
        return "<node " + key + ">";
    }

}
