package datastructure;

import org.junit.Before;

public class KdProfileTreeRecursiveTest extends AbstractKdProfileTreeTest {

    @Before
    public void setUp() {
        kdProfileTree = new KdProfileTreeRecursive(thirdPartyIDs);
        fillKdTree();
    }

}
