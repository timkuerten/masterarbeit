package datastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NodeTest {

    private Node<Integer> rootInteger;
    private Node<Integer> oneLeafInteger;
    private Node<String> rootString;
    private Set<Node<String>> childrenOfRootString = new HashSet<>();

    @Before
    public void setUp() {
        rootInteger = new Node<>(0);
        rootString = new Node<>("a");
        addTenChildren();
    }

    private void addTenChildren() {
        if (rootInteger == null) {
            throw new NullPointerException("rootInteger is null");
        } else if (rootString == null) {
            throw new NullPointerException("rootString is null");
        }

        for (int i = 1; i < 10; i++) {
            rootInteger.addChild(i);
        }
        oneLeafInteger = rootInteger.addChild(10);
        childrenOfRootString.addAll(rootString.addChildren(
                new ArrayList<String>(Arrays.asList("b", "c", "d", "e", "f", "g", "h", "i", "j", "k"))));
    }

    @Test
    public void data() {
        assertThat(rootInteger.getData(), is(0));
        assertThat(rootString.getData(), is("a"));
    }

    @Test
    public void childrenCount() {
        assertThat(rootInteger.getChildrenCount(), is(10));
    }

    @Test
    public void isRoot() {
        assertThat(rootInteger.isRoot(), is(true));
        assertThat(oneLeafInteger.isRoot(), is(false));
    }

    @Test
    public void isLeaf() {
        assertThat(rootInteger.isLeaf(), is(false));
        assertThat(oneLeafInteger.isLeaf(), is(true));
    }

    @Test
    public void addChild() {
        assertThat(rootInteger.getChildrenCount(), is(10));
        Node<Integer> newNode = new Node<>(11);
        rootInteger.addChild(newNode);
        assertThat(rootInteger.getChildrenCount(), is(11));
        assertThat(rootInteger.hasChild(newNode), is(true));
        rootInteger.addChild(12);
        assertThat(rootInteger.getChildrenCount(), is(12));
    }

    @Test
    public void addChildren() {
        assertThat(rootInteger.getChildrenCount(), is(10));
        rootInteger.addChildren(new ArrayList<Node<Integer>>(
                Arrays.asList(new Node<>(11), new Node<>(12), new Node<>(13))));
        assertThat(rootInteger.getChildrenCount(), is(13));
        rootInteger.addChildren(new ArrayList<Integer>(Arrays.asList(14, 15, 16)));
        assertThat(rootInteger.getChildrenCount(), is(16));
    }

    @Test
    public void removeChild() {
        assertThat(oneLeafInteger.getParent(), is(rootInteger));
        assertThat(rootInteger.hasChild(oneLeafInteger), is(true));
        rootInteger.removeChild(oneLeafInteger);
        assertThat(oneLeafInteger.getParent(), is(nullValue()));
        assertThat(rootInteger.hasChild(oneLeafInteger), is(false));
    }

    @Test
    public void removeChildren() {
        assertThat(rootString.getChildrenCount(), is(10));
        rootString.removeChildren(childrenOfRootString);
        assertThat(rootString.getChildrenCount(), is(0));
    }

    @Test
    public void setParent() {
        assertThat(rootInteger.getChildrenCount(), is(10));
        Node<Integer> eleven = new Node<>(11, rootInteger);
        assertThat(rootInteger.getChildrenCount(), is(11));
        assertThat(eleven.getParent(), is(rootInteger));
        assertThat(rootInteger.hasChild(eleven), is(true));
    }

    @Test
    public void removeParent() {
        assertThat(oneLeafInteger.getParent(), is(rootInteger));
        assertThat(rootInteger.hasChild(oneLeafInteger), is(true));
        oneLeafInteger.removeParent();
        assertThat(oneLeafInteger.getParent(), is(nullValue()));
        assertThat(rootInteger.hasChild(oneLeafInteger), is(false));
    }

    @Test
    public void getChildren() {
        assertThat(rootString.getChildren().containsAll(childrenOfRootString), is(true));
        assertThat(childrenOfRootString.containsAll(rootString.getChildren()), is(true));
    }

}
