package datastructure.Trees;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class NodeTest {

    private Node<String> root;
    private Set<Node<String>> childrenOfRoot = new HashSet<>();

    @Before
    public void setUp() {
        root = new Node<>("root");
        addTenChildren();
    }

    private void addTenChildren() {
        if (root == null) {
            throw new NullPointerException("root is null");
        }

        childrenOfRoot.addAll(root.addChildren(
                new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j"))));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    // constructor and key tests

    @Test
    public void createNodeWithData() {
        Node<String> newNode = new Node<>("Hello World!");
        assertThat(newNode.getKey(), is("Hello World!"));
    }

    @Test
    public void createNodeWithDataAndParent() {
        Node<String> newNode = new Node<>("Hello World!", root);
        assertThat(newNode.getKey(), is("Hello World!"));
        assertThat(newNode.getParent(), is(root));
    }

    @Test
    public void createNodeWithNullData() {
        thrown.expect(NullPointerException.class);
        Node<String> nullNode = new Node<>(null);
    }

    @Test
    public void createNodeWithDataAndNullParent() {
        thrown.expect(NullPointerException.class);
        Node<String> newNode = new Node<>("Hello World!", null);
    }

    @Test
    public void createNodeWithNullDataAndParent() {
        thrown.expect(NullPointerException.class);
        Node<String> newNode = new Node<>(null, root);
    }

    @Test
    public void getData() {
        assertThat(root.getKey(), is("root"));
    }

    // parent tests

    @Test
    public void getParent() {
        assertThat(childrenOfRoot.iterator().next().getParent(), is(root));
    }

    @Test
    public void getParentOfRoot() {
        assertThat(root.getParent(), is(nullValue()));
    }

    @Test
    public void removeExistentParent() {
        Node<String> oneChildOfRoot = childrenOfRoot.iterator().next();
        assertThat(oneChildOfRoot.getParent(), is(root));
        assertThat(root.getChildren().contains(oneChildOfRoot), is(true));
        oneChildOfRoot.removeParent();
        assertThat(oneChildOfRoot.getParent(), is(nullValue()));
        assertThat(root.getChildren().contains(oneChildOfRoot), is(false));
    }

    @Test
    public void removeNonExistentParent() {
        thrown.expect(NullPointerException.class);
        root.removeParent();
    }

    // children tests

    @Test
    public void childrenCount() {
        assertThat(root.getChildren().size(), is(10));
    }

    @Test
    public void getChildren() {
        assertThat(root.getChildren().containsAll(childrenOfRoot), is(true));
    }

    @Test
    public void getChildrenOfLeaf() {
        assertThat(childrenOfRoot.iterator().next().getChildren().isEmpty(), is(true));
    }

    @Test
    public void hasChild() {
        assertThat(root.hasChild(childrenOfRoot.iterator().next()), is(true));
    }

    @Test
    public void hasNotChild() {
        Node<String> newNode = new Node<>("Hello World!");
        assertThat(root.hasChild(newNode), is(false));
    }

    @Test
    public void hasNullChild() {
        thrown.expect(NullPointerException.class);
        root.hasChild(null);
    }

    @Test
    public void addChildAsData() {
        Node<String> newNode = root.addChild("newNode");
        assertThat(newNode.getParent(), is(root));
        assertThat(root.getChildren().contains(newNode), is(true));
    }

    @Test
    public void addChildAsNode() {
        Node<String> newNode = new Node<>("newNode");
        root.addChild(newNode);
        assertThat(newNode.getParent(), is(root));
        assertThat(root.getChildren().contains(newNode), is(true));
    }

    @Test
    public void addChildrenAsData() {
        Collection<Node<String>> newNodes =
                root.addChildren(new ArrayList<String>(Arrays.asList("newNode1", "newNode2", "newNode3")));
        assertThat(newNodes.iterator().next().getParent(), is(root));
        assertThat(root.getChildren().containsAll(newNodes), is(true));
    }

    @Test
    public void addChildrenAsNodes() {
        Collection<Node<String>> newNodes = new ArrayList<>(
                Arrays.asList(new Node<>("newNode1"), new Node<>("newNode2"), new Node<>("newNode3")));
        root.addChildren(newNodes);
        assertThat(newNodes.iterator().next().getParent(), is(root));
        assertThat(root.getChildren().containsAll(newNodes), is(true));
    }

    @Test
    public void removeExistentChild() {
        Node<String> oneChildOfRoot = childrenOfRoot.iterator().next();
        assertThat(oneChildOfRoot.getParent(), is(root));
        assertThat(root.getChildren().contains(oneChildOfRoot), is(true));
        root.removeChild(oneChildOfRoot);
        assertThat(oneChildOfRoot.getParent(), is(nullValue()));
        assertThat(root.getChildren().contains(oneChildOfRoot), is(false));
    }

    @Test
    public void removeNonExistentChild() {
        Node<String> newNode = new Node<>("newNode");
        thrown.expect(RuntimeException.class);
        root.removeChild(newNode);
    }

    @Test
    public void removeNullChild() {
        thrown.expect(NullPointerException.class);
        root.removeChild(null);
    }

    @Test
    public void removeAllChildrenOfRoot() {
        assertThat(root.getChildren().containsAll(childrenOfRoot), is(true));
        root.removeAllChildren();
        assertThat(root.getChildren().isEmpty(), is(true));
    }

    @Test
    public void removeAllChildrenOfLeaf() {
        Node<String> oneChildOfRoot = childrenOfRoot.iterator().next();
        oneChildOfRoot.removeAllChildren();
        assertThat(oneChildOfRoot.getChildren().isEmpty(), is(true));
    }

    @Test
    public void removeExistentChildren() {
        Set<Node<String>> nodes = new HashSet<>(Arrays.asList(childrenOfRoot.iterator().next(), childrenOfRoot.iterator().next(), childrenOfRoot.iterator().next()));
        assertThat(root.getChildren().containsAll(nodes), is(true));
        root.removeChildren(nodes);
        assertThat(Collections.disjoint(root.getChildren(), nodes), is(true));
    }

    @Test
    public void removeNonExistentChildren() {
        Collection<Node<String>> newNodes = new ArrayList<>(
                Arrays.asList(new Node<>("newNode1"), new Node<>("newNode2"), new Node<>("newNode3")));
        thrown.expect(RuntimeException.class);
        root.removeChildren(newNodes);
    }

    @Test
    public void removeNullChildren() {
        thrown.expect(NullPointerException.class);
        root.removeChildren(null);
    }

    // root and leaf tests

    @Test
    public void isRoot() {
        assertThat(root.isRoot(), is(true));
        assertThat(childrenOfRoot.iterator().next().isRoot(), is(false));
    }

    @Test
    public void isLeaf() {
        assertThat(root.isLeaf(), is(false));
        assertThat(childrenOfRoot.iterator().next().isLeaf(), is(true));
    }

}
