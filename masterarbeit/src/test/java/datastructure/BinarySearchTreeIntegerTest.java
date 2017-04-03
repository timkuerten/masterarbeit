package datastructure;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BinarySearchTreeIntegerTest {

    private BinarySearchTreeInteger binarySearchTreeInteger;
    private BinaryNodeInteger innerNode, leftLeaf, rightLeaf;

    @Before
    public void setUp() {
        binarySearchTreeInteger = new BinarySearchTreeInteger(4);
        innerNode = binarySearchTreeInteger.insert(2);
        binarySearchTreeInteger.insert(1);
        binarySearchTreeInteger.insert(3);
        binarySearchTreeInteger.insert(7);
        binarySearchTreeInteger.insert(6);
        leftLeaf = binarySearchTreeInteger.insert(5);
        binarySearchTreeInteger.insert(8);
        rightLeaf = binarySearchTreeInteger.insert(9);

    }

    @Ignore
    @Test
    public void printInOrder() {
        System.out.println("inOrder: " + binarySearchTreeInteger.inOrder());
    }

    @Ignore
    @Test
    public void printPreOrder() {
        System.out.println("preOrder: " + binarySearchTreeInteger.preOrder());
    }

    @Ignore
    @Test
    public void printPostOrder() {
        System.out.println("postOrder: " + binarySearchTreeInteger.postOrder());
    }

    @Ignore
    @Test
    public void printReverseLevelOrder() {
        System.out.println("reverseLevelOrder: " + binarySearchTreeInteger.reverseLevelOrder());
    }

    @Ignore
    @Test
    public void printNode() {
        System.out.println(
                innerNode
                        + "key: " + innerNode.key + ", l: " + innerNode.left + ", r: " + innerNode.right + ", p: " + innerNode.parent);
    }

    @Test
    public void getNode() {
        assertThat(binarySearchTreeInteger.get(2), is(innerNode));
    }

    @Test
    public void findNode() {
        assertThat(binarySearchTreeInteger.find(6), is(true));
    }

    @Test
    public void insertNode() {
        assertThat(binarySearchTreeInteger.find(10), is(false));
        BinaryNodeInteger insertedNode = binarySearchTreeInteger.insert(10);
        assertThat(binarySearchTreeInteger.find(10), is(true));
        assertThat(insertedNode.parent, is(rightLeaf));
        assertThat(rightLeaf.right, is(insertedNode));
    }

    @Test
    public void deleteNodeLeaf() {
        BinaryNodeInteger parent = rightLeaf.parent;
        assertThat(binarySearchTreeInteger.delete(9), is(true));
        assertThat(parent.right, is(nullValue()));
    }

    @Test
    public void deleteNodeOnlyRightChild() {
        BinaryNodeInteger parent = binarySearchTreeInteger.get(8).parent;
        assertThat(binarySearchTreeInteger.delete(8), is(true));
        assertThat(parent.right, is(rightLeaf));
    }

    @Test
    public void deleteNodeOnlyLeftChild() {
        BinaryNodeInteger parent = binarySearchTreeInteger.get(6).parent;
        assertThat(binarySearchTreeInteger.delete(6), is(true));
        assertThat(parent.left, is(leftLeaf));
    }

    @Test
    public void deleteInnerNode() {
        assertThat(binarySearchTreeInteger.delete(2), is(true));
        assertThat(binarySearchTreeInteger.root.left, is(binarySearchTreeInteger.get(3)));
    }
}
