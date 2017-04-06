package datastructure.Trees;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BinaryNode<T extends Comparable<? super T>> {

    public BinaryNode<T> left;
    public BinaryNode<T> right;
    public BinaryNode<T> parent = null;
    public T key;

    public BinaryNode(T key) {
        setKey(key);
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

    private void setKey(T key) {
        if (key == null) {
            throw new NullPointerException("Data cannot be null");
        }
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    public boolean isRoot() {
        return (parent == null);
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    // traverse

    public List<BinaryNode<T>> preOrder() {
        if (this.isRoot()) {
            return Collections.emptyList();
        }

        Stack<BinaryNode<T>> integerStack = new Stack<>();
        integerStack.push(this);
        List<BinaryNode<T>> nodes = new ArrayList<>();
        BinaryNode<T> node;
        while (!integerStack.isEmpty()) {
            node = integerStack.pop();
            nodes.add(node);
            if (node.right != null) {
                integerStack.push(node.right);
            }
            if (node.left != null) {
                integerStack.push(node.left);
            }
        }
        return nodes;
    }

    public List<BinaryNode<T>> inOrder() {
        Stack<BinaryNode<T>> integerStack = new Stack<>();
        List<BinaryNode<T>> nodes = new ArrayList<>();
        BinaryNode<T> node = this;
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

    public List<BinaryNode<T>> postOrder() {
        Stack<BinaryNode<T>> integerStack = new Stack<>();
        List<BinaryNode<T>> nodes = new ArrayList<>();
        BinaryNode<T> lastVisitedNode = null;
        BinaryNode<T> peekNode;
        BinaryNode<T> node = this;
        while (!integerStack.isEmpty() || node != null) {
            if (node != null) {
                integerStack.push(node);
                node = node.left;
            } else {
                peekNode = integerStack.peek();
                if (peekNode.right != null && lastVisitedNode != peekNode.left) {
                    node = peekNode.right;
                } else {
                    nodes.add(peekNode);
                    lastVisitedNode = integerStack.pop();
                }
            }
        }
        return nodes;
    }

    public List<BinaryNode<T>> reverseLevelOrder() {
        if (this.isRoot()) {
            return Collections.emptyList();
        }

        Stack<BinaryNode<T>> integerStack = new Stack<>();
        Stack<BinaryNode<T>> integerStack2 = new Stack<>();
        Stack<BinaryNode<T>> integerStack3 = new Stack<>();
        List<BinaryNode<T>> nodes = new ArrayList<>();
        integerStack2.push(this);

        while (!integerStack2.isEmpty()) {
            while (!integerStack2.isEmpty()) {
                integerStack.push(integerStack2.pop());
            }
            while (!integerStack.isEmpty()) {
                if (integerStack.peek().right != null) {
                    integerStack2.push(integerStack.peek().right);
                }
                if (integerStack.peek().left != null) {
                    integerStack2.push(integerStack.peek().left);
                }
                integerStack3.push(integerStack.pop());
            }
        }

        while (!integerStack3.isEmpty()) {
            nodes.add(integerStack3.pop());
        }

        return nodes;
    }

    @Override
    public String toString() {
        return "<node " + this.key + ">";
    }

}
