package datastructure;

import java.util.*;

public class Schema {

    private Set<String> schema = new HashSet<>();
    private Set<String> thirdPartyIDs = new HashSet<>();

    /**
     * constructor to create schema and third-party-IDs
     *
     * @param schema schema of profiles
     * @param thirdPartyIDs third-party-IDs in schema
     * @throws Exception third-party-IDs must be contained in schema
     */
    public Schema(Set<String> schema, Set<String> thirdPartyIDs) throws Exception {
        if (schema.containsAll(thirdPartyIDs)) {
            this.schema = schema;
            this.thirdPartyIDs = thirdPartyIDs;
        } else {
            throw new Exception("thirdPartyIDs " + thirdPartyIDs + " are not in schema " + schema);
        }
    }

    /**
     *
     * @return current schema
     */
    public Set<String> getSchema() {
        return Collections.unmodifiableSet(this.schema);
    }

    /**
     *
     * @return current third-party-IDs
     */
    public Set<String> getThirdPartyIDs() {
        return Collections.unmodifiableSet(this.thirdPartyIDs);
    }

    /**
     * set new schema and third-party-IDs
     *
     * @param schema schema of profiles
     * @param thirdPartyIDs third-party-IDs in schema
     * @return if third-party-IDs contained in schema
     */
    public boolean update(Set<String> schema, Set<String> thirdPartyIDs) {
        if (schema.containsAll(thirdPartyIDs)) {
            this.schema = schema;
            this.thirdPartyIDs = thirdPartyIDs;
            return true;
        } else {
            return false;
        }
    }

}
