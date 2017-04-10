package datastructure.Trees;

import org.junit.Before;

public class KdRecursiceTest extends AbstractKdTreeTest {

    @Before
    public void setUp() {
        kdProfileTree = new KdTreeRecursive<>(thirdPartyIDs.size());
        fillKdTree();
    }

}
