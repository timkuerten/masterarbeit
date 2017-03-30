package datastructure;

import java.util.*;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Node<T> {

    private Collection<Node<T>> children = new HashSet<>();
    private Node<T> parent = null;
    private T data;

    public Node(T data) {
        setData(data);
    }

    public Node(T data, Node<T> parent) {
        this(data);
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

    public int getChildrenCount() {
        return children.size();
    }

    public void removeChild(Node<T> child) {
        if (child == null) {
            throw new NullPointerException("Child cannot be null");
        }

        if (!this.children.contains(child)) {
            throw new RuntimeException("Node " + child + " is not a child of " + this);
        }
        child.parent = null;
        children.remove(child);
    }

    public void removeChildren(Collection<Node<T>> children) {
        if (children == null) {
            throw new NullPointerException("Children cannot be null");
        }
        if (!children.isEmpty()) {
            children.forEach(this::removeChild);
        }
    }

    private void setParent(Node<T> parent) {
        if (parent == null) {
            throw new NullPointerException("Parent cannot be null");
        }
        parent.addChild(this);
    }

    public Node<T> getParent() {
        return parent;
    }

    public void removeParent() {
        if (this.parent == null) {
            throw new NullPointerException("Node " + this + " has no parent");
        }
        parent.removeChild(this);
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
        return (children.size() == 0);
    }

    public boolean hasChild(Node<T> node) {
        return node != null && children.contains(node);
    }

    @Override
    public String toString() {
        return "<node " + this.data + ">";
    }

}
