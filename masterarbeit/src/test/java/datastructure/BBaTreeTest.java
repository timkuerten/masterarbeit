package datastructure;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BBaTreeTest {

    private BBaTree bBaTree;

    @Before
    public void SetUp() {
        bBaTree = new BBaTree();
    }

    private void printTree() {
        System.out.println(bBaTree.root);
    }

    @Test
    public void insert() {
        bBaTree.insert(2);
        assertThat(bBaTree.root.weight, is(1));
        bBaTree.insert(1);
        assertThat(bBaTree.root.weight, is(2));
        bBaTree.insert(3);
        assertThat(bBaTree.root.weight, is(3));
    }

    @Test
    public void insertLeftRotation() {
        insert();
        bBaTree.insert(4);
        assertThat(bBaTree.root.weight, is(4));
    }

    @Test
    public void insertRightRotation() {
        insert();
        bBaTree.insert(0);
        bBaTree.insert(-1);
        bBaTree.insert(-2);
        assertThat(bBaTree.root.weight, is(6));
    }

    @Test
    public void delete() {
        insert();
        bBaTree.delete(2);
        assertThat(bBaTree.root.weight, is(2));
    }

}
