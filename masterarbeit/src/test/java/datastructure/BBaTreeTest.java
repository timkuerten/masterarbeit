package datastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
        //System.out.println(bBaTree.inOrder());
    }

    @Test
    public void insertLeftRotation() {
        insert();
        bBaTree.insert(4);
        assertThat(bBaTree.root.weight, is(4));
        printTree();
    }

    @Test
    public void insertRightRotation() {
        insert();
        bBaTree.insert(0);
        bBaTree.insert(-1);
        bBaTree.insert(-2);
        assertThat(bBaTree.root.weight, is(6));
        printTree();
    }

    @Test
    public void delete() {
        insert();
        bBaTree.delete(2);
        System.out.println(bBaTree.root);
        assertThat(bBaTree.root.weight, is(2));
    }

    @Test
    public void insertKeys(){
        bBaTree.insertKeys(new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18)));
        printTree();
    }
}
