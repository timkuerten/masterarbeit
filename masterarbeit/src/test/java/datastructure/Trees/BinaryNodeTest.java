package datastructure.Trees;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BinaryNodeTest {

    private BinaryNode<Integer> rootInteger;
    private BinaryNode<String> rootString;

    @Before
    public void setUp() {
        rootInteger = new BinaryNode<>(0);
        rootString = new BinaryNode<>("a");
        addChildrenToRoots();
    }

    private void addChildrenToRoots() {
        if (rootInteger == null) {
            throw new NullPointerException("rootInteger is null");
        } else if (rootString == null) {
            throw new NullPointerException("rootString is null");
        }

        rootInteger.addChildren(1, 2);
        rootString.addChildren("b", "c");
    }

    @Test
    public void data() {
        assertThat(rootInteger.getKey(), is(0));
        assertThat(rootString.getKey(), is("a"));
    }

    @Test
    public void children() {
        assertThat(rootInteger.getLeftChild().getKey(), is(1));
        assertThat(rootInteger.getRightChild().getKey(), is(2));

        assertThat(rootString.getLeftChild().getKey(), is("b"));
        assertThat(rootString.getRightChild().getKey(), is("c"));
    }

    @Test
    public void isRoot() {
        assertThat(rootInteger.isRoot(), is(true));
        assertThat(rootInteger.getLeftChild().isRoot(), is(false));
    }

    @Test
    public void isLeaf() {
        assertThat(rootInteger.isLeaf(), is(false));
        assertThat(rootInteger.getLeftChild().isLeaf(), is(true));
    }

    @Test
    public void addChild() {
        assertThat(rootInteger.getLeftChild().getLeftChild(), is(nullValue()));
        rootInteger.getLeftChild().addLeftChild(3);
        assertThat(rootInteger.getLeftChild().getLeftChild().getKey(), is(3));
    }

    @Test
    public void addChildren() {
        assertThat(rootInteger.getLeftChild().getChildren(),
                is(new Pair<BinaryNode<Integer>, BinaryNode<Integer>>(null, null)));
        rootInteger.getLeftChild().addChildren(3, 4);
        assertThat(rootInteger.getLeftChild().getLeftChild().getKey(), is(3));
        assertThat(rootInteger.getLeftChild().getRightChild().getKey(), is(4));
    }

    @Test
    public void removeChild() {
        BinaryNode<Integer> leftRootIntegerChild = rootInteger.getLeftChild();
        assertThat(leftRootIntegerChild.getParent(), is(notNullValue()));
        assertThat(rootInteger.getLeftChild(), is(notNullValue()));
        rootInteger.removeLeftChild();
        assertThat(leftRootIntegerChild.getParent(), is(nullValue()));
        assertThat(rootInteger.getLeftChild(), is(nullValue()));
    }

    @Test
    public void removeChildren() {
        assertThat(rootInteger.getLeftChild(), is(notNullValue()));
        assertThat(rootInteger.getLeftChild(), is(notNullValue()));
        rootInteger.removeChildren();
        assertThat(rootInteger.getLeftChild(), is(nullValue()));
        assertThat(rootInteger.getLeftChild(), is(nullValue()));
    }

    @Test
    public void removeParent() {
        BinaryNode<Integer> leftRootIntegerChild = rootInteger.getLeftChild();
        assertThat(leftRootIntegerChild.getParent(), is(notNullValue()));
        assertThat(rootInteger.getLeftChild(), is(notNullValue()));
        leftRootIntegerChild.removeParent();
        assertThat(leftRootIntegerChild.getParent(), is(nullValue()));
        assertThat(rootInteger.getLeftChild(), is(nullValue()));
    }

    @Test
    public void getChildren() {
        Pair<BinaryNode<Integer>, BinaryNode<Integer>> pair =
                new Pair<>(rootInteger.getLeftChild(),
                        rootInteger.getRightChild());
        assertThat(rootInteger.getChildren(), is(pair));
    }

}
