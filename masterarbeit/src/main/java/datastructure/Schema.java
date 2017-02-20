package datastructure;

import exception.SchemaException;

import java.util.*;

public class Schema {

    private Set<String> schema = new HashSet<>();
    private Set<String> thirdPartyIDs = new HashSet<>();

    /**
     * constructor to create schema and third-party-IDs
     *
     * @param schema        schema of profiles
     * @param thirdPartyIDs third-party-IDs in schema
     * @throws Exception third-party-IDs must be contained in schema
     */
    public Schema(Set<String> schema, Set<String> thirdPartyIDs) throws SchemaException {
        if (update(schema, thirdPartyIDs) == false) { //if it is true, schema and thirdPartyIDs get updated
            throw new SchemaException(schema, thirdPartyIDs);
        }
    }

    /**
     * Returns a unmodifiableSet which contain current schema.
     *
     * @return current unmodifiable schema
     */
    public Set<String> getSchema() {
        //use unmodifiableSet to prevent manipulation
        return Collections.unmodifiableSet(this.schema);
    }

    /**
     * Returns an unmodifiableSet which contains current third-party-IDs.
     *
     * @return current unmodifiable third-party-IDs
     */
    public Set<String> getThirdPartyIDs() {
        //use unmodifiableSet to prevent manipulation
        return Collections.unmodifiableSet(this.thirdPartyIDs);
    }

    /**
     * Sets new schema and third-party-IDs. It checks if third-party-IDs contained in schema.
     *
     * @param schema        schema of profiles
     * @param thirdPartyIDs third-party-IDs in schema
     * @return if third-party-IDs are contained in schema
     */
    protected boolean update(Set<String> schema, Set<String> thirdPartyIDs) {
        if (schema.containsAll(thirdPartyIDs)) {
            //delete old schema and thirdPartyIDs and set new one
            this.schema.clear();
            this.thirdPartyIDs.clear();
            //use addAll to prevent manipulation
            this.schema.addAll(schema);
            this.thirdPartyIDs.addAll(thirdPartyIDs);
            return true;
        } else {
            //if schema doesn't contain thirdPartyIDs don't change it
            return false;
        }
    }

}
