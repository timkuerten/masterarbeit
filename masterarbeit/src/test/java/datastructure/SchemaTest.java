package datastructure;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SchemaTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void createNotAllowedSchema() throws Exception {
        Set<String> schema = new HashSet<>();
        schema.addAll(Arrays.asList("Name", "Adresse", "Stadt", "Alter"));
        Set<String> thirdPartyIDs = new HashSet<>();
        thirdPartyIDs.add("Fluss");
        thrown.expect(Exception.class);
        Schema s = new Schema(schema, thirdPartyIDs);
    }
}