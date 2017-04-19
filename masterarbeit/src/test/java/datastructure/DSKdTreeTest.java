package datastructure;

import org.junit.Before;

public class DSKdTreeTest extends AbstractDataStructureTest {

    @Before
    public void setUp() {
        //create ds
        ds = new DSKdTree(schema, thirdPartyIDs);

        //create profiles and add them to ds
        createExampleProfilesAndAddThem();
    }

}
