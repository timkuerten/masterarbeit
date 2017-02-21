package datastructure;

import org.junit.Before;

public class DSHashMapTest extends AbstractDataStructureTest {

    @Before
    public void setUp() {
        //create ds
        ds = new DSHashMap(schema, thirdPartyIDs);

        //create profiles and add them to ds
        createExampleProfilesAndAddThem();
    }

}
