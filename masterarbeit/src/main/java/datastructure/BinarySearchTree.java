package datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> {

    BinaryNode<T> root;

    public BinarySearchTree(T data) {
        root = new BinaryNode<>(data);
    }

    public BinaryNode<T> insert(T data) {
        BinaryNode<T> node = root;
        while (true) {
            if (node.data.compareTo(data) == 0) {
                throw new RuntimeException("inserted key exists");
            } else if (data.compareTo(node.data) > 0) {
                if (node.right == null) {
                    return node.addRightChild(new BinaryNode<>(data));
                } else {
                    node = node.right;
                }
            } else {
                if (node.left == null) {
                    return node.addLeftChild(new BinaryNode<>(data));
                } else {
                    node = node.left;
                }
            }
        }
    }

    public BinaryNode<T> get(T data) {
        BinaryNode<T> node = root;
        while (node != null) {
            if (node.data.compareTo(data) == 0) {
                return node;
            } else if (node.data.compareTo(data) < 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    public boolean find(T data) {
        return (get(data) != null);
    }

    public boolean delete(T data) {
        BinaryNode<T> node = get(data);
        if (node == null || root == null) {
            return false;
        }

        // node to be removed has two children
        if (node.right != null && node.left != null) {
            BinaryNode<T> node2 = inOrder(node.right).get(0);
            node.data = node2.data;
            node = node2;
        }

        // node to be removed is leaf
        if (node.isLeaf()) {
            if (node == root) {
                root = null;
                return true;
            }
            BinaryNode<T> parent = node.parent;

            if (parent.left != null && parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            return true;

        } else {
            // node to be removed has one child
            BinaryNode<T> child;
            if (node.left != null) {
                if (node == root) {
                    root = node.left;
                    return true;
                }

                child = node.left;
            } else {
                if (node == root) {
                    root = node.right;
                    return true;
                }

                child = node.right;
            }

            BinaryNode<T> parent = node.parent;
            if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            return true;
        }
    }

    public List<BinaryNode<T>> preOrder() {
        return preOrder(root);
    }

    private List<BinaryNode<T>> preOrder(BinaryNode<T> node) {
        if (node == null) {
            return Collections.emptyList();
        }

        Stack<BinaryNode<T>> integerStack = new Stack<>();
        integerStack.push(node);
        List<BinaryNode<T>> nodes = new ArrayList<>();
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
        return inOrder(root);
    }

    private List<BinaryNode<T>> inOrder(BinaryNode<T> node) {
        Stack<BinaryNode<T>> integerStack = new Stack<>();
        List<BinaryNode<T>> nodes = new ArrayList<>();
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
        return postOrder(root);
    }

    private List<BinaryNode<T>> postOrder(BinaryNode<T> node) {
        Stack<BinaryNode<T>> integerStack = new Stack<>();
        List<BinaryNode<T>> nodes = new ArrayList<>();
        BinaryNode<T> lastVisitedNode = null;
        BinaryNode<T> peekNode;
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
        return reverseLevelOrder(root);
    }

    private List<BinaryNode<T>> reverseLevelOrder(BinaryNode<T> node) {
        if (node == null) {
            return Collections.emptyList();
        }

        Stack<BinaryNode<T>> integerStack = new Stack<>();
        Stack<BinaryNode<T>> integerStack2 = new Stack<>();
        Stack<BinaryNode<T>> integerStack3 = new Stack<>();
        List<BinaryNode<T>> nodes = new ArrayList<>();
        integerStack2.push(node);

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

}
