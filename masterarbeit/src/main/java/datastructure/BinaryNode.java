package datastructure;

import javafx.util.Pair;

public class BinaryNode<T extends Comparable<? super T>> {

    public BinaryNode<T> left;
    public BinaryNode<T> right;
    public BinaryNode<T> parent = null;
    public T data;

    public BinaryNode(T data) {
        setData(data);
    }

    public BinaryNode<T> addLeftChild(T data) {
        return addLeftChild(new BinaryNode<>(data));
    }

    public BinaryNode<T> addRightChild(T data) {
        return addRightChild(new BinaryNode<>(data));
    }

    public BinaryNode<T> addLeftChild(BinaryNode<T> child) {
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

    public BinaryNode<T> addRightChild(BinaryNode<T> child) {
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

    public BinaryNode<T> getLeftChild() {
        return left;
    }

    public BinaryNode<T> getRightChild() {
        return right;
    }

    public void addChildren(BinaryNode<T> left, BinaryNode<T> right) {
        addLeftChild(left);
        addRightChild(right);
    }

    public Pair<BinaryNode<T>, BinaryNode<T>> addChildren(T dataLeft, T dataRight) {
        BinaryNode<T> left = new BinaryNode<>(dataLeft);
        BinaryNode<T> right = new BinaryNode<>(dataRight);
        addChildren(left, right);
        return new Pair<>(left, right);
    }

    public Pair<BinaryNode<T>, BinaryNode<T>> getChildren() {
        return new Pair<>(left, right);
    }

    public void removeLeftChild() {
        if (left != null) {
            left.parent = null;
            left = null;
        }
    }

    public void removeRightChild() {
        if (right != null) {
            right.parent = null;
            right = null;
        }
    }

    public void removeChildren() {
        removeLeftChild();
        removeRightChild();
    }

    public BinaryNode<T> getParent() {
        return parent;
    }

    public void removeParent() {
        if (this.parent == null) {
            throw new NullPointerException("Node " + this + " has no parent");
        }

        if (parent.getLeftChild() == this) {
            parent.removeLeftChild();
        } else {
            removeRightChild();
        }
    }

    private void setData(T data) {
        if (data == null) {
            throw new NullPointerException("Data cannot be null");
        }
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public boolean isRoot() {
        return (parent == null);
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    @Override
    public String toString() {
        return "<node " + this.data + ">";
    }

}
