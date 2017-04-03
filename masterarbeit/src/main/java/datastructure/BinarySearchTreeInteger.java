package datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class BinarySearchTreeInteger {

    BinaryNodeInteger root;

    public BinarySearchTreeInteger(int i) {
        root = new BinaryNodeInteger(i);
    }

    public BinaryNodeInteger insert(int key) {
        BinaryNodeInteger node = root;
        while (true) {
            if (node.key == key) {
                throw new RuntimeException("inserted key exists");
            } else if (key > node.key) {
                if (node.right == null) {
                    return node.addRightChild(new BinaryNodeInteger(key));
                } else {
                    node = node.right;
                }
            } else {
                if (node.left == null) {
                    return node.addLeftChild(new BinaryNodeInteger(key));
                } else {
                    node = node.left;
                }
            }
        }
    }

    public BinaryNodeInteger get(int key) {
        BinaryNodeInteger node = root;
        while (node != null) {
            if (node.key == key) {
                return node;
            } else if (node.key < key) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return null;
    }

    public boolean find(int key) {
        return (get(key) != null);
    }

    public boolean delete(int key) {
        BinaryNodeInteger node = get(key);
        if (node == null || root == null) {
            return false;
        }

        // node to be removed has two children
        if (node.right != null && node.left != null) {
            BinaryNodeInteger node2 = inOrder(node.right).get(0);
            node.key = node2.key;
            node = node2;
        }

        // node to be removed is leaf
        if (node.isLeaf()) {
            if (node == root) {
                root = null;
                return true;
            }
            BinaryNodeInteger parent = node.parent;

            if (parent.left != null && parent.left == node) {
                parent.left = null;
            } else {
                parent.right = null;
            }
            return true;

        } else {
            // node to be removed has one child
            BinaryNodeInteger child;
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

            BinaryNodeInteger parent = node.parent;
            if (parent.left == node) {
                parent.left = child;
            } else {
                parent.right = child;
            }
            return true;
        }
    }

    public List<BinaryNodeInteger> preOrder() {
        return preOrder(root);
    }

    private List<BinaryNodeInteger> preOrder(BinaryNodeInteger node) {
        if (node == null) {
            return Collections.emptyList();
        }

        Stack<BinaryNodeInteger> integerStack = new Stack<>();
        integerStack.push(node);
        List<BinaryNodeInteger> nodes = new ArrayList<>();
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

    public List<BinaryNodeInteger> inOrder() {
        return inOrder(root);
    }

    private List<BinaryNodeInteger> inOrder(BinaryNodeInteger node) {
        Stack<BinaryNodeInteger> integerStack = new Stack<>();
        List<BinaryNodeInteger> nodes = new ArrayList<>();
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

    public List<BinaryNodeInteger> postOrder() {
        return postOrder(root);
    }

    private List<BinaryNodeInteger> postOrder(BinaryNodeInteger node) {
        Stack<BinaryNodeInteger> integerStack = new Stack<>();
        List<BinaryNodeInteger> nodes = new ArrayList<>();
        BinaryNodeInteger lastVisitedNode = null;
        BinaryNodeInteger peekNode;
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

    public List<BinaryNodeInteger> reverseLevelOrder() {
        return reverseLevelOrder(root);
    }

    private List<BinaryNodeInteger> reverseLevelOrder(BinaryNodeInteger node) {
        if (node == null) {
            return Collections.emptyList();
        }

        Stack<BinaryNodeInteger> integerStack = new Stack<>();
        Stack<BinaryNodeInteger> integerStack2 = new Stack<>();
        Stack<BinaryNodeInteger> integerStack3 = new Stack<>();
        List<BinaryNodeInteger> nodes = new ArrayList<>();
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
