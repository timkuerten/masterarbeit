package datastructure.Trees;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BinarySearchTreeTest {

    private BinarySearchTree<Integer> binarySearchTree;
    private BinaryNode<Integer> innerNode, leftLeaf, rightLeaf;

    @Before
    public void setUp() {
        binarySearchTree = new BinarySearchTree<>(4);
        innerNode = binarySearchTree.insert(2);
        binarySearchTree.insert(1);
        binarySearchTree.insert(3);
        binarySearchTree.insert(7);
        binarySearchTree.insert(6);
        leftLeaf = binarySearchTree.insert(5);
        binarySearchTree.insert(8);
        rightLeaf = binarySearchTree.insert(9);
    }

    @Test
    public void getNode() {
        assertThat(binarySearchTree.get(2), is(innerNode));
    }

    @Test
    public void findNode() {
        assertThat(binarySearchTree.find(6), is(true));
    }

    @Test
    public void insertNode() {
        assertThat(binarySearchTree.find(10), is(false));
        BinaryNode<Integer> insertedNode = binarySearchTree.insert(10);
        assertThat(binarySearchTree.find(10), is(true));
        assertThat(insertedNode.parent, is(rightLeaf));
        assertThat(rightLeaf.right, is(insertedNode));
    }

    @Test
    public void deleteNodeLeaf() {
        BinaryNode<Integer> parent = rightLeaf.parent;
        assertThat(binarySearchTree.delete(9), is(true));
        assertThat(parent.right, is(nullValue()));
    }

    @Test
    public void deleteNodeOnlyRightChild() {
        BinaryNode<Integer> parent = binarySearchTree.get(8).parent;
        assertThat(binarySearchTree.delete(8), is(true));
        assertThat(parent.right, is(rightLeaf));
    }

    @Test
    public void deleteNodeOnlyLeftChild() {
        BinaryNode<Integer> parent = binarySearchTree.get(6).parent;
        assertThat(binarySearchTree.delete(6), is(true));
        assertThat(parent.left, is(leftLeaf));
    }

    @Test
    public void deleteInnerNode() {
        assertThat(binarySearchTree.delete(2), is(true));
        assertThat(binarySearchTree.root.left, is(binarySearchTree.get(3)));
    }

}
