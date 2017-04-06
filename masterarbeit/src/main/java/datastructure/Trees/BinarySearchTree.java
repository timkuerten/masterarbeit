package datastructure.Trees;

import datastructure.Trees.BinaryNode;
import exception.Trees.KeyAlreadyExistsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> {

    BinaryNode<T> root;

    public BinarySearchTree() {

    }

    public BinarySearchTree(T key) {
        root = new BinaryNode<>(key);
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public BinaryNode<T> insert(T key) {
        BinaryNode<T> node = root;
        while (true) {
            if (node.key.compareTo(key) == 0) {
                throw new KeyAlreadyExistsException(key.toString());
            } else if (key.compareTo(node.key) > 0) {
                if (node.right == null) {
                    return node.addRightChild(new BinaryNode<>(key));
                } else {
                    node = node.right;
                }
            } else {
                if (node.left == null) {
                    return node.addLeftChild(new BinaryNode<>(key));
                } else {
                    node = node.left;
                }
            }
        }
    }

    public BinaryNode<T> get(T key) {
        BinaryNode<T> node = root;
        while (node != null) {
            if (node.key.compareTo(key) == 0) {
                return node;
            } else if (node.key.compareTo(key) < 0) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    public boolean find(T key) {
        return (get(key) != null);
    }

    public boolean delete(T key) {
        BinaryNode<T> node = get(key);
        if (node == null || root == null) {
            return false;
        }

        // node to be removed has two children
        if (node.right != null && node.left != null) {
            BinaryNode<T> node2 = node.right.inOrder().get(0);
            node.key = node2.key;
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

    // traverse

    public List<BinaryNode<T>> preOrder() {
        return (root == null) ? Collections.emptyList() : root.preOrder();
    }

    public List<BinaryNode<T>> inOrder() {
        return (root == null) ? Collections.emptyList() : root.inOrder();
    }

    public List<BinaryNode<T>> postOrder() {
        return (root == null) ? Collections.emptyList() : root.postOrder();
    }

    public List<BinaryNode<T>> reverseLevelOrder() {
        return (root == null) ? Collections.emptyList() : root.reverseLevelOrder();
    }

}
