package datastructure;

import org.junit.Before;

public class DSKdTreeNullTest extends AbstractDataStructureTest {

    @Before
    public void setUp() {
        //create ds
        ds = new DSKdTreeNull(schema, thirdPartyIDs);

        //create profiles and add them to ds
        createExampleProfilesAndAddThem();
    }

}
