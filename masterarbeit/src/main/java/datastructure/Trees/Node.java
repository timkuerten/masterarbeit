package datastructure.Trees;

import exception.Trees.DataNullPointerException;
import exception.Trees.NoParentException;
import exception.Trees.NodeNullPointerException;

import java.util.*;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Node<T> {

    private T key;
    private Node<T> parent = null;
    private Collection<Node<T>> children = new HashSet<>();

    public Node(T key) {
        setKey(key);
    }

    public Node(T key, Node<T> parent) {
        this(key);
        setParent(parent);
    }

    public Node<T> addChild(T data) {
        return addChild(new Node<>(data));
    }

    public Node<T> addChild(Node<T> child) {
        child.parent = this;
        children.add(child);
        return child;
    }

    public void addChildren(Collection<Node<T>> children) {
        children.forEach(this::addChild);
    }

    public Collection<Node<T>> addChildren(List<T> dataList) {
        Set<Node<T>> newChildren = dataList.stream().map((Function<T, Node<T>>) Node::new).collect(Collectors.toSet());
        addChildren(newChildren);
        return Collections.unmodifiableCollection(newChildren);
    }

    public Collection<Node<T>> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    public void removeChild(Node<T> child) {
        if (child == null) {
            throw new NodeNullPointerException();
        }

        if (!this.children.contains(child)) {
            throw new RuntimeException("Node " + child + " is not a child of " + this);
        }
        child.parent = null;
        children.remove(child);
    }

    public void removeAllChildren() {
        while (!children.isEmpty()) {
            removeChild(children.iterator().next());
        }
    }

    public void removeChildren(Collection<Node<T>> children) {
        if (children == null) {
            throw new NodeNullPointerException();
        }
        if (!children.isEmpty()) {
            children.forEach(this::removeChild);
        }
    }

    private void setParent(Node<T> parent) {
        if (parent == null) {
            throw new NodeNullPointerException();
        }
        parent.addChild(this);
    }

    public Node<T> getParent() {
        return parent;
    }

    public void removeParent() {
        if (this.parent == null) {
            throw new NoParentException(this);
        }
        parent.removeChild(this);
    }

    private void setKey(T key) {
        if (key == null) {
            throw new DataNullPointerException();
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
        return (children.size() == 0);
    }

    public boolean hasChild(Node<T> node) {
        if (node == null) {
            throw new NodeNullPointerException();
        }
        return children.contains(node);
    }

    @Override
    public String toString() {
        return "<node " + this.key + ">";
    }

}
