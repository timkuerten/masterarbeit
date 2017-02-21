package datastructure;

import org.junit.Before;

public class DSUnsortedTest extends AbstractDataStructureTest {

    @Before
    public void setUp() {
        //create ds
        ds = new DSUnsorted(schema, thirdPartyIDs);

        //create profiles and add them to ds
        createExampleProfilesAndAddThem();
    }

}