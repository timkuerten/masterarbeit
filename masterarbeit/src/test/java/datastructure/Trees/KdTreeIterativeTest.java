package datastructure.Trees;

import org.junit.Before;

public class KdTreeIterativeTest extends AbstractKdTreeTest {

    @Before
    public void setUp() {
        kdProfileTree = new KdTreeIterative<>(thirdPartyIDs.size());
        fillKdTree();
    }

}
