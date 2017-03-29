package datastructure;

import org.junit.Before;

public class KdProfileTreeIterativeTest extends AbstractKdProfileTreeTest {

    @Before
    public void setUp() {
        kdProfileTree = new KdProfileTreeIterative(thirdPartyIDs);
        fillKdTree();
    }

}
