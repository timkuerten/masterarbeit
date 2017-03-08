package datastructure;

import org.junit.Before;

public class DSSortedArrayTest extends AbstractDataStructureTest {

    @Before
    public void setUp() {
        //create ds
        ds = new DSSortedArray(schema, thirdPartyIDs);

        //create profiles and add them to ds
        createExampleProfilesAndAddThem();
    }

}
