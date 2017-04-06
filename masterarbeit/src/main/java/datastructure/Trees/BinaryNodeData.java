package datastructure.Trees;

import exception.Trees.DataNullPointerException;
import exception.Trees.NoParentException;
import exception.Trees.NodeNullPointerException;
import javafx.util.Pair;

import java.util.*;

public class BinaryNodeData<T extends Comparable<? super T>, U> {

    public BinaryNodeData<T, U> left;
    public BinaryNodeData<T, U> right;
    public BinaryNodeData<T, U> parent = null;
    public T key;
    public Set<U> database;

    public BinaryNodeData(T key, U data) {
        setKey(key);
        insertData(data);
    }

    public BinaryNodeData<T, U> addLeftChild(T key, U data) {
        return addLeftChild(new BinaryNodeData<>(key, data));
    }

    public BinaryNodeData<T, U> addRightChild(T key, U data) {
        return addRightChild(new BinaryNodeData<>(key, data));
    }

    public BinaryNodeData<T, U> addLeftChild(BinaryNodeData<T, U> child) {
        if (child == null) {
            throw new NodeNullPointerException();
        }
        if (left != null) {
            throw new RuntimeException("Left child cannot add because it exists already");
        }
        child.parent = this;
        left = child;
        return child;
    }

    public BinaryNodeData<T, U> addRightChild(BinaryNodeData<T, U> child) {
        if (child == null) {
            throw new NodeNullPointerException();
        }
        if (right != null) {
            throw new RuntimeException("Right child cannot add because it exists already");
        }
        child.parent = this;
        right = child;
        return child;
    }

    public BinaryNodeData<T, U> getLeftChild() {
        return left;
    }

    public BinaryNodeData<T, U> getRightChild() {
        return right;
    }

    public void addChildren(BinaryNodeData<T, U> left, BinaryNodeData<T, U> right) {
        addLeftChild(left);
        addRightChild(right);
    }

    public Pair<BinaryNodeData<T, U>, BinaryNodeData<T, U>> addChildren(T keyLeft, U dataLeft, T keyRight, U dataRight) {
        BinaryNodeData<T, U> left = new BinaryNodeData<>(keyLeft, dataLeft);
        BinaryNodeData<T, U> right = new BinaryNodeData<>(keyRight, dataRight);
        addChildren(left, right);
        return new Pair<>(left, right);
    }

    public Pair<BinaryNodeData<T, U>, BinaryNodeData<T, U>> getChildren() {
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

    public BinaryNodeData<T, U> getParent() {
        return parent;
    }

    public void removeParent() {
        if (this.parent == null) {
            throw new NoParentException(this);
        }

        if (parent.getLeftChild() == this) {
            parent.removeLeftChild();
        } else {
            removeRightChild();
        }
    }

    private void setKey(T key) {
        if (key == null) {
            throw new DataNullPointerException();
        }
        this.key = key;
    }

    public void insertData(U data) {
        if (data == null) {
            throw new DataNullPointerException();
        } else {
            database.add(data);
        }
    }

    public T getKey() {
        return key;
    }

    public Set<U> getDatabase() {
        return database;
    }

    public boolean isRoot() {
        return (parent == null);
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    // traverse

    public List<BinaryNodeData<T, U>> preOrder() {
        if (this.isRoot()) {
            return Collections.emptyList();
        }

        Stack<BinaryNodeData<T, U>> nodeStack = new Stack<>();
        nodeStack.push(this);
        List<BinaryNodeData<T, U>> nodes = new ArrayList<>();
        BinaryNodeData<T, U> node;
        while (!nodeStack.isEmpty()) {
            node = nodeStack.pop();
            nodes.add(node);
            if (node.right != null) {
                nodeStack.push(node.right);
            }
            if (node.left != null) {
                nodeStack.push(node.left);
            }
        }
        return nodes;
    }

    public List<BinaryNodeData<T, U>> inOrder() {
        Stack<BinaryNodeData<T, U>> nodeStack = new Stack<>();
        List<BinaryNodeData<T, U>> nodes = new ArrayList<>();
        BinaryNodeData<T, U> node = this;
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

    public List<BinaryNodeData<T, U>> postOrder() {
        Stack<BinaryNodeData<T, U>> nodeStack = new Stack<>();
        List<BinaryNodeData<T, U>> nodes = new ArrayList<>();
        BinaryNodeData<T, U> lastVisitedNode = null;
        BinaryNodeData<T, U> peekNode;
        BinaryNodeData<T, U> node = this;
        while (!nodeStack.isEmpty() || node != null) {
            if (node != null) {
                nodeStack.push(node);
                node = node.left;
            } else {
                peekNode = nodeStack.peek();
                if (peekNode.right != null && lastVisitedNode != peekNode.left) {
                    node = peekNode.right;
                } else {
                    nodes.add(peekNode);
                    lastVisitedNode = nodeStack.pop();
                }
            }
        }
        return nodes;
    }

    public List<BinaryNodeData<T, U>> reverseLevelOrder() {
        if (this.isRoot()) {
            return Collections.emptyList();
        }

        Stack<BinaryNodeData<T, U>> nodeStack = new Stack<>();
        Stack<BinaryNodeData<T, U>> nodeStack2 = new Stack<>();
        Stack<BinaryNodeData<T, U>> nodeStack3 = new Stack<>();
        List<BinaryNodeData<T, U>> nodes = new ArrayList<>();
        nodeStack2.push(this);

        while (!nodeStack2.isEmpty()) {
            while (!nodeStack2.isEmpty()) {
                nodeStack.push(nodeStack2.pop());
            }
            while (!nodeStack.isEmpty()) {
                if (nodeStack.peek().right != null) {
                    nodeStack2.push(nodeStack.peek().right);
                }
                if (nodeStack.peek().left != null) {
                    nodeStack2.push(nodeStack.peek().left);
                }
                nodeStack3.push(nodeStack.pop());
            }
        }

        while (!nodeStack3.isEmpty()) {
            nodes.add(nodeStack3.pop());
        }

        return nodes;
    }

    @Override
    public String toString() {
        return "<node " + this.key + ">";
    }

}
