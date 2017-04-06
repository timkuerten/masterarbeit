package datastructure;

import org.junit.Before;

public class KdProfileTreeRecursiveGenericTest extends AbstractKdProfileTreeTest {

    @Before
    public void setUp() {
        kdProfileTree = new KdProfileTreeRecursive(thirdPartyIDs);
        fillKdTree();
    }

}
