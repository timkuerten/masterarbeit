package datastructure.Trees;

import exception.Trees.KeyAlreadyExistsException;

import java.util.Collections;
import java.util.List;

public class BinarySearchTreeData<T extends Comparable<? super T>, U extends Comparable<? super U>> {

    private BinaryNodeData<T, U> root;

    public BinarySearchTreeData() {

    }

    public BinarySearchTreeData(T key, U data) {
        root = new BinaryNodeData<>(key, data);
    }

    public boolean isEmpty() {
        return (root == null);
    }

    public BinaryNodeData<T, U> insert(T key, U data) {
        BinaryNodeData<T, U> node = root;
        while (true) {
            if (node.key.compareTo(key) == 0) {
                node.database.add(data);
                return node;
            } else if (key.compareTo(node.key) > 0) {
                if (node.right == null) {
                    return node.addRightChild(new BinaryNodeData<>(key, data));
                } else {
                    node = node.right;
                }
            } else {
                if (node.left == null) {
                    return node.addLeftChild(new BinaryNodeData<>(key, data));
                } else {
                    node = node.left;
                }
            }
        }
    }

    public BinaryNodeData<T, U> get(T key) {
        BinaryNodeData<T, U> node = root;
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

    public void delete(T key, U data) {
        BinaryNodeData<T, U> node = get(key);
        if (node != null && root != null && data != null && node.database != null && node.database.contains(data)) {
            // remove data
            node.database.remove(data);
            if (node.database.isEmpty()) {
                // delete node
                delete(node);
            }
        }

    }

    private void delete(BinaryNodeData<T, U> node) {
        // node to be removed has two children
        if (node.right != null && node.left != null) {
            BinaryNodeData<T, U> node2 = node.right.inOrder().get(0);
            node.key = node2.key;
            node = node2;
        }

        // node to be removed is leaf
        if (node.isLeaf()) {
            if (node == root) {
                root = null;
                return;
            }
            BinaryNodeData<T, U> parent = node.parent;

            if (parent.left != null && parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
        } else {
            // node to be removed has one child
            BinaryNodeData<T, U> child;
            if (node.left != null) {
                if (node == root) {
                    root = node.left;
                    return;
                }

                child = node.left;
            } else {
                if (node == root) {
                    root = node.right;
                    return;
                }

                child = node.right;
            }

            BinaryNodeData<T, U> parent = node.parent;
            if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
        }
    }

    // traverse

    public List<BinaryNodeData<T, U>> preOrder() {
        return (root == null) ? Collections.emptyList() : root.preOrder();
    }

    public List<BinaryNodeData<T, U>> inOrder() {
        return (root == null) ? Collections.emptyList() : root.inOrder();
    }

    public List<BinaryNodeData<T, U>> postOrder() {
        return (root == null) ? Collections.emptyList() : root.postOrder();
    }

    public List<BinaryNodeData<T, U>> reverseLevelOrder() {
        return (root == null) ? Collections.emptyList() : root.reverseLevelOrder();
    }

}
