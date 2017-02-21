package datastructure;

import org.junit.*;

public class DSUnsortedTest extends AbstractDataStructureTest {

    @Before
    public void setUp() {
        //create schema
        setUpSchema();

        //create ds
        ds = new DSUnsorted(schema, thirdPartyIDs);

        //create profiles and add them to ds
        createExampleProfilesAndAddThem();
    }

}
